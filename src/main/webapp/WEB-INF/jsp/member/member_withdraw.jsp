<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
</head>
<title>회원 탈퇴</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
</head>
<body>
    <div id="userForm2">
        <h2>회원 탈퇴</h2>
        <form action="memberWithdraw.do" method="post" onsubmit="return false;">
            <div class="form-group-insert">
            <div class="form-group-insert">
                <label for="userid">아이디:</label>
                <input type="text" id="userid" name="memberid" placeholder="아이디를 입력하세요" required>
            </div>
                <label for="password">비밀번호:</label>
                <input type="password" id="password" name="pwd" placeholder="비밀번호를 입력하세요" required>
            	<label class="pwdlabel" for="password">비밀번호 확인:</label>
            	<input type="password" id="password2" name="pwd2" placeholder="비밀번호를 입력하세요" required>
            </div>
            <div class="form-group-insert">
                <input type="submit" value="회원 탈퇴 하기">
            </div>
            <a href="#" class="back-upwt">돌아가기</a>
        </form>
    </div>
    <script type="text/javascript">
  		var href = "/workProject/member/memberInfo";
		
		var back = document.querySelector(".back-upwt");
    	back.addEventListener("click", () => {
    		window.location.href = href;
    		
    	});
		
    	var pwdCheck = document.querySelector("form");
    	pwdCheck.addEventListener("submit", (event) => {
    		if(document.getElementById("password").value !== document.getElementById("password2").value ) {
    			event.preventDefault();
    			alert("비밀번호가 일치 하지 않습니다.")
    		} else {
    			if(confirm("정말로 회원 탈퇴 하시겠습니까?")) {
    				const param = {
    			    		memberid: userid.value,
    				        pwd: password.value
    				};
    			
    			    fetch("memberWithdraw.do", {
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
    			      	  location.href = "<c:url value='mainIndex.do'/>"; 
    			        }
    			    });
    				
    			} else {
    				event.preventDefault();
    			}
    			
    		}
    	});
    	
    </script>
</body>
</html>