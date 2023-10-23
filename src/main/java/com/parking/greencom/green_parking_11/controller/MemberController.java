package com.parking.greencom.green_parking_11.controller;

import java.net.URI;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.parking.greencom.java.User_Manager;
import com.parking.greencom.java.User_data;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MemberController extends HttpServlet{
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/member/register", method = RequestMethod.GET)
	public String register(Locale locale, Model model, HttpServletRequest request) {
		return "member/register";
	}
	
	//edit String address = user.get("address") + " " + user.get("detail_address") + " " + user.get("loc_other") + " [ " + user.get("post_id") + " ]";
	@RequestMapping(value = "/member/edit")
	public String edit(Model model, @RequestParam Map<?, ?> requestMap) {
		if (requestMap.get("Numb") != null) {
			User_data user = User_Manager.getMember(Integer.parseInt(String.valueOf(requestMap.get("Numb"))));
			
			model.addAllAttributes(user.get_user());
			System.out.println(user.get_user().toString());
			
			return "member/edit";
		} else {
			return "index";
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/member/id_duplicate.do", method = RequestMethod.POST)
	public String id_duplicate(HttpServletRequest request) {
		String user_id = request.getParameter("user_id");
		
		boolean res = User_Manager.check_id_duplicate(user_id);
		return String.valueOf(res);
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/member/register.do", method = RequestMethod.POST)
	public ResponseEntity<?> register(/*HttpServletRequest request*/@RequestParam Map requestMap) {
		String res = User_Manager.register(requestMap);
		HttpHeaders headers = new HttpHeaders();
		
		if (res.equals("Success")) {			
	        headers.setLocation(URI.create("/parking/main/main"));
	        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
		} else {
			if (res.equals("error (uid_select,_update_failed)")) {
				headers.setLocation(URI.create("/parking/member/register?error=uid_error"));
		        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
			} else {
				if (res.equals("error (phone_error)")) {
					headers.setLocation(URI.create("/parking/member/register?error=phone_error"));
			        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
				} else {
					if (res.equals("error (user_insert_failed)")) {
						headers.setLocation(URI.create("/parking/member/register?error=insert_error"));
				        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
					} else {
						headers.setLocation(URI.create("/parking/member/register?error=unknown_error"));
				        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
					}
				}
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/member/login.do", method = RequestMethod.POST)
	public ResponseEntity<?> Login(@RequestParam Map requestMap, HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		HttpSession session = request.getSession();
		Map<String, Object> user = User_Manager.Login(requestMap);
		session.setAttribute("idKey", user.get("user_id"));
		session.setAttribute("Numb", user.get("numb"));
		
		if (user.get("error") != null) {
			headers.setLocation(URI.create("/parking/main/left"));
	        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
		} else {
			headers.setLocation(URI.create("/parking/main/left?error?="+user.get("error")));
	        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/member/logout.do", method = RequestMethod.POST)
	public ResponseEntity<?> Logout(@RequestParam Map requestMap, HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		HttpSession session = request.getSession();
		session.invalidate();
		headers.setLocation(URI.create("/parking/main/left"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/member/edit.do", method = RequestMethod.POST)
	public ResponseEntity<?> edit(@RequestParam Map requestMap) {
		String res = User_Manager.edit(requestMap);
		HttpHeaders headers = new HttpHeaders();
		
		if (res == "Success") {			
	        headers.setLocation(URI.create("/parking/main/main"));
	        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
		} else {
			if (res == "error (uid_select,_update_failed)") {
				headers.setLocation(URI.create("/parking/member/register?error=uid_error"));
		        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
			} else {
				if (res == "error (phone_error)") {
					headers.setLocation(URI.create("/parking/member/register?error=phone_error"));
			        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
				} else {
					if (res == "error (user_insert_failed)") {
						headers.setLocation(URI.create("/parking/member/register?error=insert_error"));
				        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
					} else {
						headers.setLocation(URI.create("/parking/member/register?error=unknown_error"));
				        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
					}
				}
			}
		}
	}
}