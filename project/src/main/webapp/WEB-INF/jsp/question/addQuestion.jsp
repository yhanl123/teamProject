<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>질문</title>
<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<script type="text/javascript">
function add(){	
	var formdata = $('#addForm').serialize();
	$.ajax({
		url:"/question/add",
		data:formdata,
		method:"post",
		cache:false,
		dataType:"json",
		success:function( res ){
			alert(res.added ? "등록 성공" : "등록 실패");
			if(res.added) location.href='/question/get/'+ res.PKNum;
		},
		error:function(err) {
			alert('에러:' + err);
		}
	});
	return false;
}
</script>
</head>
<body>
<main>
<h2>질문</h2>
<form id="addForm" action="/question/add" method="post" onsubmit="return add();">
	<div>
		<label for="questionTitle">제목</label>
		<input type="text" name="questionTitle" id="questionTitle" required>
	</div>
	<div>
		<label for="questionContents">내용</label>
		<textarea name="questionContents" id="questionContents" required></textarea>
	</div>
	<div id="btn">
		<button type="reset">취소</button>
		<button type="submit">등록</button>
	</div>
</form>
</main>
</body>
</html>