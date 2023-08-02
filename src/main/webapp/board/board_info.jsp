<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>게시물 상세 보기</title>
  <style>
    .board-title {
      font-size: 24px;
      font-weight: bold;
      margin-bottom: 20px;
    }
    .post-detail {
      margin-bottom: 20px;
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
    }
    .post-actions {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-top: 20px;
    }
    .button-container {
      display: flex;
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
  </style>
</head>
<body>
  <div id="container">
    <%@ include file="../header.jsp" %>
    <h1 class="board-title">게시물 상세 보기</h1>
    <div class="post-detail">
      <h2 class="post-title" id="post-title">${infoBoard.title }</h2>
      <p class="post-meta" id="post-meta">작성자: ${infoBoard.mem_id } | 작성일: ${infoBoard.mod_date } | 조회수: ${infoBoard.view_count }</p>
      <div class="post-content" id="post-content">
        ${infoBoard.content }
      </div>
    </div>
    <div class="post-actions">
      <div class="button-container">
        <a class="edit-button">글 수정</a>
        <a class="delete-button">글 삭제</a>
      </div>
      <a class="back-button">목록</a>
    </div>
  </div>
</body>
</html>