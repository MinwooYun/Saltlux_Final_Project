import pandas as pd
import numpy as np

from collections import Counter

import warnings
warnings.filterwarnings('ignore')


'''
    - 주어진 텍스트에 관련 점수를 도출하는 클래스

    - param: keyword_list

    - 인용논문: https://scienceon.kisti.re.kr/commons/util/originalView.do?cn=JAKO200910348031067&oCn=JAKO200910348031067&dbt=JAKO&journal=NJOU00294759
'''
class TextScore():
    def __init__(self, keyword_list) -> None:

        self.keyword_list = keyword_list

        print('check 4')
        
        # 전체 문서 키워드 리스트 1차원 변환
        keyword_1d_list = sum(keyword_list, [])

        print('check 5')

        # 전체 문서에 대한 키워드 빈도 수를 담아낸 딕셔너리
        self.btf_dict = dict(Counter(keyword_1d_list))

        print('check 6')

        # 문서집합 내에서 출현한 모든 단어들의 btf 값 중 최댓값
        self.max_btf = max(self.btf_dict.values())

        print('check 7')

        # 개별 문서에 대한 키워드 빈도 수 담아낸 딕셔너리
        self.ntf2_list = [dict(Counter(keyword)) for keyword in keyword_list]

        print('check 8')

        # 각 문서의 모든 단어에 대한 발생 빈도
        self.all_tf_list = [sum(tf_dic.values()) for tf_dic in self.ntf2_list]

        print('check 9')


    '''
        - btf(basic term frequency) 점수 도출 (전체 문서에서 해당 키워드가 나타나는 총 횟수)

        - param: keyword (찾고자 하는 키워드)

        - return: btf (해당 키워드의 btf 점수)
    '''
    def get_btf(self, keyword:str) -> int:

        self.btf = self.btf_dict.get(keyword)
        if self.btf == None:
            return 0

        return self.btf

    
    '''
        - ntf1 점수 도출 (해당 키워드의 btf 값 / 문서집합 내에서 출현한 모든 단어들의 btf 값 중 최댓값)

        - param: keyword (찾고자 하는 키워드)

        - return: ntf1 (해당 키워드의 ntf1 점수)
    '''
    def get_ntf1(self) -> float:
        
        # 해당 키워드의 btf 값
        keyword_btf = self.btf

        ntf1 = keyword_btf / self.max_btf
        ntf1 = np.round(ntf1, 3)

        return ntf1


    '''
        - ntf2 점수 도출 ((해당 단어의 문서에서의 발생 빈도 / 각 문서의 모든 단어에 대한 발생 빈도)의 합)

        - param: target (찾고자 하는 키워드)

        - return: ntf2 (해당 키워드의 ntf2 점수)
    '''
    def get_ntf2(self, keyword:str) -> float:
        
        # 해당 단어의 문서에서의 발생 빈도
        keyword_tf_list = [tf_dic.get(keyword) for tf_dic in self.ntf2_list]
        keyword_tf_list = [tf if tf != None else 0 for tf in keyword_tf_list]

        ntf2_list = np.array(keyword_tf_list) / np.array(self.all_tf_list)

        ntf2 = sum(ntf2_list)
        ntf2 = np.round(ntf2, 3)

        return ntf2


    '''
        - idf 점수 도출 (df(해당 키워드가 나타나는 문서의 수)의 역수 값)

        - param: keyword (찾고자 하는 키워드)

        - return: idf (해당 키워드의 idf 점수)
    '''
    def get_idf(self, keyword:str) -> float:

        # 총 문서 개수
        doc_num = len(self.keyword_list)

        df = len([keyword for keywords in self.keyword_list if keyword in keywords])

        idf = np.log((doc_num + 1) / (df + 1)) + 1
        idf = np.round(idf, 3)

        return idf


def normalize_tf(tf):
    return np.log(tf) + 1.0


def get_score_df(df:pd.DataFrame) -> pd.DataFrame:
    date_list = df['news_date'].to_list()
    noun_list = df['nouns'].to_list()

    print('check 1')

    # 각 문서에서 출현한 순서대로 나열된 명사 리스트 (중복 허용)
    keyword_list = [keyword.split(' ') for keyword in noun_list if type(keyword) == str]

    # date와 해당 date 출현 명사들 매핑
    date_keyword_list = []
    for date_keywords in list(zip(date_list, keyword_list)):
        # date에서 초, 분 단위는 제거
        date = date_keywords[0][:-6]
        keywords = date_keywords[1]
        date_keyword_by_doc = [(str(date), keyword) for keyword in keywords]
        date_keyword_list.append(date_keyword_by_doc)

    print('check 2')

    # 각 날짜마다 존재하는 명사 리스트 (중복 허용 x)
    target_date_keyword_list = list(set(sum(date_keyword_list, [])))
    target_date_keyword_list = list(set(target_date_keyword_list))

    print('check 3')

    # textscore 클래스 객체 생성
    text_score = TextScore(keyword_list)

    print('check 10')

    # 데이터프레임에 들어갈 리스트 목록
    date = []; keyword = []; btf = []; ntf1 = []; ntf2 = []; idf = []; tf_idf = []

    # 전체 문서에 대하여 탐색 키워드들의 점수 도출
    for date_keyword in target_date_keyword_list:
        date_time = date_keyword[0]
        target_keyword = date_keyword[1]

        btf_score = text_score.get_btf(target_keyword)
        ntf1_score = text_score.get_ntf1()
        ntf2_score = text_score.get_ntf2(target_keyword)
        # idf_score = text_score.get_idf(target_keyword)
        # tf_idf_score = np.round((ntf1_score * idf_score), 3)

        print(date_time, target_keyword, btf_score, ntf1_score, ntf2_score)

        date.append(date_time)
        keyword.append(target_keyword)
        btf.append(btf_score)
        ntf1.append(ntf1_score)
        ntf2.append(ntf2_score)
        # idf.append(idf_score)
        # tf_idf.append(tf_idf_score)

    # score_df 생성
    score_df = pd.DataFrame(
        {
            'date': date,
            'keyword': keyword,
            'btf': btf,
            'ntf1': ntf1,
            'ntf2': ntf2
            # 'idf': idf,
            # 'tf-idf': tf_idf
        }
    )

    return score_df


if __name__ == '__main__':
    from time import time
    begin = time()

    df = pd.read_csv(r'csv_files\20210930preprocessed_data.csv', index_col=0)[:3]
    score_df = get_score_df(df)
    print(score_df)

    end = time()

    print(end-begin)