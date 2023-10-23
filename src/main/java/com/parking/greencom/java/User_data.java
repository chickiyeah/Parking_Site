package com.parking.greencom.java;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User_data {
	private Map<String, Object> user_map = new HashMap<String, Object>();
	
	public String get_Name() {
		return (String) user_map.get("name");
	}
	
	public String get_p_num() {
		return (String) user_map.get("p_num");
	}
	
	public String get_email() {
		return (String) user_map.get("email");
	}
	
	public String get_gender() {
		return (String) user_map.get("gender");
	}
	
	public String[] get_place() {
		return (String[]) user_map.get("place");
	}
	
	public String get_job() {
		return (String) user_map.get("job");
	}

	public String get_Usid() {
		return (String) user_map.get("user_id");
	}
	
	public String get_birth() {
		return (String) user_map.get("birth_day");
	}
	
	public Integer get_Numb() {
		return (Integer) user_map.get("numb");
	}
	
	public String get_kind() {
		return (String) user_map.get("kind");
	}
	
	public String get_address() {
		return (String) user_map.get("address");
	}
	
	public Map<String, Object> get_user() {
		return this.user_map;	
	}

	
	//유저 설정
	public void Member(Integer numb, String usid, String pw, String email, String name, String p_num, String gender, String[] place, String kind, String birthday, String address, String detail_address, String loc_other, Integer post_no, String car_num) {
		this.user_map.put("numb", numb); //DB상의 고유 번호
		this.user_map.put("user_id", usid); //유저의 아이디
		this.user_map.put("user_pw", pw); //유저의 비밀번호
		this.user_map.put("email", email); //유저의 이메일
		this.user_map.put("kind", kind); //유저의 권한 (user ( 일반 유저 ), manager ( 일반 관리자 ), super_manager ( 총 관리자 ) )
		this.user_map.put("name", name); //유저의 이름
		this.user_map.put("p_num", p_num); //유저의 휴대폰 번호
		this.user_map.put("gender", gender); //유저의 성별
		this.user_map.put("place", place); //유저가 담당하는 주차장의 번호들
		this.user_map.put("birth_day", birthday); //유저의 생일
		this.user_map.put("address", address); //유저의 사는위치
		this.user_map.put("detail_address", detail_address); //상세 주소
		this.user_map.put("loc_other", loc_other); //기타 주소정보
		this.user_map.put("post_no", post_no); //우편번호
		this.user_map.put("car_num", car_num); //유저의 차량번호
	}
	
	
	public void set_name(String name) {
		this.user_map.put("name", name);
	}
	
	public void set_email(String email) {
		this.user_map.put("email", email);
	}
	
	public void set_p_num(String p_num) {
		this.user_map.put("p_num", p_num);
	}
	
	public void set_gender(String gender) {
		this.user_map.put("gender", gender);
	}
	
	public void set_place(List<String> register_car) {
		this.user_map.put("place", register_car);
	}
	
	public void set_pw(String pw) {
		this.user_map.put("user_pw", pw);
	}
	
	public void set_birth(String birth) {
		this.user_map.put("birth_day", birth);
	}
	
	public void set_kind(String kind) {
		this.user_map.put("kind", kind);
	}
}