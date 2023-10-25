package com.parking.greencom.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.parking.greencom.java.DBConnectionMgr;
import com.parking.greencom.java.Place_data;

public class Place_Manager {
	public static Place_data get_place(Integer id) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Place_data place = new Place_data();
		try {
			con = pool.getConnection();
			sql = "select * from place WHERE `ID` = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Gson gson = new Gson();
				place.place(rs.getInt("ID"), rs.getString("company_id"), rs.getString("Name"), rs.getString("address"), rs.getString("detail_address"), rs.getString("loc_other"), rs.getInt("post_no"), rs.getString("call"),  null, rs.getInt("freetime"), null, rs.getInt("one_time_register_free_min"), rs.getInt("per_min"), rs.getInt("per_money"), rs.getInt("kind_small_sale_per"), rs.getInt("kind_medium_sale_per"), rs.getInt("kind_large_sale_per"), rs.getInt("day_price"), rs.getInt("one_time_discount_per"), rs.getInt("base_money"), rs.getInt("member_discount_per"), rs.getInt("member_plus_freetime"));
				place.set_register_cars(gson.fromJson((String) rs.getString("register_cars"), new TypeToken<List<String>>(){}.getType()));
				place.set_one_time_register_cars(gson.fromJson((String) rs.getString("one_time_register_cars"), new TypeToken<List<String>>(){}.getType()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return place;
	}
	
	public static List<Place_data> get_manage_place(Integer uid) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<Place_data> place_list = new ArrayList<Place_data>();
		List<String> manage_place = new ArrayList<String>();
		
		try {
			con = pool.getConnection();
			sql = "select `manage_place` from `user` WHERE `id` = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, String.valueOf(uid));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Gson gson = new Gson();
				manage_place = gson.fromJson((String) rs.getString("manage_place"), new TypeToken<List<String>>(){}.getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		String wh = "";
		for (String inte : manage_place) {
			if (wh == "") {
				wh = "`ID` = '"+inte+"'";
			} else {
				wh = wh + " OR `ID` = '"+inte+"'";
			}
		}
		
		try {
			con = pool.getConnection();
			sql = "select * from place WHERE "+wh;
			pstmt = con.prepareStatement(sql);
			System.out.println(pstmt.toString());
			rs = pstmt.executeQuery();
			
			Gson gson = new Gson();
			while (rs.next()) {
				Place_data place = new Place_data();
				place.place(rs.getInt("ID"), rs.getString("company_id"), rs.getString("Name"), rs.getString("address"), rs.getString("detail_address"), rs.getString("loc_other"), rs.getInt("post_no"), rs.getString("call"),  null, rs.getInt("freetime"), null, rs.getInt("one_time_register_free_min"), rs.getInt("per_min"), rs.getInt("per_money"), rs.getInt("kind_small_sale_per"), rs.getInt("kind_medium_sale_per"), rs.getInt("kind_large_sale_per"), rs.getInt("day_price"), rs.getInt("one_time_discount_per"), rs.getInt("base_money"), rs.getInt("member_discount_per"), rs.getInt("member_plus_freetime"));
				place.set_register_cars(gson.fromJson((String) rs.getString("register_cars"), new TypeToken<List<String>>(){}.getType()));
				place.set_one_time_register_cars(gson.fromJson((String) rs.getString("one_time_register_cars"), new TypeToken<List<String>>(){}.getType()));
				place_list.add(place);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return place_list;
	}
	
	@SuppressWarnings("null")
	public static List<Place_data> get_all_place() {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<Place_data> place_list = new ArrayList<Place_data>();
		try {
			con = pool.getConnection();
			sql = "select * from place";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			Gson gson = new Gson();
			while (rs.next()) {
				Place_data place = new Place_data();
				place.place(rs.getInt("ID"), rs.getString("company_id"), rs.getString("Name"), rs.getString("address"), rs.getString("detail_address"), rs.getString("loc_other"), rs.getInt("post_no"), rs.getString("call"),  null, rs.getInt("freetime"), null, rs.getInt("one_time_register_free_min"), rs.getInt("per_min"), rs.getInt("per_money"), rs.getInt("kind_small_sale_per"), rs.getInt("kind_medium_sale_per"), rs.getInt("kind_large_sale_per"), rs.getInt("day_price"), rs.getInt("one_time_discount_per"), rs.getInt("base_money"), rs.getInt("member_discount_per"), rs.getInt("member_plus_freetime"));
				place.set_register_cars(gson.fromJson((String) rs.getString("register_cars"), new TypeToken<List<String>>(){}.getType()));
				place.set_one_time_register_cars(gson.fromJson((String) rs.getString("one_time_register_cars"), new TypeToken<List<String>>(){}.getType()));
				place_list.add(place);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return place_list;
	}
	
	public static String add_place(Map<String, String> place, String uid) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		Connection id_con = null;
		PreparedStatement pstmt = null;
		PreparedStatement id_pstmt = null;
		ResultSet rs = null;
		ResultSet id_rs = null;
		String sql = null;
		String id_sql = null;
		
		Integer pid = null;
		try {
			id_con = pool.getConnection();
			id_sql = "SELECT `id` FROM ids WHERE `name` = ?";
			id_pstmt = id_con.prepareStatement(id_sql);
			id_pstmt.setString(1, "place");
			id_rs = id_pstmt.executeQuery();
			if (id_rs.next()) {
				pid = id_rs.getInt("id") + 1;
			}
			pool.freeConnection(id_con, id_pstmt, id_rs);
			
			id_con = pool.getConnection();
			id_sql = "UPDATE ids SET `id` = ? WHERE `name` = ?";
			id_pstmt = id_con.prepareStatement(id_sql);
			id_pstmt.setInt(1, pid);
			id_pstmt.setString(2, "place");
			id_pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return "error (pid_select,_update_failed)";
		} finally {
			pool.freeConnection(id_con, id_pstmt, id_rs);
		}
		
		try {
			con = pool.getConnection();
			sql = "INSERT INTO `place` (`ID`, `Name`, `freetime`, `company_id`, `call`, `per_money`, `per_min`, `kind_small_sale_per`, `kind_medium_sale_per`, `kind_large_sale_per`, `register_cars`, `day_price`, `address`, `detail_address`, `loc_other`, `post_no`, `one_time_register_cars`, `one_time_register_free_min`, `member_discount_per`, `member_plus_freetime`, `one_time_discount_per`, `base_money`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, pid);
			pstmt.setString(2, String.valueOf(place.get("name")));
			Integer freetime = (Integer.parseInt(String.valueOf(place.get("freetime_hour"))) * 60) + (Integer.parseInt(String.valueOf(place.get("freetime_min"))));
			pstmt.setInt(3, freetime);
			pstmt.setString(4, String.valueOf(place.get("company_id")));
			pstmt.setString(5, String.valueOf(place.get("place_num")));
			pstmt.setInt(6, Integer.parseInt(String.valueOf(place.get("per_money"))));
			pstmt.setInt(7, Integer.parseInt(String.valueOf(place.get("per_min"))));
			pstmt.setInt(8, Integer.parseInt(String.valueOf(place.get("small_car_discount"))));
			pstmt.setInt(9, Integer.parseInt(String.valueOf(place.get("medium_car_discount"))));
			pstmt.setInt(10, Integer.parseInt(String.valueOf(place.get("large_car_discount"))));
			pstmt.setString(11, "[]");
			pstmt.setInt(12, Integer.parseInt(String.valueOf(place.get("day_price"))));
			pstmt.setString(13, String.valueOf(place.get("address")));
			pstmt.setString(14, String.valueOf(place.get("detail_address")));
			pstmt.setString(15, String.valueOf(place.get("loc_other")));
			pstmt.setString(16, String.valueOf(place.get("post_no")));
			pstmt.setString(17, "[]");
			Integer onetiem_freetime = (Integer.parseInt(String.valueOf(place.get("onetime_freetime_hour"))) * 60) + (Integer.parseInt(String.valueOf(place.get("onetime_freetime_min"))));
			pstmt.setInt(18, onetiem_freetime);
			pstmt.setInt(19, Integer.parseInt(String.valueOf(place.get("mem_car_discount"))));
			Integer mem_freetime = (Integer.parseInt(String.valueOf(place.get("mem_freetime_hour"))) * 60) + (Integer.parseInt(String.valueOf(place.get("mem_freetime_min"))));
			pstmt.setInt(20, mem_freetime);
			pstmt.setInt(21, Integer.parseInt(String.valueOf(place.get("onetime_car_discount"))));
			pstmt.setInt(22, Integer.parseInt(String.valueOf(place.get("default_money"))));
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return "error (insert_failed)";
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		List<String> manage_place = new ArrayList<String>();
		
		Gson gson = new Gson();
		
		try {
			con = pool.getConnection();
			sql = "SELECT `manage_place` FROM `user` WHERE `id` = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, uid);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				manage_place = gson.fromJson(rs.getString("manage_place"), new TypeToken<List<String>>(){}.getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error (insert_failed)";
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		manage_place.add(String.valueOf(pid));
		
		try {
			con = pool.getConnection();
			sql = "UPDATE `user` SET `kind` = 'manager', `manage_place` = ? WHERE `id` = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, manage_place.toString());
			pstmt.setString(2, uid);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			return "error (insert_failed)";
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return "success";
	}
	
	public static String edit_place(Map<String, String> place) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		Integer pid = Integer.parseInt(String.valueOf(place.get("pid")));
		
		try {
			con = pool.getConnection();
			sql = "UPDATE `place` SET `Name` = ?, `freetime` = ?, `company_id` = ?, `call` = ?, `per_money` = ?, `per_min` = ?, `kind_small_sale_per` = ?, `kind_medium_sale_per` = ?, `kind_large_sale_per` = ?, `day_price` = ?, `address` = ?, `detail_address` = ?, `loc_other` = ?, `post_no` = ?, `one_time_register_free_min` = ?, `member_discount_per` = ?, `member_plus_freetime` = ?, `one_time_discount_per` = ?, `base_money` = ? WHERE `ID` = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, String.valueOf(place.get("name")));
			Integer freetime = (Integer.parseInt(String.valueOf(place.get("freetime_hour"))) * 60) + (Integer.parseInt(String.valueOf(place.get("freetime_min"))));
			pstmt.setInt(2, freetime);
			pstmt.setString(3, String.valueOf(place.get("company_id")));
			pstmt.setString(4, String.valueOf(place.get("place_num")));
			pstmt.setInt(5, Integer.parseInt(String.valueOf(place.get("per_money"))));
			pstmt.setInt(6, Integer.parseInt(String.valueOf(place.get("per_min"))));
			pstmt.setInt(7, Integer.parseInt(String.valueOf(place.get("small_car_discount"))));
			pstmt.setInt(8, Integer.parseInt(String.valueOf(place.get("medium_car_discount"))));
			pstmt.setInt(9, Integer.parseInt(String.valueOf(place.get("large_car_discount"))));
			pstmt.setInt(10, Integer.parseInt(String.valueOf(place.get("day_price"))));
			pstmt.setString(11, String.valueOf(place.get("address")));
			pstmt.setString(12, String.valueOf(place.get("detail_address")));
			pstmt.setString(13, String.valueOf(place.get("loc_other")));
			pstmt.setString(14, String.valueOf(place.get("post_no")));
			Integer onetiem_freetime = (Integer.parseInt(String.valueOf(place.get("onetime_freetime_hour"))) * 60) + (Integer.parseInt(String.valueOf(place.get("onetime_freetime_min"))));
			pstmt.setInt(15, onetiem_freetime);
			pstmt.setInt(16, Integer.parseInt(String.valueOf(place.get("mem_car_discount"))));
			Integer mem_freetime = (Integer.parseInt(String.valueOf(place.get("mem_freetime_hour"))) * 60) + (Integer.parseInt(String.valueOf(place.get("mem_freetime_min"))));
			pstmt.setInt(17, mem_freetime);
			pstmt.setInt(18, Integer.parseInt(String.valueOf(place.get("onetime_car_discount"))));
			pstmt.setInt(19, Integer.parseInt(String.valueOf(place.get("default_money"))));
			pstmt.setInt(20, pid);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return "error (update_failed)";
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return "success";
	}
	
	public static String place_in(Map<String, String> car_data) {
		String place = car_data.get("numb");
		String car_num = car_data.get("car_num");
		String kind = car_data.get("kind");
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		Connection id_con = null;
		PreparedStatement pstmt = null;
		PreparedStatement id_pstmt = null;
		ResultSet rs = null;
		ResultSet id_rs = null;
		String sql = null;
		String id_sql = null;
		
		try {
			con = pool.getConnection();
			sql = "SELECT `start_time` FROM `parking_cars` WHERE `car_num` = ? AND `place` = ? AND `parking` = 'true'";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, car_num);
			pstmt.setString(2, place);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return "already_parking"+rs.getString("start_time");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error (insert_failed)";
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		
		Integer cid = null;
		try {
			id_con = pool.getConnection();
			id_sql = "SELECT `id` FROM ids WHERE `name` = ?";
			id_pstmt = id_con.prepareStatement(id_sql);
			id_pstmt.setString(1, "cars");
			id_rs = id_pstmt.executeQuery();
			if (id_rs.next()) {
				cid = id_rs.getInt("id") + 1;
			}
			pool.freeConnection(id_con, id_pstmt, id_rs);
			
			id_con = pool.getConnection();
			id_sql = "UPDATE ids SET `id` = ? WHERE `name` = ?";
			id_pstmt = id_con.prepareStatement(id_sql);
			id_pstmt.setInt(1, cid);
			id_pstmt.setString(2, "cars");
			id_pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return "error (pid_select,_update_failed)";
		} finally {
			pool.freeConnection(id_con, id_pstmt, id_rs);
		}
		
		
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		try {
			con = pool.getConnection();
			sql = "INSERT INTO `parking_cars` (`id`, `car_num`, `start_time`, `parking`, `kind`, `place`) VALUES (?, ?, ? ,? ,? ,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cid);
			pstmt.setString(2, car_num);
			pstmt.setString(3, formatedNow);
			pstmt.setString(4, "true");
			pstmt.setString(5, kind);
			pstmt.setString(6, place);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return "error (insert_failed)";
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return "success";
	}
	
	public static List<Car_data> get_out_search(Map<String, String> request) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<Car_data> c_list = new ArrayList<Car_data>();
		
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM `parking_cars` WHERE `place` = ? AND `parking` = 'true' AND `car_num` LIKE ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, request.get("numb"));
			pstmt.setString(2, "%"+request.get("car_num"));
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Car_data car = new Car_data();
				car.Car(rs.getInt("id"), rs.getString("car_num"), rs.getString("place"), rs.getString("start_time"), rs.getString("end_time"), rs.getString("kind"), rs.getString("parking"), rs.getInt("money"));
				c_list.add(car);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return c_list;
	}
	
	public static Car_data get_out_pre(Map<String, String> request) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Car_data car = new Car_data();
		
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM `parking_cars` WHERE `place` = ? AND `car_num` = ? AND `parking` = 'true'";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, request.get("numb"));
			pstmt.setString(2, request.get("car_num"));
			rs = pstmt.executeQuery();
			
			if (rs.next()) {	
				car.Car(rs.getInt("id"), rs.getString("car_num"), rs.getString("place"), rs.getString("start_time"), rs.getString("end_time"), rs.getString("kind"), rs.getString("parking"), rs.getInt("money"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return car;
	}
	
	public static String place_out(Map<String, String> request) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Car_data car = new Car_data();
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM `parking_cars` WHERE `place` = ? AND `car_num` = ? AND `parking` = 'true'";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, request.get("numb"));
			pstmt.setString(2, request.get("car_num"));
			rs = pstmt.executeQuery();
			
			if (rs.next()) {	
				car.Car(rs.getInt("id"), rs.getString("car_num"), rs.getString("place"), rs.getString("start_time"), rs.getString("end_time"), rs.getString("kind"), rs.getString("parking"), rs.getInt("money"));
			} else {
				return "no_car";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		delete_onetime_cars(request);
		
		Map<String, Object> car_m = car.get_car();
		
		Integer cid = null;
		try {
			con = pool.getConnection();
			sql = "SELECT `id` FROM ids WHERE `name` = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "recipt");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				cid = rs.getInt("id") + 1;
			}
			pool.freeConnection(con, pstmt, rs);
			
			con = pool.getConnection();
			sql = "UPDATE ids SET `id` = ? WHERE `name` = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cid);
			pstmt.setString(2, "recipt");
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return "error (pselect,_update_failed)";
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		try {
			con = pool.getConnection();
			sql = "INSERT INTO recipt (`id`, `place`, `car_num`, `discount`, `money`, `created_at`) VALUES (?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cid);
			pstmt.setString(2, request.get("numb"));
			pstmt.setString(3, request.get("car_num"));
			pstmt.setString(4, String.valueOf(car_m.get("discount_list")));
			pstmt.setInt(5, Integer.parseInt(String.valueOf(car_m.get("money"))));
			pstmt.setString(6, formatedNow);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		try {
			con = pool.getConnection();
			sql = "UPDATE `parking_cars` SET `parking` = 'false', `money` = ?, `end_time` = ?, `recipt_id` = ? WHERE `place` = ? AND `car_num` = ? AND `parking` = 'true'";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, String.valueOf(car_m.get("money")));
			pstmt.setString(2, formatedNow);
			pstmt.setInt(3, cid);
			pstmt.setString(4, request.get("numb"));
			pstmt.setString(5, request.get("car_num"));
			pstmt.executeUpdate();	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return "success";
	}
	
	public static Map<String, Integer> get_month_money(Map<String, String> request) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Map<String, Integer> res = new HashMap<String, Integer>();
		LocalDateTime now = LocalDateTime.now();
		String formatedyear = now.format(DateTimeFormatter.ofPattern("yyyy"));
		String formatedmonth = now.format(DateTimeFormatter.ofPattern("MM"));
		String formatedday = now.format(DateTimeFormatter.ofPattern("dd"));
		String numb = String.valueOf(request.get("numb"));
		try {
			con = pool.getConnection();
			sql = "SELECT `money`, `created_at` FROM `recipt` WHERE `place` = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, numb);
			rs = pstmt.executeQuery();
			Integer m_1 = 0;
			Integer m_2 = 0;
			Integer m_3 = 0;
			Integer m_4 = 0;
			Integer m_5 = 0;
			Integer m_6 = 0;
			Integer m_7 = 0;
			Integer m_8 = 0;
			Integer m_9 = 0;
			Integer m_10 = 0;
			Integer m_11 = 0;
			Integer m_12 = 0;
			
			Integer today = 0;
			Integer today_use = 0;
			while (rs.next()) {
				LocalDate date = LocalDate.parse(rs.getString("created_at").split(" ")[0]);
				if (date.getYear() == Integer.parseInt(formatedyear)) {
					if (date.getMonthValue() == Integer.parseInt(formatedmonth)) {
						if (date.getDayOfMonth() == Integer.parseInt(formatedday)) {
							today = today + rs.getInt("money");
							today_use = today_use + 1;
						}
					}
					
					if (date.getMonthValue() == 1) {
						m_1 = m_1 + rs.getInt("money");
					}
					
					if (date.getMonthValue() == 2) {
						m_2 = m_2 + rs.getInt("money");
					}
					
					if (date.getMonthValue() == 3) {
						m_3 = m_3 + rs.getInt("money");
					}
					
					if (date.getMonthValue() == 4) {
						m_4 = m_4 + rs.getInt("money");
					}
					
					if (date.getMonthValue() == 5) {
						m_5 = m_5 + rs.getInt("money");
					}
					
					if (date.getMonthValue() == 6) {
						m_6 = m_6 + rs.getInt("money");
					}
					
					if (date.getMonthValue() == 7) {
						m_7 = m_7 + rs.getInt("money");
					}
					
					if (date.getMonthValue() == 8) {
						m_8 = m_8 + rs.getInt("money");
					}
					
					if (date.getMonthValue() == 9) {
						m_9 = m_9 + rs.getInt("money");
					}
					
					if (date.getMonthValue() == 10) {
						m_10 = m_10 + rs.getInt("money");
					}
					
					if (date.getMonthValue() == 11) {
						m_11 = m_11 + rs.getInt("money");
					}
					
					if (date.getMonthValue() == 12) {
						m_12 = m_12 + rs.getInt("money");
					}
				} else {
					System.out.println("Year False");
					System.out.println(String.valueOf(date.getYear()));
				}
			}
			
			res.put("m_1", m_1);
			res.put("m_2", m_2);
			res.put("m_3", m_3);
			res.put("m_4", m_4);
			res.put("m_5", m_5);
			res.put("m_6", m_6);
			res.put("m_7", m_7);
			res.put("m_8", m_8);
			res.put("m_9", m_9);
			res.put("m_10", m_10);
			res.put("m_11", m_11);
			res.put("m_12", m_12);
			res.put("today", today);
			res.put("today_use", today_use);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return res;
	}
	
	public static Integer parking_car_count(Map<String, String> request) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		Integer car_count = 0;

		String numb = String.valueOf(request.get("numb"));
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM `parking_cars` WHERE `place` = ? AND `parking` = 'true'";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, numb);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {	
				car_count = car_count + 1;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return car_count;
	}
	
	public static List<Map<String, Object>> get_in_out(Map<String, String> request) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Car_data car = new Car_data();
		
		LocalDateTime now = LocalDateTime.now();
		
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();

		String numb = String.valueOf(request.get("numb"));
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM `parking_cars` WHERE `place` = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, numb);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				car = new Car_data();
				car.Car(rs.getInt("id"), rs.getString("car_num"), rs.getString("place"), rs.getString("start_time"), rs.getString("end_time"), rs.getString("kind"), rs.getString("parking"), rs.getInt("money"));
				if (String.valueOf(rs.getString("parking")).equals("false")) {
					car.set_recipt(rs.getInt("recipt_id"));
				}
				res.add(car.get_car());

				System.out.println("car info generating");
			}
			System.out.println("car info generated");				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return res;
	}
	
	public static Map<String, Map<String, Object>> get_recipt(Map<String, String> request) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Car_data car = new Car_data();
		Place_data place = new Place_data();
		
		String numb = String.valueOf(request.get("numb"));
		
		Map<String, Map<String, Object>> res = new HashMap<String, Map<String, Object>>();
		
		Map<String, Object> recipt = new HashMap<String, Object>();
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM `recipt` WHERE `id` = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, numb);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				recipt.put("id", numb);
				recipt.put("money", rs.getInt("money"));
				recipt.put("car_num", rs.getString("car_num"));
				recipt.put("place", rs.getString("place"));
				recipt.put("discount", rs.getString("discount"));
				recipt.put("created_at", rs.getString("created_at"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		res.put("recipt", recipt);
		
		try {
			con = pool.getConnection();
			sql = "select * from place WHERE `ID` = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(String.valueOf(recipt.get("place"))));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Gson gson = new Gson();
				place.place(rs.getInt("ID"), rs.getString("company_id"), rs.getString("Name"), rs.getString("address"), rs.getString("detail_address"), rs.getString("loc_other"), rs.getInt("post_no"), rs.getString("call"),  null, rs.getInt("freetime"), null, rs.getInt("one_time_register_free_min"), rs.getInt("per_min"), rs.getInt("per_money"), rs.getInt("kind_small_sale_per"), rs.getInt("kind_medium_sale_per"), rs.getInt("kind_large_sale_per"), rs.getInt("day_price"), rs.getInt("one_time_discount_per"), rs.getInt("base_money"), rs.getInt("member_discount_per"), rs.getInt("member_plus_freetime"));
				place.set_register_cars(gson.fromJson((String) rs.getString("register_cars"), new TypeToken<List<String>>(){}.getType()));
				place.set_one_time_register_cars(gson.fromJson((String) rs.getString("one_time_register_cars"), new TypeToken<List<String>>(){}.getType()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		res.put("place", place.get_place());
		
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM `parking_cars` WHERE `recipt_id` = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, numb);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				car = new Car_data();
				car.Car(rs.getInt("id"), rs.getString("car_num"), rs.getString("place"), rs.getString("start_time"), rs.getString("end_time"), rs.getString("kind"), rs.getString("parking"), rs.getInt("money"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		res.put("car", car.get_car());
		
		return res;
	}
	
	public static List<String> get_register_cars(Map<String, String> request) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		List<String> res = new ArrayList<String>();

		String numb = String.valueOf(request.get("numb"));
		
		Gson gson = new Gson();
		try {
			con = pool.getConnection();
			sql = "SELECT `register_cars` FROM `place` WHERE `ID` = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, numb);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				res = gson.fromJson(rs.getString("register_cars"), new TypeToken<List<String>>(){}.getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return res;
	}
	
	public static String add_register_cars(Map<String, String> request) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		List<String> res = new ArrayList<String>();

		String numb = String.valueOf(request.get("numb"));
		
		Gson gson = new Gson();
		try {
			con = pool.getConnection();
			sql = "SELECT `register_cars` FROM `place` WHERE `ID` = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, numb);
			rs = pstmt.executeQuery();
			System.out.println("numb : "+numb);
			if (rs.next()) {
				res = gson.fromJson(rs.getString("register_cars"), new TypeToken<List<String>>(){}.getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		List<String> res_2 = new ArrayList<String>();
		for (String item : res) {
			res_2.add("\""+item+"\"");
		}
		res_2.add("\""+request.get("car_num")+"\"");
		try {
			con = pool.getConnection();
			sql = "UPDATE `place` SET `register_cars` = ? WHERE `ID` = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, res_2.toString());
			pstmt.setString(2, numb);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return "success";
	}
	
	public static String delete_register_cars(Map<String, String> request) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		List<String> res = new ArrayList<String>();

		String numb = String.valueOf(request.get("numb"));
		
		Gson gson = new Gson();
		try {
			con = pool.getConnection();
			sql = "SELECT `register_cars` FROM `place` WHERE `ID` = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, numb);
			rs = pstmt.executeQuery();
			System.out.println("numb : "+numb);
			if (rs.next()) {
				res = gson.fromJson(rs.getString("register_cars"), new TypeToken<List<String>>(){}.getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		List<String> res_2 = new ArrayList<String>();
		for (String item : res) {
			res_2.add("\""+item+"\"");
		}
		
		if (res_2.contains("\""+request.get("car_num"+"\""))) {
			res_2.remove("\""+request.get("car_num")+"\"");
			try {
				con = pool.getConnection();
				sql = "UPDATE `place` SET `register_cars` = ? WHERE `ID` = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, res_2.toString());
				pstmt.setString(2, numb);
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
			}
		}
		
		return "success";
	}
	
	public static List<String> get_onetime_cars(Map<String, String> request) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		List<String> res = new ArrayList<String>();

		String numb = String.valueOf(request.get("numb"));
		
		Gson gson = new Gson();
		try {
			con = pool.getConnection();
			sql = "SELECT `one_time_register_cars` FROM `place` WHERE `ID` = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, numb);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				res = gson.fromJson(rs.getString("one_time_register_cars"), new TypeToken<List<String>>(){}.getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return res;
	}
	
	public static String add_onetime_cars(Map<String, String> request) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		List<String> res = new ArrayList<String>();

		String numb = String.valueOf(request.get("numb"));
		
		Gson gson = new Gson();
		try {
			con = pool.getConnection();
			sql = "SELECT `one_time_register_cars` FROM `place` WHERE `ID` = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, numb);
			rs = pstmt.executeQuery();
			System.out.println("numb : "+numb);
			if (rs.next()) {
				res = gson.fromJson(rs.getString("one_time_register_cars"), new TypeToken<List<String>>(){}.getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		List<String> res_2 = new ArrayList<String>();
		for (String item : res) {
			res_2.add("\""+item+"\"");
		}
		res_2.add("\""+request.get("car_num")+"\"");
		try {
			con = pool.getConnection();
			sql = "UPDATE `place` SET `one_time_register_cars` = ? WHERE `ID` = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, res_2.toString());
			pstmt.setString(2, numb);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return "success";
	}
	
	public static String delete_onetime_cars(Map<String, String> request) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		
		List<String> res = new ArrayList<String>();

		String numb = String.valueOf(request.get("numb"));
		
		Gson gson = new Gson();
		try {
			con = pool.getConnection();
			sql = "SELECT `one_time_register_cars` FROM `place` WHERE `ID` = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, numb);
			rs = pstmt.executeQuery();
			System.out.println("numb : "+numb);
			if (rs.next()) {
				res = gson.fromJson(rs.getString("one_time_register_cars"), new TypeToken<List<String>>(){}.getType());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		List<String> res_2 = new ArrayList<String>();
		for (String item : res) {
			res_2.add("\""+item+"\"");
		}
		
		if (res_2.contains("\""+request.get("car_num")+"\"")) {
			res_2.remove("\""+request.get("car_num")+"\"");
			try {
				con = pool.getConnection();
				sql = "UPDATE `place` SET `one_time_register_cars` = ? WHERE `ID` = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, res_2.toString());
				pstmt.setString(2, numb);
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
			}
		}
		
		return "success";
	}
	
}
