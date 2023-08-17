<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="<c:url value='/css/favoriate.css'/>">
</head>
<body>
  <div class="description">
    <h1>사진 목록</h1>
    <span><a href="#">돌아가기</a></span>
  </div>
  
  <div class="image-gallery">
    <img src="<c:url value='/images/s1.jpg'/>">
    <img src="<c:url value='/images/s2.jpg'/>">
    <img src="<c:url value='/images/s3.jpg'/>">
    <img src="<c:url value='/images/s4.jpg'/>">
    <img src="<c:url value='/images/s5.jpg'/>">
    <img src="<c:url value='/images/s6.jpg'/>">
    <img src="<c:url value='/images/s7.jpg'/>">
    <img src="<c:url value='/images/s8.jpg'/>">
    <img src="<c:url value='/images/s9.jpg'/>">
    <img src="<c:url value='/images/s10.jpg'/>">
    
  </div>
  
<script>
	var link = document.querySelector(".description span a");
	var href = "<c:url value='/main/mainIndex.do'/>";
	link.addEventListener('click', () => {
		window.location.href = href;
	});
	
	var cover = document.querySelectorAll(".image-gallery img");
	for (let i = 0; i < cover.length; i++) {
		let num = i + 1;
		cover[i].addEventListener("mouseover", () => {
			cover[i].src = "/workProject/images/f"+ num + ".jpg";
		});
		cover[i].addEventListener('mouseout', () => {
			cover[i].src = "/workProject/images/s"+ num + ".jpg";
		});
		
	}
</script>
</body>
</html>
