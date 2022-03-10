package com.kosa.saltlux.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.annotation.Resource;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class SampletController {

	private static final Logger logger = LoggerFactory.getLogger(SampletController.class);

	@Resource(name="restHighLevelClientSSLIgnore")
	private RestHighLevelClient restHighLevelClientSSLIgnore;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@GetMapping(value = "/jsonTest")
	public String jsonTest(Model model) {
		
		HashMap<String, String> hashMap = new HashMap<String, String>() {

			private static final long serialVersionUID = 1L;
			{
				put("classes", "1조");
				put("name", "Pre Luxsian");
			}
			
		};
		
		model.addAttribute("jsonData", hashMap);
		
		return "jsonView";
	}

	@GetMapping(value = "/search")
	public String search(Model model) throws IOException {

		SearchRequest searchRequest = new SearchRequest("news");
		
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequest.source(sourceBuilder);
		
		SearchResponse searchResponse = restHighLevelClientSSLIgnore.search(searchRequest, RequestOptions.DEFAULT);
		
		model.addAttribute("searchResponse", searchResponse);
		
		return "jsonView";
		
	}
	
	
	@GetMapping(value = "/newsTest")
	public String newsTest(Model model) {
		
		HashMap<String, String> hashMap = new HashMap<String, String>() {

			private static final long serialVersionUID = 1L;
			{
				put("classes", "1조");
				put("name", "Pre Luxsian");
				put("title", "name");
			}
		};
		model.addAttribute("jsonData", hashMap);
		
		return "jsonView";
	}	
}
