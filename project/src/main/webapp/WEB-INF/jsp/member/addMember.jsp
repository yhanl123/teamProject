<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>회원가입 페이지</title>
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

function addMember() {
    var memberID = $("#memberID").val();

    $.ajax({
        url: "/member/checkDuplicate",
        method: "post",
        data: { memberID: memberID },
        dataType: "json",
        success: function (res) {
            if (res.duplicated) {
                alert("이미 사용 중인 아이디입니다.");
            } else {
                var formdata = $('#joinForm').serialize();
                
                var addr = document.getElementById('sample6_address').value;
                var detailAddr = document.getElementById('sample6_detailAddress').value;
                var extraAddr = document.getElementById('sample6_extraAddress').value;

                var fullAddress = addr + ' ' + detailAddr + ' ' + extraAddr;
                formdata += '&memberAddress=' + encodeURIComponent(fullAddress);

                $.ajax({
                    url: "/member/add",
                    data: formdata,
                    method: "post",
                    cache: false,
                    dataType: "json",
                    success: function (res) {

                        alert(res.added ? "가입 성공" : "가입 실패");
                        if (res.added) {
                            location.href = "/member/login";
                        }
                    },
                    error: function (err) {
                        alert("에러:" + err);
                    }
                });
            }
        },
        error: function (err) {
            alert("에러: " + JSON.stringify(err));
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

function checkDuplicate() {
    var memberID = $("#memberID").val();
    $.ajax({
      url: "/member/checkDuplicate",
      method: "post",
      data: { memberID: memberID },
      dataType:"json",
      success:function(res) {
        if (res.duplicated) {
          $("#idValidationMessage").text("이미 사용 중인 아이디입니다.");
        } else {
          $("#idValidationMessage").text("사용 가능한 아이디입니다.");
        }
      },
      error: function (err) {
        alert("에러: " + JSON.stringify(err));
      }
    });
  }
  
function checkPasswordMatch() {
    var memberPwd = $("#memberPwd").val();
    var confirmPassword = $("#confirmPassword").val();
    
    if (memberPwd === confirmPassword) {
        $("#passwordMatchMessage").text("비밀번호가 일치합니다.");
    } else {
        $("#passwordMatchMessage").text("비밀번호가 일치하지 않습니다.");
    }
}

var timer = null;
var email_address = "";
function auth_check()
{
   $.ajax({
      url:'/member/auth_check',
      method:'get',
      cache:false,
      data:{'memberEmail':email_address},
      dataType:'json',
      success : function(res){
         if(res.auth) {
            clearInterval(timer);
            $("#emailVerificationMessage").text("이메일 인증 성공");
         }
      },
      error : function(xhr,status,err){
         alert('에러:' + err);
      }
   })
}

function reqAuth() {
	   email_address = $('#memberEmail').val();
	   $.ajax({
	      url:'/member/auth/'+email_address,
	      method:'get',
	      cache:false,
	      dataType:'json',
	      success:function(res){
	         if(res.sent){
	            alert('입력하신 이메일 주소로 인증 메일을 보냈습니다\n인증메일의 링크를 클릭해주세요');
	            timer = setInterval(auth_check, 1000);(10)
	         }else{
	            alert('메일 보내기 실패. 다시 시도해주세요');
	         }
	      },
	      error:function(xhr,status,err){
	         alert('에러:' + err);
	      }
	   })

	   return false;
	}
</script>
</head>
<body>
<main>
<h2>회원가입 페이지</h2>
<form id="joinForm" action="/member/add" method="post" onsubmit="return addMember();">

	<div>
		<label for="memberID">아이디</label>
		<input type="text" name="memberID" id="memberID" required>
		
		<button type="button" onclick="checkDuplicate();">중복확인</button>
      	<span id="idValidationMessage"></span>
	</div>
	
	<div>
		<label for="memberPwd">비밀번호</label>
		<input type="password" name="memberPwd" id="memberPwd" required>
	</div>
	<div>
	    <label for="confirmPassword">비밀번호 확인</label>
	    <input type="password" name="confirmPassword" id="confirmPassword" required>
	    <button type="button" onclick="checkPasswordMatch()">비밀번호 확인</button>
	    <span id="passwordMatchMessage"></span>
	</div>
	<div>
		<label for="memberName">이름</label>
		<input type="text" name="memberName" id="memberName" required>
	</div>
	<div>
		<label for="memberPhone">연락처</label>
		<input type="tel" name="memberPhone" id="memberPhone" pattern="[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}" required>
	</div>
	<div>
		<label for="memberEmail">이메일</label>
		<input type="email" name="memberEmail" id="memberEmail" required>
		<button type="button" onclick="reqAuth();">이메일 인증</button>
		<span id="emailVerificationMessage"></span>
	</div>
	<div>
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
	</div>
	<div>
		<label>성별</label>
		<input type="radio" name="memberSex" id="memberSex" value="m" required checked="checked">남
		<input type="radio" name="memberSex" id="memberSex" value="f" required>여
	</div>
	<div>
		<label for="memberBirth">생년월일</label>
		<input type="date" name="memberBirth" id="memberBirth" required>
	</div>
	<div>
		<label for="national">내/외국인</label>
		<input type="radio" name="national" id="national" value="local" required checked="checked">내국인
		<input type="radio" name="national" id="national" value="foreigner" required>외국인
	</div>
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