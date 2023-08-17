<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<c:url value='/css/complete.css'/>">
</head>
<body>
	<div class="complete" id="link">
    <p class="alert-message">${alertmessage}</p>
    <a class="back" href="#">돌아가기</a>
  </div>
  <script>
  	var chkMem = '${chkMem}';
  	var link = document.querySelector('#link a');
  	var href = 'memberWrite.do';
  	
  	if(chkMem == 'findid' || chkMem =='findpwd') {
  		href = 'memberSearchMove.do?chkMem=' + chkMem;
  	}
  	
  	link.addEventListener('click', () => {
			window.location.href = href;
		});
  
  </script>
</body>
</html>