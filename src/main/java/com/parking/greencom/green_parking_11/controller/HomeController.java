package com.parking.greencom.green_parking_11.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController extends HttpServlet {
	
	final DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@GetMapping("/")
	public String home(Locale locale, Model model, HttpServletRequest request) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "/index";
	}
	
	
	@GetMapping("/main/main")
	public String main(Locale locale) {
		logger.info("Welcome main! The client locale is {}.", locale);
		return "main/main";
	}
	
	@GetMapping("/main/head")
	public String head(Model model) {
		model.addAttribute("green_img", "/resources/img/green.jpg");
		return "main/head";
	}
	
	@GetMapping("/main/left")
	public String left() {
		return "main/left";
	}
	
	@GetMapping("/main/footer")
	public String footer() {
		return "main/footer";
	}
}
