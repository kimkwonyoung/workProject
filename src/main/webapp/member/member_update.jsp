<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
</head>
<title>회원 정보 수정</title>
<link rel="stylesheet" href="/project/css/style.css">
</head>
<body>
    <div id="userForm">
        <h2 id="titleuser">회원 정보 수정</h2>
        <form action="/project/main" method="post">
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
                <input type="submit" value="정보 수정 하기">
            </div>
            <a href="#" class="back-upwt">돌아가기</a>
            <input type="hidden" name="searchId" value="${param.searchId}" >
            <input type="hidden" name="action" value="memberupdate">
        </form>
    </div>
    <script src="/project/js/check.js"></script>
    <script>
    	var back = document.querySelector('.back-upwt');
		back.addEventListener('click', () => {
			window.history.back();
		});

/*     	var pwdCheck = document.querySelector('form');
    	pwdCheck.addEventListener('submit', (event) => {
    		if(document.getElementById('password').value !== document.getElementById('password2').value ) {
    			event.preventDefault();
    			alert('변경 할 비밀번호가 일치 하지 않습니다.')
    		}
    	}); */
    	
    </script>

</body>
</html>