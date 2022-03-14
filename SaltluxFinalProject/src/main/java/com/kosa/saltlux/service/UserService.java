package com.kosa.saltlux.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.saltlux.repository.IUserRepository;
import com.kosa.saltlux.vo.ClusterVO;
import com.kosa.saltlux.vo.CriteriaVO;
import com.kosa.saltlux.vo.MainDashBoardVO;
import com.kosa.saltlux.vo.NewsVO;
import com.kosa.saltlux.vo.RealtimeVO;

@Service
public class UserService implements IUserService {
	
	@Inject
	private IUserRepository mapper;
	
	@Override
	public int getPageTotal() {
		return mapper.getPageTotal();
	}


	@Override
	public List<NewsVO> getNews(CriteriaVO cri) {
		return mapper.getNews(cri);
	}


	@Override
	public List<NewsVO> getDB(int cnt) {
		return mapper.getDB(cnt);
	}
	
//	메인페이지 대시보드의 Wordcloud, Barchart
	@Override
	public List<MainDashBoardVO> getMainDashBoardChart(String category) {
		return mapper.getMainDashBoardChart(category);
	}

//	검색 결과페이지의 급상승 키워드 Top 10
	@Override
	public List<RealtimeVO> getRealtimeTop() {
		return mapper.getRealtimeTop();
	}

//	GET 오늘의 이슈 리스트
	@Override
	public List<ClusterVO> getTodayIssue() {
		return mapper.getTodayIssue();
	}

//	GET 오늘의 이슈 뉴스리스트
	@Override
	public NewsVO getTodayNews(int index) {
		return mapper.getTodayNews(index);
	}
}
