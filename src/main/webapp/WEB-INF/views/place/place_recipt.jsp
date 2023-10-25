<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.parking.greencom.java.Car_data"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.google.gson.GsonBuilder"%>
<%@ page import="com.google.gson.reflect.TypeToken"%>
<jsp:useBean id="pMgr" class="com.parking.greencom.java.Place_Manager"/>
<%	
	String cPath = request.getContextPath();
	String usid = (String) session.getAttribute("idKey");
	request.setCharacterEncoding("UTF-8");
	
	Map<String, String> c_data = new HashMap<String, String>();
	
	c_data.put("numb", request.getParameter("numb"));
	c_data.put("car_num", request.getParameter("car_num"));
	 
	Gson gson = new Gson();
	Map<String, Map<String, Object>> recipt_d = pMgr.get_recipt(c_data);
	Map<String, Object> recipt = recipt_d.get("recipt");
	Map<String, Integer> discount = gson.fromJson(String.valueOf(recipt.get("discount")), new TypeToken<Map<String, Integer>>(){}.getType());
    
%>
<html>
<head>
	<title>영수증</title>
	<link href="<%=cPath%>/resources/css/style.css" rel="stylesheet" type="text/css">

</head>
<script>

</script>
<body bgcolor="#FFFFCC">
	<div align="center">
		<br/><br/>
		<table width="900" cellpadding="3">
			<tr style="display: flex; flex-direction: column">
				<td bgcolor=gray height="21" style="width:900px" align="center">
				 	영수증
				</td>
			</tr>
		</table>
		<form name="place_in_Frm" method="post" action="<%=cPath%>/api/place/in.do">
			<table width="900" cellpadding="2" border="1">
				<tr align="center">
					<td>차량 번호</td>
					<td>입차 시간</td>
					<td>출차 시간</td>
					<td>주차장 이용시간</td>
					<td>금액</td>
				</tr>
					<tr align="center">
					<td>
						<span>${car_num }</span>
					</td>
					<td>
						<span>${start_time }</span>
					</td>
					<td>
						<span>${end_time }</span>
					</td>
					<td>
						<span>${usage_time}</span>
					</td>
					<td>
						<span>${money } 원</span>
					</td>
				</tr>
				<tr>
					<td colspan="5">
						<table width="100%" cellpadding="2" cellspacing="0" border="1">
							<tr align="center">
								<td>주차장 이름</td>
								<td>주소</td>
								<td>발급시간</td>
							</tr>
							<tr align="center">
								<td>${Name }</td>
								<td>${total_address }</td>
								<td>${created_at }</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="5">
						<!-- 할인 목록 -->
						<table width="100%" cellpadding="2" cellspacing="0" border="1">
							<tr>
								<td align="center" bgcolor=#dddddd colspan="2">할인 내용</td>
							</tr>
							<tr bgcolor=#dddddd>
								<td align="center">할인 종류</td>
								<td align="center">할인률 (%)</td>
							</tr>
							<%
								for (String type : discount.keySet()) {
									String r_type = "";
									Integer percent = Integer.parseInt(String.valueOf(discount.get(type)));
									if (type.equals("in_time")) {
										r_type = "무료시간 내 출차";
									}
									
									if (type.equals("kind_small")) {
										r_type = "소형 차량 할인";
									}
									
									if (type.equals("kind_medium")) {
										r_type = "중형 차량 할인";
									}
									
									if (type.equals("kind_large")) {
										r_type = "대형 차량 할인";
									}
									
									if (type.equals("member")) {
										r_type = "회원 할인";
									}
									
									if (type.equals("one_time")) {
										r_type = "회차 차량 할인";
									}
									
									if (type.equals("one_time_over")) {
										r_type = "회차 차량 시간 초과분 추가 할인";
									}
									
									if (type.equals("register_car")) {
										r_type = "등록된 차량";
									}
							%>
							<tr>
								<td bgcolor=#dddddd align="center"> <span><%=r_type %></span> </td>
								<td align="center"><span><%=percent %> %</span></td>
							</tr>
							<%} %>
						</table>
					</td>
				</tr>
			</table>
			<input type="hidden" name="numb" value="${numb }">
		</form>
	</div>
</body>
</html>