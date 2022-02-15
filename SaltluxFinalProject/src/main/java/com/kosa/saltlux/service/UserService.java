package com.kosa.saltlux.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.saltlux.repository.IUserRepository;

@Service
public class UserService implements IUserService {
	
	@Autowired
	IUserRepository userRepository;
	
	@Override
	public int test() {
		return userRepository.test();
	}

}
