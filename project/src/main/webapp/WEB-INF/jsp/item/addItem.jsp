<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>아이템 추가</title>
<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<script type="text/javascript">
function addItem() {
	var formdata = new FormData($('#addForm')[0]);
	$.ajax({
		url:'/item/add',
		method:'post',
		enctype:'multipart/form-data',
		processData:false,
		contentType:false,
		timeout:3600,
		cache:false,
		data:formdata,
		dataType:'json',
		success:function(res) {
			alert(res.added ? '아이템 등록 성공':'아이템 등록 실패');
			//if(res.added) location.href='/item/get/'+res.PKNum;
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
<h2>아이템 추가</h2>
	<form id="addForm" action="/item/add" method="post" onsubmit="return addItem();">
		<div>
			<label for="goods">품명</label>
			<input type="text" name="goods" id="goods" required>
		</div>
		<div>
			<label for="price">가격</label>
			<input type="text" name="price" id="price" required>
		</div>
		<div>
			<label>카테고리</label>
				<select name="category">
					<option value="상의">상의</option>
					<option value="하의">하의</option>
					<option value="아우터">아우터</option>
					<option value="신발">신발</option>
					<option value="모자">모자</option>
					<option value="뷰티">뷰티</option>
					<option value="가방">가방</option>
					<option value="악세서리">악세서리</option>
				</select>
		</div>
		<div>
			<label for="explains">설명</label>
			<textarea name="explains" id="explains" required></textarea>
		</div>
		<div>
			<label>사진 첨부</label>
			<input type="file" name="files" onchange="preview(event);" multiple>
		</div>
		<div id="thumbnail_view"></div>
		<div id="btn">
			<button type="reset">취소</button>
			<button type="submit">저장</button>
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