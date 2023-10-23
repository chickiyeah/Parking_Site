<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%  request.setCharacterEncoding("UTF-8");%>

<html>
<head>
	<title>회원가입</title>
	<link href="style.css" rel="stylesheet" type="text/css">
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
                	if (value === "uid_error") {
                		alert("회원가입 처리중 유저 고유번호 생성중 오류가 발생하였습니다.\n나중에 다시 시도해주세요.");
                	}
                	
                	if (value === "phone_error") {
                		alert("회원가입 처리중 휴대폰 번호 타입 오류가 발생하였습니다.\n휴대폰 번호를 형식에 맞게 입력하고 다시 시도해주세요.");
                	}
                	
                	if (value === "insert_error") {
                		alert("회원가입 처리중 데이터 삽입 오류가 발생했습니다.\n나중에 다시 시도해주세요.");
                	}
                	
                	if (value === "unknown_error") {
                		alert("회원가입 처리중 알 수 없는 오류가 발생하였습니다.\n나중에 다시 시도해주세요.");
                	}
                }
            })
		}
	})
</script>
<script type="text/javascript">
	function idCheck(usid) {
		frm = document.regFrm;
		if (usid == "") {
			alert("아이디를 입력해 주세요.");
			frm.usid.focus();
			return;
		}
		url = "/parking/api/member/id_duplicate.do?user_id=" + usid;
		fetch(url, {
			method: "POST"
		}).then(async (res) => {
			res.json().then((response) => {
				if (response === false) {
					alert("사용가능한 아이디 입니다.");
					return "un_used";
				} else {
					alert("이미 사용중인 아이디입니다.");
					return "used";
				}
			})
		})
	}

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
		id = document.getElementById("usid");
		reg_form = document.getElementById("register")
		pw_a = document.getElementById("pw_A");
		pw_b = document.getElementById("pw_B");
		p_num = document.getElementById("p_num");
		q_name = document.getElementById("name");
		birthday = document.getElementById("birthday");
		email = document.getElementById("email");
		car_num = document.getElementById("car_num");
		if (id.value.trim() === "") {
			alert("아이디를 입력해주세요.")
		} else {
			fetch("/parking/api/member/id_duplicate.do?user_id=" + id.value, {
				method: "POST"
			}).then(async (res) => {
				res.json().then((response) => {
					if (response === false) {
						if (pw_a.value.trim() === "") {
							alert("비밀번호를 입력해주세요.")
							pw_a.focus();
						} else {
							if (pw_a.value != pw_b.value) {
								alert("비밀번호 확인란과 비밀번호가 일치하지 않습니다.");
								pw_a.focus();
							} else {
								if ((p_num.value.split("-").length - 1) != 2) {
									alert("전화번호 형식이 올바르지 않습니다.");
									p_num.focus();
								} else {
									if (q_name.value.trim() === "") {
										alert("이름을 입력해주세요.");
										name.focus();
									} else {
										if (birthday.value.trim() === "") {
											alert("생년월일을 입력해주세요.");
											birthday.focus();
										} else {
											if (car_num.value.trim() === "") {
												alert("차량 번호를 입력해주세요.");
												car_num.focus();
											} else {
												if (email.value.trim() === "") {
													alert("이메일을 입력해주세요");
													email.focus();
												} else {
													if (document.getElementById("location").value.trim() === "") {
														alert("주소를 입력해주세요.");
														postcode_search();
													} else {
														register.submit();
													}
												}
											}
										}
									}
								}
							}
						}			
					} else {
						alert("이미 사용중인 아이디입니다.");
						id.focus();
					}
				})
			})			
		}
	}
</script>
</head>
<body bgcolor="#FFFFCC" onLoad="regFrm.usid.focus()">
	<div align="center">
		<br /><br />
		<form id="register" name="regFrm" method="post" action="/parking/api/member/register.do">
			<table cellpadding="5">
				<tr>
					<td bgcolor="#FFFFCC">
						<table border="1" cellspacing="0" cellpadding="2" width="900">
							<tr bgcolor="#996600">
								<td align="center" colspan="3"><font color="#FFFFFF"><b>회원 가입</b></font></td>
							</tr>
							<tr>
								<td width="20%">아이디</td>
								<td width="50%">
									<input id="usid" name="user_id" size="15"> 
									<input type="button" value="ID중복확인" onClick="idCheck(this.form.usid.value)">
								</td>
								<td width="30%">아이디를 적어 주세요.</td>
							</tr>
							<tr>
								<td>패스워드</td>
								<td><input type="password" id="pw_A" name="user_pw" size="15"></td>
								<td>패스워드를 적어주세요.</td>
							</tr>
							<tr>
								<td>패스워드 확인</td>
								<td><input type="password" id="pw_B" name="repwd" size="15"></td>
								<td>패스워드를 확인합니다.</td>
							</tr>
							<tr>
								<td>이름</td>
								<td><input id="name" name="name" size="15">
								</td>
								<td>이름을 적어주세요.</td>
							</tr>
							<tr>
								<td>전화번호</td>
								<td><input id="p_num" name="p_num" size="15"> ex) 010-0000-0000
								</td>
								<td>전화번호를 적어주세요.</td>
							</tr>
							<tr>
								<td>성별</td>
								<td>
									남<input type="radio" name="gender" value="male" checked> 
									여<input type="radio" name="gender" value="female">
								</td>
								<td>성별을 선택 하세요.</td>
							</tr>
							<tr>
								<td>생년월일</td>
								<td><input id="birthday" name="birth_day" size="6">
									ex)830815</td>
								<td>생년월일를 적어 주세요.</td>
							</tr>
							<tr>
								<td>Email</td>
								<td><input id="email" name="email" size="30">
								</td>
								<td>이메일를 적어 주세요.</td>
							</tr>
							<tr>
								<td>차량번호</td>
								<td><input id="car_num" name="car_num" size="29"> 
									ex) 000가 5555, 00나 5555
								</td> 
								<td>차랑번호를 적어주세요.</td>
							</tr>
							<tr>
								<td>주소</td>
								<td>
									<input id="post_num" name="post_id" size="10" placeholder="우편번호" readonly> <button type="button" onclick="postcode_search()">우편번호 검색</button>
									<input id="location" name="address" style="margin-top:5px;margin-bottom:5px;" size="56" placeholder="주소">
									<input id="detail_location" name="detail_address" size="25" placeholder="상세주소"> <input id="other" name="loc_other" size="25" placeholder="참고항목">
								</td>
								<td>주소를 입력하세요.</td>
							</tr>
							<tr>
								<td colspan="3" align="center">
								    <input type="button" value="회원가입" onclick="inputCheck()"> &nbsp; &nbsp; 
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