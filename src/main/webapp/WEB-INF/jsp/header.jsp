<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
<script src="<c:url value='/js/jsrender.js'/>"></script>
<script src="<c:url value='/js/jsviews.js'/>"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="<c:url value='/css/style.css'/>">
<script src="<c:url value='/js/slideshow.js'/>"></script>
<body>
<header>
      <div id="logo">
        <a href="<c:url value='/main/mainIndex.do'/>">
          <h1>DreamHome</h1>
        </a>
      </div>
	 <nav>
        <ul id="topMenu">
          <li><a href="#">주거 공간<span>▼</span></a>
            <ul>
              <li><a href="#">가구1</a></li>
              <li><a href="#">가구2</a></li>
            </ul>
          </li>
          <li><a href="#">상업 공간<span>▼</span></a>
            <ul>
              <li><a href="#">상업1</a></li>
              <li><a href="#">상업2</a></li>
              <li><a href="#">상업3</a></li>
            </ul>
          </li>
          <li><a href="#">문의하기</a></li>
          <li>
			<a href="#" class="openbtn">메뉴 열기</a>
			<div class="sidebar">
				<a href="#" class="closebtn">×</a> <a href="#">회사소개</a>
				<a href="#">연혁</a> 
				<a href="#">비즈니스</a> 
				<a href="#">연락처</a>
				 <c:if test="${empty loginMember.memberid eq 'admin' }">
				 	<a href="<c:url value='/member/memberList.do'/>" class="forlink">전체 회원 보기</a>
				 </c:if>
				<a href="<c:url value='/board/boardList.do?board_code=10'/>" class="forlink">일반 게시판</a>
				<a href="<c:url value='/board/boardList.do?board_code=20'/>" class="forlink">공지사항 게시판</a>
				<a href="<c:url value='/board/boardList2.do'/>" class="forlink">일반 더보기 게시판</a>
				
				
			</div>
          </li>
        </ul>
      </nav>
    </header>
<script type="text/javascript">
	$(".openbtn").on("click", () => {
		$(".sidebar").css("width", "226px");
		$(this).css("display", "none");
	});
	$(".closebtn").on("click", () => {
		$(".sidebar").css("width", "0");
		$(".openbtn").css("display", "block");
	});
/* 	var open = document.querySelector('.openbtn');
	var close = document.querySelector('.closebtn');
	open.addEventListener('click', () => {
		document.querySelector('.sidebar').style.width = "226px";
		document.querySelector('.openbtn').style.display = 'none';
	});
	close.addEventListener('click', () => {
		document.querySelector('.sidebar').style.width = "0";
		document.querySelector('.openbtn').style.display = 'block';
	}); */
</script>
</body>
</html>