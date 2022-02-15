package com.kosa.saltlux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kosa.saltlux.service.IUserService;

@Controller
public class UserController {
	
	@Autowired
	IUserService userService;
	
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
	public String index() {
		return "index";
	}
}
