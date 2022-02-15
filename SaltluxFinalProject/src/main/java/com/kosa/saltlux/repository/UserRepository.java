package com.kosa.saltlux.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements IUserRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public int test() {
		String sql = "SELECT COUNT(*) FROM EMPLOYEES";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

}
