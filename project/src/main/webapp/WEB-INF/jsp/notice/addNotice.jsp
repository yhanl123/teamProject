<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>공지사항 추가</title>
<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<script type="text/javascript">
function addNotice(){	
	var formdata = $('#addForm').serialize();
	$.ajax({
		url:"/notice/add",
		data:formdata,
		method:"post",
		cache:false,
		dataType:"json",
		success:function( res ){
			alert(res.added ? "등록 성공" : "등록 실패");
			if(res.added){
				location.href="/notice/get/"+ res.PKNum;
			}else if(res.cause){
				alert(res.cause);
			}
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
<h2>공지사항 추가</h2>
<form id="addForm" action="/notice/add" method="post" onsubmit="return addNotice();">
	<div>
		<label for="noticeTitle">제목</label>
		<input type="text" name="noticeTitle" id="noticeTitle" required>
	</div>
	<div>
		<label for="noticeContents">내용</label>
		<textarea name="noticeContents" id="noticeContents" rows="5" cols="50" required></textarea>
	</div>
	<div id="btn">
		<button type="reset">취소</button>
		<button type="submit">저장</button>
	</div>
</form>
</main>
</body>
</html>