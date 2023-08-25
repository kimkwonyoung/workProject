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
<form name="pageForm" id="pageForm" action="<c:url value='/board/boardList.do'/>" method="post" >
		<input type="hidden" name="pageNo" id="pageNo" value="${result.pageBoard.pageNo}" />
		<input type="hidden" name="searchTitle" id="searchTitle" value="${result.pageBoard.searchTitle}" >
		<input type="hidden" name="pageLength" id="pageLength" value="${result.pageBoard.pageLength}" >
		<input type="hidden" name="board_code" value="${requestScope.board_code}" >
</form>
<form name="mForm" id="mForm" action="<c:url value='/board/boardList.do'/>" method="post" >
	<input type="hidden" name="pageNo" id="pageNo" value="${result.pageBoard.pageNo}" />
	<input type="hidden" name="board_code" value="${requestScope.board_code}" />
	
  <div class="list-all" id="container">
  <%@ include file="../header.jsp" %>
    <div class="board-header">
    
      <c:if test="${empty loginMember.memberid eq 'admin' }">
      <div class="link-header">
          <a class="write-button" id="del">글삭제</a>
	      <a class="write-button" id="wr">글쓰기</a> 
      </div>
      </c:if>
    </div>

    <table>
    
    <caption class="board-title">공지사항 게시판</caption>
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
      <c:forEach var="notice" items="${result.notice }">
       <tr class="highlight">
      	  <td class="checkbox-cell"><input type="checkbox" name="chkBoardNum" class="chkbox" value="${notice.board_num }"></td>
          <td>${notice.board_num }</td>
          <td><a href="<c:url value='boardInfo.do?board_num=${notice.board_num }&board_code=${notice.board_code}'/>">${notice.title}</a></td>
          <td>${notice.mem_id }</td>
          <td>${notice.mod_date }</td>
          <td>${notice.view_count }</td>
       </tr>
      </c:forEach>
       <c:forEach var="board" items="${result.list}">
        <tr>
          <td class="checkbox-cell"><input type="checkbox" name="chkBoardNum" class="chkbox" value="${board.board_num }"></td>
          <td>${board.board_num }</td>
          <td><a href="<c:url value='boardInfo.do?board_num=${board.board_num }&board_code=${board.board_code}'/>">${board.title}</a></td>
          <td>${board.mem_id }</td>
          <td>${board.mod_date }</td>
          <td>${board.view_count }</td>
        </tr>
        </c:forEach>
      </tbody>
      <c:if test="${empty result.list}">
	  <tr>
	      <td colspan=6>검색결과가 없습니다</td>
	  </tr>
	  </c:if>
    </table>
   <div class="tableDiv">
   	<div class="selectBox">
	<select name="pageLength" id="pageLength" class="pagelen" >
		<option value="10" ${result.pageBoard.pageLength == 10 ? 'selected="selected"' : ''} >10건</option>
		<option value="20" ${result.pageBoard.pageLength == 20 ? 'selected="selected"' : ''} >20건</option>
		<option value="50" ${result.pageBoard.pageLength == 50 ? 'selected="selected"' : ''} >50건</option>
		<option value="100" ${result.pageBoard.pageLength == 100 ? 'selected="selected"' : ''} >100건</option>
	</select>
    </div>  
  </div>
  <div style="text-align: center;margin-bottom:20px;">
      	<c:if test="${result.pageBoard.navStart != 1}">
      		<a href="javascript:jsPageNo(${result.pageBoard.navStart-1})" style="padding: 13px;"> 이전 &lt; </a> 
      	</c:if>
      	<c:forEach var="item" begin="${result.pageBoard.navStart}" end="${result.pageBoard.navEnd}">
      		<c:choose>
      			<c:when test="${result.pageBoard.pageNo != item }">
      				<a href="javascript:jsPageNo(${item})" class="pageTag" style="padding: 13px;">${item}</a>  
      			</c:when>
      			<c:otherwise>
      				<strong class="pageStrong">${item}</strong>   
      			</c:otherwise>
      		</c:choose>
      	</c:forEach>
      	<c:if test="${result.pageBoard.navEnd != result.pageBoard.totalPageSize}">
      		<a href="javascript:jsPageNo(${result.pageBoard.navEnd+1})" style="padding: 13px;"> 다음 &gt; </a> 
      	</c:if>
    </div>

    
    <div style="margin:0px auto;">
		<div style="display: flex; margin:0px auto; width:60%; justify-align: center">
			<div class="selectBox">
			<select name="searchType" class="searchType" >
				<option value="">제목</option>
				<option value="">작성자</option>
				<option value="">글번호</option>
				<option value="">작성일</option>
			</select>
			</div>
			<input type="text" name="searchTitle" id="searchTitle" value="${result.pageBoard.searchTitle}" style="flex:1">
			<input type="submit" value="검색" class="searchButton"/>
		</div>
	
	</div>
  
    
  </div>
  
   
</form>
	
<script>
/* document.querySelector("#mForm").addEventListener("submit", e => {
	document.querySelector("#mForm > #pageNo").value = "1";
	
	return true;
});
 */
$("#mForm").on("submit", () => {
	$("#mForm > #pageNo").val(1);
	return true;
});

function jsPageNo(pageNo) {
	/* document.querySelector("#pageForm > #pageNo").value = pageNo;
	document.querySelector("#pageForm").submit();  */
	$("#pageForm > #pageNo").val(pageNo);
	$("#pageForm").submit();
}
 
//글쓰기 이동
/* var open = document.querySelector('#wr');
open.addEventListener('click', () => {
  window.location.href = 'boardWrite.do';
}); */

$("#wr").on("click", () => {
	window.location.href = 'boardWrite.do';
});


/* var checkAllCheckbox = document.getElementById("checkAll");
var chkboxs = document.querySelectorAll(".chkbox");


checkAllCheckbox.addEventListener("click", () => {
	chkboxs.forEach((chk) => {
		chk.checked = checkAllCheckbox.checked;
	});
}); */

//체크 박스 전체 선택
$("#checkAll").on("click", () => {
	$(".chkbox").prop("checked", $("#checkAll").prop("checked"));
});

var board_code = '${board_code}';

//체크 박스 선택한것 삭제
$("#del").on("click", (event) => {
	if (confirm('정말로 삭제 하시겠습니까?')) {
		var deleteStr = "";
		$("input[name='chkBoardNum']").each((i, chk) => {
		    if ($(chk).prop("checked")) {
		        deleteStr += $(chk).val() + ',';
		    }
		});
		if (deleteStr === '') {
			alert('삭제할 글을 선택 하세요.');
		} else {
			deleteStr = deleteStr.substr(0, deleteStr.length - 1);
			window.location.href = 'boardDeleteChkbox.do?board_code=' + board_code + '&bnumStr=' + deleteStr;
		}
		
	} else {
        event.preventDefault();
        return false;
    }
});

/* var deleteLink = document.getElementById('del');
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
			window.location.href = 'boardDeleteChkbox.do?board_code=' + board_code + '&bnumStr=' + deleteStr;
		}
	} else {
        event.preventDefault();
        return false;
    }
	
}); */
   
   
</script>
</body>
</html>