package com.kosa.saltlux.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kosa.saltlux.HomeController;
import com.kosa.saltlux.service.IUserService;
import com.kosa.saltlux.vo.ClusterVO;
import com.kosa.saltlux.vo.CriteriaVO;
import com.kosa.saltlux.vo.MainDashBoardVO;
import com.kosa.saltlux.vo.NewsVO;
import com.kosa.saltlux.vo.RealtimeVO;

@Controller
public class UserController {
	
	@Autowired
	IUserService userService;
	
	public static final String version = "/api/v1";
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		List<ClusterVO> clusterList = userService.getTodayIssue();
		List<Integer> index = new ArrayList<>();
		
		for(ClusterVO vo: clusterList) {
			String[] data = vo.getArticleIndex().split(" ");
			for(int i = 0; i < 5; i++) {
				index.add(Integer.parseInt(data[i]));
			}
		}

		List<NewsVO> newsList1 = new ArrayList<>();
		List<NewsVO> newsList2 = new ArrayList<>();
		List<NewsVO> newsList3 = new ArrayList<>();
		List<NewsVO> newsList4 = new ArrayList<>();
		List<NewsVO> newsList5 = new ArrayList<>();


		for(int i = 0; i < 5; i++) {
			NewsVO data = userService.getTodayNews(index.get(i));
			newsList1.add(data);
		}

		for(int i = 5; i < 10; i++) {
			NewsVO data = userService.getTodayNews(index.get(i));
			newsList2.add(data);
		}

		for(int i = 10; i < 15; i++) {
			NewsVO data = userService.getTodayNews(index.get(i));
			newsList3.add(data);
		}

		for(int i = 15; i < 20; i++) {
			NewsVO data = userService.getTodayNews(index.get(i));
			newsList4.add(data);
		}

		for(int i = 20; i < 25; i++) {
			NewsVO data = userService.getTodayNews(index.get(i));
			newsList5.add(data);
		}
		
		HashMap<Integer, Integer> map = new HashMap<>();
		
		for(ClusterVO vo : clusterList) {
			map.put(vo.getClusterNum(), vo.getCnt());
		}
		
		int cnt1 = map.get(1);
		int cnt2 = map.get(2);
		int cnt3 = map.get(3);
		int cnt4 = map.get(4);
		int cnt5 = map.get(5);
	
		model.addAttribute("newsList1", newsList1);
		model.addAttribute("newsList2", newsList2);
		model.addAttribute("newsList3", newsList3);
		model.addAttribute("newsList4", newsList4);
		model.addAttribute("newsList5", newsList5);
		
		model.addAttribute("cnt1", cnt1);
		model.addAttribute("cnt2", cnt2);
		model.addAttribute("cnt3", cnt3);
		model.addAttribute("cnt4", cnt4);
		model.addAttribute("cnt5", cnt5);
		
		return "home";
	}
	
	
	/**
	 * MainDashBoard테이블의 그룹별 데이터를 검색
	 * BTF가 높은 순서대로 n개의 키워드 추출
	 * @param categoryName
	 * @param model
	 * @return jsonList.toString()
	 */
	@ResponseBody
	@RequestMapping(value = version + "/main-board/dash-board/chart/{categoryName}", method = RequestMethod.POST, produces = "application/text; charset=UTF-8")
	public String MainDashBoardChart(@PathVariable String categoryName, Model model) {
		List<MainDashBoardVO> mainDashBoardList = userService.getMainDashBoardChart(categoryName);
		Map<String, Object> map = new HashMap<>();
		List<JSONObject> jsonList = new ArrayList<>();
		
		for(MainDashBoardVO vo : mainDashBoardList) {
			map.put("name", vo.getKeyword());
			map.put("weight", vo.getBtf() * 10);
			JSONObject jsonObject = new JSONObject(map);
			jsonList.add(jsonObject);
			map.clear();
		}
		/**
		 * 카테고리 div id = (wordcloud, barchart)
		 * 전체 : all-wordcloud / all-barchart
		 * 사회 : society-wordcloud / society-barchart
		 * 연예 : entertainment-wordcloud / entertainment-barchart
		 * 경제 : economy-wordcloud / economy-barchart
		 * 정치 : politics-wordcloud / politics-barchart
		 * 스포츠 : sport-wordcloud / sport-barchart
		 * 문화 : culture-wordcloud / culture-barchart
		 * 국제 : global-wordcloud / global-barchart
		 * IT : it-wordclout / it-barchart
		 */

		return jsonList.toString();
	}
	
//	@GetMapping(value = version + "/autocomplete")
//	public String Autocomplete(Model model, @RequestParam final String term) throws IOException {
//
//		model.addAttribute("words", Arrays.asList("Saltlux", "솔트룩스", term));
//		
//		return "jsonView";
//		
//	}
	
	@ResponseBody
	@RequestMapping(value = "/risings")
	public Object KeywordRisings(Model model) throws IOException {
		List<RealtimeVO> realtimeList = userService.getRealtimeTop();
		return realtimeList;
	}
}
