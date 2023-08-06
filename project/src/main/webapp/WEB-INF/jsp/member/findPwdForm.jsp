<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>비밀번호 찾기</title>
</head>
<body>
<h2>비밀번호 찾기</h2>
<form action="/member/findpwd" method="post">
	<div>
		<label for="memberName">이름</label>
		<input type="text" name="memberName" id="memberName" required>
	</div>
	<div>
		<label for="memberID">아이디</label>
		<input type="text" name="memberID" id="memberID" required>
	</div>
	<div id="btn">
		<button type="reset">취소</button>
		<button type="submit">찾기</button>
	</div>
</form>
</body>
</html>