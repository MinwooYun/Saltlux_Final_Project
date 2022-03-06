import numpy as np
import pandas as pd

import numba

from konlpy.tag import Mecab

from sklearn.feature_extraction.text import CountVectorizer

import warnings
warnings.filterwarnings('ignore')

'''
    - 주어진 텍스트에 관련 점수를 도출하는 클래스
    - param: None
'''
class TextScore():
    def __init__(self) -> None:
        pass


    '''
        - mecab 형태소 분석기를 사용하여 명사 태깅된 키워드들을 추출하는 함수
        
        - param: text (전처리 모듈에서 전처리를 수행한 (제목 + 본문 text), 마지막 문장에서 기자 소개하는 문장은 제거 요청, 마지막 마침표도 제거)
                 divide_by_sentence 
                 (default: False => 원문 링크 기사 전체에 대한 키워드 추출, optional: True => 문장 별 키워드 추출)

        - return: keyword_list
                 (1차원 또는 2차원 리스트 return)
    '''
    @numba.jit
    def get_keywords(self, text:str, divide_by_sentence=False) -> list:
        mecab = Mecab(dicpath=r"C:\mecab\mecab-ko-dic")

        # 전체 text 키워드 추출
        if divide_by_sentence == False:
            text_pos_list = mecab.pos(text)
            keyword_list = [i[0] for i in text_pos_list if i[1] == 'NNP' or i[1] == 'NNG']
            return keyword_list
        
        # 문장별 text 키워드 추출
        elif divide_by_sentence == True:
            text_list = text.split('.')
            keyword_list = []

            for text in text_list:
                text_pos_list = mecab.pos(text)
                keyword_sentence_list = [i[0] for i in text_pos_list if i[1] == 'NNP' or i[1] == 'NNG']
                keyword_list.append(keyword_sentence_list)

            return keyword_list

        else:
            return 'divide_by_sentence needs to be allocated only True or False!'


    '''
        - tdm metrix 도출 (해당 원문 링크에서 문장별로 키워드가 나타나는 횟수 도출)

        - param: keywords(형태소 분석기로 돌린 키워드 리스트 => get_keywords(divide_by_sentence == True))

        - return: tdm_df (문장 별 키워드 출현 빈도 수 데이터프레임)
                  현재(한 문장에 하나의 행)
                  향후(한 문서에 하나의 행)
    '''
    def get_tdm(self, keywords:list) -> pd.DataFrame:
        
        # 데이터 수집 완료 후 원문 데이터 돌릴 시 해당 코드 수정 필요
        corpus = [(' ').join(keyword) for keyword in keywords]

        vector = CountVectorizer()

        tdm_array = vector.fit_transform(corpus).toarray()
        tf_dic = vector.vocabulary_

        tf_dic_sorted = dict(sorted(tf_dic.items(), key=lambda item: item[1]))
        self.tdm_df = pd.DataFrame(tdm_array, columns=tf_dic_sorted.keys())
        
        return self.tdm_df


    '''
        - tf 점수 도출 (해당 원문 링크에서 키워드가 나타나는 총 횟수) aka BTF (basic term frequency)

        - param: target (찾고자 하는 키워드)

        - return: tf (해당 키워드의 tf 점수)
    '''
    def get_tf(self, target:str) -> int:
        try:
            return self.tdm_df[target].sum()
        except:
            return 0


    '''
        - 인용논문: https://scienceon.kisti.re.kr/commons/util/originalView.do?cn=JAKO200910348031067&oCn=JAKO200910348031067&dbt=JAKO&journal=NJOU00294759

        - ntf1 점수 도출 (해당 키워드의 btf 값 / 문서집합 내에서 출현한 모든 단어들의 btf 값 중 최댓값)

        - param: target (찾고자 하는 키워드)

        - return: ntf1 (해당 키워드의 ntf1 점수)
    '''
    def get_ntf1(self, target:str) -> float:
        tdm_col = list(self.tdm_df.columns)

        # 문서 집합 내에 출현한 모든 단어들의 btf 값 리스트
        btf_list = []
        for keyword in tdm_col:
            btf_list.append((keyword, self.get_tf(keyword)))

        # 해당 키워드의 btf 값
        try:
            btf = [btf[1] for btf in btf_list if target == btf[0]][0]

            # 문서집합 내에서 출현한 모든 단어들의 btf 값 중 최댓값
            max_btf = max([btf[1] for btf in btf_list])

            ntf1 = btf / max_btf

            return np.round(ntf1, 3)

        # 해당 키워드가 없을 경우
        except:
            ntf1 = 0

            return ntf1

    
    '''
        - ntf2 점수 도출 ((해당 단어의 문서에서의 발생 빈도 / 각 문서의 모든 단어에 대한 발생 빈도)의 합)

        - param: target (찾고자 하는 키워드)

        - return: ntf2 (해당 키워드의 ntf2 점수)
    '''
    def get_ntf2(self, target:str) -> float:
        tdm_col = list(self.tdm_df.columns)
        
        # 해당 단어의 문서에서의 발생 빈도 리스트
        try:
            target_tf = []
            for idx in range(len(self.tdm_df)):
                target_tf_list = []

                for keyword in tdm_col:
                    # 해당 단어의 문서에서의 발생 빈도
                    if target == keyword:
                        target_tf_list.append(self.tdm_df.loc[idx, keyword])

                target_tf.extend(target_tf_list)

            # 각 문서의 모든 단어에 대한 발생 빈도 리스트
            all_tf = self.tdm_df.sum(axis=1).to_list()

            # 문서 또는 문장의 전체 단어 개수가 0인 경우는 제외함.
            total_tf = list(zip(target_tf, all_tf))
            new_target_tf = []
            new_all_tf = []
            for idx, val in enumerate(total_tf):
                target_val = val[0]
                all_val = val[1]
                if all_val != 0:
                    new_target_tf.append(target_val)
                    new_all_tf.append(all_val)

            # target_tf / all_tf
            ntf2_list = np.array(new_target_tf) / np.array(new_all_tf)

            ntf2 = ntf2_list.sum()
            ntf2 = np.round(ntf2, 3)
            ntf2 = float(str(ntf2).rstrip('.0'))

            return ntf2

        # 해당 키워드가 없을 경우
        except:
            ntf2 = 0

            return ntf2
        
    
    '''
        - idf 점수 도출 (df의 역수 값)

        - param: None

        - return: idf (각 키워드들의 idf 점수)
    '''
    def get_idf(self) -> pd.Series:
        num = len(self.tdm_df)
        df = self.tdm_df.astype(bool).sum(axis=0)
        idf = np.log((num + 1) / (df + 1)) + 1
        
        return idf


    '''
        - tf-idf 점수 도출, original tf 버전으로 적용된 것 (btf)

        - param: idf (각 키워드들의 idf 점수)
                 
        - return: total_tf_idf (문장별 모든 키워드 각각의 tf-idf 점수 리스트, 2차원 리스트로 반환)
    '''
    def get_tf_idf(self, idf:pd.Series) -> pd.DataFrame:
        try:
            tf_idf = self.tdm_df * idf

            # tf-idf 정규화
            tf_idf = tf_idf / np.linalg.norm(tf_idf, axis=1, keepdims=True)

            return tf_idf

        except:
            return 'idf must be a Series type'

    
    '''
        - 문서별 해당 키워드에 대한 tf-idf 점수 도출
          향후 수집 완료 후 사용자가 검색어 키워드 입력 시 문서 콘텐츠 별 tf-idf 점수 도출 => 연관도순 정렬 옵션으로 사용 가능

        - param: target (찾고자 하는 키워드),
                 tf_opt (default: tf, optional: ntf1, ntf2),
                 normalize (default: False, optional: True)
                 
        - return: tf_idf (전체 또는 단일 문서에서 해당 키워드에 대한 tf-idf 점수)
    '''
    def get_tf_idf2(self, target, tf_opt='tf', normalize=False) -> float:
        try:
            # 특정 키워드에 대한 idf 도출
            idf = self.get_idf().to_dict()
            target_idf = idf.get(target)

            tf = 0
            if tf_opt == 'tf':
                tf = self.get_tf(target)
            elif tf_opt == 'ntf1':
                tf = self.get_ntf1(target)
            elif tf_opt == 'ntf2':
                tf = self.get_ntf2(target)

            if normalize == True:
                tf = np.log(tf) + 1.0

            tf_idf = tf * target_idf
            tf_idf = np.round(tf_idf, 3)

            return tf_idf

        # 해당 키워드가 없을 경우
        except:
            tf_idf = 0

            return tf_idf


'''
    - 주어진 키워드(검색어)에 대하여 전체 또는 개별 문서에 대한 텍스트 점수 딕셔너리 도출

    - param: text_list (텍스트 리스트, 제목 + 본문),
                keyword (찾고자 하는 키워드 또는 검색어),
                scale_opt (default: 'all' => 전체 문서, optional: 'by_doc' => 개별 문서)
                tf_opt (default: tf, optional: ntf1, ntf2),
                normalize (default: False, optional: True)
                
    - return: text_score_dict (전체 또는 단일 문서에서 해당 키워드에 대한 텍스트 점수 딕셔너리)
'''
def get_text_score(text_list:list, keyword:str, scale_opt='all', tf_opt='tf', normalize=False) -> dict:
    text_score_dict = {}
    
    if scale_opt == 'all':
        keywords_list = []
        for text in text_list:
            text_score = TextScore()
            keywords_list.append(text_score.get_keywords(text))

        # tdm_df 객체 생성
        text_score.get_tdm(keywords_list)

        # 전체 문서에 대한 해당 키워드의 점수 딕셔너리 도출
        text_score_dict['tf'] = text_score.get_tf(keyword)
        text_score_dict['ntf1'] = text_score.get_ntf1(keyword)
        text_score_dict['ntf2'] = text_score.get_ntf2(keyword)
        text_score_dict['tf-idf'] = text_score.get_tf_idf2(keyword, tf_opt=tf_opt, normalize=normalize)

        return text_score_dict

    elif scale_opt == 'by_doc':
        keywords_list = []
        for text in text_list:
            text_score = TextScore()
            keywords_list.append(text_score.get_keywords(text, divide_by_sentence=True))

        for idx, keywords in enumerate(keywords_list):
            single_text_score_dict = {}

            # tdm_df 객체 생성
            text_score.get_tdm(keywords)

            # 개별 문서에 대한 해당 키워드의 점수 딕셔너리 도출
            single_text_score_dict['tf'] = text_score.get_tf(keyword)
            single_text_score_dict['ntf1'] = text_score.get_ntf1(keyword)
            single_text_score_dict['ntf2'] = text_score.get_ntf2(keyword)
            single_text_score_dict['tf-idf'] = text_score.get_tf_idf2(keyword, tf_opt=tf_opt, normalize=normalize)

            text_score_dict[idx] = single_text_score_dict

        return text_score_dict


'''
    - 분석용 전처리 데이터를 score table 데이터 (키워드, 문서번호, tf, ntf1, ntf2, tf-idf)로 변환하는 함수

    - param: df (분석용 전처리 데이터 => 문서번호, td (text + ' ' + detail), nouns (출현빈도 순서로 중복을 허용한 명사 리스트))
            
    - return: score_df

    - issue: 해당 문서에 속한 키워드들(nouns)만 문서 별로 집계
'''
def get_score_df_by_indi(df:pd.DataFrame) -> pd.DataFrame:
    # text_list
    df['td'] = df['title'] + ' ' + df['detail']
    td_list = df['td'].to_list()
    td_list = [td.strip('.') for td in td_list]
    td_dic = {td:idx for idx, td in enumerate(td_list)}

    # keyword
    keyword_list = df['nouns'].to_list()
    keyword_list = [list(set(keyword.split(' '))) for keyword in keyword_list]

    # score_df value list
    ids = []; words = []; tf = []; ntf1 = []; ntf2 = []; tf_idf = []

    for td, keywords in zip(td_list, keyword_list):
        # 문서 번호
        id = td_dic.get(td)

        # 해당 문서에 등장한 명사들의 모음 (중복제거)
        # 각 명사(키워드)가 속한 해당 문서의 점수 도출
        for keyword in keywords:
            text_score_dic = get_text_score([td], keyword, scale_opt='by_doc', tf_opt='ntf2')

            # 데이터프레임 리스트에 추가
            ids.append(id)
            words.append(keyword)

            tf.append(text_score_dic[0]['tf'])
            ntf1.append(text_score_dic[0]['ntf1'])
            ntf2.append(text_score_dic[0]['ntf2'])
            tf_idf.append(text_score_dic[0]['tf-idf'])

            print(id, keyword, text_score_dic)

    # score_df 생성
    score_df = pd.DataFrame(
        {
            'id': ids,
            'keyword': words,
            'tf': tf,
            'ntf1': ntf1,
            'ntf2': ntf2,
            'tf-idf': tf_idf
        }
    )

    # tf, ntf1, ntf2, tf-idf 값이 모두 0인 건 제외
    # condition = ~(score_df['tf'] == 0 & score_df['ntf1'] == 0 & score_df['ntf2'] == 0.0 & score_df['tf-idf'] == 0)
    # score_df = score_df[condition]

    return score_df


'''
    - 분석용 전처리 데이터를 score table 데이터 (키워드, 문서번호, tf, ntf1, ntf2, tf-idf)로 변환하는 함수

    - param: df (분석용 전처리 데이터 => 문서번호, td (text + ' ' + detail), nouns (출현빈도 순서로 중복을 허용한 명사 리스트))
             scale_opt (default: 'all' => 전체 문서, optional: 'by_doc' => 개별 문서)
            
    - return: score_df

    - issue: 전체 문서에 대한 모든 키워드들(nouns)을 문서 별로 집계
'''
def get_score_df(df:pd.DataFrame, scale_opt='all') -> pd.DataFrame:
    # text_list
    df['td'] = df['title'] + ' ' + df['detail']
    td_list = df['td'].to_list()
    td_list = [td.strip('.') for td in td_list]

    # keyword
    keyword_list = df['nouns'].to_list()
    keyword_list = [list(set(keyword.split(' '))) for keyword in keyword_list]
    keyword_list = [(idx, keyword) for idx, keywords in enumerate(keyword_list) for keyword in keywords]

    # score_df value list
    ids = []; words = []; tf = []; ntf1 = []; ntf2 = []; tf_idf = []

    # 전체 문서에 대한 모든 키워드들(nouns)을
    for value in keyword_list:
        id = value[0]
        keyword = value[1]
        
        # 문서 단위로 집계 (기존의 tf-idf 도출 방식)
        if scale_opt == 'all':
            text_score_dic = get_text_score(td_list, keyword, scale_opt=scale_opt, tf_opt='ntf2')

            # 데이터프레임 리스트에 추가
            ids.append(id)
            words.append(keyword)

            tf.append(text_score_dic['tf'])
            ntf1.append(text_score_dic['ntf1'])
            ntf2.append(text_score_dic['ntf2'])
            tf_idf.append(text_score_dic['tf-idf'])

            print(id, keyword, text_score_dic)

        # 문서 내 문장 단위로 집계
        elif scale_opt == 'by_doc':
            text_score_dic = get_text_score(td_list, keyword, scale_opt=scale_opt, tf_opt='ntf2')

            # 개별 문서에 해당 키워드에 대한 관련 점수 도출 (문서 내 문장 단위)
            for idx, value in text_score_dic.items():
                # 데이터프레임 리스트에 추가
                ids.append((id, idx))
                words.append(keyword)

                tf.append(value['tf'])
                ntf1.append(value['ntf1'])
                ntf2.append(value['ntf2'])
                tf_idf.append(value['tf-idf'])

                print(id, idx, keyword, value)


    # score_df 생성
    score_df = pd.DataFrame(
        {
            'id': ids,
            'keyword': words,
            'tf': tf,
            'ntf1': ntf1,
            'ntf2': ntf2,
            'tf-idf': tf_idf
        }
    )

    # tf, ntf1, ntf2, tf-idf 값이 모두 0인 건 제외
    # condition = ~(score_df['tf'] == 0 & score_df['ntf1'] == 0 & score_df['ntf2'] == 0.0 & score_df['tf-idf'] == 0)
    # score_df = score_df[condition]

    return score_df


if __name__ == '__main__':
    sample_text_list = ['''
                    '유엔 인권전문가 "형법으로 낙태 처벌하는 한국 우려" 유엔 인권이사회 산하 전문가들이 한국 정부의 낙태 처벌 관련 개정안에 우려를 표했다.
                    여성차별 실무위원회와 건강권 특별보고관, 여성폭력 특별보고관은 1일(현지시간) "낙태 규제를 위해 형법을 계속해 사용하는 점을 우려한다"면서 
                    "우리는 여성이 임신 중절로 절대 형사 처벌돼서는 안된다는 바를 상기하고자 한다"고 밝혔다. 또 "국제인권 규범에 따라 임신 중절을 범죄로 여기지 
                    않는 데 필요한 조처와 합법적이고 안전한 낙태 서비스에 대한 접근권을 보장하는 조처를 촉구한다"고 강조했다. 그러면서 "합법적 임신 중절에 
                    대한 접근에 있어 대기 기간, 의무 상담과 같은 의료적 필요에 근거하지 않은 차별적 장애물을 제거하라"고 권고했다. 앞서 헌법재판소는 2019년 
                    4월 낙태죄에 대해 헌법 불합치 결정을 내리면서 현행 형법은 지난해 말까지 개정돼야 한다고 주문했다. 이에 따라 정부는 지난해 10월 임신 14주 이내에서는 
                    낙태를 허용하는 내용의 형법·모자보건법 개정안을 발의했다. 하지만 국회에 계류중이라 대체 법률 미비로 낙태에 대한 형사 처벌을 규정한 형법 조항은 
                    지난해 12월31일부로 효력을 상실했다. 이보배 한경닷컴 객원기자 newsinfo@hankyung.com ⓒ 한국경제 & , 무단전재 및 재배포 금지'
                ''', 
                '''
                '이란, 솔레이마니 사망 1주기 앞두고 보복 다짐 이란 국영 IRNA 통신에 따르면 에브라힘 라이시 이란 사법부 수장은 현지 시간으로 1일 
                "솔레이마니 살해범에게 안전한 곳은 어디에도 없을 것"이라고 말했습니다. 라이시 사법부 수장은 "미군을 완전히 철수시키고 극악무도한 
                암살범을 재판에 회부하는 것이 솔레이마니 암살에 대한 가혹한 복수가 될 것"이라고 강조했습니다. 이어 "원수들은 이제 매우 힘든 삶을 살게 될 것"이라며 
                "마지막 보복을 기다리게 될 것"이라고 덧붙였습니다. 솔레이마니의 뒤를 이어 쿠드스군 사령관 직을 맡은 에스마일 가니 역시 "적의 집 안에서 그들의 범죄에 
                복수할 누군가가 나타날 것"이라며 보복을 다짐했습니다. 이어 "이란은 언제나 미국에 대응할 준비가 돼 있다"며 "미국의 장난이 쿠드스군의 저항을 막지는 못할 것"이라고 
                덧붙였습니다. 솔레이마니 전 사령관은 지난해 1월 3일 이라크 바그다드에서 미군의 무인기 공격을 받아 사망했습니다. 그는 이란-이라크 전쟁 당시 사단장으로서 혁혁한 공을 
                세워 명성을 얻은 뒤 지난 1998년 쿠드스군 총사령관에 임명됐습니다. 이란에서는 영웅 대접을 받았으며, 선출직 대통령보다 쿠드스군의 수장인 솔레이마니가 최고지도자 알리 하메네이 
                다음 '2인자 '에 가깝다고 보는 시각도 존재했습니다. 그러나 미국과 이스라엘은 쿠드스군을 테러리즘 지원의 핵심으로 여기고 그의 제거를 노려왔습니다
                '''
                ]

    # total_text_score = get_text_score(sample_text_list, '강조', tf_opt='ntf2', normalize=True)
    # print(total_text_score)

    '''
        - 문서 즉 기사 원문 링크 별 tf-idf 점수를 도출하여
        - 검색어에 해당하는 기사 콘텐츠를 연관도 즉 tf-idf 점수 순으로 주기 위해서는
        - get_keywords(divide_by_sentence=True) 로 주어
        - 기사 원문 하나 당 문장 별로 이루어진 tdm으로 tf-idf 분석이 올바르다고 생각함.
    '''
    # single_text_score = get_text_score(sample_text_list, '강조', scale_opt='by_doc', tf_opt='ntf2')
    # print(single_text_score)

    df = pd.read_csv('sample_elastic.csv', index_col=0)[:3]
    # print(df)
    # score_df = get_score_df_by_indi(df)
    # score_df.to_csv('sample_elastic_score.csv', encoding='utf-8-sig', index=False)

    score_df_all = get_score_df(df, scale_opt='all')
    # score_df_all.to_csv('sample_elastic_score_v3.csv', encoding='utf-8-sig', index=False)