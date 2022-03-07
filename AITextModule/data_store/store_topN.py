# Maria DB의 topN, 오늘의 이슈용 topN (2021-02-28) 테이블 데이터 적재

import sys
[sys.path.append(i) for i in ['.', '..']]

from data_analysis.text_score import get_score_df

import pandas as pd

if __name__ == '__main__':
    from time import time
    begin = time()

    df = pd.read_csv(r'csv_files\FinalDataset_DB.csv')
    print(df.columns)
    score_df = get_score_df(df)
 
    # datetime split
    year_month_day = score_df['date'].str.split(' ').str[0]
    score_df['year'] = year_month_day.str.split('-').str[0]
    score_df['month'] = year_month_day.str.split('-').str[1]
    score_df['day'] = year_month_day.str.split('-').str[2]
    score_df['hour'] = score_df['date'].str.split(' ').str[1]

    # delete date column
    score_df = score_df.drop(['date'], axis=1)

    print(score_df)

    score_df.to_csv('topN.csv', encoding='utf-8-sig', index=False)

    end = time()
    print(end - begin)