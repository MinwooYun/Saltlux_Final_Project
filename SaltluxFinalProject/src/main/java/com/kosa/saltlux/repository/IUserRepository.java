package com.kosa.saltlux.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kosa.saltlux.vo.Criteria;
import com.kosa.saltlux.vo.newsVO;

public interface IUserRepository {
//	총 게시물 수 구하기 (페이징)
	int getPageTotal();
	
//	모든 뉴스데이터 가져오기
	List<newsVO> getNews(Criteria cri);
	
//	mariaDB 데이터 조회
	List<newsVO> getDB(@Param("cnt")int cnt);
}
