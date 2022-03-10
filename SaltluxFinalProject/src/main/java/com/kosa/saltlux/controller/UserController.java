package com.kosa.saltlux.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosa.saltlux.HomeController;
import com.kosa.saltlux.service.IUserService;
import com.kosa.saltlux.vo.NewsVO;
import com.kosa.saltlux.vo.CriteriaVO;
import com.kosa.saltlux.vo.MainDashBoardVO;

@Controller
public class UserController {
	
	@Autowired
	IUserService userService;
	
	public static final String version = "/api/v1/";
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		return "home";
	}
	
	@RequestMapping("/results")
	public String testRestRequest(Model model, String search, CriteriaVO cri) {
		try {
			System.out.println(search);
			String keyword = search.replace(" ", "-");
			String reqUrl = "http://127.0.0.1:5000/results?search=" + URLEncoder.encode(keyword, "UTF-8");
			URL url = new URL(reqUrl);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			
			//서버에서 응답한걸 받기위한 stream
			InputStream in = con.getInputStream();
			//바이트단위로 쪼개서 보내짐, 한글로 받기 위해
			InputStreamReader reader = new InputStreamReader(in, "UTF-8");
			BufferedReader response = new BufferedReader(reader);
			
			String str = null;
			StringBuffer buff = new StringBuffer();
			
			while((str = response.readLine()) != null) {
				buff.append(str + "\n");
			}
			String data = buff.toString().trim();
			model.addAttribute("reqResult", data);
//			int pageTotal = userService.getPageTotal();
//			PageMakerDTO pageMake = new PageMakerDTO(cri, pageTotal);
			List<NewsVO> newsList = userService.getNews(cri);
			
			model.addAttribute("search", search);
			model.addAttribute("newsList", newsList);
//			model.addAttribute("pageMaker", pageMake);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "results";
	}
	
	/**
	 * MainDashBoard테이블의 그룹별 데이터를 검색
	 * BTF가 높은 순서대로 n개의 키워드 추출
	 * @param categoryName
	 * @param model
	 * @return jsonList.toString()
	 */
	@ResponseBody
	@RequestMapping(value = version + "main-board/dash-board/wordcloud/{categoryName}", method = RequestMethod.POST, produces = "application/text; charset=UTF-8")
	public String MainDashBoardWordcloud(@PathVariable String categoryName, Model model) {
		List<MainDashBoardVO> mainDashBoardList = userService.getMainDashBoard(categoryName);
		Map<String, Object> map = new HashMap<>();
		List<JSONObject> jsonList = new ArrayList<>();
		
		for(MainDashBoardVO vo : mainDashBoardList) {
			map.put("name", vo.getKeyword());
			map.put("weight", vo.getBtf());
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
	
	@ResponseBody
	@RequestMapping(value = version + "main-board/dash-board/barchart/{categoryName}", method = RequestMethod.POST, produces = "application/text; charset=UTF-8")
	public String MainDashBoardBarchart(@PathVariable String categoryName, Model model) {
		System.out.println(categoryName);
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
		
		String data = "sample";

		return data;
	}
}
