<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="/project/css/style.css">
 
</head>
<body>
    <div id="userForm">
    <c:if test="${param.dis eq 'searchId' }">
    	<h2>아이디 찾기</h2>
    </c:if>
    <c:if test="${param.dis eq 'searchPwd' }">
    	<h2>비밀번호 찾기</h2>
    </c:if>
        
        <form action="/project/main" method="post">
         <c:if test="${param.dis eq 'searchPwd' }">
            <div class="form-group-insert">
                <label for="userid">아이디:</label>
                <input type="text" id="userid" name="uid" placeholder="아이디를 입력하세요" required>
            </div>
         </c:if>
            <div class="form-group-insert">
                <label for="username">이름:</label>
                <input type="text" id="username" name="name" placeholder="이름을 입력하세요" required>
            </div>
            <div class="form-group-insert">
            	<c:if test="${param.dis eq 'searchId' }">
            		<input type="hidden" name="dis" value="searchId">
                	<input type="submit" value="아이디 찾기">
                </c:if>
                <c:if test="${param.dis eq 'searchPwd' }">
                	<input type="hidden" name="dis" value="searchPwd">
                	<input type="submit" value="비밀번호 찾기">
                </c:if>
            </div>
                	<input type="hidden" name="action" value="membersearch">
                	
        </form>
    </div>
    <script type="text/javascript">
    	
    </script>

</body>
</html>