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
<script type="text/javascript">
	function zipSearch() {
		url = "zipSearch.jsp?search=n";
		window.open(url, "ZipCodeSearch","width=500,height=300,scrollbars=yes");
	}

	function loginForm(){
		document.regFrm.target = "left";
		document.regFrm.action = "../login/login.jsp";
		document.regFrm.submit();
	}
	
	function inputCheck() {
		if (regFrm.name.value.trim() === "") {
			alert("주차장 이름을 입력해주세요.")
			regFrm.name.focus();
		} else {
			if (regFrm.company_id.value.trim() === "") {
				alert("사업자 번호를 입력해주세요.");
				regFrm.company_id.focus();
			} else {
				if (regFrm.post_no.value.trim() === "") {
					alert("주소를 입력해주세요.");
					postcode_search();
				} else {
					if ((regFrm.place_num.value.split("-").length - 1) != 2) {
						alert("전화번호 형식이 올바르지 않습니다.");
						p_num.focus();
					} else {
						if (regFrm.default_money.value.trim() === "") {
							alert("기본금액을 입력해주세요.");
							regFrm.default_money.focus();
						} else {
							if (regFrm.freetime_hour.value.trim() === "") {
								regFrm.freetime_hour.value = "0";
							} else {
								if (regFrm.freetime_min.value.trim() === "") {
									console.log(regFrm.freetime_min)
									regFrm.freetime_min.value = "0";
								} else {
									if (regFrm.per_min.value.trim() === "") {
										regFrm.per_min.value = "0"
									} else {
										if (regFrm.per_money.value.trim() === "") {
											regFrm.per_money.value = "0"
										} else {
											if (regFrm.day_price.value.trim() === "") {
												regFrm.day_price.value = "0"
											} else {
												if (regFrm.small_car_discount.value.trim() === "") {
													regFrm.small_car_discount.value = "0"
												} else {
													if (regFrm.medium_car_discount.value.trim() === "") {
														regFrm.medium_car_discount.value = "0"
													} else {
														if (regFrm.large_car_discount.value.trim() === "") {
															regFrm.large_car_discount.value = "0"
														} else {
															if (regFrm.onetime_freetime_hour.value.trim() === "") {
																regFrm.onetime_freetime_hour.value = "0"
															} else {
																if (regFrm.onetime_freetime_min.value.trim() === "") {
																	regFrm.onetime_freetime_min.value = "0"
																} else {
																	if (regFrm.onetime_car_discount.value.trim() === "") {
																		regFrm.onetime_car_discount.value = "0"
																	} else {
																		if (regFrm.mem_freetime_hour.value.trim() === "") {
																			regFrm.mem_freetime_hour.value = "0"
																		} else {
																			if (regFrm.mem_freetime_min.value.trim() === "") {
																				regFrm.mem_freetime_min.value = "0"
																			} else {
																				if (regFrm.mem_car_discount.value.trim() === "") {
																					regFrm.mem_car_discount.value = "0"
																				} else {
																					regFrm.submit();
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}			
	}
</script>
</head>
<body bgcolor="#FFFFCC" onLoad="regFrm.name.focus()">
	<div align="center">
		<br /><br />
		<form id="register" name="regFrm" method="post" action="/parking/api/place/add.do">
			<table cellpadding="5">
				<tr>
					<td bgcolor="#FFFFCC">
						<table border="1" cellspacing="0" cellpadding="2" width="1200">
							<tr bgcolor="#996600">
								<td align="center" colspan="3"><font color="#FFFFFF"><b>주차장 등록</b></font></td>
							</tr>
							<tr>
								<td width="20%">주차장(사업자) 이름</td>
								<td><input id="name" name="name" size="15">
								</td>
								<td width="35%">주차장(사업자) 이름을 적어주세요.</td>
							</tr>
							<tr>
								<td>사업자 번호</td>
								<td><input id="company_id" name="company_id" size="20"> ex) xxx-xx-xxxxx</td>
								<td>사업자 번호를 적어주세요.</td>
							</tr>
							<tr>
								<td>주소</td>
								<td>
									<input id="post_num" name="post_no" size="10" placeholder="우편번호" readonly> <button type="button" onclick="postcode_search()">우편번호 검색</button>
									<input id="location" name="address" style="margin-top:5px;margin-bottom:5px;" size="56" placeholder="주소">
									<input id="detail_location" name="detail_address" size="25" placeholder="상세주소"> <input id="other" name="loc_other" size="25" placeholder="참고항목">
								</td>
								<td>주소를 입력하세요.</td>
							</tr>
							<tr>
								<td>전화번호</td>
								<td><input id="place_num" name="place_num" size="15"> ex) 010-0000-0000
								</td>
								<td>전화번호를 적어주세요.</td>
							</tr>
							<tr>
								<td>기본 금액</td>
								<td><input id="default_money" name="default_money" size="15" value="0"> 원</td>
								<td>기본 금액을 입력해주세요.</td>
							</tr>
							<tr>
								<td>기본 무료 시간 </td>
								<td><input id="freetime_hour" name="freetime_hour" type="number" min="0" style="width:60px" value="0"> 시간 <input id="freetime_min" name="freetime_min" type="number" min="0" max="60" size="6" value="0"> 분</td>
								<td>기본 무료 시간을 입력해주세요.</td>
							</tr>
							<tr>
								<td>추가 금액 설정</td>
								<td><input id="per_min" name="per_min" type="number" min="0" style="width:60px" value="0"> 분마다 <input id="per_money" name="per_money" size="15" value="0"> 원을 추가합니다.</td>
								<td>추가금액 규칙과 금액을 설정해주세요.</td>
							</tr>
							<tr>
								<td>일간 금액 설정</td>
								<td><input id="day_price" name="day_price" style="width:100px" value="0"> 원</td>
								<td>일간 금액(24시간)을 설정해주세요</td>
							</tr>
							<tr>
								<td>소형 차량 할인율</td>
								<td><input id="small_car_discount" name="small_car_discount" type="number" min="0" max="100" style="width:60px" value="0"> %</td> 
								<td>소형 차량 할인율을 설정해주세요.</td>
							</tr>
							<tr>
								<td>중형 차량 할인율</td>
								<td><input id="medium_car_discount" name="medium_car_discount" type="number" min="0" max="100" style="width:60px" value="0"> %</td> 
								<td>중형 차량 할인율을 설정해주세요.</td>
							</tr>
							<tr>
								<td>대형 차량 할인율</td>
								<td><input id="large_car_discount" name="large_car_discount" type="number" min="0" max="100" style="width:60px" value="0"> %</td> 
								<td>대형 차량 할인율을 설정해주세요.</td>
							</tr>
							<tr>
								<td>회차 무료 시간 </td>
								<td><input id="onetime_freetime_hour" name="onetime_freetime_hour" type="number" min="0" style="width:60px" value="0"> 시간 <input id="onetime_freetime_min" name="onetime_freetime_min" type="number" min="0" max="60" size="6" value="0"> 분</td>
								<td>회차(상가 방문) 차량의 무료시간을 설정해주세요.</td>
							</tr>
							<tr>
								<td>회차 차량 할인율</td>
								<td><input id="onetime_car_discount" name="onetime_car_discount" type="number" min="0" max="100" style="width:60px" value="0"> %</td> 
								<td>회차(상가 방문) 차량 할인율을 설정해주세요.</td>
							</tr>
							<tr>
								<td>회원 추가 무료 시간 </td>
								<td><input id="mem_freetime_hour" name="mem_freetime_hour" type="number" min="0" style="width:60px" value="0"> 시간 <input id="mem_freetime_min" name="mem_freetime_min" type="number" min="0" max="60" size="6" value="0"> 분</td>
								<td>회원 차량의 추가 무료시간을 설정해주세요.</td>
							</tr>
							<tr>
								<td>회원 차량 추가 할인율</td>
								<td><input id="mem_car_discount" name="mem_car_discount" type="number" min="0" max="100" style="width:60px" value="0"> %</td> 
								<td>회원 차량의 추가 할인율을 설정해주세요.</td>
							</tr>
							<tr>
								<td colspan="3" align="center">
								    <input type="button" value="주차장 추가히기" onclick="inputCheck()"> &nbsp; &nbsp; 
								 </td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>