package com.graham.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		    if (auth != null){    
		        new SecurityContextLogoutHandler().logout(request, response, auth);
		    }
		    return "redirect:login";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
		}
	}
