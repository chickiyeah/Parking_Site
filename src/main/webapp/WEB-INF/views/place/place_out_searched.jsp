<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.parking.greencom.java.Car_data"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<jsp:useBean id="pMgr" class="com.parking.greencom.java.Place_Manager"/>
<%	
	String cPath = request.getContextPath();
	String usid = (String) session.getAttribute("idKey");
	request.setCharacterEncoding("UTF-8");
	
	Map<String, String> c_data = new HashMap<String, String>();
	String numb = request.getParameter("numb");
	c_data.put("numb", request.getParameter("numb"));
	c_data.put("car_num", request.getParameter("car_num"));
	  
    int totalCars = 0;
    int listSize = 0;
	List<Car_data> vlist = null;
	
	vlist = pMgr.get_out_search(c_data);
	if (vlist != null) {
		totalCars = vlist.size();
	}
    
%>
<html>
<head>
	<title>주차장 출차 검색</title>
	<link href="<%=cPath%>/resources/css/style.css" rel="stylesheet" type="text/css">

</head>
<script>
function go_out(car_num, place) {
	location.href = "<%=cPath%>/place/out?car_num="+car_num+"&numb="+place;
}
</script>
<body bgcolor="#FFFFCC">
	<div align="center">
		<br/><br/>
		<table width="900" cellpadding="3">
			<tr style="display: flex; flex-direction: column">
				<td bgcolor=gray height="21" style="width:900px" align="center">
					주차장 출차 검색
				</td>
				<td bgcolor=#dddddd height="21" align="center">
					아래 정보를 확인하고 자신의 차를 선택해주세요.
				</td>
			</tr>
		</table>
		<form name="place_in_Frm" method="post" action="">
			<%if (totalCars == 0) { out.println ("검색된 차량이 없습니다."); } else { %>
				<table width="900" cellpadding="2" border="1">
					<tr align="center">
						<td>차량 번호</td>
						<td>입차 시간</td>
						<td>현재 금액</td>
						<td>선택</td>
					</tr>
					<%
						for (int i=0; i<totalCars; i++) {
							Map<String, Object> car = vlist.get(i).get_car();
							String car_num = String.valueOf(car.get("Car_num"));
							String start_time = String.valueOf(car.get("start_time")).substring(0,19);
							Integer money = Integer.parseInt(String.valueOf(car.get("money")));
					%>	
						<tr align="center">
							<td>
								<%=car_num %>
							</td>
							<td>
								<%=start_time %>
							</td>
							<td>
								<%=money %> 원
							</td>
							<td width="13%">
								<button onclick="go_out('<%=car_num %>', <%=numb %>)" style="width:100%" type="button">이차량 선택</button>
							</td>
						</tr>
					<%} %>
				</table>
			<%} %>
			<input type="hidden" name="numb" value="${numb }">
		</form>
	</div>
</body>
</html>