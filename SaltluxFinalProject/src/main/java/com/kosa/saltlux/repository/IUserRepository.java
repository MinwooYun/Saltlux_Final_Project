package com.kosa.saltlux.repository;

import java.util.List;

import com.kosa.saltlux.vo.Criteria;
import com.kosa.saltlux.vo.newsVO;

public interface IUserRepository {
//	총 게시물 수 구하기 (페이징)
	int getPageTotal();
	
//	모든 뉴스데이터 가져오기
	List<newsVO> getNews(Criteria cri);
}
