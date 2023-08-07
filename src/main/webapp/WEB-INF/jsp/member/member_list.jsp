<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="/workProject/css/memberlist.css">
</head>

<body>
  <div id="container">
  <%@ include file="../header.jsp" %>
    <c:set var="memberlist" value="${requestScope.memberlist}" />
    <table>
      <thead>
        <tr>
          <th>이름</th>
          <th>아이디</th>
          <th>패스워드</th>
          <th>핸드폰 번호</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="member" items="${memberlist}">
          <tr>
            <td>${member.name}</td>
            <td>${member.memberid}</td>
            <td>${member.pwd}</td>
            <td>${member.phone}</td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    <a class="back" href="/workProject/main">돌아가기</a>
  </div>
</body>
</html>