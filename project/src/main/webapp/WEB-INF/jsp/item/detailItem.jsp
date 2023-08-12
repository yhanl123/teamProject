<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>상품 상세보기</title>
<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<script type="text/javascript">
	function delItem() {
		if(!confirm('정말 삭제하시겠습니까?')) return;
		
		$.ajax({
			url:'/item/delete/${item.itemNum}',
			method:'get',
			cache:false,
			dataType:'json',
			success:function(res) {
				alert(res.deleted ? '삭제 성공':'삭제 실패');
				if(res.deleted){
					location.href="/item/list";
				}
			},
			error:function(xhr,status,err){
				alert('에러:' + err);
			}
		});
	}
	
	function direct_order(itemNum){
		var qty = $('#qty').val();
		var serdata = {itemNum:itemNum, qty:qty};
		if (!confirm("바로 구매하시겠습니까?")) return;
		
		 $.ajax({
		        url: '/order/direct',
		        method: 'post',
		        data: serdata,
		        cache: false,
		        dataType: 'json',
		        success: function (res) {
		            alert(res.ordered ? '결제 완료되었습니다' : '결제가 실패되었습니다');
		            if(res.ordered) location.href='/order/list';
		            else alert(res.msg);
		        },
		        error: function (xhr, status, err) {
		            alert(status + "/" + err);
		        }
		    });
		    return false;
		
		
	}
	
	function add_cart(itemNum){
		var qty = $('#qty').val();
		var serdata = {itemNum:itemNum, qty:qty};
		if (!confirm("정말로 장바구니에 담을까요?")) return;
		
	    $.ajax({
	        url: '/cart/add',
	        method: 'post',
	        data: serdata,
	        cache: false,
	        dataType: 'json',
	        success: function (res) {
	            alert(res.added ? '장바구니에 담았습니다' : '로그인 후 사용 가능합니다');
	            if(res.added) {
	            	if(!confirm("장바구니로 이동할까요?")) return;
	            	location.href = '/cart/list';
	            }
	        },
	        error: function (xhr, status, err) {
	            alert(status + "/" + err);
	        }
	    });
	    return false;
	}

	function loadReviewList(itemNum, pageNum) {
	    $.ajax({
	        url: '/review/list/' + itemNum + '/page/' + pageNum,
	        method: 'GET',
	        cache: false,
	        success: function(response) {
	            $("#review-list").html(response);
	        },
	        error: function(xhr, status, error) {
	            console.log(error);
	        }
	    });
	}

	loadReviewList(${item.itemNum}, 1);

</script>
<style type="text/css">
 img {width: 200px; height: 400px;}
</style>
</head>
<body>
<main>
<h2>상품 상세보기</h2>
<c:forEach var="att" items="${item.iattList}">
	<div><img class="item-image" src="${pageContext.request.contextPath}/items/${att.itemAttachName }"></div>
</c:forEach>
	<div><label>제품명</label>${item.goods }</div>
	<div><label>가 격</label>${item.price }</div>
	<div><label>설 명</label>${item.explains }</div>
<p>
수량 <input type= "number" id="qty" value ="1" min="1" max="50">
	<button type="button" onclick="add_cart(${item.itemNum});">장바구니에 담기</button>
	<button type="button" onclick="direct_order(${item.itemNum});">바로구매</button>
	
<c:if test="${userid != null && userid == 'admin'}">
	<a href="/item/update/${item.itemNum }">수 정</a>
	<a href="javascript:delItem();">삭 제</a>
</c:if>
<p>
<c:if test="${hasPurchased }"> <!-- 구매한 사람만 리뷰쓰기가 보이는 기능 -->
	<button onclick="window.location.href='/review/add/${item.itemNum}'">리뷰쓰기</button>
</c:if>
<p>
<div id="review-list">
</div>
</main>
</body>
</html>