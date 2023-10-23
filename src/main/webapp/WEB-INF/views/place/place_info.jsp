<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<% 
	request.setCharacterEncoding("UTF-8");
	String cPath = request.getContextPath();
%>

<html>
<head>
	<title>회원가입</title>
	<link href="<%=cPath%>/resources/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="script.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    function postcode_search() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("other").value = extraAddr;
                
                } else {
                    document.getElementById("other").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('post_num').value = data.zonecode;
                document.getElementById("location").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("detail_location").focus();
            }
        }).open();
    }
</script>
<script type="text/javascript">
	window.addEventListener('DOMContentLoaded', async function () {
		if (location.href.includes("?")) {
			const parameters = this.location.href.split("?")[1].split("&");
            parameters.forEach((param) => {
                let key = param.split("=")[0];
                let value = param.split("=")[1];
                
                if (key === "error") {
                	if (value === "pid_error") {
                		alert("주차장 고유번호 생성중 오류가 발생하였습니다.\n나중에 다시 시도해주세요.");
                	}
                	
                	if (value === "phone_error") {
                		alert("휴대폰 번호 타입 오류가 발생하였습니다.\n휴대폰 번호를 형식에 맞게 입력하고 다시 시도해주세요.");
                	}
                	
                	if (value === "insert_error") {
                		alert("데이터 삽입 오류가 발생했습니다.\n나중에 다시 시도해주세요.");
                	}
                	
                	if (value === "unknown_error") {
                		alert("알 수 없는 오류가 발생하였습니다.\n나중에 다시 시도해주세요.");
                	}
                }
            })
		}
	})
</script>
</head>
<body bgcolor="#FFFFCC" onLoad="regFrm.name.focus()">
	<div align="center">
		<br /><br />
		<form id="register" name="regFrm" method="post" action="<%=cPath%>/api/place/add.do">
			<table cellpadding="5">
				<tr>
					<td bgcolor="#FFFFCC">
						<table border="1" cellspacing="0" cellpadding="2" width="1200">
							<tr bgcolor="#996600">
								<td align="center" colspan="3"><font color="#FFFFFF"><b>주차장 정보</b></font></td>
							</tr>
							<tr>
								<td width="20%">주차장(사업자) 이름</td>
								<td><span>${Name }</span>
								</td>
							</tr>
							<tr>
								<td>사업자 번호</td>
								<td><span>${company_id }</span></td>
							</tr>
							<tr>
								<td>주소</td>
								<td>
									<span>${post_no }</span>
									<span>${address }</span>
									<span>${detail_address }</span> <span>${loc_other }</span>
								</td>
							</tr>
							<tr>
								<td>전화번호</td>
								<td><span>${call }</span>
								</td>
							</tr>
							<tr>
								<td>기본 금액</td>
								<td><span>${base_money }</span> 원</td>
							</tr>
							<tr>
								<td>기본 무료 시간 </td>
								<td><span>${freetime_hour }</span> 시간 <span>${freetime_minute }</span> 분</td>
							</tr>
							<tr>
								<td>추가 금액</td>
								<td><span>${per_min }</span> 분마다 <span>${per_money }</span> 원이 추가됩니다.</td>
							</tr>
							<tr>
								<td>일간 금액</td>
								<td><span>${day_price }</span> 원</td>
							</tr>
							<tr>
								<td>소형 차량 할인율</td>
								<td><span>${kind_small_sale_per }</span> %</td> 
							</tr>
							<tr>
								<td>중형 차량 할인율</td>
								<td><span>${kind_medium_sale_per }</span> %</td> 
							</tr>
							<tr>
								<td>대형 차량 할인율</td>
								<td><span>${kind_large_sale_per }</span> %</td> 
							</tr>
							<tr>
								<td>회차 무료 시간 </td>
								<td><span>${one_time_register_free_min_hour }</span> 시간 <span>${one_time_register_free_min_minute }</span> 분</td>
							</tr>
							<tr>
								<td>회차 차량 할인율</td>
								<td><span>${one_time_discount_per }</span> %</td> 
							</tr>
							<tr>
								<td>회원 추가 무료 시간 </td>
								<td><span>${member_plus_freetime_hour }</span> 시간 <span>${member_plus_freetime_minute }</span> 분</td>
							</tr>
							<tr>
								<td>회원 차량 추가 할인율</td>
								<td><span>${member_discount_per }</span> %</td> 
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>