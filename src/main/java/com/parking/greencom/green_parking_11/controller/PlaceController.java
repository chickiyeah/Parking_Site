package com.parking.greencom.green_parking_11.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
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

import com.parking.greencom.java.Car_data;
import com.parking.greencom.java.Place_Manager;
import com.parking.greencom.java.Place_data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PlaceController {
	@RequestMapping(value="/place/list", method = RequestMethod.GET)
	public String place_list() {
		return "place/place_list";
	}
	
	@RequestMapping(value="/place/manage", method = RequestMethod.GET)
	public String place_manage(@RequestParam Map requestMap, Model model) {
		if (requestMap.get("numb") != null) {
			Map<String, Integer> data = Place_Manager.get_month_money(requestMap);
			Integer parking = Place_Manager.parking_car_count(requestMap);
			List<String> register_cars = Place_Manager.get_register_cars(requestMap);
			List<String> onetime_cars = Place_Manager.get_onetime_cars(requestMap);
			model.addAllAttributes(data);
			model.addAttribute("numb", requestMap.get("numb"));
			model.addAttribute("parking_cars", parking);
			model.addAttribute("register_car", register_cars.size());
			model.addAttribute("onetime_car", onetime_cars.size());
			return "place/place_manage";
		} else {
			return "main/main";
		}
	}
	
	@RequestMapping(value="/place/manage/list", method = RequestMethod.GET)
	public String place_manage_list() {
		return "place/place_manage_list";
	}
	
	@RequestMapping(value="/place/add", method = RequestMethod.GET)
	public String place_add() {
		return "place/place_add";
	}
	
	@RequestMapping(value="/place/in", method = RequestMethod.GET)
	public String place_in(@RequestParam Map requestMap, Model model) {
		if (requestMap.get("numb") != null) {
			Place_data place = Place_Manager.get_place(Integer.parseInt(String.valueOf(requestMap.get("numb"))));
			
			model.addAllAttributes(place.get_place());
			model.addAttribute("numb", Integer.parseInt(String.valueOf(requestMap.get("numb"))));
			
			Map<String, Object> s_place = place.get_place();
			
			String total_address = s_place.get("address") + " " + s_place.get("detail_address") + " " + s_place.get("loc_other") + " [ " + String.valueOf(s_place.get("post_no")) + " ]";
			model.addAttribute("total_address", total_address);
			
			return "place/place_in";
		} else {
			return "main/main";
		}
	}
	
	@RequestMapping(value="/place/out_search", method = RequestMethod.GET)
	public String place_out_search(@RequestParam Map requestMap, Model model) {
		if (requestMap.get("numb") != null) {
			Place_data place = Place_Manager.get_place(Integer.parseInt(String.valueOf(requestMap.get("numb"))));
			
			model.addAllAttributes(place.get_place());
			model.addAttribute("numb", Integer.parseInt(String.valueOf(requestMap.get("numb"))));
			
			Map<String, Object> s_place = place.get_place();
			
			String total_address = s_place.get("address") + " " + s_place.get("detail_address") + " " + s_place.get("loc_other") + " [ " + String.valueOf(s_place.get("post_no")) + " ]";
			model.addAttribute("total_address", total_address);
			
			return "place/place_out_search";
		} else {
			return "main/main";
		}
	}
	
	@RequestMapping(value="/place/out", method = RequestMethod.GET)
	public String place_out(@RequestParam Map requestMap, Model model) {
		if (requestMap.get("numb") != null) {
			Car_data car = Place_Manager.get_out_pre(requestMap);
			Map<String, Object> s_car = car.get_car();
			model.addAllAttributes(car.get_car());
			model.addAttribute("numb", requestMap.get("numb"));
			model.addAttribute("start_time", String.valueOf(s_car.get("start_time")).substring(0,19));
			model.addAttribute("car_num", car.get_car_num());
			return "place/place_out";
		} else {
			return "main/main";
		}
	}
	
	@RequestMapping(value="/place/out_searched", method = RequestMethod.GET)
	public String place_out_searched() {
		return "place/place_out_searched";
	}
	
	@RequestMapping(value="/place/info", method = RequestMethod.GET)
	public String place_info(@RequestParam Map requestMap, Model model) {
		if (requestMap.get("numb") != null) {
			Place_data place = Place_Manager.get_place(Integer.parseInt(String.valueOf(requestMap.get("numb"))));
			
			model.addAllAttributes(place.get_place());
			
			Map<String, Integer> t_freetime = new HashMap<String, Integer>();
			Map<String, Object> s_place = place.get_place();
			Integer freetime = place.get_freetime();
			Integer onetime_freetime = place.get_one_time_register_free_min();
			Integer member_plus_freetime = place.get_member_plus_freetime();
			t_freetime.put("freetime_hour", freetime/60);
			t_freetime.put("freetime_minute", freetime%60);
			t_freetime.put("one_time_register_free_min_hour", onetime_freetime/60);
			t_freetime.put("one_time_register_free_min_minute", onetime_freetime%60);
			t_freetime.put("member_plus_freetime_hour", member_plus_freetime/60);
			t_freetime.put("member_plus_freetime_minute", member_plus_freetime%60);
			model.addAllAttributes(t_freetime);
			
			
			return "place/place_info";
		} else {
			return "main/main";
		}
	}
	
	@RequestMapping(value="/place/edit", method = RequestMethod.GET)
	public String place_edit(@RequestParam Map requestMap, Model model) {
		if (requestMap.get("numb") != null) {
			Place_data place = Place_Manager.get_place(Integer.parseInt(String.valueOf(requestMap.get("numb"))));
			
			model.addAllAttributes(place.get_place());
			
			Map<String, Integer> t_freetime = new HashMap<String, Integer>();
			Map<String, Object> s_place = place.get_place();
			Integer freetime = place.get_freetime();
			Integer onetime_freetime = place.get_one_time_register_free_min();
			Integer member_plus_freetime = place.get_member_plus_freetime();
			t_freetime.put("freetime_hour", freetime/60);
			t_freetime.put("freetime_minute", freetime%60);
			t_freetime.put("one_time_register_free_min_hour", onetime_freetime/60);
			t_freetime.put("one_time_register_free_min_minute", onetime_freetime%60);
			t_freetime.put("member_plus_freetime_hour", member_plus_freetime/60);
			t_freetime.put("member_plus_freetime_minute", member_plus_freetime%60);
			t_freetime.put("pid", Integer.parseInt(String.valueOf(requestMap.get("numb"))));
			model.addAllAttributes(t_freetime);
			
			
			return "place/place_edit";
		} else {
			return "main/main";
		}
	}
	
	@RequestMapping(value="/place/in_out_log", method = RequestMethod.GET)
	public String place_in_out_log(@RequestParam Map requestMap) {
		return "place/place_in_out_log";
	}
	
	@RequestMapping(value="/place/recipt", method = RequestMethod.GET)
	public String recipt(@RequestParam Map requestMap, Model model) {
		Map<String, Map<String, Object>> s_recipt = Place_Manager.get_recipt(requestMap);
		Map<String, Object> recipt = s_recipt.get("recipt");
		Map<String, Object> place = s_recipt.get("place");
		Map<String, Object> s_car = s_recipt.get("car");
		model.addAllAttributes(s_car);
		model.addAllAttributes(place);
		model.addAllAttributes(recipt);
		String total_address = place.get("address") + " " + place.get("detail_address") + " " + place.get("loc_other") + " [ " + String.valueOf(place.get("post_no")) + " ]";
		model.addAttribute("total_address", total_address);
		model.addAttribute("numb", requestMap.get("numb"));
		model.addAttribute("start_time", String.valueOf(s_car.get("start_time")).substring(0,19));
		model.addAttribute("end_time", String.valueOf(s_car.get("end_time")).substring(0,19));
		model.addAttribute("created_at", String.valueOf(recipt.get("created_at")).substring(0,19));
		model.addAttribute("car_num", s_car.get("Car_num"));
		return "place/place_recipt";
	}
	
	@RequestMapping(value="/place/manage/place_register_car", method = RequestMethod.GET)
	public String place_register_car(@RequestParam Map requestMap, Model model) {
		model.addAttribute("numb", requestMap.get("numb"));
		return "place/place_register_car";
	}
	
	@RequestMapping(value="/place/manage/place_onetime_car", method = RequestMethod.GET)
	public String place_onetime_car(@RequestParam Map requestMap, Model model) {
		model.addAttribute("numb", requestMap.get("numb"));
		return "place/place_onetime_car";
	}
	
	@ResponseBody
	@RequestMapping(value="/api/place/add.do", method = RequestMethod.POST)
	public ResponseEntity<?> place_add(@RequestParam Map requestMap, HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		HttpSession session = request.getSession();
		String uid = null;
		uid = String.valueOf(session.getAttribute("Numb"));
		String res = Place_Manager.add_place(requestMap, uid);
		if (res.equals("success")) {
			headers.setLocation(URI.create("/parking/place/list"));
		       return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
		} else {
			if (res.equals("error (pid_select,_update_failed)")) {
				headers.setLocation(URI.create("/parking/place/add?error=pid_error"));
			    return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
			} else {
				if (res.equals("error (phone_error)")) {
					headers.setLocation(URI.create("/parking/place/add?error=phone_error"));
				       return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
				} else {
					if (res.equals("error (insert_failed)")) {
						headers.setLocation(URI.create("/parking/place/add?error=insert_error"));
						return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
					} else {
						headers.setLocation(URI.create("/parking/place/add?error=unknown_error"));
					    return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
					}
				}
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/api/place/edit.do", method = RequestMethod.POST)
	public ResponseEntity<?> place_edit(@RequestParam Map requestMap, HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		HttpSession session = request.getSession();
		String uid = null;
		uid = String.valueOf(session.getAttribute("Numb"));
		String res = Place_Manager.edit_place(requestMap);
		if (res.equals("success")) {
			headers.setLocation(URI.create("/parking/place/list"));
		       return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
		} else {
			if (res.equals("error (pid_select,_update_failed)")) {
				headers.setLocation(URI.create("/parking/place/edit?error=pid_error"));
			    return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
			} else {
				if (res.equals("error (phone_error)")) {
					headers.setLocation(URI.create("/parking/place/edit?error=phone_error"));
				       return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
				} else {
					if (res.equals("error (update_failed)")) {
						headers.setLocation(URI.create("/parking/place/edit?error=update_error"));
						return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
					} else {
						headers.setLocation(URI.create("/parking/place/edit?error=unknown_error"));
					    return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
					}
				}
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/place/in.do", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public String place_in(@RequestParam Map requestMap) {
		String res = Place_Manager.place_in(requestMap);
		if (res == "success") {
			return "입차 처리에 성공했습니다. 해당창을 닫아주세요.";
		} else {
			if (res.contains("already_parking")) {
				return "입차 처리에 실패했습니다. 이미 주차중인 차량입니다.\\n" +
						"차량 번호 : "+requestMap.get("car_num")+"\\n" +
						"주차 시작시간 : "+res.split("already_parking")[1].substring(0,19);
			} else {
				return "입차 처리에 실패했습니다. 알수 없는 오류입니다.";
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/place/out.do", produces="application/json;charset=UTF-8", method = RequestMethod.POST)
	public String place_out(@RequestParam Map requestMap) {
		String res = Place_Manager.place_out(requestMap);
		if (res == "success") {
			return "출차 처리에 성공했습니다. 해당창을 닫아주세요.";
		} else {
			if (res.contains("no_car")) {
				return "출차 처리에 실패했습니다. 주차중이지 않은 차량입니다.";
			} else {
				return "출차 처리에 실패했습니다. 알수 없는 오류입니다. "+res;
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/place/register_add.do" ,method = RequestMethod.POST)
	public ResponseEntity<?> register_add(@RequestParam Map requestMap, HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		HttpSession session = request.getSession();
		if (requestMap.get("numb") != null) {	
			String res = Place_Manager.add_register_cars(requestMap);
			headers.setLocation(URI.create("/parking/place/manage/place_register_car?numb="+requestMap.get("numb")));
		    return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
		} else {
			headers.setLocation(URI.create("/parking/place/manage/place_register_car?error=unknown_error&numb="+requestMap.get("numb")));
		    return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/place/register_remove.do" ,method = RequestMethod.POST)
	public ResponseEntity<?> register_remove(@RequestParam Map requestMap, HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		HttpSession session = request.getSession();
		if (requestMap.get("numb") != null) {	
			String res = Place_Manager.delete_register_cars(requestMap);
			headers.setLocation(URI.create("/parking/place/manage/place_register_car?numb="+requestMap.get("numb")));
		    return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
		} else {
			headers.setLocation(URI.create("/parking/place/manage/place_register_car?error=unknown_error&numb="+requestMap.get("numb")));
		    return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/place/onetime_add.do" ,method = RequestMethod.POST)
	public ResponseEntity<?> onetime_add(@RequestParam Map requestMap, HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		HttpSession session = request.getSession();
		if (requestMap.get("numb") != null) {	
			String res = Place_Manager.add_onetime_cars(requestMap);
			headers.setLocation(URI.create("/parking/place/manage/place_onetime_car?numb="+requestMap.get("numb")));
		    return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
		} else {
			headers.setLocation(URI.create("/parking/place/manage/place_onetime_car?error=unknown_error&numb="+requestMap.get("numb")));
		    return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/api/place/onetime_remove.do" ,method = RequestMethod.POST)
	public ResponseEntity<?> onetime_remove(@RequestParam Map requestMap, HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		HttpSession session = request.getSession();
		if (requestMap.get("numb") != null) {	
			String res = Place_Manager.delete_onetime_cars(requestMap);
			headers.setLocation(URI.create("/parking/place/manage/place_onetime_car?numb="+requestMap.get("numb")));
		    return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
		} else {
			headers.setLocation(URI.create("/parking/place/manage/place_onetime_car?error=unknown_error&numb="+requestMap.get("numb")));
		    return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
		}
	}
	
}
