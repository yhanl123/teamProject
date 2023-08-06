<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>상세보기</title>
<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<script type="text/javascript">
function delNotice() {
	if(!confirm('정말 삭제하시겠습니까?')) return;
	
	$.ajax({
		url:'/notice/delete/${n.noticeNum}',
		method:'get',
		cache:false,
		dataType:'json',
		success:function(res) {
			alert(res.deleted ? '삭제 성공':'삭제 실패');
			if(res.deleted){
				location.href="/notice/list";
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
<h2>공지사항 상세보기</h2>
<table>
	<tr><th>제목</th><td>${n.noticeTitle }</td></tr>
	<tr><th>내용</th><td>${n.noticeContents }</td></tr>
</table>
<c:if test="${userid != null && userid == 'admin'}">
	<a href="/notice/edit/${n.noticeNum }">수정</a>
	<a href="javascript:delNotice();">삭제</a>
</c:if>
</main>
</body>
</html>