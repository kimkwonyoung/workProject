<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="<c:url value='/css/memberlist.css'/>">
  
</head>

<body>
  <div id="container">
  <%@ include file="../header.jsp" %>
  <div class="linkdiv">
    <a class="back" href="<c:url value='/main/mainIndex.do'/>">돌아가기</a>
    <a class="back" id="create-user" href="#">등록</a>
  </div>
    <c:set var="memberlist" value="${memberlist}" />
    <table>
    <tr id="memberItem" style="display:none">
       <td id="memnum"></td>
       <td id="memname"></td>
       <td id="memid"></td>
       <td id="mempwd"></td>
       <td id="memphone"></td>  
    </tr>   
      <thead>
        <tr>
          <th>회원순번</th>
          <th>이름</th>
          <th>아이디</th>
          <th>패스워드</th>
          <th>핸드폰 번호</th>
        </tr>
      </thead>
      <tbody id = "memberList">
        <c:forEach var="member" items="${memberlist}">
          <tr>
            <td>${member.membernum }</td>
            <td>${member.name}</td>
            <td>${member.memberid}</td>
            <td>${member.pwd}</td>
            <td>${member.phone}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    <div style="text-align: center; margin-top:10px">
    	<input type="button" id="moreBtn" value="더보기" />
    </div>
    
    
  <div id="dialog-form" style="background-color: #f1f1f1;">
  <form>
    <fieldset>
      <label for="id">아이디</label>
      <input type="text" name="memberid" id="memberid" class="text ui-widget-content ui-corner-all">
      <label for="password">비밀번호</label>
      <input type="password" name="password" id="memberpwd"  class="text ui-widget-content ui-corner-all">
      <label for="name">이름</label>
      <input type="text" name="name" id="membername"  class="text ui-widget-content ui-corner-all">
      <label for="email">휴대폰</label>
      <input type="text" name="phone" id="memberphone"  class="text ui-widget-content ui-corner-all">
      
      <!-- Allow form submission with keyboard without duplicating the dialog button -->
      <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
    </fieldset>
  </form>
</div>
 
    
</div>
<script id="memberItemTemplate" type="text/x-jsrender">
    <tr>
        <td>{{:membernum}}</td>
        <td>{{:name}}</td>
        <td>{{:memberid}}</td>
        <td>{{:pwd}}</td>
        <td>{{:phone}}</td>
    </tr>
</script>
  
<script type="text/javascript">
$(function() {
    function addUser() {
      const param = {
  	        memberid: memberid.value,
  	        pwd: memberpwd.value,
  	        name: membername.value,
  	        phone: memberphone.value,
  	      };
      
      $.ajax({
  		url: "<c:url value='/member/memberAjaxInsert.do'/>",
  		type: "POST",
  		contentType: "application/json; charset=UTF-8",
  		data: JSON.stringify(param),
  		dataType: "json",
  		success: (json) => {
            if (json.status) {
          	  const memberList = json.list;
          	  const memberItem = $("#memberItem");
          	  const memberListHTML = $("#memberList");
          	  console.log(memberList);
          	  for (let i=0; i<memberList.length; i++) {
  	        	  const member = memberList[i];
  	        	  //const newMemberItem = $(memberItem).clone(true);
  	        	  
  	  			  const newMemberItem = $("<tr>");

		          newMemberItem.append("<td>" + member.membernum + "</td>");
		          newMemberItem.append("<td>" + member.name + "</td>");
		          newMemberItem.append("<td>" + member.memberid + "</td>");
		          newMemberItem.append("<td>" + member.pwd + "</td>");
		          newMemberItem.append("<td>" + member.phone + "</td>");
		
		          memberListHTML.prepend(newMemberItem);
  				  
          	  }
            }
  		}
  	});
      dialog.dialog( "close" );
    }
    dialog = $( "#dialog-form" ).dialog({
      autoOpen: false,
      height: 400,
      width: 350,
      modal: true,
      buttons: {
        "Create an account": addUser,
        Cancel: function() {
          dialog.dialog( "close" );
        }
      },
      close: function() {
        //form[ 0 ].reset();
      }
    });
    dialog.closest(".ui-dialog").find(".ui-dialog-buttonpane button:contains('Cancel')").addClass("cancel-button");
    dialog.closest(".ui-dialog").find(".ui-dialog-buttonpane button:contains('Create an account')").addClass("custom-button");
    dialog.closest(".ui-dialog").find(".ui-dialog-buttonpane button:contains('Cancel')").addClass("custom-button");

    $( "#create-user" ).button().on( "click", function() {
      dialog.dialog( "open" );
    });
  } );



$("#moreBtn").on("click", e => {
	e.preventDefault();
	const param = {
	        membernum: $("#memberList tr:last-child td:first-child").text(),
	      };
	
	$.ajax({
		url: "<c:url value='/member/memberAjaxList.do'/>",
		type: "POST",
		contentType: "application/json; charset=UTF-8",
		data: JSON.stringify(param),
		dataType: "json",
		success: (json) => {
          if (json.status) {
        	  	const memberList = json.memberlist;
        	    const memberItemTemplate = $("#memberItemTemplate").html();
        	    const tmpl = $.templates(memberItemTemplate);
        	    const memberListHTML = $("#memberList");

        	    for (let i = 0; i < memberList.length; i++) {
        	        const member = memberList[i];
        	        const renderedMemberItem = tmpl.render(member);
        	        $(memberListHTML).append(renderedMemberItem);
        	    }
          }
		}
	});
	
	return false;
})
</script>  
</body>
</html>