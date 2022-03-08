package com.kosa.saltlux.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.saltlux.repository.IUserRepository;
import com.kosa.saltlux.vo.Criteria;
import com.kosa.saltlux.vo.newsVO;
import com.kosa.saltlux.vo.testVO;

@Service
public class UserService implements IUserService {
	
	@Inject
	private IUserRepository mapper;
	
	@Override
	public int getPageTotal() {
		return mapper.getPageTotal();
	}


	@Override
	public List<newsVO> getNews(Criteria cri) {
		return mapper.getNews(cri);
	}


	@Override
	public List<newsVO> getDB(int cnt) {
		return mapper.getDB(cnt);
	}

}
