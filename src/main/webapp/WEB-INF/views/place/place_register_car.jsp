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
	List<String> vlist = null;
	
	vlist = pMgr.get_register_cars(c_data);
	if (vlist != null) {
		totalCars = vlist.size();
	}
    
%>
<html>
<head>
	<title>등록 차량 관리</title>
	<link href="<%=cPath%>/resources/css/style.css" rel="stylesheet" type="text/css">

</head>
<script>
function car_add() {
	car_num = place_in_Frm.car_num.value;
	if (car_num.trim() != "") {
		place_in_Frm.submit();
	} else {
		alert("차량 번호를 입력해주세요.");
		place_in_Frm.car_num.focus();
	}
}

function car_remove(car_num) {
	fetch("<%=cPath%>/api/place/register_remove.do?numb="+place_in_Frm.numb.value+"&car_num="+car_num, {
		method: "POST"
	}).then((res) => {
		if (res.status === 200) {
			location.reload();
		}
	})
}
</script>
<body bgcolor="#FFFFCC">
	<div align="center">
		<br/><br/>
		<table width="900" cellpadding="3">
			<tr style="display: flex; flex-direction: column">
				<td bgcolor=gray height="21" style="width:900px" align="center">
					등록 차량 관리
				</td>
				<td bgcolor=#dddddd height="21" align="center">
					아래 정보를 확인하고 차량을 등록하거나 삭제 할수 있습니다.
				</td>
			</tr>
		</table>
		<form name="place_in_Frm" method="post" action="<%=cPath%>/api/place/register_add.do">
		<table width="900" cellpadding="2" border="1">
				<tr align="center">
					<td colspan="2" bgcolor=#dddddd> 등록할 차량 번호를 입력하고 등록 버튼을 누르세요. </td>
				</tr>
				<tr align="center">
					<td width="75%"><input style="width:100%" id="car_num" name="car_num" placeholder="차량 번호"></td>
					<td><button type="button" onclick="car_add()" style="width:100%">등록</button></td>
				</tr>
				<table width="900" cellpadding="2" border="1">
					<tr align="center">
						<td width="75%">차량 번호</td>
						<td>삭제</td>
					</tr>
					<%
						for (int i=0; i<totalCars; i++) {
							String car_num = vlist.get(i);
					%>	
						<tr align="center">
							<td>
								<%=car_num %>
							</td>
							<td width="13%">
								<button type="button" style="width:100%" onclick="car_remove('<%=car_num %>')">삭제</button>
							</td>
						</tr>
					<%} %>
				</table>
			<input type="hidden" name="numb" value="${numb }">
		</table>
		</form>
	</div>
</body>
</html>