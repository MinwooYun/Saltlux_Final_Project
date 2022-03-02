package com.kosa.saltlux.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kosa.saltlux.vo.testVO;

@Repository
public class UserRepository implements IUserRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public int test() {
		String sql = "SELECT COUNT(*) FROM NEWS";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	private class ProductMapper implements RowMapper<testVO> {
		@Override
		public testVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			testVO vo = new testVO();
			vo.setProductNo(rs.getInt("product_no"));
			vo.setSallerUserId(rs.getString("saller_user_id"));
			vo.setProductName(rs.getString("product_name"));
			vo.setProductPrice(rs.getInt("product_price"));
			vo.setProductDescription(rs.getString("product_description"));
			vo.setProductSaleStatusId(rs.getString("product_sale_status_id"));
			vo.setProductCategoryName(rs.getString("product_category_name"));
			vo.setPurchaseDate(rs.getDate("purchase_date"));
			return vo;
		}			
	}
	
	@Override
	public List<testVO> getTest() {
		String sql = "select * from product where purchase_date is null order by product_no";
		return jdbcTemplate.query(sql, new ProductMapper());	
	}
}
