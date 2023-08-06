<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>아이템 리스트</title>

<style type="text/css">
    .item-container {
        display: flex;
        flex-wrap: wrap; /* 아이템이 줄바꿈되도록 설정 */
        max-width: 1000px; /* 아이템 컨테이너의 최대 너비를 설정 */
        margin: 0 auto; /* 가운데 정렬을 위해 margin을 auto로 설정 */
    }

    .item {
        flex-basis: calc(20% - 20px); /* 아이템이 5개씩 보이도록 설정 */
        margin-right: 20px; /* 각 아이템 사이의 간격 조절 */
        margin-bottom: 20px; /* 아이템 아래쪽 간격 조절 */
    }

    .item-image {
        width: 200px;
        height: 400px;
    }
</style>
</head>
<body>
<main>
    <h2>아이템 리스트</h2>
    <div class="item-container">
        <c:forEach var="item" items="${list}">
            <div class="item">
            	<a href="/item/get/${item.itemNum }">
                	<img class="item-image" src="${pageContext.request.contextPath}/items/${item.itemAttachNames}"><br>
                </a>
                <strong>${item.goods }</strong><br>
                ${item.price }<br>
            </div>
        </c:forEach>
    </div>
</main>
</body>
</html>
