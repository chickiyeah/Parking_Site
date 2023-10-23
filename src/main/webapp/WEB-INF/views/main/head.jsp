<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.parking.greencom.java.User_data"%>
<jsp:useBean id="m_Mgr" class="com.parking.greencom.java.User_Manager" />
<%
	String usid = (String) session.getAttribute("idKey");
	String cPath = request.getContextPath();
	User_data user = null;
	String url1 = cPath+"/member/register";
	String url2 = cPath+"/place/list";;
	String url3 = cPath+"/main/main";
	String label = "회원가입";
	String kind = null;
	 Integer Numb = null;
	  if (usid != null) {
		  Numb = (Integer)session.getAttribute("Numb");
		  user = m_Mgr.getMember(Numb);
		  kind = user.get_kind();
	  }
	
    if(usid != null){
      url1 = "../member/h02.jsp";
      url2 = cPath+"/place/list";
	  url3 = cPath+"/place/manage/list";
      label = "회원수정";
    }
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var = "path" value = "${pageContext.request.contextPath}"/>
<html>
<head>
	<title>head</title>
	<link href="<%=cPath%>/resources/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<table width="1280" cellpadding="0" cellspacing="0" >
		<tr>
			<td colspan="5">
				<table>
					<tr>
						<td height="50">
							<a href="${path}/" target="_parent" onFocus="this.blur();">
							<img src="${path}${green_img}" width="300" border="0"></a>
						</td>
						<td align="center">
							<font size="6" color="blue">주차 관리 시스템 (나경원)</font>
						</td>						
					</tr>
				</table>
			</td>
		</tr>
		<tr height="20" >
			<td colspan="5">&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<td width="250">&nbsp;</td>
			<% if (usid == null) {%>
				<td><font size="3"><a href="<%=url1%>" target="content"><b><%=label%></b></a></font></td>
				<td><font size="3"><a href="<%=url2%>" target="content"><b>주차장 목록</b></a></font></td>
			<%} else { %>	
				<td><font size="3"><a href="<%=url2%>" target="content"><b>주차장 목록</b></a></font></td>
				<%if (kind.equals("manager") || kind.equals("super_manager")) {%>
					<td><font size="3"><a href="<%=url3%>" target="content"><b>주차장 관리</b></a></font></td>
				<%} %>
			<%} %>
		</tr>
	</table>
</body>
</html>