<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>회원 가입</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
</head>
<body>
    <div id="userForm">
        <h2>회원 가입</h2>
        <form action="<c:url value='memberInsert.do'/>" method="post" onsubmit="return false;">
            <div class="form-group-insert">
                <label for="userid">아이디:</label>
                <input type="text" id="userid" name="memberid" placeholder="아이디를 입력하세요" required>
            	<input type="button" id="existUid" value="중복확인"/>
            </div>
            <div class="form-group-insert">
                <label for="password">비밀번호:</label>
                <input type="password" id="password" name="pwd" placeholder="비밀번호를 입력하세요" required>
            	<label for="password">비밀번호 확인:</label>
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
                <input type="submit" id="register" value="가입하기">
            </div>
        </form>
    </div>
<script>
let existUidChecked = false;

document.querySelector("#existUid").addEventListener("click", e => {
    const param = {memberid: userid.value};

    fetch("memberExist.do", {
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
    	   userid.value = "";
    	   userid.focus();
  	       existUidChecked = false;
       } else {
    	   existUidChecked = true;
       }
    });
});

document.querySelector("#register").addEventListener("click", e => {
	if (!existUidChecked) {
		alert("아이디 중복을 확인 해주세요");
		existUid.focus();
		return;
	}
    const param = {
    		memberid: userid.value,
	        pwd: password.value,
	        name: username.value,
	        phone: tel.value,
	      };

	      fetch("memberInsert.do", {
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
	        	  window.parent.postMessage("closeIframe", "*");
	          }
	      });
	
});
    
    
</script>
    <script src="<c:url value='/js/check.js'/>"></script>
</body>
</html>