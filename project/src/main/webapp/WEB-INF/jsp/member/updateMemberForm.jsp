<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>회원정보 수정</title>
<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	  $('#memberPhone').on('input', function() {
	    var input = $(this).val().replace(/[^0-9]/g, '');
	    var formatted = input.replace(/(\d{3})(\d{0,4})(\d{0,4})/, function(match, p1, p2, p3) {
	      var result = p1;
	      if (p2) result += '-' + p2;
	      if (p3) result += '-' + p3;
	      return result;
	    });

	    $(this).val(formatted);
	  });
	}); //번호만 쳐도 하이픈이 자동으로 나오게


function update() {
    var addr = document.getElementById('sample6_address').value;
    var detailAddr = document.getElementById('sample6_detailAddress').value;
    var extraAddr = document.getElementById('sample6_extraAddress').value;
    var fullAddress = addr + ' ' + detailAddr + ' ' + extraAddr;
		
	var serdata = $('#updateForm').serialize();
	serdata += '&memberAddress=' + encodeURIComponent(fullAddress);
	
	$.ajax({
		url:'/member/update',
		method:'post',
		cache:false,
		data:serdata,
		dataType:'json',
		success:function(res){
			alert(res.updated ? '수정 성공':'수정 실패');
			if(res.updated) location.href='/member/get/${m.memberNum}';
		},
		error:function(xhr,status,err){
			alert('에러:' + err);
		}
	});
	return false;
}
	
function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져옵니다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일 때 참고항목을 조합합니다.
            if (data.userSelectedType === 'R') {
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
            }

            // 우편번호와 주소 정보를 해당 필드에 넣습니다.
            document.getElementById('sample6_postcode').value = data.zonecode;
            document.getElementById('sample6_address').value = addr;
            // 커서를 상세주소 필드로 이동합니다.
            document.getElementById('sample6_detailAddress').focus();
            // 주소 관련 필드에 값을 설정합니다.
            document.getElementById('memberPostcode').value = data.zonecode;
            document.getElementById('memberAddress').value = addr + ' ' + data.jibunAddressEnglish + extraAddr; // 주소 정보를 하나의 문자열로 합침
        }
    }).open();
}
</script>
</head>
<body>
<main>
<h2>회원정보 수정</h2>
<form id="updateForm" action="/member/update" method="post" onsubmit="return update();">
<input type="hidden" name="memberNum" value="${m.memberNum }">
<table>
	<tr><th>아이디</th><td>${m.memberID }</td></tr>
	<tr><th>비밀번호</th><td><input type="password" name="memberPwd" id="memberPwd" value="${m.memberPwd }"></td></tr>
	<tr><th>이름</th><td>${m.memberName }</td></tr>
	<tr><th>연락처</th><td><input type="tel" name="memberPhone" id="memberPhone" pattern="[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}" value="${m.memberPhone }"></td></tr>
	<tr><th>이메일</th><td><input type="email" name="memberEmail" id="memberEmail" value="${m.memberEmail }"></td></tr>
	<tr><th>생년월일</th><td>${m.memberBirth }</td></tr>
	<tr><th>주소</th>
	<td>
	    <label for="sample6_postcode">우편번호</label>
	    <input type="text" id="sample6_postcode" placeholder="우편번호">
	    <input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
	    <label for="sample6_address">주소</label>
	    <input type="text" id="sample6_address" placeholder="주소"><br>
	    <label for="sample6_detailAddress">상세주소</label>
	    <input type="text" id="sample6_detailAddress" placeholder="상세주소">
	    <label for="sample6_extraAddress">참고항목</label>
	    <input type="text" id="sample6_extraAddress" placeholder="참고항목">
	    <input type="hidden" id="memberAddress" name="memberAddress">
	</td></tr>
</table>
	<div>
		<fieldset>
			<legend>관심사</legend>
			<div>
				1<input type="checkbox" name="interest" value="1">
				2<input type="checkbox" name="interest" value="2">
				3<input type="checkbox" name="interest" value="3">
				4<input type="checkbox" name="interest" value="4">
				5<input type="checkbox" name="interest" value="5">
				6<input type="checkbox" name="interest" value="6">
				7<input type="checkbox" name="interest" value="7">
				8<input type="checkbox" name="interest" value="8">
				9<input type="checkbox" name="interest" value="9">
				10<input type="checkbox" name="interest" value="10">
			</div>
		</fieldset>
	</div>
	<div id="btn">
		<button type="reset">취소</button>
		<button type="submit">저장</button>
	</div>
</form>
</main>
</body>
</html>