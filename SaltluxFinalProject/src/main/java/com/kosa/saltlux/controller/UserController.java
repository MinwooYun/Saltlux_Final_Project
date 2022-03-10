package com.kosa.saltlux.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

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

import com.kosa.saltlux.HomeController;
import com.kosa.saltlux.repository.PageMakerDTO;
import com.kosa.saltlux.service.IUserService;
import com.kosa.saltlux.vo.Criteria;
import com.kosa.saltlux.vo.NewsVO;

@Controller
public class UserController {
	
	@Autowired
	IUserService userService;
	
	public static final String version = "api/v1";
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		model.addAttribute("data", "[{country: 'USA', value: 2025}, {country: 'KOR', value: 1500}]");
		
		return "home";
	}
	
	@RequestMapping("/results")
	public String testRestRequest(Model model, String search, Criteria cri) {
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
			List<newsVO> testList = userService.getNews(cri);
			model.addAttribute("search", search);
			model.addAttribute("testList", testList);
//			model.addAttribute("pageMaker", pageMake);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "results";
	}
	
	@RequestMapping(value = version + "main-board/{category}")
	public String tests(Model model,@PathVariable String category) {
		System.out.println(category);
		return "/";
	}
	
	
	@RequestMapping("/test")
	public String test(Model model) {
		newsVO newsVO = new newsVO();
//		int test = userService.getPageTotal();
//		model.addAttribute("test", test);
		return "test";
	}
	
	@RequestMapping("/req_image_ajax1")
	public String flaskImage(Model model) {
		return "req_image_ajax1";
	}
	
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("data", "[{country: 'USA', value: 70}, {country: 'KOR', value: 15}]");
		
		return "index";
	}
	
}
