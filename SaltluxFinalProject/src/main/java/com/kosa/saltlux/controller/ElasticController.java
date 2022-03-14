package com.kosa.saltlux.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosa.saltlux.HomeController;
import com.kosa.saltlux.service.ElasticsearchService;

@Controller
public class ElasticController {
	@Autowired
	ElasticsearchService elasticsearchService;
	
	public static final String version = "/api/v1";
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	// news 기사 조회
	@GetMapping(value = version + "/news")
	public String searchNews(Model model, @RequestParam String question, @RequestParam int pageNum) throws Exception {
		
		model.addAttribute("news", elasticsearchService.searchNews(question, pageNum));
		
		return "results";
	}
	
	// news인덱스 count 조회
	@GetMapping(value = version + "/count")
	public String getCount(Model model) throws Exception {
		model.addAttribute("count", elasticsearchService.count());
		
		return "results";
	}
	
	

}
