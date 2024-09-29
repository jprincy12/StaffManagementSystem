package com.princy.staffmanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("title","Home Page");
		return "index";
	}
	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("title","About Page");
		return "index";
	}

}
