import numpy as np
import pandas as pd

from collections import defaultdict

import warnings
warnings.filterwarnings('ignore')

'''
    - 단일 문서에서 출현한 명사 리스트에 대하여 검색된 키워드를 바탕으로 연관 검색어를 도출하는 클래스
    - param: noun_list (기사 본문 하나의 문서에서 추출된 중복이 적용된 명사 리스트 => mecab을 이용한 형태소 분석으로 도출됨)
'''
class AssociatedWords():
    def __init__(self, noun_list) -> None:
        self.noun_list = noun_list


    '''
        - 주어진 명사 리스트에 대하여 동반 출현 빈도 matrix 도출

        - param: cnt (출현 빈도 가중치, default: 1), window_size (주변 단어 탐색 범위 사이즈, default: 3)

        - return: co_occurrence_df
    '''
    def get_co_occurrence(self, cnt=1, window_size=3) -> pd.DataFrame:
        word_dic = defaultdict(int)
        vocab = set()

        # 명사 리스트 탐색
        for idx, noun in enumerate(self.noun_list):
            vocab.add(noun)
            # 인접 단어 window_size 수 만큼 탐색
            next_noun = self.noun_list[idx+1 : idx+1+window_size]

            # 탐색된 인접단어들에 대하여 가중치만큼 출현 빈도 부여
            for t in next_noun:
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

        - param: keyword (사용자가 검색한 단어 => keyword_preprocess.py에서 정제)
                 top_size (연관 단어 개수, default: 5)

        - return: co_occurrence_words (해당 키워드에 대한 문서 내 연관 단어 리스트)
    '''
    def get_co_occurrence_words(self, keyword:str, top_size=5) -> list:

        # 해당 키워드에 대한 문서 내 연관 단어 리스트
        co_occurrence_words = []

        try:
            correlated_words = self.co_occurrence_df[keyword].to_dict()
            correlated_words = {word:score for word, score in correlated_words.items() if score != 0}
            correlated_words = dict(sorted(correlated_words.items(), key=lambda x: x[1], reverse=True))
            co_occurrence_words = list(correlated_words.keys())

            return co_occurrence_words[:top_size]

        # 찾고자 하는 키워드가 문서(동반 출현 빈도 matrix)에 없을 경우
        except:
            co_occurrence_words.append('None')

            return co_occurrence_words


if __name__ == '__main__':
    sample_noun_list = [
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
                    ]

    associated_word = AssociatedWords(sample_noun_list)
    df = associated_word.get_co_occurrence()
    # print(df)

    words = associated_word.get_co_occurrence_words('인권', top_size=7)
    print(words)