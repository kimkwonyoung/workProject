<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<link rel="stylesheet" href="/workProject/css/board.css">
  <title>게시판 목록</title>
</head>
<body>
  <div class="list-all" id="container">
  <%@ include file="../header.jsp" %>
    <div class="board-header">
    <c:if test="${board_type eq 20 }">
    	<h1 class="board-title">공지사항 게시판</h1>
    </c:if>
    <c:if test="${board_type eq 10 }">
    	<h1 class="board-title">일반 게시판</h1>
    </c:if>
      <c:if test="${not empty loginMember}">
      <div class="link-header">
          <a class="write-button" id="del">글삭제</a>
	      <a class="write-button" id="wr">글쓰기</a> 
      </div>
      </c:if>
    </div>
    <table>
      <thead>
        <tr>
          <th class="checkbox-all"><input type="checkbox" id="checkAll"></th>
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
          <td class="checkbox-cell"><input type="checkbox" name="chkBoardNum" class="chkbox" value="${board.board_num }"></td>
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
    var open = document.querySelector('#wr');
    open.addEventListener('click', () => {
      window.location.href = '/workProject/board/boardWrite';
    });
	
    
    var board_type = '${board_type}';
    var checkAllCheckbox = document.getElementById("checkAll");
    var chkboxs = document.querySelectorAll(".chkbox");
    
    checkAllCheckbox.addEventListener("click", () => {
    	chkboxs.forEach((chk) => {
    		chk.checked = checkAllCheckbox.checked;
    	});
    });
    
    var deleteLink = document.getElementById('del');
	deleteLink.addEventListener('click', (event) => {		
		if (confirm('정말로 삭제 하시겠습니까?')) {
			var deleteStr = '';
			var chkb = document.getElementsByName('chkBoardNum');
			chkb.forEach((chkbox) => {
				if (chkbox.checked) deleteStr += chkbox.value + ','
			});
			
			if (deleteStr == '') {
				alert('삭제할 글을 선택 하세요.');
			} else {
				deleteStr = deleteStr.substr(0, deleteStr.length - 1);
				window.location.href = '/workProject/board/boardDeleteChkbox?board_type=' + board_type + '&bnumStr=' + deleteStr;
			}
		} else {
	        event.preventDefault();
	        return false;
	    }
		
	});
    
    
  </script>
</body>
</html>