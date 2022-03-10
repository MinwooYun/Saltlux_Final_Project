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
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/elastictest", method = RequestMethod.POST)
	public String elasticTest1(String word, Model model) throws Exception{
		System.out.println(word);
		List<Object> elasticList = getAutoCompletionList(word);
		model.addAttribute("elasticList", elasticList);
		return "elastic";
	}
	
	public List<Object> getAutoCompletionList(String word) throws Exception{
		List<Object> elasticList = elasticsearchService.autoCompletion(word);
		System.out.println(elasticList);	
		return elasticList;
	}

}
