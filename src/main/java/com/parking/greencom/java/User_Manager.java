package com.parking.greencom.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.parking.greencom.java.DBConnectionMgr;
import com.parking.greencom.java.User_data;
import com.parking.greencom.java.Other;

public class User_Manager {
	
	public DBConnectionMgr pool;

	public static boolean check_id_duplicate(String user_id) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boolean flag = false;
		try {
			con = pool.getConnection();
			sql = "select user_id from user where user_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user_id);
			flag = pstmt.executeQuery().next();
			System.out.println(flag);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return flag;
	}

	public static String register(Map<String, String> user) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		Connection id_con = null;
		PreparedStatement pstmt = null;
		PreparedStatement id_pstmt = null;
		ResultSet rs = null;
		ResultSet id_rs = null;
		String sql = null;
		String id_sql = null;
		
		Integer uid = null;
		try {
			id_con = pool.getConnection();
			id_sql = "SELECT `id` FROM ids WHERE `name` = ?";
			id_pstmt = id_con.prepareStatement(id_sql);
			id_pstmt.setString(1, "user");
			id_rs = id_pstmt.executeQuery();
			if (id_rs.next()) {
				uid = id_rs.getInt("id") + 1;
			}
			pool.freeConnection(id_con, id_pstmt, id_rs);
			
			id_con = pool.getConnection();
			id_sql = "UPDATE ids SET `id` = ? WHERE `name` = ?";
			id_pstmt = id_con.prepareStatement(id_sql);
			id_pstmt.setInt(1, uid);
			id_pstmt.setString(2, "user");
			id_pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return "error (uid_select,_update_failed)";
		} finally {
			pool.freeConnection(id_con, id_pstmt, id_rs);
		}
		
		try {
			con = pool.getConnection();
			sql = "INSERT INTO user (`id`, `user_id`, `user_pw`, `manage_place`, `kind`, `place`, `p_num`, `birthday`, `gender`, `name`, `email`, `car_num`, `address`, `detail_address`, `loc_other`, `post_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, uid); //유저의 고유아이디
			pstmt.setString(2, user.get("user_id")); //유저의 아이디 (로그인)
			pstmt.setString(3, user.get("user_pw")); //유저의 비밀번호 (로그인)
			pstmt.setString(4, "[]"); //해당 유저가 관리하는 주차장 ( ID )
			pstmt.setString(5, "user"); //해당 유저의 등급 ( user - 일반 유저 / manager - 한개 이상의 주차장의 관리자 / super_manager - 모든 주차장의 관리자 )
			pstmt.setString(6, "[]"); //해당 유저가 회원으로 가입한 주차장 ( ID )
			
			String p_num = user.get("p_num");
			long count = Other.countChar(p_num, '-');
			if (count != 2) {
				return "error (phone_error)";
			}
			pstmt.setString(7, user.get("p_num")); //해당 유저의 휴대폰 번호
			pstmt.setString(8, user.get("birth_day")); //해당 유저의 생년월일
			pstmt.setString(9, user.get("gender")); //해당 유저의 성별
			pstmt.setString(10, user.get("name")); //해당 유저의 이름
			pstmt.setString(11, user.get("email")); //해당 유저의 이메일
			pstmt.setString(12, user.get("car_num")); //해당 유저의 차량번호
			pstmt.setString(13, user.get("address")); //해당 유저의 도로명/지번 주소
			pstmt.setString(14, user.get("detail_address")); //상세주소
			pstmt.setString(15, user.get("loc_other")); //주소 기타정보
			pstmt.setString(16, user.get("post_id")); //우편번호
			System.out.println(pstmt);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return "error (user_insert_failed)";
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return "Success";
	}
	
	public static Map<String, Object> Login(Map<String, String> data) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		User_data user = new User_data();
		
		Map<String, Object> error = new HashMap<String, Object>();
		
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM user WHERE user_id = ? AND user_pw = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, data.get("user_id"));
			pstmt.setString(2, data.get("user_pw"));
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				user.Member(rs.getInt("id"), rs.getString("user_id"), rs.getString("user_pw"), rs.getString("email"), rs.getString("name"), rs.getString("p_num"), rs.getString("gender"), null, rs.getString("kind"), rs.getString("birthday"), rs.getString("address"), rs.getString("detail_address"), rs.getString("loc_other"), rs.getInt("post_id"), rs.getString("car_num"));
				Gson gson = new Gson();
				
				List<String> register_car = gson.fromJson(rs.getString("place"), new TypeToken<List<String>>(){}.getType());
				user.set_place(register_car);
			} else {
				error.put("error", "invaild_id_password");
				return error;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return user.get_user();
	}
	
	public static User_data getMember(Integer Numb) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		User_data user = new User_data();
		
		try {
			con = pool.getConnection();
			sql = "SELECT * FROM user WHERE `id` = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Numb);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				user.Member(rs.getInt("id"), rs.getString("user_id"), rs.getString("user_pw"), rs.getString("email"), rs.getString("name"), rs.getString("p_num"), rs.getString("gender"), null, rs.getString("kind"), rs.getString("birthday"), rs.getString("address"), rs.getString("detail_address"), rs.getString("loc_other"), rs.getInt("post_id"), rs.getString("car_num"));
				Gson gson = new Gson();
				
				List<String> register_car = gson.fromJson(rs.getString("place"), new TypeToken<List<String>>(){}.getType());
				user.set_place(register_car);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return user;
	}
	
	public static String edit(Map<String, String> user) {
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		Connection con = null;
		Connection id_con = null;
		PreparedStatement pstmt = null;
		PreparedStatement id_pstmt = null;
		ResultSet rs = null;
		ResultSet id_rs = null;
		String sql = null;
		String id_sql = null;
		
		Integer uid = Integer.parseInt(String.valueOf(user.get("uid")));
		
		try {
			con = pool.getConnection();
			sql = "UPDATE user SET `user_pw` = ?, `p_num` = ?, `birthday` = ?, `gender` = ?, `name` = ?, `email` = ?, `car_num` = ?, `address` = ?, `detail_address` = ?, `loc_other` = ?, `post_id` = ? WHERE `id` = ?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, user.get("user_pw")); //유저의 비밀번호 (로그인)
			
			String p_num = user.get("p_num");
			long count = Other.countChar(p_num, '-');
			if (count != 2) {
				return "error (phone_error)";
			}
			pstmt.setString(2, user.get("p_num")); //해당 유저의 휴대폰 번호
			pstmt.setString(3, user.get("birth_day")); //해당 유저의 생년월일
			pstmt.setString(4, user.get("gender")); //해당 유저의 성별
			pstmt.setString(5, user.get("name")); //해당 유저의 이름
			pstmt.setString(6, user.get("email")); //해당 유저의 이메일
			pstmt.setString(7, user.get("car_num")); //해당 유저의 차량번호
			pstmt.setString(8, user.get("address")); //해당 유저의 도로명/지번 주소
			pstmt.setString(9, user.get("detail_address")); //상세주소
			pstmt.setString(10, user.get("loc_other")); //주소 기타정보
			pstmt.setString(11, user.get("post_id")); //우편번호
			pstmt.setInt(12, uid);

			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return "error (user_insert_failed)";
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
		return "Success";
	}
}