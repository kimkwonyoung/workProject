<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="<c:url value='/css/board.css'/>">
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
    				<a href="commentDelete.do?board_num=${infoBoard.board_num}&board_code=${requestScope.board_code }&comment_num=${comment.comment_num }"class="delete-comment">삭제</a>
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
        <form id="commentForm" action="commentInsert.do">
        	<textarea name="detail" rows="4" cols="50" placeholder="댓글 내용"></textarea>
        	<a class="write-comment" href="#">댓글 작성</a>
        	<input type="hidden" name="board_code" value="${requestScope.board_code }" />
        	<input type="hidden" name="board_num" value="${infoBoard.board_num}" />
        	<input type="hidden" name="mem_id" value="${loginMember.memberid }" />
   	 	</form>
	    </div>
	    
    </div>
    </c:if>
      <div class="button-container">
        <a class="edit-button-info" id="edit" href="#">글 수정</a>
        <a class="delete-button-info" id="del" href="#">글 삭제</a>
        <a class="back-button-info" id="back" href="boardList.do?board_code=${requestScope.board_code }">목록</a>
      </div>
  </div>
  <div>
<%--    <div id = "aaaa" class="comment-list" style="display:none;">
	            <h5 id="mem_id">${newComment.mem_id}</h5>
	            <div class="comment-area">
	                <div class="commentdetail">
	                    <span class="comment-content">${newComment.detail}</span>
	                </div>
	                <div class="up-del-link">
	    			<h5 class="comment-date">${newComment.reg_date}</h5>	
	    				<a href="" class="edit-comment-link" style="margin-right:10px" data-mem-id="${newComment.mem_id }">수정</a>
	    				<a href="/workProject/board/commentDelete?board_num=${infoBoard.board_num}&board_code=${requestScope.board_code }&comment_num=${newComment.comment_num }"class="delete-comment">삭제</a>
	    			</div>
	            </div>
	            <div class="edit-comment-form" style="display:none;">
	            	<textarea class="edit-comment-textarea" rows="4" cols="50">${newComment.detail}</textarea>
	        		<a href="#" class="save-edited-comment" data-comment-id="${newComment.comment_num}">저장</a>
	        		<a href="#" class="cancel-edit-comment">취소</a>
	            </div>
	</div> --%>
  </div>
  <script>
 	var boardnum = '${infoBoard.board_num}';
 	var boardcode = '${infoBoard.board_code}';
 	var loginmem = '${loginMember.memberid}';
 	var infomem = '${infoBoard.mem_id}';
 	var boardtype = '${board_code}';
	
	//로그인 회원과 게시글의 회원과 일치 하면 수정 삭제 링크 스크립트 작동
	if (loginmem === infomem) {
		//글 수정
		$("#edit").on("click", () => {
			window.location.href = 'boardUpdateInfo.do?board_num=' + boardnum;
		});
		//글 삭제
		$("#del").on("click", (event)=> {
			if(confirm('정말로 삭제 하시겠습니까?')) {
				const param = {
					board_num: boardnum,
					board_code: boardcode,
				  };
				$.ajax({
					url: "boardDelete.do",
					type: "POST",
					contentType: "application/json; charset=UTF-8",
					data: JSON.stringify(param),
					dataType: "json",
					success: () => {
						alert(json.message);
					    if (json.status) {
					    	location.href = "boardList.do?board_code=" + boardtype; 
					    }
					}
				});
			} else {
				event.preventDefault();
			}
		});
	}
	
	//댓글 작성 폼 전송
	$(".write-comment").on("click", () => {
	   $("#commentForm").submit();
	});

    $(window).on("load", () => {
        if (loginmem === infomem) {
            $("#edit, #del").css("display", "inline-block");
        }
        
        $(".edit-comment-link").each((index, link) => {
            const memid = $(link).attr('data-mem-id');
            if (loginmem === memid) {
                $(link).css("display", "block");
                //$(deleteComment[index]).css("display", "block");
                $(".delete-comment").eq(index).css("display", "block");
            }
        });
    });
    
    //댓글 수정하기 수정폼 보이게 하기
    $(".edit-comment-link").each((index, link) => {
        $(link).on("click", (event) => {
            event.preventDefault();
            $(".edit-comment-form").eq(index).css("display", "block");
            $(".comment-content").eq(index).css("display", "none");
            $(".comment-area").eq(index).css("display", "none");
        });
    });
    
    
    //ajax fetch로 댓글 수정
    $(".save-edited-comment").each((index, button) => {
        $(button).on("click", (event) => {
        	event.preventDefault();
            const param = {
            		comment_num: $(button).attr('data-comment-id'),
    				detail: $(".edit-comment-textarea").eq(index).val()
    			  };
            
            $.ajax({
				url: "boardUpdateComment.do",
				type: "POST",
				contentType: "application/json; charset=UTF-8",
				data: JSON.stringify(param),
				dataType: "json",
				success: () => {
					if (json.status) {
						$(".comment-content").eq(index).text(json.detail);
						$(".comment-date").eq(index).text(json.comment_date);
						$(".edit-comment-form").eq(index).css("display", "none");
						$(".comment-content").eq(index).css("display", "block");
						$(".comment-area").eq(index).css("display", "block");
    				}
				}
			});	
    		);
         });
     });
    
    //댓글 수정 취소 원래 댓글로 되돌리기
    $(".cancel-edit-comment").each((index, link) => {
        $(link).on("click", (event) => {
            event.preventDefault();
            $(".edit-comment-form").eq(index).css("display", "none");
            $(".comment-content").eq(index).css("display", "block");
            $(".comment-area").eq(index).css("display", "block");
        });
    });
	
  </script>
</body>
</html>