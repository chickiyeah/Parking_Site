<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.parking.greencom.java.Place_data"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<jsp:useBean id="pMgr" class="com.parking.greencom.java.Place_Manager"/>
<%	
	String cPath = request.getContextPath();
	String usid = (String) session.getAttribute("idKey");
	request.setCharacterEncoding("UTF-8");
	  
    int totalPlace = 0; //전체 주차랑
    int listSize = 0;    //현재 읽어온 주차장의 수
	List<Place_data> vlist = new ArrayList<Place_data>();
	Integer pid = Integer.parseInt(request.getParameter("numb"));
	if (pid != null) {
		vlist.add(pMgr.get_place(pid));
		if (vlist != null) {
			totalPlace = vlist.size();
		}
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
		document.readFrm.action="<%=cPath%>/place/info?id="+numb;
		document.readFrm.target="content";
		document.readFrm.submit();
	}
	
	function open_in_out(numb) {
		url = "<%=cPath%>/place/in_out_log?numb="+numb;
		window.open(url, "입출차 내역", "width=950,height=380")
	}
	
	function open_register(numb) {
		url = "<%=cPath%>/place/manage/place_register_car?numb="+numb;
		window.open(url, "등록 차량", "width=950,height=380")
	}
	
	function open_onetime(numb) {
		url = "<%=cPath%>/place/manage/place_onetime_car?numb="+numb;
		window.open(url, "회차 차량", "width=950,height=380")
	}
</script>
</head>
<body leftmargin="0" topmargin="0" bgcolor="#FFFFCC">

<div align="center">
    <br/>
		<h2>주차장 관리</h2>
    <br>
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
					</tr>
					<%}//for%>
				</table> <%
 			}//if
 		%>
			</td>
		</tr>
		<tr>
			<td colspan="5" width="100%">
				<table width="100%" cellpadding="2" cellspacing="0" border="1">
					<tr align="center" bgcolor="#D0D0D0" height="120%">
						<td colspan="2">주차장 대시보드</td>
					</tr>
					<tr align="center" bgcolor="#D0D0D0" height="120%">
						<td>올해 월 수익</td>
						<td>주차장 통계/관리</td>
					</tr>
					<tr>
						<td width="25%">
							<table width="100%" cellpadding="2" cellspacing="0" border="1">
								<tr>
									<td bgcolor="#D0D0D0" width="20%"> 1 월 </td>
									<td> ${m_1 } 원 </td>
								</tr>
								<tr>
									<td bgcolor="#D0D0D0"> 2 월 </td>
									<td> ${m_2 } 원 </td>
								</tr>
								<tr>
									<td bgcolor="#D0D0D0"> 3 월 </td>
									<td> ${m_3 } 원 </td>
								</tr>
								<tr>
									<td bgcolor="#D0D0D0"> 4 월 </td>
									<td> ${m_4 } 원 </td>
								</tr>
								<tr>
									<td bgcolor="#D0D0D0"> 5 월 </td>
									<td> ${m_5 } 원 </td>
								</tr>
								<tr>
									<td bgcolor="#D0D0D0"> 6 월 </td>
									<td> ${m_6 } 원 </td>
								</tr>
								<tr>
									<td bgcolor="#D0D0D0"> 7 월 </td>
									<td> ${m_7 } 원 </td>
								</tr>
								<tr>
									<td bgcolor="#D0D0D0"> 8 월 </td>
									<td> ${m_8 } 원 </td>
								</tr>
								<tr>
									<td bgcolor="#D0D0D0"> 9 월 </td>
									<td> ${m_9 } 원 </td>
								</tr>
								<tr>
									<td bgcolor="#D0D0D0"> 10 월 </td>
									<td> ${m_10 } 원 </td>
								</tr>
								<tr>
									<td bgcolor="#D0D0D0"> 11 월 </td>
									<td> ${m_11 } 원 </td>
								</tr>
								<tr>
									<td bgcolor="#D0D0D0"> 12 월 </td>
									<td> ${m_12 } 원 </td>
								</tr>
							</table>
						</td>
						
						<td valign="top">
							<table width="100%" cellpadding="2" cellspacing="0" border="1">
								<tr style="height:48px">
									<td bgcolor="#D0D0D0" width="30%">오늘 총 수익</td>
									<td>${today } 원</td>
								</tr>
								<tr style="height:48px">
									<td bgcolor="#D0D0D0">일일 주차 차량 (출차 기준)</td>
									<td>${today_use } 대</td>
								</tr>
								<tr style="height:48px">
									<td bgcolor="#D0D0D0">현재 주차중인 차량 수</td>
									<td>${parking_cars } 대</td>
								</tr>
								<tr style="height:48px">
									<td bgcolor="#D0D0D0">현재 등록된 차량 수</td>
									<td>${register_car } 대</td>
								</tr>
								<tr style="height:48px">
									<td bgcolor="#D0D0D0">현재 등록된 회차 차량 수</td>
									<td>${onetime_car } 대</td>
								</tr>
								<tr>
									<td valign="bottom" colspan="2">
										<table width="100%" cellpadding="2" cellspacing="0" border="1">
											<tr align="center" bgcolor="#D0D0D0" height="120%">
												<td>입출차 내역</td>
												<td>차량 등록</td>
												<td>회차 차량</td>
											</tr>
											
											<tr>
												<td align="center"><button onclick='open_in_out(${numb })'>확인하기</button></td>
												<td align="center"><button onclick='open_register(${numb })'>관리하기</button></td>
												<td align="center"><button onclick='open_onetime(${numb })'>관리하기</button></td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		</table>
</div>

</body>
</html>