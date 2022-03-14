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
    - uri: /results
'''
@app.route('/results', methods=['GET'])
def get_refined_keyword():

    data = request.args.get('search')

    # 사용자가 검색한 키워드
    search_keyword = data.split('+')[0]

    # 검색 결과 문서 리스트
    doc_info_str = data.split('+')[1]
    doc_info_list = json.loads(json.dumps(doc_info_str))
    doc_info_list = ast.literal_eval(doc_info_list)

    refined_data_list = get_preprocessed_keyword(search_keyword)
    print(refined_data_list)

    # sample_doc_info = [{"nouns": '유엔 인권 여성 인권 보장 필요 유엔 여성 인권',"bm25":2.5}]

    associated_words_dic = get_total_co_occurrence(
                                    doc_info_list=doc_info_list, 
                                    search_text_list=refined_data_list,
                                    word_top_size=5,
                                    top_size=20
                                    )

    dic_json = jsonify(associated_words_dic)

    response = make_response(dic_json)
    response.headers.add("Access-Control-Allow-Origin", "*")

    return response


if __name__ == '__main__':
    app.debug = True
    app.config['JSON_AS_ASCII'] = False
    app.run()