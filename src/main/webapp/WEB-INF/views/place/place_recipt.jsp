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
	 
	Gson gson = new Gson();

	DecimalFormat decFormat = new DecimalFormat("###,###");
	Map<String, Map<String, Object>> recipt_d = pMgr.get_recipt(c_data);
	Map<String, Object> recipt = recipt_d.get("recipt");
	Map<String, List<Integer>> discount = new HashMap<String, List<Integer>>();
	discount = gson.fromJson(String.valueOf(recipt.get("discount")), new TypeToken<Map<String, List<Integer>>>(){}.getType());
    
%>
<html>
<head>
	<title>영수증</title>
	<link href="<%=cPath%>/resources/css/style.css" rel="stylesheet" type="text/css">

</head>
<script>
	function print_recipt(element) {
		element.style.display = "none";
		window.print();
		element.style.display = "";
	}
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
					<td colspan="6">
						<!-- 할인 목록 -->
						<table width="100%" cellpadding="2" cellspacing="0" border="1">
							<tr>
								<td align="center" bgcolor=#dddddd colspan="3">할인 내용</td>
							</tr>
							<tr bgcolor=#dddddd>
								<td align="center">할인 종류</td>
								<td align="center">할인률 (%)</td>
								<td align="center">할인 금액 (원)</td>
							</tr>
							<%
								for (String type : discount.keySet()) {
									String r_type = "";
									List<Integer> value = discount.get(type);
									Integer percent = value.get(0); //할인율
									String money = decFormat.format(value.get(1)); //할인 금액

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
							<%}else{}} %>
						</table>
					</td>
				</tr>
			</table>
			<input type="button" style="margin-top:20px" onclick="print_recipt(this)" value="출력하기">
			<input type="hidden" name="numb" value="${numb }">
		</form>
	</div>
</body>
</html>