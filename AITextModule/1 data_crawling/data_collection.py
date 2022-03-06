#일마다 300페이지 고정 크롤링 
def news_crawling(date:int, page) -> None :

    import requests
    from bs4 import BeautifulSoup as bs
    import pandas as pd
    import time
    df2 = pd.DataFrame(index=range(0), 
                       columns=['title', 'press','date','link','img','thumb_img','category','detail'])
    cnt = 0
    for m in page :
        url = 'https://news.daum.net/breakingnews?page='+str(m)+'&regDate='+str(date)
        try : 
            res = requests.get(url)
        except : 
            time.sleep(5)
            res = requests.get(url)
        soup = bs(res.text, 'lxml')
        ul = soup.find('ul',{'class' :'list_news2 list_allnews'}).findAll('li')

        article_title = []
        article_link = [] 
        article_press = []
        article_thumb_img_link=[]

        for i in range(len(ul)) : 
            article_link.append(ul[i].find('a').get('href'))
            article_press.append(ul[i].find('span',{'class':'info_news'}).text[:-8])
            article_title.append(ul[i].find('a',{'class':'link_txt'}).text)
            try :
                article_thumb_img_link.append(ul[i].find('a').find('img').get('src'))
            except : 
                article_thumb_img_link.append('Null')
           
        article_img_link = []
        article_detail = []
        article_category = []
        article_date = []
        
        for j in article_link:
            url = j
            try : 
                res = requests.get(url)
            except : 
                time.sleep(5)
                res = requests.get(url)
            soup = bs(res.text, 'lxml')
            try : 
                article_img_link.append(soup.find('p',{'class':"link_figure"}).img.attrs['src'])
                article_category.append(soup.find_all('h2',{'class':'screen_out','id':'kakaoBody'})[0].text)
                article_date.append(soup.find_all('span',{'class':"num_date"})[0].text)

                str_detail = ""
            
                for k in range(len(soup.find_all('div')[1].section.find_all('p'))):
                    str_detail = str_detail+(soup.find_all('div')[1].section.find_all('p')[k].text)+ '\n'    
                article_detail.append(str_detail)
            except : 
                article_img_link.append('Null')
                article_category.append('Null')
                article_date.append('Null')
                article_detail.append('Null')
  
        cnt +=1
        print(f"총 {len(page)}번 페이지중 {cnt}번 째 진행중")

        df= pd.DataFrame({'title':article_title,'press':article_press,
                          'date':article_date,'link':article_link,
                          'img':article_img_link,'thumb_img':article_thumb_img_link,
                          'category':article_category,'detail':article_detail})
        df2 = pd.concat([df2,df])
    filename = str(date)+'crawling.csv'
    df2.to_csv(filename,index=False,encoding= 'utf-8-sig')
    time.sleep(10)
    

def get_crawling_date(start : str, end : str)->str:
    from datetime import datetime, timedelta 
    # 시작일,종료일 설정 
    # 시작일, 종료일 datetime 으로 변환 
    start_date = datetime.strptime(start, "%Y-%m-%d") 
    last_date = datetime.strptime(end, "%Y-%m-%d") 
    dates=[]
    # 종료일 까지 반복 
    while start_date <= last_date: 
        dates.append(start_date.strftime("%Y%m%d"))  
        # 하루 더하기 
        start_date += timedelta(days=1)
    return dates


    # 해당 날짜의 마지막페이지를 구하고, 그안에서 구간을 구해 300개만 추출
def get_page_list(date : int) -> int: 
    import requests
    from bs4 import BeautifulSoup as bs
    url = 'https://news.daum.net/breakingnews?page=5000&regDate='+str(date)
    res = requests.get(url)
    soup = bs(res.text, 'lxml')
    ul = soup.find('em')
    ul.select_one('span').decompose()
    last_page = ul.text.strip()
    seq = (int(last_page))/300
    page_list=[]
    j=0
    for i in range(300) : 
        j+=seq
        page_list.append(int(j)) 
        
    return page_list
#  실행 예시
# dates = get_crawling_date('2022-02-21','2022-02-28')        
# for date in dates :
#     news_crawling(date, get_page_list(date))