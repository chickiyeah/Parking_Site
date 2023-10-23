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
	List<Map<String, Object>> vlist = null;
	
	vlist = pMgr.get_in_out(c_data);
	if (vlist != null) {
		totalCars = vlist.size();
	}
    
%>
<html>
<head>
	<title>입출차 내역</title>
	<link href="<%=cPath%>/resources/css/style.css" rel="stylesheet" type="text/css">

</head>
<script>
function view_recipt(id) {
	url = "<%=cPath%>/place/recipt?numb="+id
	window.open(url, "영수증", "width=950,height=380")
}
</script>
<body bgcolor="#FFFFCC">
	<div align="center">
		<br/><br/>
		<table width="900" cellpadding="3">
			<tr style="display: flex; flex-direction: column">
				<td bgcolor=gray height="21" style="width:900px" align="center">
					입출차 내역
				</td>
				<td bgcolor=#dddddd height="21" align="center">
					아래 정보에서 입출차 내역을 확인하세요.
				</td>
			</tr>
		</table>
		<form name="place_in_Frm" method="post" action="">
			<%if (totalCars == 0) { out.println ("데이터가 없습니다."); } else { %>
				<table width="900" cellpadding="2" border="1">
					<tr align="center">
						<td>차량 번호</td>
						<td>입차 시간</td>
						<td>출차 시간</td>
						<td>상태</td>
						<td>금액</td>
						<td>영수증 확인하기</td>
					</tr>
					<%
						for (int i=0; i<totalCars; i++) {
							Map<String, Object> car = vlist.get(i);
							String car_num = String.valueOf(car.get("Car_num"));
							String start_time = String.valueOf(car.get("start_time")).substring(0,19);
							String end_time = String.valueOf(car.get("end_time")).substring(0,19);
							String status = "";
							Integer recipt = 0;
							if (String.valueOf(car.get("parking")) == "true") {
								status = "현재 주차중";
							} else {
								recipt = Integer.parseInt(String.valueOf(car.get("recipt")));
								status = "결제 완료";
							}
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
								<%=end_time %>
							</td>								
							<td>
								<%=status %>	
							</td>
							<td>
								<%=money %> 원
							</td>
							<%if (status == "결제 완료") {%>
								<td width="13%">
									<button onclick="view_recipt(<%=recipt %>)" style="width:100%" type="button">영수증 확인</button>
								</td>
							<%} else { %>
								<td width="13%">
									<button style="width:100%" type="button">현재 주차중</button>
								</td>
							<%} %>
						</tr>
					<%} %>
				</table>
			<%} %>
			<input type="hidden" name="numb" value="${numb }">
		</form>
	</div>
</body>
</html>