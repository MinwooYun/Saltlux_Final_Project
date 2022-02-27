import pandas as pd
import numpy as np

from konlpy.tag import Mecab

'''
    - 주어진 텍스트에 대하여 다양한 수치를 제공하는 클래스
    - param: text_noun_list (전처리 모듈에서 도출한 명사 품사만 태깅된 단어 리스트)
'''
class TextScore():
    def __init__(self, text_noun_list:list) -> None:
        self.text_noun_list = text_noun_list

    '''
        - tf 점수 도출 (해당 원문 링크에서 키워드가 나타나는 총 횟수)
        - param: keyword (찾고자 하는 키워드)
        - return: tf (해당 키워드의 tf 점수)
        - 이슈: return 을 모든 중복 제외한 키워드들과 이에 해당하는 tf 점수 딕셔너리로 줄지, 개별 단어에 대한 tf 점수 하나만 뱉어줄지 고민
    '''
    def get_tf(self, keyword:str) -> int:
        text_noun_count = {}
        text_count = np.unique(self.text_noun_list, return_counts=True)

        for noun, count in zip(*text_count):
            text_noun_count[noun] = count

        text_noun_count = sorted(text_noun_count.items(), key=lambda x: x[1], reverse=True)

        # 특정 키워드의 tf 점수 탐색
        try:
            tf = [text_tf[1] for text_tf in text_noun_count if keyword == text_tf[0]][0]
            return tf
        except:
            tf = 0
            return tf

    '''
        - tdm 점수 도출 (문서별로 단어의 빈도수를 계산하여 행렬로 만듦)
    '''
    def get_tdm(self):
        pass


if __name__ == '__main__':
    sample_text = '''
                    '유엔 인권전문가 "형법으로 낙태 처벌하는 한국 우려" 유엔 인권이사회 산하 전문가들이 한국 정부의 낙태 처벌 관련 개정안에 우려를 표했다.
                    여성차별 실무위원회와 건강권 특별보고관, 여성폭력 특별보고관은 1일(현지시간) "낙태 규제를 위해 형법을 계속해 사용하는 점을 우려한다"면서 
                    "우리는 여성이 임신 중절로 절대 형사 처벌돼서는 안된다는 바를 상기하고자 한다"고 밝혔다. 또 "국제인권 규범에 따라 임신 중절을 범죄로 여기지 
                    않는 데 필요한 조처와 합법적이고 안전한 낙태 서비스에 대한 접근권을 보장하는 조처를 촉구한다"고 강조했다. 그러면서 "합법적 임신 중절에 
                    대한 접근에 있어 대기 기간, 의무 상담과 같은 의료적 필요에 근거하지 않은 차별적 장애물을 제거하라"고 권고했다. 앞서 헌법재판소는 2019년 
                    4월 낙태죄에 대해 헌법 불합치 결정을 내리면서 현행 형법은 지난해 말까지 개정돼야 한다고 주문했다. 이에 따라 정부는 지난해 10월 임신 14주 이내에서는 
                    낙태를 허용하는 내용의 형법·모자보건법 개정안을 발의했다. 하지만 국회에 계류중이라 대체 법률 미비로 낙태에 대한 형사 처벌을 규정한 형법 조항은 
                    지난해 12월31일부로 효력을 상실했다. 이보배 한경닷컴 객원기자 newsinfo@hankyung.com ⓒ 한국경제 & , 무단전재 및 재배포 금지'
                '''
    
    mecab = Mecab(dicpath=r"C:\mecab\mecab-ko-dic")

    text_pos_list = mecab.pos(sample_text)
    text_noun_list = [i[0] for i in text_pos_list if i[1] == 'NNP' or i[1] == 'NNG']

    text_score = TextScore(text_noun_list)
    # tf test
    tf = text_score.get_tf('인권')
    print(tf)