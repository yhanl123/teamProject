<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>아이템 수정 폼</title>
<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<script type="text/javascript">
function updateItem() {
	var serdata = $('#updateForm').serialize();
	$.ajax({
		url:'/item/update',
		method:'post',
		cache:false,
		data:serdata,
		dataType:'json',
		success:function(res){
			alert(res.updated ? '수정 성공':'수정 실패');
			if(res.updated) location.href='/item/get/${item.itemNum }';
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
<h2>아이템 수정 폼</h2>
<c:forEach var="att" items="${item.iattList}">
	<div><img class="item-image" src="${pageContext.request.contextPath}/items/${att.itemAttachName }"></div>
</c:forEach>
<form id="updateForm" action="/item/update" method="post" onsubmit="return updateItem();">
	<input type="hidden" name="itemNum" value="${item.itemNum }">
	<div><label>제품명</label><input type="text" name="goods" id="goods" value="${item.goods }"></div>
	<div><label>가 격</label><input type="text" name="price" id="price" value="${item.price }"></div>
	<div><label>설 명</label><textarea name="explains" id="explains">${item.explains }</textarea></div>
	
	<div id="btn">
			<button type="reset">취소</button>
			<button type="submit">저장</button>
	</div>
</form>
</main>
</body>
</html>