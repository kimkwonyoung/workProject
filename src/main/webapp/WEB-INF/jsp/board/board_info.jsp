<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/workProject/css/style.css">
<link rel="stylesheet" href="/workProject/css/board.css">
  <title>게시물 상세 보기</title>
  <style>
    
    

    
    
  </style>
</head>
<body>
  <div id="container">
    <%@ include file="../header.jsp" %>
    <!-- <h1 class="board-title">게시물 상세 보기</h1> -->
    <div class="post-detail">
      <h2 class="post-title" id="post-title">${infoBoard.title }</h2>
      <p class="post-meta" id="post-meta">작성자: ${infoBoard.mem_id } | 작성일: ${infoBoard.mod_date } | 조회수: ${infoBoard.view_count }</p>
      <div class="post-content" id="post-content">
        ${infoBoard.content }
      </div>
    </div>
      <div class="button-container">
      <c:if test="${loginMember.memberid eq infoBoard.mem_id }">
        <a class="edit-button-info" id="edit" href="#">글 수정</a>
        <a class="delete-button-info" id="del" href="#">글 삭제</a>
       </c:if>
        <a class="back-button-info" id="back" href="/workProject/board/boardList?board_type=${requestScope.board_type }">목록</a>
      </div>
      
  </div>
  <script>
 	var boardnum = '${infoBoard.board_num}';
 	var boardcode = '${infoBoard.board_code}';
	var updateLink = document.getElementById('edit');
	updateLink.addEventListener('click', () => {
		window.location.href = '/workProject/board/boardUpdateInfo?board_num=' + boardnum;
	});
/* 	var back = document.getElementById('back');
	back.addEventListener('click', () => {
		window.location.href = '/workProject/board?action=Boardlist';
  }); */

	var deleteLink = document.getElementById('del');
	deleteLink.addEventListener('click', (event)=> {
		if(confirm('정말로 삭제 하시겠습니까?')) {
			window.location.href = '/workProject/board/boardDelete?board_num=' + boardnum + '&board_code=' + boardcode;
		} else {
			event.preventDefault();
		}
	});
  </script>
</body>
</html>