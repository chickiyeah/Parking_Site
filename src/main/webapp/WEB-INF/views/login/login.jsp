<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.parking.greencom.java.User_data"%>
<jsp:useBean id="m_Mgr" class="com.parking.greencom.java.User_Manager" />
<%
      String cPath = request.getContextPath();
	  String usid = (String)session.getAttribute("idKey");
	  String name = "anonymous";
	  String kind = "user";
	  System.out.println(kind == "user");
	  if (usid != null) {
		  Integer Numb = (Integer)session.getAttribute("Numb");
		  User_data user = m_Mgr.getMember(Numb);
		  name = user.get_Name();
		  kind = user.get_kind();
	  }
	  System.out.println(kind.equals("user"));
%>
<html>
<head>
	<title>로그인</title>
	<link href="<%=cPath%>/resources/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function loginCheck() {
		if (document.loginFrm.user_id.value == "") {
			alert("아이디를 입력해 주세요.");
			document.loginFrm.user_id.focus();
			return;
		}
		if (document.loginFrm.user_pw.value == "") {
			alert("비밀번호를 입력해 주세요.");
			document.loginFrm.user_pw.focus();
			return;
		}
		document.loginFrm.action = "<%=cPath%>/api/member/login.do";
		top.document.location.reload();
		document.loginFrm.submit();
	}
	
	function logout() {
		fetch("<%=cPath%>/api/member/logout.do", {
			method: 'POST'
		}).then((res) => {
			if (res.status === 200) {
				top.document.location.reload();
			}
		})
	}
	
	function memberForm(){
		document.loginFrm.target = "content";
		document.loginFrm.method = "get"
		document.loginFrm.action = "<%=cPath%>/member/register";
		document.loginFrm.submit();
	}
</script>
</head>
<body bgcolor="#FFFFCC">
<br/><br/>
	<div align="center">
		<% if (usid != null) {%>
		<b><%=name %></b> [<%=usid%>]<b></b>님 환영 합니다.
			<% if (kind.equals("user")) {%>
				<p>제한된 기능을 사용 할 수가 있습니다.
			<%} else if (kind.equals("manager")) {%>
				<p>제한된 기능을 사용 할 수가 있습니다.
			<%} else if (kind.equals("super_manager")) {%>
				<p>모든 기능을 사용 할 수가 있습니다.
			<%} %>
		
		<p>
			<a onclick="logout()" href="javascript:">[로그아웃]</a>
			<%} else {%>
		<form name="loginFrm" method="post" action="<%=cPath%>/api/member/login.do">
			<table>
				<tr>
					<td align="center" colspan="2"><h4>로그인</h4></td>
				</tr>
				<tr>
					<td>아이디</td>
					<td><input name="user_id" value=""></td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td><input type="password" name="user_pw" value=""></td>
				</tr>
				<tr>
					<td colspan="2">
						<div align="right">
							<input type="button" value="로그인" onclick="loginCheck()">&nbsp;
							<input type="button" value="회원가입" onClick="memberForm()" >
						</div>
					</td>
				</tr>
			</table>
		</form>
		<% } %>
	</div>
</body>
</html>