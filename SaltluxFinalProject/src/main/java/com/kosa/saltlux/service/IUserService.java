package com.kosa.saltlux.service;

import java.util.List;

import com.kosa.saltlux.vo.CriteriaVO;
import com.kosa.saltlux.vo.MainDashBoardVO;
import com.kosa.saltlux.vo.NewsVO;
import com.kosa.saltlux.vo.RealtimeVO;

public interface IUserService {
//	총 게시물 수 구하기 (페이징)
	int getPageTotal();
	
//	모든 뉴스데이터 가져오기
	List<NewsVO> getNews(CriteriaVO cri);
	
//	mariaDB 데이터 조회
	List<NewsVO> getDB(int cnt);
	
//	GET 메인페이지 대시보드의 Wordcloud
	List<MainDashBoardVO> getMainDashBoardWordcloud(String category);
	
//	GET 결과페이지 Top 10
	List<RealtimeVO> getRealtimeTop();
}
