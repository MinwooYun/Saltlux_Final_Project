import numpy as np
import pandas as pd

from collections import defaultdict
from collections import Counter

from itertools import islice

import sys
[sys.path.append(i) for i in ['.', '..']]

from data_preprocess.keyword_preprocess import get_preprocessed_keyword

import warnings
warnings.filterwarnings('ignore')

'''
    - 단일 문서에서 출현한 명사 리스트에 대하여 검색된 키워드를 바탕으로 연관 검색어를 도출하는 클래스
    
    - param: noun_list (기사 본문 하나의 문서에서 추출된 중복이 적용된 명사 리스트 => mecab을 이용한 형태소 분석으로 도출됨)
             bm_25_score (elastic-search index에서 받아온 키워드 점수)

    - elastic-search index로부터 해당 키워드에 대하여 점수가 높은 문서의 명사 리스트와 bm_25 점수를 받음
'''
class AssociatedWords():
    def __init__(self, noun_list:list, bm_25_score:float) -> None:
        self.noun_list = noun_list
        self.bm_25_score = bm_25_score


    '''
        - 주어진 명사 리스트에 대하여 동반 출현 빈도 matrix 도출

        - param: cnt (출현 빈도 가중치, default: 1), window_size (주변 단어 탐색 범위 사이즈, default: 3)

        - return: co_occurrence_df
    '''
    def get_co_occurrence_matrix(self, cnt=1, window_size=3) -> pd.DataFrame:
        word_dic = defaultdict(int)
        vocab = set()

        # 명사 리스트 탐색
        for idx, noun in enumerate(self.noun_list):
            vocab.add(noun)
            # 인접 단어 window_size 수 만큼 탐색
            next_noun = self.noun_list[idx+1 : idx+1+window_size]

            # 탐색된 인접단어들에 대하여 가중치만큼 출현 빈도 부여
            for t in next_noun:
                if noun == t:
                    continue
                key = tuple(sorted([t, noun]))
                word_dic[key] += cnt

        # 단어 정렬
        vocab = sorted(vocab)

        # matrix dataframe 생성
        self.co_occurrence_df = pd.DataFrame(
                                    data=np.zeros((len(vocab), len(vocab))),
                                    dtype=np.int16,
                                    index=vocab,
                                    columns=vocab
                                )

        # 앞서 구한 출현 빈도 값을 단어의 행과 열에 동시 부여
        for key, value in word_dic.items():
            self.co_occurrence_df.at[key[0], key[1]] = value
            self.co_occurrence_df.at[key[1], key[0]] = value

        return self.co_occurrence_df


    '''
        - 특정 검색어에 대하여 동반 출현 빈도 점수 높은 연관 단어 도출 (단일 문서 기준)

        - param: keyword_list (사용자가 검색한 문장 또는 단어에 대하여 뽑아낸 명사 리스트 => keyword_preprocess.py에서 정제)
                 top_size (연관 단어 개수, default: 5)

        - return: co_occurrence_words (해당 키워드에 대한 문서 내 (연관 단어, 점수) 리스트)
    '''
    def get_co_occurrence_words(self, keyword_list:list, top_size) -> list:

        related_keywords_list = []
        # 한 키워드에 대한 연관검색어 리스트 생성
        for keyword in keyword_list:
            try:
                correlated_words = self.co_occurrence_df[keyword].to_dict()
                correlated_words = {word:score for word, score in correlated_words.items() if score != 0}
                correlated_words = dict(sorted(correlated_words.items(), key=lambda x: x[1], reverse=True))

                # 연관어, 동반출현빈도 점수 추가
                co_occurrence_words = list(correlated_words.items())
                related_keywords_list.append(co_occurrence_words)

            # 검색 키워드가 동반 출현 빈도 매트릭스 행, 열에 존재하지 않을 경우
            except:
                pass

        # 키워드 별 연관단어 2차원 리스트 1차원 변환
        related_keywords_1d_list = sum(related_keywords_list, [])    
        related_keywords_1d_dic = [{related_keyword[0] : related_keyword[1]} for related_keyword in related_keywords_1d_list]
        
        # 각 키워드의 동반출현빈도 합산
        total_dic = {}
        for i in related_keywords_1d_dic:
            total_dic = Counter(total_dic) + Counter(i)

        total_dic = dict(total_dic)

        # 검색 키워드와 동일한 연관단어 삭제 => 검색결과 페이지의 대시보드에 있는 연관검색어의 컨셉과 부합하지 않기 때문
        for keyword in keyword_list:
            try:
                del total_dic[keyword]
            except:
                pass

        # 각 연관키워드에 대한 동반출현빈도 수 * bm_25_score => 연관 키워드 간 점수 차등성 주기 위함
        total_dic = {text:(score * self.bm_25_score) for text, score in total_dic.items()}

        # 점수 오름차순 정렬
        co_occurrence_words = list(sorted(total_dic.items(), key=lambda x: x[1], reverse=True))[:top_size]
        
        return co_occurrence_words


def get_total_co_occurrence(doc_info_list:list, search_text_list:list, word_top_size=5, top_size=5) -> dict:
    related_words_list = []
    # 개별 문서에 대한 연관어 및 해당 동반출현빈도 점수 도출
    for doc_info in doc_info_list:
        noun_list = doc_info['nouns'].split(' ')
        associated_word = AssociatedWords(noun_list, doc_info['scores'])
        associated_word.get_co_occurrence_matrix()
        related_words = associated_word.get_co_occurrence_words(search_text_list, top_size=word_top_size)
        related_words_list.append(related_words)

    # 문서 별 연관단어 2차원 리스트 1차원 변환
    related_words_1d_list = sum(related_words_list, [])    
    related_words_1d_dic = [{related_keyword[0] : related_keyword[1]} for related_keyword in related_words_1d_list]
    
    # 각 문서의 최종 연관키워드 및 동반출현빈도 최종 합산
    # 모든 문서에 대하여 해당 키워드의 연관검색어 도출
    total_dic = {}
    for i in related_words_1d_dic:
        total_dic = Counter(total_dic) + Counter(i)
    total_dic = dict(total_dic)

    # 점수 순 정렬
    total_dic = dict(sorted(total_dic.items(), key=lambda x: x[1], reverse=True))

    # 모든 문서에 대한 해당 키워드의 연관검색어 및 점수 딕셔너리 도출
    total_co_occurrence_words = dict(islice(total_dic.items(), top_size))

    return total_co_occurrence_words


if __name__ == '__main__':

    '''
        - 사용자가 검색한 단어 명사 품사 태깅
        - 명사 품사 태깅된 해당 리스트는 향후
         1. 자바에 다시 전달하여 자바가 이 전처리된 명사들을 보고 엘라스틱 인덱스 조회 시행
         2. 인덱스 조회 후 해당 모듈에 정렬 조회된 nouns 필드 결과 값 전달
         3. 받은 nouns 필드 결과 값들을 바탕으로 연관어분석 시행하여 해당 연관어의 출현빈도 점수와 함께 자바에 다시 전달
    '''
    # 사용자가 검색한 단어 명사 품사 태깅, 자바에 다시 전달
    sample_search_text = '낙태에 대한 범죄 인식'
    sample_search_text_list = get_preprocessed_keyword(sample_search_text)
    print(sample_search_text_list)

    # 자바에서 해당 모듈에 건네줘야 할 데이터 양식
    sample_doc_info = [
        {
            '_doc':0,
            'nouns':[ 
                            '유엔', '인권', '전문가', '형법', '낙태', '처벌', '한국', '우려', '유엔', 
                            '인권', '사회', '산하', '전문가', '한국', '정부', '낙태', '처벌', '관련', 
                            '개정안', '우려', '여성', '차별', '실무', '위원회', '건강', '특별', '관', 
                            '여성', '폭력', '특별', '관은', '현지', '시간', '낙태', '규제', '형법', 
                            '계속', '사용', '점', '우려', '여성', '임신', '형사', '처벌', '상기', 
                            '국제', '인권', '규범', '임신', '중절', '범죄', '필요', '조처', '합법', 
                            '안전', '낙태', '서비스', '접근권', '보장', '조처', '촉구', '강조', '합법', 
                            '임신', '절', '접근', '대기', '기간', '의무', '상담', '의료', '필요', '근거', 
                            '차별', '장애물', '제거', '권고', '헌법', '재판소', '낙태죄', '헌법', '합치', 
                            '결정', '현행', '형법', '지난해', '개정', '주문', '정부', '지난해', '임신', '이내', 
                            '낙태', '허용', '내용', '형법', '모자', '보건', '법', '개정안', '발의', '국회', 
                            '계류', '법률', '미비', '낙태', '형사', '처벌', '규정', '형법', '조항', '지난해', 
                            '일부', '효력', '상실', '이보배', '한경', '닷컴', '객원', '기자', '한국', '경제', 
                            '무단', '전재', '배포', '금지'
                    ],
            'bm25':2.5
        },
        {
            '_doc':1,
            'nouns':[ 
                            '유엔', '인권', '전문가', '형법', '낙태', '처벌', '한국', '우려', '유엔', 
                            '인권', '사회', '산하', '전문가', '한국', '정부', '낙태', '처벌', '관련', 
                            '개정안', '우려', '여성', '차별', '실무', '위원회', '건강', '특별', '관', 
                            '여성', '폭력', '특별', '보장', '현지', '시간', '낙태', '규제', '형법', 
                            '계속', '사용'
                    ],
            'bm25':3.7
        },
        {
            '_doc':2,
            'nouns':[ 
                            '유엔', '인권', '전문가', '형법', '낙태', '처벌', '한국', '우려', '유엔', 
                            '인권', '사회', '산하', '전문가', '한국', '정부', '낙태', '처벌', '관련', 
                            '개정안', '우려', '여성', '차별', '실무', '위원회', '건강', '특별', '관', 
                            '여성', '폭력', '특별', '현지', '시간', '낙태', '규제', '형법', 
                            '계속', '사용'
                    ],
            'bm25':5.0
        }
    ]

    from time import time
    begin = time()

    # sample_co_words = get_total_co_occurrence(sample_doc_info, sample_search_text_list, word_top_size=5, top_size=20)
    # print(sample_co_words)

    a = AssociatedWords(['apple',  'banana',  'apple',  'grape'], 0.5)
    print(a.get_co_occurrence_matrix(window_size=2))

    end = time()

    print(end-begin)