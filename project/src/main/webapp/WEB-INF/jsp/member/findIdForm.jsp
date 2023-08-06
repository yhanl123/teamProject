<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>아이디 찾기</title>
</head>
<body>
<main>
<h2>아이디 찾기</h2>
<form action="/member/findid" method="post">
	<div>
		<label for="memberName">이름</label>
		<input type="text" name="memberName" id="memberName" required>
	</div>
		<div>
		<label for="memberEmail">이메일</label>
		<input type="email" name="memberEmail" id="memberEmail" required>
	</div>
	<div id="btn">
		<button type="reset">취소</button>
		<button type="submit">찾기</button>
	</div>
</form>
</main>
</body>
</html>