<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>

<link rel="stylesheet" href="<c:url value='/css/board.css'/>">

  <title>게시판 목록</title>
</head>
<body>

<div class="list-all" id="container">
  <%@ include file="../header.jsp" %>
    <div class="board-header">
      <div class="link-header">
      
      <c:if test="${loginMember.memberid eq 'admin' }">
          <a class="write-button" id="del">글삭제</a>
      </c:if>
      <c:if test="${not empty loginMember }">   
          <a class="write-button" id="wr" href="#">글쓰기</a>
      </c:if>
      </div>
    </div>

    <table>
    <tr id="boardItem" style="display:none">
          <td class="checkbox-cell"><input type="checkbox" name="chkBoardNum" class="chkbox"></td>
          <td id="boardN"></td>
          <td><a href="#" id="title"></a></td>
          <td id="memId"></td>
          <td id="mod_date"></td>
          <td id="view_count"></td>
          <td><input type="button" value="삭제" class="checkDel" onclick="deleteChk(this)"></td>
        </tr> 
    <caption class="board-title">일반 더보기 게시판</caption>
      <thead>
        <tr>
          <th class="checkbox-all"><input type="checkbox" id="checkAll"></th>
          <th>글번호</th>
          <th>글제목</th>
          <th>작성자</th>
          <th>작성일</th>
          <th>조회수</th>
          <th>삭제</th>
        </tr>
      </thead>
      <tbody id ="board_list">
       <c:forEach var="board" items="${boardList2}">
        <tr>
          <td class="checkbox-cell"><input type="checkbox" name="chkBoardNum" class="chkbox"></td>
          <td>${board.board_num }</td>
          <td><a href="#" onclick="info(${board.board_num})">${board.title}</a></td>
          <td>${board.mem_id }</td>
          <td>${board.mod_date }</td>
          <td>${board.view_count }</td>
          <td><input type="button" value="삭제" class="checkDel" onclick="deleteChk(this)"></td>
        </tr>
        </c:forEach>
        
        
        
      </tbody>
    </table>
    <div style="text-align: center; margin-top:10px">
    	<input type="button" id="moreBtn" value="더보기" />
    </div>
  </div>

<!-- 게시판 글 상세보기 -->
<div id="dialogInfo" title="">
<input type="hidden" id="infoBoardnum" value="">
	<div>
      <p class="post-meta" id="post-meta">작성자: <span id="author"></span> | 작성일: <span id="date"></span> | 조회수: <span id="views"></span></p>
      <div class="post-content" id="post-content">
      </div>
    </div>
    
    <!-- 댓글 -->
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
	<%-- <c:if test="${not empty loginMember}"> --%>
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
    <%-- </c:if> --%>
</div>

<!-- 게시판 글 작성하기  -->
<div id="dialogWrite" title="글 쓰기">
<label for="title">제목:</label><br>
      <input type="text" id="writeTitle" name="title" class="form-input"><br>

      <label for="content">내용:</label><br>
      <textarea id="writeContent" name="content" rows="10" class="form-input"></textarea><br>
</div>

<!-- 게시판 글 수정하기  -->
<div id="dialogUpdate" title="글 수정">
<label for="title">제목:</label><br>
      <input type="text" id="updateTitle" name="title" class="form-input"><br>

      <label for="content">내용:</label><br>
      <textarea id="updateContent" name="content" rows="10" class="form-input"></textarea><br>
</div>
	
<script>
$(document).ready(function() {
    $("#dialogInfo").dialog({
        autoOpen: false,
        modal: true,
        width: 1000,
        height: 800,
        buttons: {
        	"글 수정": function() {
        		const boardnum = $("#infoBoardnum").val();
    			openUpdate(boardnum);
    		},
            "Close": function() {
                $(this).dialog("close");
            }
    		
        }
    });
    $("#dialogWrite").dialog({
        autoOpen: false,
        modal: true,
        width: 800,
        height: 500,
        buttons: {
        	"글 작성": function() {
        		addWrite();
        		$(this).dialog("close");
        	},
            "Close": function() {
                $(this).dialog("close");
            }
        }
    });
    $("#dialogUpdate").dialog({
        autoOpen: false,
        modal: true,
        width: 800,
        height: 500,
        buttons: {
        	"글 수정 완료": function() {
        		const boardnum = $("#dialogUpdate").data("boardnum");
        		console.log("보드넘=" + boardnum);
        		addUpdate(boardnum)
        		$(this).dialog("close");
        	},
            "Close": function() {
                $(this).dialog("close");
            }
        }
    });
});

function addUpdate(boardnum) {
	const param = {
	        board_num: boardnum,
	        board_code: 10,
	        title: $("#updateTitle").val(),
	        content: $("#updateContent").val(),
	      };
    
	$.ajax({
		url: "<c:url value='/board/ajaxUpdate2.do'/>",
		type: "POST",
		contentType: "application/json; charset=UTF-8",
		data: JSON.stringify(param),
		dataType: "json",
		success: (json) => {
			location.reload();
		}
	});
	
	$("#dialogInfo").dialog("close");
	
}

function openUpdate(boardnum) {
	console.log(boardnum)
	const param = {
	        board_num: boardnum,
	      };
    
	$.ajax({
		url: "<c:url value='/board/ajaxInfo2.do'/>",
		type: "POST",
		contentType: "application/json; charset=UTF-8",
		data: JSON.stringify(param),
		dataType: "json",
		success: (json) => {
       	  const infoList = json.boardInfo;
       	  for (let i=0; i<infoList.length; i++) {
       		  const info = infoList[i];
       		  
       		  $("#updateTitle").val(info.title);
     		  $("#updateContent").html(info.content);
       		 
       	  }
			
		}
	});
	$("#dialogUpdate").data("boardnum", boardnum).dialog("open");
	
}

function addWrite() {
	mem = "${loginMember.memberid}";
	const param = {
			title: $("#writeTitle").val(),
			content: $("#writeContent").val(),
			mem_id: mem
	      };
    
	$.ajax({
		url: "<c:url value='/board/ajaxWrite2.do'/>",
		type: "POST",
		contentType: "application/json; charset=UTF-8",
		data: JSON.stringify(param),
		dataType: "json",
		success: (json) => {
		const list = json.boardAjaxOne;	
		const boardItem = $("#boardItem");
		const boardListHTML = $("#board_list");
		  
		  for (let i=0; i<list.length; i++) {
			  const board = list[i];
			  const newBoardItem = boardItem.clone(true);
			  const title = newBoardItem.find("#title");
			  
		   	  title.text(board.title);
		   	  title.on("click", () => info(board.board_num));
		      
		   	  newBoardItem.find("#boardN").text(board.board_num);
		      newBoardItem.find("#memId").text(board.mem_id);
		      newBoardItem.find("#mod_date").text(board.mod_date);
		      newBoardItem.find("#view_count").text(board.view_count);
		
		   	  newBoardItem.show();
			  boardListHTML.prepend(newBoardItem);
		  }
			
		}
	});
	
}

function info(boardnum) {
    const param = {
	        board_num: boardnum,
	      };
    
	$.ajax({
		url: "<c:url value='/board/ajaxInfo2.do'/>",
		type: "POST",
		contentType: "application/json; charset=UTF-8",
		data: JSON.stringify(param),
		dataType: "json",
		success: (json) => {
       	  const infoList = json.boardInfo;
       	  for (let i=0; i<infoList.length; i++) {
       		  const info = infoList[i];
       		  
       		  $("#infoBoardnum").val(info.board_num);
       		  $("#author").text(info.mem_id);
       	      $("#date").text(info.mod_date);
       	      $("#views").text(info.view_count);
       		  $("#post-content").html(info.content);
       		  $("#dialogInfo").dialog("option", "title", info.title);
       	  }
			
		}
	});
	$("#dialogInfo").dialog("open");
}

function innerHtml(list) {
	  const boardItem = $("#boardItem");
	  const boardListHTML = $("#board_list");
	  
	  for (let i=0; i<list.length; i++) {
		  const board = list[i];
		  const newBoardItem = boardItem.clone(true);
		  const title = newBoardItem.find("#title");
		  
	   	  title.text(board.title);
	   	  title.on("click", () => info(board.board_num));
	      
	   	  newBoardItem.find("#boardN").text(board.board_num);
	      newBoardItem.find("#memId").text(board.mem_id);
	      newBoardItem.find("#mod_date").text(board.mod_date);
	      newBoardItem.find("#view_count").text(board.view_count);
	
	   	  newBoardItem.show();
		  boardListHTML.append(newBoardItem);
	  }
}
function deleteChk(btn) {
    const tr = $(btn).closest("tr");
    const boardNumCell = tr.find("td:nth-child(2)");
    const boardNum = boardNumCell.text().trim();
    
    const param = {
	        board_num: boardNum,
	        board_code: 10,
	        lastBnum: $("#board_list tr:last-child td:nth-child(2)").text(),
	      };
    
		$.ajax({
			url: "<c:url value='/board/ajaxDeleteChkOne2.do'/>",
			type: "POST",
			contentType: "application/json; charset=UTF-8",
			data: JSON.stringify(param),
			dataType: "json",
			success: (json) => {
			  let html = "";
	          if (json.status) {
	        	  const bod = json.bod;
	        	  alert(json.message);
	        	  $(tr).css("display", "none");
	        	  innerHtml(bod);

	          } else {
	        	  alert(json.message);
	          }
				
			}
		});
	return false;
}

$("#wr").on("click", () => {
	$("#writeTitle").text("");
	$("#writeContent").text("");
	$("#dialogWrite").dialog("open");
})

//체크 박스 전체 선택
$("#checkAll").on("click", () => {
	$(".chkbox").prop("checked", $("#checkAll").prop("checked"));
});
   
//체크 박스 선택한것 삭제
$("#del").on("click", (event) => {		
	if (confirm('정말로 삭제 하시겠습니까?')) {
		var count = 0;
		var deleteStr = "";
		$("input[name='chkBoardNum']").each((i, chkbox) => {
		    if ($(chkbox).prop("checked")) {
		        count++;
		        var row = $(chkbox).closest('tr');
		        
		        var boardNumCell = row.find('td:nth-child(2)');
		        var boardNum = boardNumCell.text().trim();
		        deleteStr += boardNum + ',';
		        row.css("display", "none");
		    }
		});
		if (deleteStr == '') {
			alert('삭제할 글을 선택 하세요.');
		} else {
			deleteStr = deleteStr.substr(0, deleteStr.length - 1);
			const param = {
					 	count: count,
				        board_code: 10,
				        deleteStr: deleteStr,
				        lastBnum: $("#board_list tr:last-child td:nth-child(2)").text(),
				      };
			$.ajax({
				url: "<c:url value='/board/ajaxDeleteChkAll2.do'/>",
				type: "POST",
				contentType: "application/json; charset=UTF-8",
				data: JSON.stringify(param),
				dataType: "json",
				success: (json) => {
					const bod = json.bodChk;
					innerHtml(bod);
				}
			});		
		}
	} else {
        event.preventDefault();
        return false;
    }
});

$("#moreBtn").on("click", e => {
	const boardnum = $("#board_list tr:last-child td:nth-child(2)").text();
	e.preventDefault();
	const param = {
	        board_num: boardnum,
	      };
	$.ajax({
		url: "<c:url value='/board/boardAjaxList2.do'/>",
		type: "POST",
		contentType: "application/json; charset=UTF-8",
		data: JSON.stringify(param),
		dataType: "json",
		success: (json) => {
       		const boardList = json.boardAjaxList;
       		innerHtml(boardList);
		}
	});
	
	return false;
});
</script>
</body>
</html>