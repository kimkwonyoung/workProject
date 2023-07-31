<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style>
.sidebar {
	height: 100%;
	width: 0px;
	position: fixed;
	z-index: 1;
	top: 0;
	left: 0;
	background-color: #111;
	overflow-x: hidden;
	transition: 0.3s; /*펼침속도*/
	padding-top: 60px;
}

.sidebar a {
	padding: 8px 8px 8px 32px;
	text-decoration: none;
	font-size: 20px;
	color: #818181;
	display: block;
	transition: 0.3s;
}

/*메뉴열기 hover 시*/
.sidebar a:hover {
	color: #f1f1f1;
}

.sidebar .closebtn {
	position: absolute;
	top: 0;
	right: 25px;
	font-size: 36px;
	margin-left: 50px;
}

.openbtn {
	font-size: 15px;
	cursor: pointer;
	background-color: #111;
	color: white;
	padding: 10px 15px;
	border: none;
}

.openbtn:hover {
	background-color: #444;
}

#main {
	transition: margin-left 0.4s;
	padding: 16px;
}
</style>
<title>확장형 메뉴 샘플</title>
</head>
<body>
	<!--사이드 메뉴 영역-->
	<div class="sidebar">
		<a class="closebtn" onclick="closeMenu()">×</a> <a href="#">회사소개</a> <a
			href="#">연혁</a> <a href="#">비즈니스</a> <a href="#">연락처</a>
	</div>

	<!--화면영역-->
	<div id="main">
		<button class="openbtn" onclick="openMenu()">☰ 메뉴 열기</button>
		<h2>확장형 메뉴</h2>
	</div>
	<script>
            
		function openMenu() {
			document.getElementById("main").style.marginLeft = "250px";
			document.querySelector('.sidebar').style.width = "250px";
			document.querySelector('.openbtn').style.display = 'none';
		}

		function closeMenu() {
			document.getElementById("main").style.marginLeft = "0";
			document.querySelector('.sidebar').style.width = "0";
			document.querySelector('.openbtn').style.display = 'block';
		}
	</script>
</body>
</html>