<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<%
	String strTitle = "주차 관리 시스템";
	String cPath = request.getContextPath();
%>
<html>
<head>
	<title><%=strTitle%></title>
</head>
<frameset frameborder="0" framespacing="0" border="0" rows="130,*" >
	<frame  frameborder="0" scrolling="NO" noresize name="head" src="<%=cPath%>/main/head">
	<frameset name="body" frameborder="0" framespacing="0" border="0" rows="*,20"> <!-- 240,* -->		
        <frameset name="main" frameborder="0" framespacing="0" border="0" cols="270,*"> <!-- *,0,37,12 -->
    			<frame name="left" marginwidth="0" marginheight="0" frameborder="0" scrolling="NO" resize="NO" src="<%=cPath%>/main/left">
    			<frame name="content" src="<%=cPath%>/main/main" scrolling="YES" marginwidth="0" marginheight="0" frameborder="0" noresize>
        </frameset>
		<frame name="copy" src="<%=cPath%>/main/footer" scrolling="NO" marginwidth="0" marginheight="0" frameborder="0" noresize>        
	</frameset>
</frameset>
</html>