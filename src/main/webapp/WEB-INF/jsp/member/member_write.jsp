<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>회원 가입</title>
    <link rel="stylesheet" href="/workProject/css/style.css">
</head>
<body>
    <div id="userForm">
        <h2>회원 가입</h2>
        <form action="/workProject/member/memberInsert" method="post">
            <div class="form-group-insert">
                <label for="userid">아이디:</label>
                <input type="text" id="userid" name="memberid" placeholder="아이디를 입력하세요" required>
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
                <input type="submit" value="가입하기">
            </div>
        </form>
    </div>
    <script src="/workProject/js/check.js"></script>
</body>
</html>