package com.parking.greencom.java;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.parking.greencom.java.DBConnectionMgr;


public class Place_data {
	private Map<String, Object> place_map = new HashMap<String, Object>();
	
	public void place(Integer numb, String company_id, String Name, String address, String detail_address, String loc_other, Integer post_no, String call, String register_cars, Integer freetime, String one_time_register_cars, Integer one_time_register_free_min, Integer per_min, Integer per_money, Integer kind_small_sale_per, Integer kind_medium_sale_per, Integer kind_large_sale_per, Integer day_price, Integer one_time_discount_per, Integer base_money, Integer member_discount_per, Integer member_plus_freetime) {
		this.place_map.put("numb", numb); //DB상의 고유 번호
		this.place_map.put("company_id", company_id); //주차장의 사업자 번호
		this.place_map.put("Name", Name); //주차장 이름
		this.place_map.put("call", call); //주차장 전화번호
		this.place_map.put("address", address); //주차장 기본 주소
		this.place_map.put("detail_address", detail_address); //주차장 상세주소
		this.place_map.put("loc_other", loc_other); //주차장 주소 참고사항
		this.place_map.put("post_no", post_no); //추차장 우편번호

		Gson gson = new Gson();
	
		List<String> register_car = gson.fromJson(register_cars, new TypeToken<List<String>>(){}.getType());
		
		this.place_map.put("one_time_register_cars", one_time_register_cars); //1회성 등록차량 ( 회차차량 특정시간 무료 )
		this.place_map.put("one_time_register_free_min", one_time_register_free_min); //회차 차량의 무료시간 (분)
		this.place_map.put("register_cars", register_car); //주차장에서 등록한 차량 ( 무료 차량, 영구 )
		this.place_map.put("freetime", freetime); //주차 무료시간 (분)
		this.place_map.put("per_min", per_min); //요금이 부과되는 기준의 분
		this.place_map.put("per_money", per_money); //특정 분당 금액
		this.place_map.put("kind_small_sale_per", kind_small_sale_per); //소형 차량 할인율 ( % )
		this.place_map.put("kind_medium_sale_per", kind_medium_sale_per); //중형 차량 할인율 ( % )
		this.place_map.put("kind_large_sale_per", kind_large_sale_per); //대형 차량 할인율 ( % )
		this.place_map.put("day_price", day_price); //일일 요금
		this.place_map.put("one_time_discount_per", one_time_discount_per); //회차 차량 할인율
		this.place_map.put("base_money", base_money); //기본 요금
		this.place_map.put("member_discount_per", member_discount_per); //회원 추가 할인
		this.place_map.put("member_plus_freetime", member_plus_freetime); //회원 추가할인
	}
	
	//데이터 별도로 리턴
	public Integer get_member_plus_freetime() {
		return (Integer) this.place_map.get("member_plus_freetime");
	}
	
	public Integer get_numb() {
		return (Integer) this.place_map.get("numb");
	}
	
	public String get_company_id() {
		return (String) this.place_map.get("company_id");
	}
	
	public String get_name() {
		return (String) this.place_map.get("Name");
	}
	
	public String get_call() {
		return (String) this.place_map.get("call");
	}
	
	public List<String> get_register_cars() {
		Gson gson = new Gson();
		List<String> register_car = gson.fromJson((String) this.place_map.get("register_cars"), new TypeToken<List<String>>(){}.getType());
		return register_car;
	}
	
	public List<String> get_one_time_register_cars() {
		Gson gson = new Gson();
		List<String> register_car = gson.fromJson((String) this.place_map.get("one_time_register_cars"), new TypeToken<List<String>>(){}.getType());
		return register_car;
	}
	
	public Integer get_one_time_register_free_min() {
		return (Integer) this.place_map.get("one_time_register_free_min");
	}
	
	public Integer get_freetime() {
		return (Integer) this.place_map.get("freetime");
	}
	
	public Integer get_per_min() {
		return (Integer) this.place_map.get("per_min");
	}
	
	public Integer get_per_money() {
		return (Integer) this.place_map.get("per_money");
	}
	
	public Integer get_kind_small_sale_per() {
		return (Integer) this.place_map.get("kind_small_sale_per");
	}
	
	public Integer get_kind_medium_sale_per() {
		return (Integer) this.place_map.get("kind_medium_sale_per");
	}
	
	public Integer get_kind_large_sale_per() {
		return (Integer) this.place_map.get("kind_large_sale_per");
	}
	
	public Integer get_day_price() {
		return (Integer) this.place_map.get("day_price");
	}
	
	public String get_location() {
		return (String) this.place_map.get("location");
	}
	
	public Map<String, Object> get_place() {
		return this.place_map;
	}
	
	//데이터 별도로 설정
	public void set_company_id(String company_id) {
		this.place_map.put("company_id", company_id);
	}
	
	public void set_name(String name) {
		this.place_map.put("Name", name);
	}
	
	public void set_call(String call) {
		this.place_map.put("call", call);
	}
	
	public void set_register_cars(List<String> cars) {
		this.place_map.put("register_cars", cars);
	}
	
	public void set_freetime(Integer time) {
		this.place_map.put("freetime", time);
	}
	
	public void set_per_min(Integer minute) {
		this.place_map.put("per_min", minute);
	}
	
	public void set_per_money(Integer price) {
		this.place_map.put("per_money", price);
	}
	
	public void set_kind_small_sale_per(Integer percent) {
		this.place_map.put("kind_small_sale_per", percent);
	}
	
	public void set_kind_medium_sale_per(Integer percent) {
		this.place_map.put("kind_medium_sale_per", percent);
	}
	
	public void set_kind_large_sale_per(Integer percent) {
		this.place_map.put("kind_large_sale_per", percent);
	}

	public void set_day_price(Integer price) {
		this.place_map.put("day_price", price);
	}

	public void set_location(String location) {
		this.place_map.put("location", location);
	}
	
	public void set_one_time_register_cars(List<String> cars) {
		this.place_map.put("one_time_register_cars", cars);
	}
	
	public void get_one_time_register_free_min(Integer minute) {
		this.place_map.put("one_time_register_free_min", minute);
	}
}
