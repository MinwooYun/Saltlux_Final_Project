package com.kosa.saltlux.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kosa.saltlux.HomeController;
import com.kosa.saltlux.service.IUserService;
import com.kosa.saltlux.vo.testVO;

@Controller
public class UserController {
	
	@Autowired
	IUserService userService;
	
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
	public String testRestRequest(Model model, String search) {
		try {
			search = search.replace(" ", "-");
			String reqUrl = "http://127.0.0.1:5000/results?search=" + search;
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
			System.out.println(data);
//			model.addAttribute("reqResult", data);
			List<testVO> testList = userService.getTest();
			model.addAttribute("testList", testList);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "results";
	}
	
	
	@RequestMapping("/test")
	public String test(Model model) {
		int test = userService.test();
		model.addAttribute("test", test);
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
