<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>리뷰 쓰기</title>
<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<script type="text/javascript">
function addReview() {
	var formdata = new FormData($('#addForm')[0]);
	$.ajax({
		url:'/review/add',
		method:'post',
		enctype:'multipart/form-data',
		processData:false,
		contentType:false,
		timeout:3600,
		cache:false,
		data:formdata,
		dataType:'json',
		success:function(res) {
			alert(res.added ? '리뷰 등록 성공':'리뷰 등록 실패');
			if(res.added) location.href='/review/get/'+res.PKNum;
		},
		error:function(xhr,status,err) {
			alert('에러:' + err);
		}
	});
	return false;
}
</script>
</head>
<body>
<main>
<h2>리뷰 쓰기</h2>
	<div><label>상품명 :</label>${goods }</div>
<form id="addForm" action="/review/add" method="post" onsubmit="return addReview();">
	<input type="hidden" name="reviewParentsNum" value="${itemNum }">
		<div>
			<label for="reviewTitle">제 목</label>
			<input type="text" name="reviewTitle" id="reviewTitle" required>
		</div>
		<div>
			<label for="reviewContents">내 용</label>
			<textarea rows="5" cols="25" name="reviewContents" id="reviewContents" required></textarea>
		</div>
		<div>
			<label>사진 첨부</label>
			<input type="file" name="files" onchange="preview(event);" multiple>
		</div>
		<div id="thumbnail_view"></div>
		<div id="btn">
			<button type="reset">취소</button>
			<button type="submit">등록</button>
		</div>
</form>
</main>
<script>
	function preview(evt) {
		var reader = new FileReader();
		
		reader.onload = function(event) {
			let parent = document.querySelector("#thumbnail_view");		
			while(parent.firstChild) {
				parent.removeChild(parent.firstChild);
			}
			var img = document.createElement("img");
			img.setAttribute("src", event.target.result);
			parent.appendChild(img);
		};
		
		reader.readAsDataURL(evt.target.files[0]);
	}
</script>
</body>
</html>