package com.graham.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountController {	
	
	//Display login page
	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}
	
	//Default route
	@RequestMapping("/")
	public String defaultRoute() {
		return "redirect:/cluster/";
	}
	
}
