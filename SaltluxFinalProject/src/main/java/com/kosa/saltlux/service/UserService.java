package com.kosa.saltlux.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.saltlux.repository.IUserRepository;
import com.kosa.saltlux.vo.testVO;

@Service
public class UserService implements IUserService {
	
	@Autowired
	IUserRepository userRepository;
	
	@Override
	public int test() {
		return userRepository.test();
	}

	@Override
	public List<testVO> getTest() {
		return userRepository.getTest();
	}

}
