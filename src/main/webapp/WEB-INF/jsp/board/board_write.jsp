<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<link rel="stylesheet" href="/workProject/css/style.css">
  <title>게시판 글쓰기</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f1f1f1;
      margin: 0;
      padding: 0;
    }
    .container {
      max-width: 800px;
      margin: 20px auto;
      background-color: #fff;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      padding: 20px;
      border-radius: 5px;
    }
    .form-title {
      font-size: 24px;
      font-weight: bold;
      margin-bottom: 20px;
    }
    .form-input {
      width: 100%;
      padding: 10px;
      font-size: 16px;
      border: 1px solid #ddd;
      border-radius: 5px;
      margin-bottom: 20px;
    }
    .radio-group {
      display: flex;
      align-items: center;
      margin-bottom: 20px;
    }
    .radio-label {
      margin-right: 10px;
    }
    .radio-input {
      margin-right: 5px;
    }
    .submit-button,
    .back-button {
      background-color: #007bff;
      color: #fff;
      border: none;
      padding: 10px 20px;
      font-size: 16px;
      border-radius: 5px;
      cursor: pointer;
    }
    .back-button {
    	float:right;
    }
    
    .submit-button:hover,
    .back-button:hover {
      background-color: #0056b3;
    }
    .check-fix {
    	margin-bottom:20px;
    	display:none;
    }
  </style>
</head>
<body>
  <div id="container">
  <%@ include file="../header.jsp" %>
  <c:if test="${requestScope.chk eq 'write' }">
    <h1 class="form-title">글쓰기</h1>
  </c:if>
  <c:if test="${requestScope.chk eq 'update' }">
  	<h1 class="form-title">글수정</h1>
  </c:if>
   <c:if test="${requestScope.chk eq 'write' }">
   
   </c:if>
    <form id="writeForm" action=
    
    	<c:if test="${requestScope.chk eq 'write' }">
    		"/workProject/board/boardInsert" 
    	</c:if>
    	<c:if test="${requestScope.chk eq 'update' }">
    		"/workProject/board/boardUpdate" 
    	</c:if>
    	
    method="post">
    
      <div class="radio-group">
        <input type="radio" id="notice" name="board_code" value="20" class="radio-input" required>
        <label for="notice" class="radio-label">공지사항</label>

        <input type="radio" id="normal" name="board_code" value="10" class="radio-input" required>
        <label for="normal" class="radio-label">일반글</label>
      </div>
      <div class="check-fix" id="checkbox-div">
      	<input type="checkbox" id="fixed-yn" name="fixed_yn" value="Y">
      	<label class="" id="fixed">상단 고정</label>
      </div>

      <label for="title">제목:</label><br>
      <input type="text" id="title" name="title" class="form-input" value="${infoBoard.title }" required><br>

      <label for="content">내용:</label><br>
      <textarea id="content" name="content" rows="6" class="form-input" required>${infoBoard.content }</textarea><br>

      <a href="#" id="submitLink" class="submit-button">글쓰기 완료</a>
      <a href="#" class="back-button">목록으로 돌아가기</a>
      
      <c:if test="${requestScope.chk eq 'write' }">
        <input type="hidden" name="memberid" value="${loginMember.memberid}">
      </c:if>
      <c:if test="${requestScope.chk eq 'update' }">
      	<input type="hidden" name="board_num" value="${infoBoard.board_num}">
      </c:if>
    </form>
  </div>
<script>
const board_code = '${infoBoard.board_code}';
const radioButtons = document.getElementsByName("type");
	
for (const radioButton of radioButtons) {
	if (radioButton.value === board_code) {
		radioButton.checked = true;
	}
}
document.getElementById("submitLink").addEventListener("click", () => {
	document.getElementById("writeForm").submit();
});
var open = document.querySelector('.back-button');
open.addEventListener('click', () => {
	history.back();
});
    
const fixedCheckbox = document.getElementById('fixed-yn');
const fiexed = document.getElementById('fixed');
fiexed.addEventListener('click', () => {
	fixedCheckbox.checked = !fixedCheckbox.checked;
});
    
const noticeRadioButton = document.getElementById('notice');
const normalRadioButton = document.getElementById('normal');

const checkboxDiv = document.getElementById('checkbox-div');

noticeRadioButton.addEventListener('change', ()=> {
	if (noticeRadioButton.checked) {
		checkboxDiv.style.display = 'block'; 
	} else {
		checkboxDiv.style.display = 'none'; 
	}
});

normalRadioButton.addEventListener('change', ()=> {
if (normalRadioButton.checked) {
	checkboxDiv.style.display = 'none'; 
} else {
	checkboxDiv.style.display = 'block'; 
	}
});
</script>
</body>
</html>