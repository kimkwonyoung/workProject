<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/project/css/favoriate.css">
</head>
<body>
  <div class="description">
    <h1>사진 목록</h1>
    <span><a href="#">돌아가기</a></span>
  </div>
  
  <div class="image-gallery">
    <img src="/project/images/s1.jpg">
    <img src="/project/images/s2.jpg">
    <img src="/project/images/s3.jpg">
    <img src="/project/images/s4.jpg">
    <img src="/project/images/s5.jpg">
    <img src="/project/images/s6.jpg">
    <img src="/project/images/s7.jpg">
    <img src="/project/images/s8.jpg">
    <img src="/project/images/s9.jpg">
    <img src="/project/images/s10.jpg">
  </div>
  
<script>
	var link = document.querySelector('.description span a');
	var href = '/project/index.jsp';
	link.addEventListener('click', () => {
		window.location.href = href;
	});
	
	var cover = document.querySelectorAll('.image-gallery img');
	for (let i = 0; i < cover.length; i++) {
		let num = i + 1;
		cover[i].addEventListener('mouseover', () => {
			cover[i].src = '/project/images/f'+ num + '.jpg';
		});
		cover[i].addEventListener('mouseout', () => {
			cover[i].src = '/project/images/s'+ num + '.jpg';
		});
		
	}
</script>
</body>
</html>
