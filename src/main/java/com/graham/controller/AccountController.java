package com.graham.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountController {

	@RequestMapping("/Login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}
	
	@RequestMapping("/")
	public ModelAndView welcome() {
		return new ModelAndView("login");
	}
	
	
	@RequestMapping("Logout")
	public void logout() {
		
	}
	
}
