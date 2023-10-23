<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.parking.greencom.java.Place_data"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<jsp:useBean id="pMgr" class="com.parking.greencom.java.Place_Manager"/>
<%	
	String cPath = request.getContextPath();
	String usid = (String) session.getAttribute("idKey");
	request.setCharacterEncoding("UTF-8");
	  
    int totalPlace = 0; //전체 주차랑
    int listSize = 0;    //현재 읽어온 주차장의 수
	List<Place_data> vlist = null;
	
	vlist = pMgr.get_all_place();
	if (vlist != null) {
		totalPlace = vlist.size();
	}

	String info_url = cPath + "/place/info?id=";
    
%>

<html>
<head>
	<title>주차장 목록</title>
	<link href="<%=cPath%>/resources/css/style.css" rel="stylesheet" type="text/css">
<script>
	function place_info(numb) {
		document.readFrm.numb.value=numb;
		document.readFrm.method="get";
		document.readFrm.action="<%=info_url%>"+numb;
		document.readFrm.target="content";
		document.readFrm.submit();
	}
	
	function place_in(numb) {
		url = "<%=cPath%>/place/in?numb="+numb;
		window.open(url, "입차 처리", "width=950,height=380")
	}
	
	function place_out_search(numb) {
		url = "<%=cPath%>/place/out_search?numb="+numb;
		window.open(url, "출차 검색", "width=950,height=900")
	}
</script>
</head>
<body leftmargin="0" topmargin="0" bgcolor="#FFFFCC">

<div align="center">
    <br/>
		<h2>주차장 목록</h2>
    <br>
	<table align="center" width="1200" border="1">
		<tr>
			<td>주차장 수 : <%=totalPlace%></td>
		</tr>
	</table>
	<table align="center" width="1200" cellpadding="3"	 border="1">
		<tr>
			<td align="center" colspan="3">
			<%
				  if (vlist == null) {
					out.println("등록된 주차장이 없습니다.");
				  } else {
			%>
				  <table width="100%" cellpadding="2" cellspacing="0" border="1">
					<tr align="center" bgcolor="#D0D0D0" height="120%">
						<td>번 호</td>
						<td>주차장 이름</td>
						<td>사업자 번호</td>
						<td>주소</td>
						<td>전화번호</td>
						<td>입차</td>
						<td>출차</td>
					</tr>
					<%
						  for (int i = 0;i<totalPlace; i++) {
							Map<String, Object> bean = vlist.get(i).get_place();
							String numb = String.valueOf(bean.get("numb"));
							String name = (String) bean.get("Name");
							String company_id = (String) bean.get("company_id");
							String address = bean.get("address") + " " + bean.get("detail_address") + " " + bean.get("loc_other") + " [ " + String.valueOf(bean.get("post_no")) + " ]";
							String call = (String) bean.get("call");
					%>
					<tr>
						<td align="center">
						   <a href="javascript:place_info(<%=numb%>)"><%=numb%></a>
						</td>
						<td align="center">
 						   <a href="javascript:place_info(<%=numb%>)"><%=name%></a>
						</td>
						<td align="center">
						   <%=company_id%>
						</td>
						<td align="center" width="60%">
						   <%=address%>
						</td>
						<td align="center">
						   <%=call%>
						</td>
						<td align="center">
 						   <a href="javascript:place_in(<%=numb%>)">입차</a>
						</td>
						<td align="center">
 						   <a href="javascript:place_out_search(<%=numb%>)">출차</a>
						</td>
					</tr>
					<%}//for%>
				</table> <%
 			}//if
 		%>
			</td>
		</tr>
		</table>
	<form name="readFrm" method="get">
	    <br>
	    <%if (usid != null) {%>
			<input type="button" value="신규 주차장 등록" onClick="location.href='add'">
		<%} %>
		<input type="button" value="새로고침" onClick="location.reload()">
		<input type="hidden" name="numb"> 
	</form>
</div>

</body>
</html>