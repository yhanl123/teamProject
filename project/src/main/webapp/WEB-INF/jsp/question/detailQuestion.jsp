<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>질문 글 보기</title>
<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<script type="text/javascript">
function delContents() {
	if(!confirm('정말 삭제하시겠습니까?')) return;
	
	$.ajax({
		url:'/question/delete/${q.questionNum}',
		method:'get',
		cache:false,
		dataType:'json',
		success:function(res) {
			alert(res.deleted ? '삭제 성공':'삭제 실패');
			if(res.deleted){
				location.href="/question/list";
			}
		},
		error:function(xhr,status,err){
			alert('에러:' + err);
		}
	});
}

function addAnswer() {
	var formdata = $('#answerForm').serialize();
	
	if (answerContents === '') {
		alert('답변 내용을 입력해주세요.');
		return;
	}
	
	$.ajax({
		url:'/answer/add',
		method:'post',
		cache:false,
		dataType:'json',
		data:formdata,
		success:function(res) {
			if (res.added) {
				alert('답변이 등록되었습니다.');
				location.reload();
			} else {
				alert('답변 등록에 실패했습니다.');
			}
		},
		error:function(xhr,status,err){
			alert('에러:' + err);
		}
	});
	
	return false;
}

//답변 달리면 답변이 보여지는 폼
$(document).ready(function() {
	$.ajax({
		url: '/answer/get/${q.questionNum}',
		method: 'get',
		cache: false,
		dataType: 'json',
		success: function(res) {
			if (res.a) {
				var answerAuthor = $('<p>').text(res.a.answerAuthor);
				var answerContents = $('<p>').text(res.a.answerContents);
				
				$('#answer').append(answerAuthor);
				$('#answer').append(answerContents);
				
			}
		},
		error: function(xhr, status, err) {
			alert('에러:' + err);
		}
	});
});

function deleteAnswer(answerNum) {
    if (!confirm('정말 답변을 삭제하시겠습니까?')) return;
    
    $.ajax({
        url: '/answer/delete/' + answerNum,
        method: 'get',
        cache: false,
        dataType: 'json',
        success: function(res) {
            alert(res.deleted ? '답변 삭제 완료' : '답변 삭제 실패');
            if (res.deleted) {
                location.reload();
            }
        },
        error: function(xhr, status, err) {
            alert('에러:' + err);
        }
    });
}
</script>
</head>
<body>
<main>
<h2>질문 글 보기</h2>
<table>
	<tr><th>제목</th><td>${q.questionTitle }</td></tr>
	<tr><th>내용</th><td>${q.questionContents }</td></tr>
	
</table>
<p>
<c:if test="${userid==q.questionAuthor}"> <!-- 글쓴 사람만 수정 삭제가 보임 -->
	<a href="/question/update/${q.questionNum }">수정</a>
	<a href="javascript:delContents();">삭제</a>
</c:if>
</p>
<h3>답변</h3>
<div id="answer"></div>

<c:if test="${userid=='admin'}">  <!-- admin 만 수정 삭제가 보임 -->
	<c:if test="${not empty a}"> <!-- 답변(a)이 존재 한다면 보이는 링크들. 답변이 달려있지 않다면 보이지 않음 -->
        <a href="/answer/update/${a.answerNum}/${q.questionNum}">수정</a>
        <a href="javascript:deleteAnswer(${a.answerNum});">삭제</a>
    </c:if>
</c:if>
<p>
<hr>
<c:if test="${userid=='admin'}"> <!-- admin만 답변 작성 폼이 보임 -->
<h3>답변 작성</h3>
<form id="answerForm" onsubmit="return addAnswer();">
<input type="hidden" name="pQuestionNum" value="${q.questionNum }">
	<textarea id="answerContents" name="answerContents" rows="5" cols="50"></textarea><br>
	<button type="submit">답변 등록</button>
</form>
</c:if>
</main>
</body>
</html>
