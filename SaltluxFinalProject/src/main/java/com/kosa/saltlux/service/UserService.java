package com.kosa.saltlux.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.saltlux.repository.IUserRepository;
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


	@Override
	public List<MainDashBoardVO> getMainDashBoardWordcloud(String category) {
		return mapper.getMainDashBoardWordcloud(category);
	}


	@Override
	public List<RealtimeVO> getRealtimeTop() {
		return mapper.getRealtimeTop();
	}
}
