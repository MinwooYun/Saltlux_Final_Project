from konlpy.tag import Mecab

import re

'''
    - 자바 웹 단에서 받은 사용자 검색어를 명사 키워드 리스트로 도출
    - param: text (자바 웹 단에서 받은 사용자 검색어)
    - return: noun_list
'''

def get_preprocessed_keyword(text:str) -> list:
    # nouns 컬럼 생성
    mecab = Mecab(dicpath=r"C:\mecab\mecab-ko-dic")

    refined_text = re.sub(r"[^가-힣]", "", text)

    text_pos_list = mecab.pos(refined_text)
    keyword_list = [i[0] for i in text_pos_list if i[1] == 'NNP' or i[1] == 'NNG']

    return keyword_list


if __name__ == '__main__':
    text = 'fta'
    refined_text = get_preprocessed_keyword(text)
    print(refined_text)