package com.kosa.saltlux.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.saltlux.repository.NewsRepository;
import com.kosa.saltlux.vo.NewsVO;

@Service
public class NewsService {

	@Autowired
	private NewsRepository newsRepository;
	
	public List<NewsVO> getAllNews() {
		return newsRepository.getAllNews();
	}
}
