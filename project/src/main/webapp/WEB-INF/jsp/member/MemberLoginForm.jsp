<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>로그인 페이지</title>
<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<script type="text/javascript">
	function login(){
		var formdata = $('#loginForm').serialize();
		$.ajax({
			url:'/member/login',
			method:'post',
			data:formdata,
			cache:false,
			dataType:'json',
			success:function(res){
				alert(res.login ? "로그인 성공" : "실패");
				if(res.login){
					location.href="/member/";
				}
			},
			error:function(xhr,status,err){
				alert('에러:' + status+'/'+err);
			}
		});
		return false;
	}
</script>
</head>
<body>
<main>
<h2>로그인</h2>
<form id="loginForm" action="/member/login" method="post" onsubmit="return login();">
	<div>
		<label for="memberID">아이디</label>
		<input type="text" name="memberID" id="memberID">
	</div>
	<div>
		<label for="memberPwd">비밀번호</label>
		<input type="password" name="memberPwd" id="memberPwd">
	</div>
	<div id="btn">
		<button type="submit">로그인</button>
	</div>
</form>
</main>
</body>
</html>