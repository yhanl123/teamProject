<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>리뷰 리스트</title>
</head>
<body>
<main>
<h6>리뷰</h6>
<table>
<tr><th>제목</th><th>작성자</th><th>추천수</th></tr>
	<c:set var="reviewNum" value="${0}"></c:set>
	<c:forEach var="r" items="${pageInfo.list}">
		<c:if test="${r.reviewNum!=reviewNum }">
		<tr>
			<td><a href="/review/get/${r.reviewNum }"><span>${r.reviewTitle }</span></a></td>
			<td>${r.reviewAuthor }</td>
			<td>${r.reviewLikeCnt }</td>
		</tr>	
		</c:if>
		<c:set var="reviewNum" value="${r.reviewNum }"/>
	</c:forEach>
</table>	
<nav id="pagination">
<c:forEach var="pn" items="${pageInfo.navigatepageNums}">
    <c:choose>
        <c:when test="${pn == pageInfo.pageNum}">
            <span id="pageNum">${pn}</span>
        </c:when>
        <c:otherwise>
            <a href="javascript:void(0);" onclick="loadReviewList(${itemNum}, ${pn});">${pn}</a>
        </c:otherwise>
    </c:choose>
</c:forEach>
</nav>
</main>
</body>
</html>