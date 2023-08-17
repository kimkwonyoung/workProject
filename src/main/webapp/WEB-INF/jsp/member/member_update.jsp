<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
</head>
<title>회원 정보 수정</title>
<link rel="stylesheet" href="<c:url value='/css/style.css'/>">
</head>
<body>
    <div id="userForm">
        <h2 id="titleuser">회원 정보 수정</h2>
        <form action="memberUpdate.do" method="post" onsubmit="return false;">
            <div class="form-group-insert">
                <label for="password">변경 비밀번호:</label>
                <input type="password" id="password" name="pwd" placeholder="비밀번호를 입력하세요" required>
            	<label class="pwdlabel" for="password">변경 비밀번호 확인:</label>
            	<input type="password" id="password2" name="pwd2" placeholder="비밀번호를 입력하세요" required>
            </div>
            <div class="form-group-insert">
                <label for="username">이름:</label>
                <input type="text" id="username" name="name" placeholder="이름을 입력하세요" required>
            </div>
            <div class="form-group-insert">
                <label for="tel">휴대폰 번호:</label>
                <input type="tel" id="tel" name="phone" placeholder="휴대폰 번호를 입력하세요" required>
            </div>
            <div class="form-group-insert">
                <input type="submit" id="updateMem" value="정보 수정 하기">
            </div>
            <a href="#" class="back-upwt">돌아가기</a>
        </form>
    </div>
    <script src="<c:url value='/js/check.js'/>"></script>
<script>
var sMemid = "${sMemid}";
var back = document.querySelector(".back-upwt");
back.addEventListener("click", () => {
	window.history.back();
});

document.querySelector("#updateMem").addEventListener("click", e => {
	const param = {
		memberid: sMemid,
	    pwd: password.value,
	    name: username.value,
	    phone: tel.value,
	  };
	
	  fetch("memberUpdate.do", {
	    method: "POST",
	    headers: {
	      "Content-Type": "application/json; charset=UTF-8",
	    },
	    body: JSON.stringify(param),
	  })
	  .then((response) => response.json())
	  .then((json) => {
		  alert(json.message);
	      if (json.status) {
	    	  //location.href = "<c:url value='memberInfo.do'/>"; 
	    	  location.href = "memberInfo.do"; 
	      }
	  });
	
});
</script>

</body>
</html>