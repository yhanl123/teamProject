<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>후기 상세보기</title>
<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<script type="text/javascript">
function delReview(reviewNum) {
	if(!confirm('정말 후기를 삭제하시겠습니까?')) return;
	
	$.ajax({
		url:'/review/delete/'+reviewNum,
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

function likeReview(reviewNum) {
    $.ajax({
        url: '/review/like/' + reviewNum,
        method: 'get',
        cache: false,
        dataType: 'json',
        success: function(res) {
        	alert(res.likeUpdated ? '추천 성공':'추천 실패');
            if(res.likeUpdated) location.reload();
        },
        error: function(xhr, status, err) {
            alert('에러: ' + err);
        }
    });
}
</script>
<style type="text/css">
.item-image {
        width: 200px;
        height: 400px;
    }
    
    /* Flexbox layout */
  .attachment-container {
    display: flex;
    flex-wrap: wrap; /* Wrap the items to the next line if there's not enough space */
    justify-content: flex-start; /* Align items to the left (start) of the container */
  }

  /* Optional: Add some spacing between the attachment images */
  .attachment-container img {
    margin-right: 10px;
  }
</style>
</head>
<body>
<main>
<h2>후기</h2>
<table>
	<tr><th>제 목</th><td>${r.reviewTitle}</td></tr>
	<tr><th>내 용</th><td>${r.reviewContents}</td></tr>
	<tr><th>등록일</th><td>${r.reviewDate}</td></tr>
	<tr><th>작성자</th><td>${r.reviewAuthor}</td></tr>
	<tr><th>추천수</th>
	<td>
        <span id="likeCount">${r.reviewLikeCnt}</span>
        <c:if test="${not empty uid}">  <!-- 로그인을 해야 좋아요를 누를 수 있게 로그인이 아닌 상태는 보이지 않음 -->
        	<button title="좋아요" onclick="likeReview(${r.reviewNum})">👍🏼</button>
        </c:if>
    </td></tr>
	<tr>
		<th>첨부</th>
		<td>
			<c:forEach var="att" items="${r.rattList}">
				<c:if test="${att!=null }">
					<img class="item-image" src="${pageContext.request.contextPath}/reviewPhoto/${att.reviewAttachName }">
				</c:if>
			</c:forEach>
		</td>
	</tr>
</table>
<p>
<a href="/review/update/${r.reviewNum}">수정</a>
<a href="javascript:delReview(${r.reviewNum});">삭제</a>
</main>
</body>
</html>