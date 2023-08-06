<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>답변 수정 폼</title>
<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<script type="text/javascript">
	function updateAnswer() {
		var serdata = $('#updateForm').serialize();
		$.ajax({
			url:'/answer/update',
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
<h2>질문 글 보기</h2>
<table>
	<tr><th>제목</th><td>${q.questionTitle }</td></tr>
	<tr><th>내용</th><td>${q.questionContents }</td></tr>
</table>
</p>
<h3>답변</h3>
	<form id="updateForm" onsubmit="return updateAnswer();">
		<input type="hidden" name="answerNum" value="${a.answerNum}">
		<textarea id="answerContents" name="answerContents" rows="5" cols="50">${a.answerContents }</textarea><br>
		<button type="submit">답변 수정 완료</button>
	</form>
</main>
</body>
</html>