package com.fiveone.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Date 2017��3��20��22:37:23
 * @author luwanding
 * @version 1.0
 * */
@Controller
public class MyController {
	
	@RequestMapping(value="home" , method = RequestMethod.GET)
	public ModelAndView query(HttpServletRequest req,HttpServletResponse res){
		ModelAndView view = new ModelAndView();
		System.out.println("���óɹ�!");
		view.setViewName("index");
		return view;
	}
}
