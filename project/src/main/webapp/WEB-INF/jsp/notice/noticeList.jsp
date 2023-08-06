<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>공지사항</title>
</head>
<body>
<main>
<h2>공지사항</h2>
<table>
	<tr><th>글번호</th><th>제 목</th><th>작성자</th><th>작성일</th></tr>
	<c:forEach var="n" items="${pageInfo.list}">
		<tr>
			<td>${n.noticeNum }</td>
			<td>
				<c:url var="pgURL" value="/notice/get/${n.noticeNum}">
					<c:param name="pageNum" value="${pageInfo.pageNum}"/>
				<c:if test="${category!=null }">
					<c:param name="category" value="${category }"/>
				  	<c:param name="keyword" value="${keyword }"/>
				</c:if>
				</c:url>
					<a href="${pgURL}">${n.noticeTitle }</a>
			</td>
			<td>${n.noticeAuthor }</td>
			<td>${n.noticeDate }</td>
		</tr>
		
	</c:forEach>
</table>
<p>
	<div id="search">
		<form id="searchForm" action="/notice/search" method="post" >
			<label>검색 카테고리</label>
			<select name="category">
				<option value="title">제 목</option>
				<option value="contents">내 용</option>
			</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<label>검색어</label> 
			<input type="text" name="keyword">
			<button type="submit">검색</button>
		</form>
	</div>
<p>
	<nav id="pagination">
		<c:forEach var="pn" items="${pageInfo.navigatepageNums}">
			<c:choose>
		  		<c:when test="${pn==pageInfo.pageNum}">
		  			<span id="pageNum">${pn}</span>
		  		</c:when>
		  		
				<c:otherwise>
					<c:url var="pgURL" value="/notice/list/page/${pn}">
			  			<c:if test="${category!=null }">
			  				<c:param name="category" value="${category }"/>
			  				<c:param name="keyword" value="${keyword }"/>
			  			</c:if>
			  		</c:url>
			  		<a href="${pgURL}">${pn}</a>
		  		</c:otherwise>
		  		
	    	</c:choose>
		</c:forEach>
	</nav>	
</main>
</body>
</html>