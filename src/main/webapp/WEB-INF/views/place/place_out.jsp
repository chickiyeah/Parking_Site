<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.parking.greencom.java.Car_data"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.google.gson.GsonBuilder"%>
<%@ page import="com.google.gson.reflect.TypeToken"%>
<%@ page import="java.text.DecimalFormat"%>
<jsp:useBean id="pMgr" class="com.parking.greencom.java.Place_Manager"/>
<%	
	String cPath = request.getContextPath();
	String usid = (String) session.getAttribute("idKey");
	request.setCharacterEncoding("UTF-8");
	
	Map<String, String> c_data = new HashMap<String, String>();
	
	c_data.put("numb", request.getParameter("numb"));
	c_data.put("car_num", request.getParameter("car_num"));
	  
	Car_data car_d = pMgr.get_out_pre(c_data);
	Map<String, Object> car = car_d.get_car();
	
	Gson gson = new Gson();

	DecimalFormat decFormat = new DecimalFormat("###,###");
	System.out.println(car.get("discount_list"));
	Map<String, List<Integer>> discount = new HashMap<String, List<Integer>>();
	discount = gson.fromJson(String.valueOf(car.get("discount_list")), new TypeToken<Map<String, List<Integer>>>(){}.getType());
    
%>
<html>
<head>
	<title>주차장 출차</title>
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
					주차장 출차
				</td>
				<td bgcolor=#dddddd height="21" align="center">
					아래 정보를 확인하고 출차 버튼을 클릭해주세요.
				</td>
			</tr>
		</table>
		<form name="place_in_Frm" method="post" action="<%=cPath%>/api/place/out.do">
			<table width="900" cellpadding="2" border="1">
				<tr align="center">
					<td>차량 번호</td>
					<td>입차 시간</td>
					<td>출차 시간</td>
					<td>주차장 이용 시간</td>
					<td>할인 전 총 금액</td>
					<td>금액</td>
				</tr>
					<tr align="center">
					<td>
						<span name="car_num">${car_num }</span>
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
						<span>${total_base_money} 원</span>
					</td>
					<td>
						<span>${money } 원</span>
					</td>
				</tr>
				<tr>
					<td colspan="6">
						<!-- 할인 목록 -->
						<table width="100%" cellpadding="2" cellspacing="0" border="1">
							<tr>
								<td align="center" bgcolor=#dddddd colspan="3">할인 내용</td>
							</tr>
							<tr bgcolor=#dddddd>
								<td align="center" width="50%">할인 종류</td>
								<td align="center" width="20%">할인률 (%)</td>
								<td align="center" width="30%">할인 금액 (원)</td>
							</tr>
								<%
								if (discount != null) {
									for (String type : discount.keySet()) {
										String r_type = "";
										List<Integer> value_s = discount.get(type);
										Integer percent = value_s.get(0); //할인율
										String money = decFormat.format(value_s.get(1)); //할인 금액

										if (type.equals("base_money") == false) {
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
									<td align="center"><span><%=money %> 원</span></td>
								</tr>
								<%} else {} }  }%>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="6"><button type="submit" style="width:100%">출차하기</button></td>
				</tr>
			</table>
			<input type="hidden" name="car_num" value="${car_num }">
			<input type="hidden" name="numb" value="${numb }">
		</form>
	</div>
</body>
</html>