package com.kosa.saltlux.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.google.gson.Gson;
import com.kosa.saltlux.HomeController;
import com.kosa.saltlux.service.ElasticsearchService;
import com.kosa.saltlux.vo.NewsVO;

@Controller
public class ElasticController {
	@Autowired
	ElasticsearchService elasticsearchService;
	
//	public static final String version = "/api/v1";
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	// 자동완성
	@GetMapping(value = "/api/v1/autocomplete")
	   public String autoComplete(Model model, @RequestParam final String term) throws Exception {

	      model.addAttribute("words", elasticsearchService.autoCompletion(term));
	      
	      return "jsonView";
	      
	   }
	
	// news 기사 조회
	@GetMapping(value = "/news")
	public String searchNews(Model model, String question, int pageNum) throws Exception {
						
		model.addAttribute("newsList", elasticsearchService.searchNews(question, pageNum));
		
		Map<String, Float> suggestionMap = new HashMap<>(resultBoard(question, elasticsearchService.searchNewsForSuggetionTerm(question)));
		
		model.addAttribute("suggestionMap", suggestionMap);
		
		return "results";
	}
	
	public HashMap<String, Float> resultBoard(String question, List<Object> result) throws Exception {
		
//			String keyword = question.replace(" ", "-");
//			String reqUrl = "http://127.0.0.1:5000/news?question=" + URLEncoder.encode(keyword, "UTF-8");
//			URL url = new URL(reqUrl);
//			HttpURLConnection con = (HttpURLConnection)url.openConnection();
//			con.setRequestMethod("GET");
//			
//			//서버에서 응답한걸 받기위한 stream
//			InputStream in = con.getInputStream();
//			//바이트단위로 쪼개서 보내짐, 한글로 받기 위해
//			InputStreamReader reader = new InputStreamReader(in, "UTF-8");
//			BufferedReader response = new BufferedReader(reader);
//			
//			String str = null;
//			StringBuffer buff = new StringBuffer();
//			
//			while((str = response.readLine()) != null) {
//				buff.append(str + "\n");
//			}
//			String data = buff.toString().trim();
//			
//			JSONParser parser = new JSONParser();
//			Object obj = parser.parse(data);
//			JSONObject jsonObj = (JSONObject) obj;
//			System.out.println(jsonObj);
//			
//			BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
//	        bWriter.write(jsonObj.toString());
//
//		return question;	
		
		String inputLine = null;
		StringBuffer stringBuffer = new StringBuffer();
		
		
		URL url = new URL("http://localhost:5000/news?question=" + URLEncoder.encode(question, "UTF-8"));
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		
		System.out.println("conn : " + conn.toString());
		
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("GET"); // GET
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Accept-Charset", "UTF-8");
			
		
		// 데이터 전송
		BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		
		System.out.println("conn.getOutputStream() : " + conn.getOutputStream().toString());
		
//		JSONParser parser = new JSONParser();
		JSONArray jsonArray = new JSONArray();
		
		for(Object obj : result) {
//			System.out.println("result's obj: " + obj);
			jsonArray.add(obj);
		}
	
		
//		
//        Object obj = parser.parse(result.toString());
        
        
        
		System.out.println("jsonArray.toJSONString : " + jsonArray.toJSONString());
		bWriter.write(jsonArray.toString());
		
		System.out.println("&&&&& conn.getInputStream: " + conn.getInputStream());
		
		// 전송된 결과를 읽어옴
		BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		while((inputLine=bReader.readLine()) != null) {
			stringBuffer.append(inputLine);
			System.out.println("inputLine: " + inputLine);
		}
		
        bWriter.close();
        bReader.close();
        conn.disconnect();
		
//		return stringBuffer.toString();
        return null;
	}
	
	// news인덱스 count 조회
	@GetMapping(value = "/api/v1/count")
	public String getCount(Model model) throws Exception {
		model.addAttribute("count", elasticsearchService.count());
		
		return "results";
	}
	

}
