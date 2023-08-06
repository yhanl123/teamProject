<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>회원 정보</title>
<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<script type="text/javascript">
	function delMem() {
		if(!confirm('정말 탈퇴하시겠습니까?')) return;
		
		$.ajax({
			url:'/member/delete/${m.memberNum}',
			method:'get',
			cache:false,
			dataType:'json',
			success:function(res) {
				alert(res.deleted ? '삭제 성공':'삭제 실패');
				if(res.deleted){
					location.href="/member/";
				}
			},
			error:function(xhr,status,err){
				alert('에러:' + err);
			}
		});
	}
</script>
</head>
<body>
<main>
<h2>회원 정보</h2>
<table>
	<tr><th>아이디</th><td>${m.memberID }</td></tr>
	<tr><th>비밀번호</th><td>${m.memberPwd }</td></tr>
	<tr><th>이름</th><td>${m.memberName }</td></tr>
	<tr><th>연락처</th><td>${m.memberPhone }</td></tr>
	<tr><th>이메일</th><td>${m.memberEmail }</td></tr>
	<tr><th>생년월일</th><td>${m.memberBirth }</td></tr>
	<tr><th>주소</th><td>${m.memberAddress }</td></tr>
	<tr><th>적립금</th><td>${m.saveMoney }</td></tr>
	<tr><th>포인트</th><td>${m.point }</td></tr>
	<tr><th>관심사</th><td>${m.interest }</td></tr>
</table>
<a href="/member/update/${m.memberNum}">수정</a>
<a href="javascript:delMem();">탈퇴</a>
</main>
</body>
</html>