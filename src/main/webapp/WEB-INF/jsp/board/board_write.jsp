<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<link rel="stylesheet" href="<c:url value='/css/board.css'/>">
  <title>게시판 글쓰기</title>
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
    		"boardInsert.do" 
    	</c:if>
    	<c:if test="${requestScope.chk eq 'update' }">
    		"boardUpdate.do" 
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
        <input type="hidden" name="mem_id" value="${loginMember.memberid}">
      </c:if>
      <c:if test="${requestScope.chk eq 'update' }">
      	<input type="hidden" name="board_num" value="${infoBoard.board_num}">
      </c:if>
    </form>
  </div>
<script>
const board_code = '${infoBoard.board_code}';

//공지사항 일반 라디오 체크
$("input[name='board_code']").each((i, radio) => {
    if ($(radio).val() === board_code) {
        $(radio).prop("checked", true);
    }
});

/* const radioButtons = $("input[name='board_code']");
for (const radioButton of radioButtons) {
	if (radioButton.value === board_code) {
		radioButton.checked = true;
	}
} */

//form submit
$("#submitLink").on("click", () => {
	$("#writeForm").submit();
});

//뒤로가기
$(".back-button").on("click", () => {
	history.back();
});

//상단 고정 체크박스
$("#fixed").on("click", () => {
	$("#fixed-yn").prop("checked", !$("#fixed-yn").prop("checked"));
});

/* const fixedCheckbox = document.getElementById('fixed-yn');
const fiexed = document.getElementById('fixed');
fiexed.addEventListener('click', () => {
	fixedCheckbox.checked = !fixedCheckbox.checked;
}); */
    
const noticeRadioButton = $("#notice");
const normalRadioButton = $("#normal");
const checkboxDiv = $("#checkbox-div");

//공지사항 라디오 버튼
$(noticeRadioButton).on("change", () => {
	if (noticeRadioButton.prop("checked")) {
		$(checkboxDiv).css("display", "block");
	} else {
		$(checkboxDiv).css("display", "none");
	}
});

//일반 라디오 버튼
$(normalRadioButton).on("change", () => {
	if (normalRadioButton.prop("checked")) {
		$(checkboxDiv).css("display", "none");
	} else {
		$(checkboxDiv).css("display", "block");
	}
});


/* noticeRadioButton.addEventListener('change', ()=> {
	if (noticeRadioButton.checked) {
		checkboxDiv.style.display = 'block'; 
	} else {
		checkboxDiv.style.display = 'none'; 
	}
}); */

/* normalRadioButton.addEventListener('change', ()=> {
if (normalRadioButton.checked) {
	checkboxDiv.style.display = 'none'; 
} else {
	checkboxDiv.style.display = 'block'; 
	}
}); */




</script>
</body>
</html>