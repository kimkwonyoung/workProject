<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/workProject/css/style.css">
  <title>게시물 상세 보기</title>
  <style>
    .post-detail {
      margin-top: 50px;
    }
    .post-title {
      font-size: 24px;
      font-weight: bold;
      margin-bottom: 10px;
      border-bottom: 1px solid #604e4e;
      padding-bottom: 10px;
    }
    .post-meta {
      color: #777;
      margin-bottom: 10px;
    }
    .post-content {
      line-height: 1.6;
      padding-left: 10px;
      border-bottom: 1px solid #604e4e;
      padding: 10px 0px 30px 5px;
      margin-top: 20px;
    }

    .button-container {
      margin-top: 20px;
      align-items: center;
    }
    .edit-button,
    .back-button,
    .delete-button {
      background-color: #007bff;
      color: #fff;
      border: none;
      padding: 8px 16px;
      font-size: 14px;
      border-radius: 5px;
      cursor: pointer;
      margin-right: 10px;
      transition: background-color 0.3s;
    }
    .edit-button:hover,
    .back-button:hover,
    .delete-button:hover {
      background-color: #0056b3;
    }
    .back-button {
      float:right;
    }
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
        <a class="edit-button" id="edit" href="#">글 수정</a>
        <a class="delete-button" id="del" href="#">글 삭제</a>
       </c:if>
        <a class="back-button" id="back" href="/workProject/board?action=Boardlist">목록</a>
      </div>
      
  </div>
  <script>
 	var boardnum = '${infoBoard.board_num}';
	var updateLink = document.getElementById('edit');
	updateLink.addEventListener('click', () => {
		window.location.href = '/workProject/board?action=BoardUpdateInfo&board_num=' + boardnum;
	});
/* 	var back = document.getElementById('back');
	back.addEventListener('click', () => {
		window.location.href = '/workProject/board?action=Boardlist';
  }); */

	var deleteLink = document.getElementById('del');
	deleteLink.addEventListener('click', (event)=> {
		if(confirm('정말로 삭제 하시겠습니까?')) {
			window.location.href = '/workProject/board?action=BoardDelete&board_num=' + boardnum;
		} else {
			event.preventDefault();
		}
	});
  </script>
</body>
</html>