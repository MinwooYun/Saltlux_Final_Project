package com.kosa.saltlux.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosa.saltlux.HomeController;
import com.kosa.saltlux.service.ElasticsearchService;
import com.kosa.saltlux.vo.NewsVO;

@Controller
public class ElasticController {
	@Autowired
	ElasticsearchService elasticsearchService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	// 자동완성
	@GetMapping(value= "api/v1/autocomplete")
	public String autoComplete(Model model, @RequestParam String term) throws Exception {
	
		model.addAttribute("words", elasticsearchService.autoCompletion(term));
      
		return "jsonView";
      
   }
	

	// 뉴스기사 조회
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/news")
	public String searchNews(Model model, @RequestParam String question, @RequestParam int pageNum) throws Exception {

		
		List<List<Object>> resultList = elasticsearchService.searchNews(question, pageNum);

		
		// 1. 뉴스기사 조회
		model.addAttribute("newsList", resultList.get(1));

		// 2. 사용자가 검색한 문자열에 대하여 플라스크에서 유사 키워드 도출
		// 해당 타입은 map <사용자 검색 문자열, 엘라스틱에서 조회한 검색 결과 콘텐츠 {nouns, score}>
		HashMap<String, Object> suggestionMap = new HashMap<>(resultBoard(question, elasticsearchService.searchNewsForSuggetionTerm(question)));
		
		// 3. 워드클라우드 형식에 맞게 형변환
		JSONArray jsonArray = new JSONArray();
		
		
		for ( String key : suggestionMap.keySet() ) {
			JSONObject suggestionObject = new JSONObject();
			suggestionObject.put("name", key);
			suggestionObject.put("weight", suggestionMap.get(key));
			jsonArray.add(suggestionObject);
		}

		model.addAttribute("jsonArray", jsonArray);
		model.addAttribute("words", elasticsearchService.getSuggestionTerms(question));
		
		long pageTotal = Math.round((long) resultList.get(0).get(0)/9);
		int pageStart = ((pageNum - 1) / 10) * 10 + 1;
		int pageEnd = pageStart + 9;
		model.addAttribute("pageTotal", pageTotal);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("pageStart", pageStart);
		model.addAttribute("pageEnd", pageEnd);
		model.addAttribute("question", question);

		
		return "results";
	}

	
	
	 /*
	    * 플라스크와 데이터 주고받는 method
	    * step 1: flask에 사용자 검색 문자열과 검색 결과 콘텐츠 전달
	    * step 2: flask로부터 연관어 키워드 jsonObject 받음
	    */
	   public HashMap<String, Float> resultBoard(String question, List<Object> result) throws Exception {
	      JSONParser parser = new JSONParser();
	   
	      // step 1 ===========================================================================
	      // 1. 사용자 검색 문자열 전달 => url에 태워 전송
	      String keyword = question.replace(" ", "-");
	      String reqUrl = "http://127.0.0.1:5000/news?question=" + URLEncoder.encode(keyword, "UTF-8");
	      URL url = new URL(reqUrl);
	      HttpURLConnection con = (HttpURLConnection)url.openConnection();
	      
	      // 2. 검색 결과 콘텐츠 정보 전달 => json으로 전송
	      Object resultObj = parser.parse(result.toString());
	      JSONArray jsonArray = (JSONArray) resultObj;
	      
	      // Json Array 전달 위한 HttpURLConnection 옵션 설정
	      con.setDoOutput(true);
	      con.setDoInput(true);
	      con.setRequestMethod("POST");
	      con.setRequestProperty("Content-Type", "application/json");
	      con.setRequestProperty("Accept-Charset", "UTF-8");
	               
	      // flask에 jsonArray 전송
	       BufferedWriter bWriter=new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
	        bWriter.write(jsonArray.toString());
	        bWriter.close();
	      
	      // step 2 ============================================================================
	        // flask로부터 유사 키워드, 해당 키워드 점수 json 데이터 읽어옴         
	      BufferedReader bReader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
	      
	      // StringBuffer에 플라스크로부터 받은 데이터 적재
	      String str = null;
	      StringBuffer buff = new StringBuffer();
	      
	      // 한 글자 씩 StringBuffer에 적재
	      while((str = bReader.readLine()) != null) {
	         buff.append(str + "\n");
	      }
	      
	      // StringBuffer타입을 String 타입으로 형변환
	      String data = buff.toString().trim();
	      
	      // data를 JSONObject 타입으로 변환
	      Object associatedWordsObj = parser.parse(data.toString());
	      JSONObject associatedWords = (JSONObject) associatedWordsObj;
	         
	      return associatedWords;
	   }
	
}
