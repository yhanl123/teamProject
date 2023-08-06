<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>공지사항 수정</title>
<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<script type="text/javascript">
function editNotice() {
	var serdata = $('#editForm').serialize();
	$.ajax({
		url:'/notice/edit',
		method:'post',
		cache:false,
		data:serdata,
		dataType:'json',
		success:function(res){
			alert(res.updated ? '수정 성공':'수정 실패');
			if(res.updated) location.href='/notice/get/${n.noticeNum}';
		},
		error:function(xhr,status,err){
			alert('에러:' + err);
		}
	});
	return false;
}
</script>
</head>
<body>
<main>
<h2>공지사항 수정</h2>
<form id="editForm" action="/notice/edit" method="post" onsubmit="return editNotice();">
<input type="hidden" name="noticeNum" value="${n.noticeNum}">
	<table>
		<tr><th>제목</th><td><input type="text" name="noticeTitle" id="noticeTitle" value="${n.noticeTitle }"></td></tr>
		<tr><th>내용</th><td><textarea name="noticeContents" id="noticeContents" rows="5" cols="50">${n.noticeContents }</textarea></td></tr>
	</table>
	
	<div id="btn">
		<button type="reset">취소</button>
		<button type="submit">저장</button>
	</div>
</form>	
</main>
</body>
</html>