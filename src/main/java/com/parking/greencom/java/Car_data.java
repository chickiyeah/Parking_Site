package com.parking.greencom.java;

import java.util.ArrayList;
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


public class Car_data {
	private Map<String, Object> car_map = new HashMap<String, Object>();
		
	public String get_car_num() {
		return (String) car_map.get("Car_num");
	}
	
	public String get_place() {
		return (String) car_map.get("place");
	}
	
	public String get_start_time() {
		return (String) car_map.get("start_time");
	}
	
	public String get_end_time() {
		return (String) car_map.get("end_time");
	}
	
	public String[] get_kind() {
		return (String[]) car_map.get("kind");
	}
	
	public String get_parking() {
		return (String) car_map.get("parking");
	}
	
	public Integer get_money() {
		return (Integer) car_map.get("money");
	}
	
	public void set_recipt(Integer id) {
		this.car_map.put("recipt", id);
	}
	
	public Integer get_recipt() {
		return (Integer) this.car_map.get("recipt");
	}
	
	public Map<String, Object> get_car() {
		return this.car_map;	
	}
	
	//주차 시간 계산 (분)
	private long elapse_time(String start_time_s, String end_time_s) {
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date start_time;
		Date end_time;
		long res_min = 0;
		try {
			start_time = time.parse(start_time_s);
			end_time = time.parse(end_time_s);
			
			long start_time_mil = start_time.getTime();
			long end_time_mil = end_time.getTime();
				
			long diff = end_time_mil - start_time_mil;
				
			res_min = diff / (1000 * 60);

			long usage_hour = res_min / 60;
			long usage_day = 0;
			long usage_min = res_min - (usage_hour * 60);
			while (usage_hour >= 24) {
				usage_hour = usage_hour - 24;
				usage_day = usage_day + 1;
				System.out.println("is deadrock u?");
			}
				
			this.car_map.put("usage_time",usage_day+" 일 "+usage_hour+" 시간 "+usage_min+" 분 ");
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.car_map.put("elapse_min", res_min);
		return res_min;
	}
	
	//요금 계산
	private void calculate_money(String start_time_s, String end_time_s, String place, String car_num, String kind) {
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			Date start_time = time.parse(start_time_s);
			Date end_time = time.parse(end_time_s);
			
			long start_time_mil = start_time.getTime();
			long end_time_mil = end_time.getTime();
			
			long diff = end_time_mil - start_time_mil;
			
			long res_min = diff / (1000 * 60);
			
			this.car_map.put("elapse_min", res_min);
			
			DBConnectionMgr pool = DBConnectionMgr.getInstance();
			Connection con = null;
			Connection mem_con = null;
			PreparedStatement pstmt = null;
			PreparedStatement mem_psmt = null;
			ResultSet rs = null;
			ResultSet mem_rs = null;
			String sql = null;
			String mem_sql = null;
			long freetime = 0;
			Integer per_min = 0;
			Integer per_money = 0;
			
			long unfree_time = 0;
			
			Integer kind_small_sale_per = 0;
			Integer kind_medium_sale_per = 0;
			Integer kind_large_sale_per = 0;
			Integer day_price = 0;
			Integer member_discount_per = 0;
			Integer member_plus_freetime = 0;
			String one_time_register_cars = null;
			Integer one_time_register_free_min = 0;
			Integer one_time_discount_per = 0;
			Integer base_money = 0;
			
			Map<String, List<Integer>> discount_list = new HashMap<String, List<Integer>>();
			//리스트 0 : 할인율, 1 : 할인금액
			
			String s_register_car = "";
			try {
				con = pool.getConnection();
				mem_con = pool.getConnection();
				sql = "select base_money, freetime, per_money, per_min, kind_small_sale_per, kind_medium_sale_per, kind_large_sale_per, register_cars, day_price, member_discount_per, member_plus_freetime, one_time_register_cars, one_time_register_free_min, one_time_discount_per from place where ID = ?";
				mem_sql = "select place from user where car_num = ?";
				mem_psmt = mem_con.prepareStatement(mem_sql);
				mem_psmt.setString(1, car_num);
				mem_rs = mem_psmt.executeQuery();
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, place);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					freetime = rs.getInt("freetime");
					per_min = rs.getInt("per_min");
					per_money = rs.getInt("per_money");
					s_register_car = rs.getString("register_cars");
					day_price = rs.getInt("day_price");
					member_plus_freetime = rs.getInt("member_plus_freetime");
					one_time_register_cars = rs.getString("one_time_register_cars");
					one_time_register_free_min = rs.getInt("one_time_register_free_min");
					one_time_discount_per = rs.getInt("one_time_discount_per");
					base_money = rs.getInt("base_money");
					
					kind_small_sale_per = rs.getInt("kind_small_sale_per");
					if (kind_small_sale_per > 100) {
						kind_small_sale_per = 100;
						System.out.println("주차장 번호 "+place+" 의 소형차량 할인율이 100%를 초과하여 100%로 조정되었습니다.");
					}
					
					if (kind_small_sale_per < 0) {
						kind_small_sale_per = 0;
						System.out.println("주차장 번호 "+place+" 의 소형차량 할인율이 0% 미만이여서 0%로 조정되었습니다.");
					}
					
					kind_medium_sale_per = rs.getInt("kind_medium_sale_per");
					if (kind_medium_sale_per > 100) {
						kind_medium_sale_per = 100;
						System.out.println("주차장 번호 "+place+" 의 중형차량 할인율이 100%를 초과하여 100%로 조정되었습니다.");
					}
					
					if (kind_medium_sale_per < 0) {
						kind_medium_sale_per = 0;
						System.out.println("주차장 번호 "+place+" 의 중형차량 할인율이 0% 미만이여서 0%로 조정되었습니다.");
					}
					
					kind_large_sale_per = rs.getInt("kind_large_sale_per");
					if (kind_large_sale_per > 100) {
						kind_large_sale_per = 100;
						System.out.println("주차장 번호 "+place+" 의 대형차량 할인율이 100%를 초과하여 100%로 조정되었습니다.");
					}
					
					if (kind_large_sale_per < 0) {
						kind_large_sale_per = 0;
						System.out.println("주차장 번호 "+place+" 의 대형차량 할인율이 0% 미만이여서 0%로 조정되었습니다.");
					}
					
					member_discount_per = rs.getInt("member_discount_per");
					if (member_discount_per > 100) {
						member_discount_per = 100;
						System.out.println("주차장 번호 "+place+" 의 회원 할인율이 100%를 초과하여 100%로 조정되었습니다.");
					}
					
					if (member_discount_per < 0) {
						member_discount_per = 0;
						System.out.println("주차장 번호 "+place+" 의 회원 할인율이 0% 미만이여서 0%로 조정되었습니다.");
					}
				}
				
				if (res_min <= freetime) {
					this.car_map.put("money", 0);
					List<Integer> in_time = new ArrayList<Integer>();
					in_time.add(100);
					in_time.add(base_money);
					discount_list.put("in_time", in_time);
					this.car_map.put("discount_list", discount_list);
				} else {
					long usage_hour = res_min / 60;
					long usage_day = 0;
					long usage_min = res_min - (usage_hour * 60);
					while (usage_hour >= 24) {
						usage_hour = usage_hour - 24;
						usage_day = usage_day + 1;
						System.out.println("is deadrock d?");
					}
				
					this.car_map.put("usage_time",usage_day+" 일 "+usage_hour+" 시간 "+usage_min+" 분 ");

					unfree_time = res_min - freetime;
					
					Integer cur_money = base_money;
					System.out.println("unfree_time : "+unfree_time);
					//무료 시간 확인
					
					System.out.println("res_min : "+res_min);
					System.out.println("free_time : "+freetime);
					
					//1일 이상 요금
					if (res_min >= 1440) {
						cur_money = day_price;
						unfree_time = res_min - 1440;
						while (unfree_time >= per_min) {
							cur_money = cur_money + per_money;
							unfree_time = unfree_time - per_min;
						}
					} else {
						cur_money = base_money;
						while (unfree_time >= per_min) {
							cur_money = cur_money + per_money;
							unfree_time = unfree_time - per_min;
						}
					}
					
					List<Integer> to_base = new ArrayList<Integer>();
				
					Integer base_total_money = cur_money;

					to_base.add(0);
					to_base.add(base_total_money);
					discount_list.put("base_money", to_base);
					Integer cal_deff = 0;

					Integer bf_cur = 0;
					
					Gson gson = new Gson();
					
					//회차 차량 할인	
					List<String> one_time_cars = gson.fromJson(one_time_register_cars, new TypeToken<List<String>>(){}.getType());
					if (one_time_cars.contains(car_num)) {
						if (res_min <= one_time_register_free_min) {
							List<Integer> one_time_discount = new ArrayList<Integer>();
							one_time_discount.add(100);
							one_time_discount.add(cur_money);
							discount_list.put("one_time", one_time_discount);
							cur_money = 0;
						} else {
							unfree_time = res_min - one_time_register_free_min;
							cur_money = base_money;
							while (unfree_time >= per_min) {
								cur_money = cur_money + per_money;
								unfree_time = unfree_time - per_min;
							}
							System.out.println("onetime = "+one_time_discount_per);
							cur_money = (int) (cur_money - (cur_money / (100 / one_time_discount_per)));
							List<Integer> one_time_discount = new ArrayList<Integer>();
							if (one_time_discount_per != 0) {
								one_time_discount.add(one_time_discount_per);
								cal_deff = base_total_money - cur_money;
								one_time_discount.add(cal_deff);
							}
							discount_list.put("one_time_over", one_time_discount);
						}
					}
					
					//회원 추가할인
					if (mem_rs.next()) {
						long total_free = freetime + member_plus_freetime;
						if (res_min <= total_free) {
							cur_money = 0;
						} else {
							unfree_time = res_min - total_free;
							List<Integer> member_discount = new ArrayList<Integer>(); 
							bf_cur = cur_money;
							cur_money = (int) (cur_money - (cur_money / (100 / member_discount_per)));
							if (member_discount_per != 0) {
								member_discount.add(member_discount_per);
								cal_deff = bf_cur - cur_money;
								member_discount.add(cal_deff);
								discount_list.put("member", member_discount);
							}
						}
					}
					
					//할인
					if (kind.equals("small")) {
						bf_cur = cur_money;
						cur_money = (int) (cur_money - (cur_money / (100 / kind_small_sale_per)));
						if (kind_small_sale_per != 0) {
							List<Integer> kind_small_sale = new ArrayList<Integer>();
							kind_small_sale.add(kind_small_sale_per);
							cal_deff = bf_cur - cur_money;
							kind_small_sale.add(cal_deff);
							discount_list.put("kind_small", kind_small_sale);
						}
					}
					
					if (kind.equals("medium")) {
						bf_cur = cur_money;
						cur_money = (int) (cur_money - (cur_money / (100 / kind_medium_sale_per)));
						if (kind_medium_sale_per != 0) {
							List<Integer> kind_medium_sale = new ArrayList<Integer>();
							kind_medium_sale.add(kind_medium_sale_per);
							cal_deff = bf_cur - cur_money;
							kind_medium_sale.add(cal_deff);
							discount_list.put("kind_small", kind_medium_sale);
						}
					}
					
					if (kind.equals("large")) {
						bf_cur = cur_money;
						cur_money = (int) (cur_money - (cur_money / (100 / kind_large_sale_per)));
						if (kind_large_sale_per != 0) {
							List<Integer> kind_large_sale = new ArrayList<Integer>();
							kind_large_sale.add(kind_large_sale_per);
							cal_deff = bf_cur - cur_money;
							kind_large_sale.add(cal_deff);
							discount_list.put("kind_small", kind_large_sale);
						}
					}
					
					
					//등록된 차량 확인
					System.out.println(s_register_car);
					try {
						List<String> register_car = gson.fromJson(s_register_car, new TypeToken<List<String>>(){}.getType());
						
						if (register_car.contains(car_num)) {
							discount_list.clear();
							List<Integer> register_sale = new ArrayList<Integer>();
							register_sale.add(100);
							register_sale.add(base_total_money);
							discount_list.put("register_car", register_sale);
							cur_money = 0;
						}
					} catch (Exception e) {
						System.out.println("등록된차 조희에 실패했습니다. 차량 번호 : "+car_num);
					}
					this.car_map.put("discount_list", discount_list);
					this.car_map.put("money", cur_money);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
				pool.freeConnection(mem_con, mem_psmt, mem_rs);
			}			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//차랑 설정
	public void Car(Integer numb, String car_num, String place, String start_time, String end_time, String kind, String parking, Integer money) {
		this.car_map.put("Numb", numb); //DB상의 아이디
		this.car_map.put("Car_num", car_num); //차량 번호
		this.car_map.put("place", place); //주차장 고유 번호
		this.car_map.put("start_time", start_time); //주차 시작 시간
		this.car_map.put("end_time", end_time); // 주차 종료 시간
		//주차 중인지 여부 판단
		if (end_time.contains("1000-01-01")) {
			this.car_map.put("parking", "true");
			LocalDateTime now = LocalDateTime.now();
			String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); //DB에서 시간을 가져와서 변환
			this.car_map.put("end_time", formatedNow);
			calculate_money(start_time, formatedNow, place, car_num, kind);
		} else {
			elapse_time(start_time, end_time);
			this.car_map.put("money", money);
			this.car_map.put("parking", "false");
		}
	}
	
	public void set_num(Integer num) {
		this.car_map.put("Car_num", num);
	}
	
	public void set_place(String place) {
		this.car_map.put("place", place);
	}
	
	public void set_start_time(String start_time) {
		this.car_map.put("start_time", start_time);
	}
	
	public void set_end_time(String end_time) {
		this.car_map.put("p_num", end_time);
	}
	
	public void set_kind(String kind) {
		this.car_map.put("gender", kind);
	}
	
	public void set_money(String[] money) {
		this.car_map.put("money", money);
	}
	
	public void set_parking(String parking) {
		this.car_map.put("parking", parking);
	}
}
