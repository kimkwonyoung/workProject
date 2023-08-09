<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/workProject/css/board.css">
  <title>게시물 상세 보기</title>
</head>
<body>
  <div id="container">
    <%@ include file="../header.jsp" %>
    <div class="post-detail">
      <h2 class="post-title" id="post-title">${infoBoard.title }</h2>
      <p class="post-meta" id="post-meta">작성자: ${infoBoard.mem_id } | 작성일: ${infoBoard.mod_date } | 조회수: ${infoBoard.view_count }</p>
      <div class="post-content" id="post-content">
        ${infoBoard.content }
      </div>
    </div>
    
    <div class="comment-form">
    <h3>댓글(${comment_count })</h3>
    <c:forEach var="comment" items="${board_comment }">
    	<div class="comment-list">
    	<h5>${comment.mem_id }</h5>
    	<div class = "comment-area">
    		<div class="commentdetail">
    			<span class="comment-content">${comment.detail }</span>
    		</div>
    		<div class="up-del-link">
    			<h5 class="comment-date">${comment.reg_date}</h5>
    			<%-- <fmt:formatDate value="${comment.reg_date}" pattern="yyyy-MM-dd HH:mm:ss" /> --%>
    			<%-- <c:if test="${loginMember.memberid eq comment.mem_id }"> --%>
    				<a href="" class="edit-comment-link" style="margin-right:10px" data-mem-id="${comment.mem_id }">수정</a>
    				<a href="/workProject/board/commentDelete?board_num=${infoBoard.board_num}&board_type=${requestScope.board_type }&comment_num=${comment.comment_num }"class="delete-comment">삭제</a>
    			<%-- </c:if> --%>
    		</div>
    	</div>
    	<div class="edit-comment-form" style="display:none;">
        	<textarea class="edit-comment-textarea" rows="4" cols="50">${comment.detail}</textarea>
        	<a href="#" class="save-edited-comment" data-comment-id="${comment.comment_num}">저장</a>
        	<a href="#" class="cancel-edit-comment">취소</a>
        </div>
    	
    	</div>
    </c:forEach>
	</div>
	<c:if test="${not empty loginMember}">
    <div id="comment">
    	 <h3>${loginMember.memberid }</h3>
	    <div class="comment-form">
	       <form id="commentForm" action="/workProject/board/commentInsert">
        	<textarea name="detail" rows="4" cols="50" placeholder="댓글 내용"></textarea>
        	<a class="write-comment" href="#">댓글 작성</a>
        	<input type="hidden" name="board_type" value="${requestScope.board_type }" />
        	<input type="hidden" name="board_num" value="${infoBoard.board_num}" />
        	<input type="hidden" name="mem_id" value="${loginMember.memberid }" />
   	 	   </form>
	    </div>
    </div>
    </c:if>
      <div class="button-container">
      <%-- <c:if test="${loginMember.memberid eq infoBoard.mem_id }"> --%>
        <a class="edit-button-info" id="edit" href="#">글 수정</a>
        <a class="delete-button-info" id="del" href="#">글 삭제</a>
      <%-- </c:if> --%>
        <a class="back-button-info" id="back" href="/workProject/board/boardList?board_type=${requestScope.board_type }">목록</a>
      </div>
      
  </div>
  <script>
 	var boardnum = '${infoBoard.board_num}';
 	var boardcode = '${infoBoard.board_code}';
 	var loginmem = '${loginMember.memberid}';
 	var infomem = '${infoBoard.mem_id}';
	var updateLink = document.getElementById('edit');
	var deleteLink = document.getElementById('del');
	const writeCommentLink = document.querySelector('.write-comment');
	
	//로그인 회원과 게시글의 회원과 일치 하면 수정 삭제 링크 스크립트 작동
	if (loginmem === infomem) {
		//글 수정
		updateLink.addEventListener('click', () => {
			window.location.href = '/workProject/board/boardUpdateInfo?board_num=' + boardnum;
		});
		//글 삭제
		deleteLink.addEventListener('click', (event)=> {
			if(confirm('정말로 삭제 하시겠습니까?')) {
				window.location.href = '/workProject/board/boardDelete?board_num=' + boardnum + '&board_code=' + boardcode;
			} else {
				event.preventDefault();
			}
		});
	}
	
	//댓글 작성 폼 전송
	writeCommentLink.addEventListener('click', (event) => {
	    event.preventDefault();
	    const commentForm = document.getElementById('commentForm');
	    commentForm.submit();
	});
	
	const commentArea = document.querySelectorAll('.comment-area');
	const editCommentLinks = document.querySelectorAll('.edit-comment-link');
    const editCommentForms = document.querySelectorAll('.edit-comment-form');
    const commentContents = document.querySelectorAll('.comment-content');
    const editTextareas = document.querySelectorAll('.edit-comment-textarea');
    const saveButtons = document.querySelectorAll('.save-edited-comment');
    const cancelLinks = document.querySelectorAll('.cancel-edit-comment');
    const commentDate = document.querySelectorAll('.comment-date');
    const deleteComment = document.querySelectorAll('.delete-comment');
    
    //정보보기 페이지 왔을때 로그인 회원과 글에 등록된 댓글 작성자와 일치시 댓글 수정,삭제 링크 활성화
	window.onload = () => {
		if (loginmem === infomem) {
			updateLink.style.display = 'inline-block';
			deleteLink.style.display = 'inline-block';
		}
		
		editCommentLinks.forEach((link, index) => {
			const memid = link.getAttribute('data-mem-id');
			if (loginmem === memid) {
				link.style.display = 'block';
				deleteComment[index].style.display = 'block';
			}
		});
    };
    
    //댓글 수정하기 수정폼 보이게 하기
    editCommentLinks.forEach((link, index) => {
        link.addEventListener('click', (event) => {
            event.preventDefault();
            editCommentForms[index].style.display = 'block';
            commentContents[index].style.display = 'none';
            commentArea[index].style.display = 'none';
        });
    });
    
    //ajax fetch로 댓글 수정 수행(초기버전)
    saveButtons.forEach((button, index) => {
        button.addEventListener('click', (event) => {
        	event.preventDefault();
            const editedContent = editTextareas[index].value;
            const commentNum = button.getAttribute('data-comment-id');
            
            fetch('/workProject/board/boardUpdateComment', {
            	method: 'POST',
            	headers: {
    			    "Content-Type": "application/json",
    			  },
    			body: JSON.stringify({
    				comment_num: commentNum,
    				detail: editedContent
    			}),
            })
            .then((response) => response.json())
    		.then((data) => {
    				commentContents[index].textContent = data.detail
    				commentDate[index].textContent = data.comment_date
    				editCommentForms[index].style.display = 'none';
    	            commentContents[index].style.display = 'block';
    	            commentArea[index].style.display = 'block';
    			}
    		);
         });
     });
    
    //댓글 수정 취소 원래 댓글로 되돌리기
    cancelLinks.forEach((link, index) => {
        link.addEventListener('click', (event) => {
            event.preventDefault();
            editCommentForms[index].style.display = 'none';
            commentContents[index].style.display = 'block';
            commentArea[index].style.display = 'block';
        });
    });
	
	
	/* 	var back = document.getElementById('back');
	back.addEventListener('click', () => {
		window.location.href = '/workProject/board?action=Boardlist';
  }); */
  </script>
</body>
</html>