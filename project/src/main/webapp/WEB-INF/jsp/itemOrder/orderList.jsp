<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>주문 목록</title>
</head>
<body>
<main>
<h2>${uid }님의 결제 목록</h2>
<table>
<tr><th>주문번호</th><th>상품명</th><th>가격</th><th>수량</th><th>주문날짜</th></tr>
	<c:forEach var="o" items="${list }">
		<tr>
			<td>${o.orderNum }</td>
			<td>${o.goods }</td>
			<td>${o.price }</td>
			<td>${o.qty }</td>
			<td>${o.orderDate }</td>
		</tr>	
	</c:forEach>
</table>
</main>
</body>
</html>