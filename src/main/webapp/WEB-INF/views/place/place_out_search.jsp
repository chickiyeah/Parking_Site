<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%	
	String cPath = request.getContextPath();
	request.setCharacterEncoding("UTF-8");
%>
<html>
<head>
	<title>주차장 입차중 차량 검색</title>
	<link href="<%=cPath%>/resources/css/style.css" rel="stylesheet" type="text/css">

</head>
<script>
function check() {
	if (place_in_Frm.car_num.value.trim() === "") {
		alert("차량 번호를 입력하세요.");
		place_in_Frm.car_num.focus();
	} else {
		location.href = "<%=cPath%>/place/out_searched?numb="+place_in_Frm.numb.value+"&car_num="+place_in_Frm.car_num.value;
	}
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
					아래 정보를 확인하고 입력 사항을 입력해주세요.
				</td>
			</tr>
		</table>
		<form name="place_in_Frm" method="post" action="<%=cPath%>/api/place/out_search.do">
			<table width="900" cellpadding="2">
				<tr>
					<td align="center">
						<table>
							<tr>
								<td>주차장 이름</td>
								<td>${Name }</td>
							</tr>
							<tr>
								<td>사업자 번호</td>
								<td>${company_id }</td>
							</tr>
							<tr>
								<td>주소</td>
								<td>${total_address }</td>
							</tr>
							<tr>
							<td>차량 뒷자리 4개</td>
								<td align="left">
									<input type="text" name="car_num" size="17" maxlength="4" placeholder="차량 뒷자리">
								</td>
							</tr>
							<tr>
								<td colspan="2"><hr size="1" color="gray"/></td>
							</tr>
							<tr>
								<td colspan="2" align="center">
									<input type="button" value="차량 검색 하기" onClick="check()"> 
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<input type="hidden" name="numb" value="${numb }">
		</form>
	</div>
</body>
</html>