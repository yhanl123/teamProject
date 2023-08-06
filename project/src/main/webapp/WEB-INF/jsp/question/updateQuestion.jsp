<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>질문 글 수정</title>
<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<script type="text/javascript">
function updateQ() {
	var serdata = $('#updateForm').serialize();
	$.ajax({
		url:'/question/update',
		method:'post',
		cache:false,
		data:serdata,
		dataType:'json',
		success:function(res){
			alert(res.updated ? '수정 성공':'수정 실패');
			if(res.updated) location.href='/question/get/${q.questionNum}';
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
<h2>질문 글 수정</h2>
<form id="updateForm" action="/question/update" method="post" onsubmit="return updateQ();">
<input type="hidden" name="questionNum" value="${q.questionNum }" >
	<table>
		<tr><th>제목</th><td><input type="text" name="questionTitle" id="questionTitle" value="${q.questionTitle }"></td></tr>
		<tr><th>내용</th><td><textarea name="questionContents" id="questionContents" required>${q.questionContents }</textarea></td></tr>
	</table>
	<div id="btn">
		<button type="reset">취소</button>
		<button type="submit">등록</button>
	</div>
</form>
</main>
</body>
</html>