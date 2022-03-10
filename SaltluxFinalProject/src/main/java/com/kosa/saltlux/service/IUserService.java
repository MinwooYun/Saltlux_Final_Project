package com.kosa.saltlux.service;

import java.util.List;

import com.kosa.saltlux.vo.Criteria;
import com.kosa.saltlux.vo.NewsVO;

public interface IUserService {
//	총 게시물 수 구하기 (페이징)
	int getPageTotal();
	
//	모든 뉴스데이터 가져오기
	List<NewsVO> getNews(Criteria cri);
//	mariaDB 데이터 조회
	
	List<newsVO> getDB(int cnt);
}
