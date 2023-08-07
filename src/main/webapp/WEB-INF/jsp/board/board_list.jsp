<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<link rel="stylesheet" href="/workProject/css/style.css">
<link rel="stylesheet" href="/workProject/css/board.css">
  <title>게시판 목록</title>
</head>
<body>
  <div class="list-all" id="container">
  <%@ include file="../header.jsp" %>
   <c:set var="boardList" value="${requestScope.boardList }" />
    <div class="board-header">
    <c:if test="${board_type eq 20 }">
    	<h1 class="board-title">공지사항 게시판</h1>
    </c:if>
    <c:if test="${board_type eq 10 }">
    	<h1 class="board-title">전체 게시판</h1>
    </c:if>
      <c:if test="${not empty loginMember}">
      <a class="write-button">글쓰기</a>
      </c:if>
    </div>
    <table>
      <thead>
        <tr>
          <th class="checkbox-all"><input type="checkbox"></th>
          <th>글번호</th>
          <th>글제목</th>
          <th>작성자</th>
          <th>작성일</th>
          <th>조회수</th>
        </tr>
      </thead>
      <tbody>
       <c:forEach var="board" items="${boardList}">
        <tr class="${board.fixed_yn eq 'Y' ? 'highlight':'' }">
          <td class="checkbox-cell"><input type="checkbox"></td>
          <td>${board.board_num }</td>
          <td><a href="/workProject/board/boardInfo?board_num=${board.board_num }&board_type=${board.board_code}">${board.title}</a></td>
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
      window.location.href = '/workProject/board/boardWrite';
    });
    
  </script>
</body>
</html>