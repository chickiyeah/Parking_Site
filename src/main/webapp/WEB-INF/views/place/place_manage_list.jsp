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
	Integer Numb = (Integer) session.getAttribute("Numb");
	  
    int totalPlace = 0; //전체 주차랑
    int listSize = 0;    //현재 읽어온 주차장의 수
	List<Place_data> vlist = null;
	
	vlist = pMgr.get_manage_place(Numb); 
	if (vlist != null) {
		totalPlace = vlist.size();
	}
    
%>

<html>
<head>
	<title>주차장 목록</title>
	<link href="<%=cPath%>/resources/css/style.css" rel="stylesheet" type="text/css">
<script>
	function place_info(numb) {
		document.readFrm.numb.value=numb;
		document.readFrm.method="get";
		document.readFrm.action="<%=cPath%>/place/info";
		document.readFrm.target="content";
		document.readFrm.submit();
	}
	
	function place_edit(numb) {
		document.readFrm.numb.value=numb;
		document.readFrm.method="get";
		document.readFrm.action="<%=cPath%>/place/edit";
		document.readFrm.target="content";
		document.readFrm.submit();
	}
	
	function place_manage(numb) {
		document.readFrm.numb.value=numb;
		document.readFrm.method="get";
		document.readFrm.action="<%=cPath%>/place/manage?";
		document.readFrm.target="content";
		document.readFrm.submit();
	}
</script>
</head>
<body leftmargin="0" topmargin="0" bgcolor="#FFFFCC">

<div align="center">
    <br/>
		<h2>관리중인 주차장 목록</h2>
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
					out.println("관리중인 주차장이 없습니다.");
				  } else {
			%>
				  <table width="100%" cellpadding="2" cellspacing="0" border="1">
					<tr align="center" bgcolor="#D0D0D0" height="120%">
						<td>번 호</td>
						<td>주차장 이름</td>
						<td>사업자 번호</td>
						<td>주소</td>
						<td>전화번호</td>
						<td>관리</td>
						<td>수정</td>
						<td>삭제</td>
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
							<a href="javascript:place_manage(<%=numb %>)">관리</a>
						</td>
						<td align="center">
							<a href="javascript:place_edit(<%=numb %>)">수정</a>
						</td>
						<td align="center">
							<a href="javascript:place_delete(<%=numb %>)">삭제</a>
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