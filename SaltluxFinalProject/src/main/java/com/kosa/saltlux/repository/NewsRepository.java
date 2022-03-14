package com.kosa.saltlux.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kosa.saltlux.vo.NewsVO;

@Repository
public class NewsRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<NewsVO> getAllNews() {
		String sql = "select * from news where news_no between 12 and 30";
		
		return jdbcTemplate.query(sql, new RowMapper<NewsVO>() {

			@Override
			public NewsVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				NewsVO news = new NewsVO();
//				news.setNewsNo(rs.getInt("news_no"));
				news.setTitle(rs.getString("title"));
				news.setContents(rs.getString("contents"));
				news.setImageURL(rs.getString("image_url"));
				news.setThumbnailURL(rs.getString("thumbnail_url"));
				news.setPress(rs.getString("press"));
				news.setCategory(rs.getString("category"));
//				news.setNewsDate(rs.getDate("news_date"));
				news.setNouns(rs.getString("nouns"));
				return news;
			}
			
		});
	}
}
