<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>MemberIndexPage</title>
<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<script type="text/javascript">
	function logout(){
		if(!confirm("로그아웃 하시겠습니까?")) return;
		$.ajax({
			url:"/member/logout",
			method:'get',
			dataType:'json',
			success:function(res){
				alert(res.ok ? '로그아웃 성공':'로그아웃 실패');
				if(res.logout) location.href='/member/login';
			},
			error:function(xhr,status,err){
				alert('에러:' + err);
			}
		})
	}
</script>
</head>
<body>
<main>
<h2>MemberIndexPage</h2>
<ul>
<li><a href="/member/add">회원가입</a></li>
<li><a href="/member/login">로그인</a></li>
<li><a href="javascript:logout()">로그아웃</a></li>
<li><a href="/member/findid">아이디 찾기</a></li>
<li><a href="/member/findpwd">비밀번호 찾기</a></li>
<c:if test="${userid!=null }">
<li><a href="/member/getbyid/${userid }">내 회원정보 보기</a></li>
</c:if>
</ul>
</main>
</body>
</html>