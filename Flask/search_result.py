from flask import Flask, request, jsonify, make_response

from data_preprocess.keyword_preprocess import get_preprocessed_keyword
from data_analysis.associated_word import get_total_co_occurrence

import json
import ast

import sys
[sys.path.append(i) for i in ['.', '..']]

app = Flask(__name__)


'''
    - 검색 문자열 형태소 분석하여 품사 태깅된 명사 리스트 자바에 전달
    - uri: /news
'''
@app.route('/news', methods=['GET', 'POST'])
def get_refined_keyword():

    # 사용자 검색 문자열
    search_text = request.args.get('question')

    # 검색 문자열 명사 리스트 변환
    refined_data_list = get_preprocessed_keyword(search_text)
    print(refined_data_list)

    # 검색 결과 콘텐츠 정보
    if request.method == 'POST':
        print(request.is_json)
        search_result_list = request.get_json(silent=True)
        print(search_result_list)

        associated_words_dic = get_total_co_occurrence(
                                            doc_info_list=search_result_list,
                                            search_text_list=refined_data_list,
                                            word_top_size=5,
                                            top_size=30
                                        )

        associated_dic_json = jsonify(associated_words_dic)

        response = make_response(associated_dic_json)
        response.headers.add("Access-Control-Allow-Origin", "*")

        return response

        return response

if __name__ == '__main__':
    app.debug = True
    app.config['JSON_AS_ASCII'] = False
    app.run()

