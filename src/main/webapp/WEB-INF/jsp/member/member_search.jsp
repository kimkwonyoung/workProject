<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
 
</head>
<body>
    <div id="userForm">
    <c:if test="${chkMem eq 'findid' }">
    	<h2>아이디 찾기</h2>
    </c:if>
    <c:if test="${chkMem eq 'findpwd' }">
    	<h2>비밀번호 찾기</h2>
    </c:if>
        
        <form action="memberSearch.do" method="post">
         <c:if test="${chkMem eq 'findpwd' }">
            <div class="form-group-insert">
                <label for="userid">아이디:</label>
                <input type="text" id="userid" name="memberid" placeholder="아이디를 입력하세요" required>
            </div>
         </c:if>
            <div class="form-group-insert">
                <label for="username">이름:</label>
                <input type="text" id="username" name="name" placeholder="이름을 입력하세요" required>
            </div>
            <c:if test="${chkMem eq 'findid' }">
	            <div class="form-group-insert">
	                <label for="userid">휴대폰번호:</label>
	                <input type="text" id="tel" name="phone" placeholder="휴대폰번호를 입력하세요" required>
	            </div>
         	</c:if>
            <div class="form-group-insert">
            	<c:if test="${chkMem eq 'findid' }">
            		<input type="hidden" name="chkMem" value="findid">
                	<input type="submit" value="아이디 찾기">
                </c:if>
                <c:if test="${chkMem eq 'findpwd' }">
                	<input type="hidden" name="chkMem" value="findpwd">
                	<input type="submit" value="비밀번호 찾기">
                </c:if>
            </div>
                	
        </form>
    </div>
    <script src="<c:url value='/js/check.js'/>"></script>

</body>
</html>