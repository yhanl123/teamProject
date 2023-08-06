<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>QnA</title>
</head>
<body>
<main>
<h2>QnA 게시판</h2>
<table>
	<tr><th>답변여부</th><th>글번호</th><th>제 목</th><th>작성자</th><th>작성일</th></tr>
	<c:forEach var="q" items="${list }">
		<tr>
			<td>
                <c:if test="${answerStatusMap[q.questionNum]}">
                    답변완료
                </c:if>
                <c:if test="${not answerStatusMap[q.questionNum]}">
                    답변예정
                </c:if>
            </td>
			<td>${q.questionNum }</td>
			 <td>
                <c:choose>
                    <c:when test="${q.questionAuthor == userid || userid == 'admin'}">
                        <a href="/question/get/${q.questionNum}">${q.questionTitle}</a>
                    </c:when>
                    <c:otherwise>
                        ${q.questionTitle}
                    </c:otherwise>
                </c:choose>
            </td>
			<td>${q.questionAuthor }</td>
			<td>${q.questionDate }</td>
		</tr>
	</c:forEach>
</table>
</main>
</body>
</html>