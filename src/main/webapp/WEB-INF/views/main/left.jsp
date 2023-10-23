<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.Vector"%>
<%@page import="com.parking.greencom.java.User_data"%>
<jsp:useBean id="m_Mgr" class="com.parking.greencom.java.User_Manager" />
<%	
	  String usid = (String) session.getAttribute("idKey");
	  String cPath = request.getContextPath();
	  User_data user = null;
	  String kind = null;
	  Integer Numb = null;
	  if (usid != null) {
		  Numb = (Integer)session.getAttribute("Numb");
		  user = m_Mgr.getMember(Numb);
		  kind = user.get_kind();
	  }
	  
	  String register_url = cPath+"/member/register";
	  String url2 = cPath+"/place/list";
	  String url3 = cPath+"/place/manage/list";
	  String label = "회원가입";
	  
      if(usid != null){
    	register_url = cPath+"/member/edit?Numb="+Numb;
        label = "회원수정";
      }
%>

<html>
<head>
	<title>copy</title>
	<link href="<%=cPath%>/resources/css/style.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0" bgcolor="#D9E5FF">
	<jsp:include page="../login/login.jsp"/>
	<hr>
	
	<div align="center">
    	<br>
		<font size="3"><a href="<%=register_url%>" target="content"><b><%=label%></b></a></font><br><br><br>
		<font size="3"><a href="<%=url2%>" target="content"><b>주차장 목록</b></a></font><br><br><br>
		<%if (kind != null) {%>
			<%if (kind.equals("manager") || kind.equals("super_manager")) {%>
				<font size="3"><a href="<%=url3%>" target="content"><b>주차장 관리</b></a></font><br><br><br>
			<%} %>
		<%} %>
	</div>
</body>
</html>