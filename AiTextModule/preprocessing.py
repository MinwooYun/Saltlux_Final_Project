def pre_processing_db(df) : 
    from konlpy.tag import Mecab
    import numpy as np
    import pandas as pd

    # 결측치 제거
    df.dropna(how='any',inplace=True)
    
    # 영어 기사 제거
    df['td']=df['title']+' '+df['detail']
    df_kor = df[df['title'].str.contains('[|가-힣]')==True]
    df_kor = df_kor.reset_index().drop(columns = ['index','link'], axis=1)
    #카테고리 그룹화 
    final_category = ['문화', '스포츠', '연예','기타']
    cate = [['영화'], ['해외축구', '배구', '농구', '축구', 'e스포츠', '야구', '골프', '해외야구'], 
            ['예능', '방송', '연예일반', '드라마', '연예가화제', '가요음악','해외연예'],
            ['사설칼럼', '일반','자동생성기사', '보도자료', '속보']]
    f_start_idx = 0
    i_start_idx = 0
    for category in range(f_start_idx, len(final_category)):
        f_start_idx += 1
        for item in range(i_start_idx, len(cate)) :
            df_kor['category'].replace(cate[item], final_category[category], inplace=True)
            i_start_idx += 1
            break
    # nouns 컬럼 생성
    mecab = Mecab(dicpath=r"C:\mecab\mecab-ko-dic")
    noun_list = []
    for i in range(len(df_kor)) : 
        text = df_kor['td'][i]
        text_pos_list = mecab.pos(text)
        text_noun_list = [i[0] for i in text_pos_list if i[1] == 'NNP' or i[1] == 'NNG']

        noun_str =' '.join(text_noun_list)
        noun_list.append(noun_str)

    df_kor['nouns']=noun_list
    df_kor = df_kor.drop(columns = 'td', axis=1)
    df_kor = df_kor[df_kor['detail'].str.contains('Null')==False]
    return df_kor

def pre_processing_analysis(df) : 
    from konlpy.tag import Mecab
    import numpy as np
    import pandas as pd
    # 결측치 제거
    df.dropna(how='any',inplace=True)
    
    # 영어 기사 제거
    df['td']=df['title']+' '+df['detail']
    df_kor = df[df['title'].str.contains('[|가-힣]')==True]
    df_kor = df_kor.reset_index().drop(columns = ['index','link'], axis=1)
    #카테고리 그룹화 
    final_category = ['문화', '스포츠', '연예','기타']
    cate = [['영화'], ['해외축구', '배구', '농구', '축구', 'e스포츠', '야구', '골프', '해외야구'], 
            ['예능', '방송', '연예일반', '드라마', '연예가화제', '가요음악','해외연예'],
            ['사설칼럼', '일반','자동생성기사', '보도자료', '속보']]
            
    f_start_idx = 0
    i_start_idx = 0
    for category in range(f_start_idx, len(final_category)):
        f_start_idx += 1
        for item in range(i_start_idx, len(cate)) :
            df_kor['category'].replace(cate[item], final_category[category], inplace=True)
            i_start_idx += 1
            break

    # nouns 컬럼 생성
    mecab = Mecab(dicpath=r"C:\mecab\mecab-ko-dic")
    noun_list = []
    for i in range(len(df_kor)) : 
        text = df_kor['td'][i]
        text_pos_list = mecab.pos(text)
        text_noun_list = [i[0] for i in text_pos_list if i[1] == 'NNP' or i[1] == 'NNG']

        noun_str =' '.join(text_noun_list)
        noun_list.append(noun_str)

    df_kor['nouns']=noun_list

    # # 마지막 마침표 제거
    # for i in range(len(df_kor)):
    #     df_kor.detail[i] = df_kor.detail[i].rstrip('.')
        
    df_kor = df_kor[df_kor['detail'].str.contains('Null')==False]
    df_kor.drop(columns=['title','press','date','img','thumb_img','detail'],axis=1,inplace=True)
    return df_kor