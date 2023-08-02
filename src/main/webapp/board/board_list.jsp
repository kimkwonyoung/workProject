<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<link rel="stylesheet" href="/workProject/css/style.css">
  <title>게시판 목록</title>
  <style>
    .board-header {
      display: flex;
      align-items: center;
      justify-content: space-between;
    }
    .board-title {
      font-size: 24px;
      font-weight: bold;
    }
    .write-button {
      background-color: #007bff;
      color: #fff;
      border: none;
      padding: 10px 20px;
      font-size: 16px;
      border-radius: 5px;
      cursor: pointer;
      margin-top:20px;
    }
    .write-button:hover {
      background-color: #0056b3;
    }
    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 20px;
    }
    th, td {
      border: 1px solid #ddd;
      padding: 10px;
      text-align: center;
    }
    th {
      background-color: #f2f2f2;
    }
    tr:nth-child(even) {
      background-color: #f2f2f2;
    }
    th:nth-child(4), td:nth-child(4) { 
      width: 100px;
    }
    tr:hover {
      background-color: #f9f9f9;
    }
    th input[type="checkbox"],
    td input[type="checkbox"] {
      margin-right: 5px;
    }
    .checkbox-cell {
      text-align: center;
    }
  </style>
</head>
<body>
  <div id="container">
  <%@ include file="../header.jsp" %>
   <c:set var="boardList" value="${requestScope.boardList }" />
    <div class="board-header">
      <h1 class="board-title">전체 게시판</h1>
      <a class="write-button">글쓰기</a>
    </div>
    <table>
      <thead>
        <tr>
          <th class="checkbox-cell"><input type="checkbox"></th>
          <th>글번호</th>
          <th>글제목</th>
          <th>작성자</th>
          <th>작성일</th>
          <th>조회수</th>
        </tr>
      </thead>
      <tbody>
       <c:forEach var="board" items="${boardList}">
        <tr>
          <td class="checkbox-cell"><input type="checkbox"></td>
          <td>${board.board_num }</td>
          <td><a href="/workProject/board?action=BoardInfo&board_num=${board.board_num }">${board.title}</a></td>
          <td>${board.mem_id }</td>
          <td>${board.mod_date }</td>
          <td>${board.view_count }</td>
        </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
  <script>
    var open = document.querySelector('.write-button');
    open.addEventListener('click', () => {
      window.location.href = '/workProject/board/board_write.jsp';
    });
  </script>
</body>
</html>