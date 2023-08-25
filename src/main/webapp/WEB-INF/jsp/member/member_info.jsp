<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="<c:url value='/css/memberinfo.css'/>">
  <script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
  <div class="container">
    <h1>나의 회원 정보</h1>
    <ul class="info-list">
      <li><span class="info-label">이름:</span> ${loginMember.name}</li>
      <li><span class="info-label">아이디:</span> ${loginMember.memberid}</li>
      <li><span class="info-label">비밀번호:</span> ${loginMember.pwd}</li>
      <li><span class="info-label">핸드폰번호:</span> ${loginMember.phone}</li>
    </ul>
    <div id="linklist">
    	<a href="#" class="back-link">돌아가기</a>
    	<a href="#" class="back-link">회원 정보 수정</a>
    	<a href="#" class="back-link">회원 탈퇴</a>
    </div>
  </div>
  <script>
  	var memberid = "${loginMember.memberid}";
  	//var links = document.querySelectorAll("#linklist a");
    var hrefArr = ["<c:url value='/main/mainIndex.do'/>", 
    			   "<c:url value='/member/memberUpdateMove.do?sMemid=" + memberid + "'/>",
    			   "<c:url value='/member/memberWithdrawMove.do'/>"];
	
/* 	for(var i = 0; i < links.length; i++) {
		var link = links[i];
		link.addEventListener("click", createClickHandler(hrefArr[i]));
	} */
	$("#linklist a").each((i, link) => {
	    $(link).on("click", createClickHandler(hrefArr[i]));
	});
	
  	function createClickHandler(href) {
  		return () => {
  		    window.location.href = href;
  		  }
  	}
  </script>
</body>
</html>