<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>

<body>
<header>
      <div id="logo">
        <a href="/workProject/main">
          <h1>DreamHome</h1>
        </a>
      </div>
	 <nav>
        <ul id="topMenu">
          <li><a href="#">주거 공간<span>▼</span></a>
            <ul>
              <li><a href="#">가구1</a></li>
              <li><a href="#">가구2</a></li>
            </ul>
          </li>
          <li><a href="#">상업 공간<span>▼</span></a>
            <ul>
              <li><a href="#">상업1</a></li>
              <li><a href="#">상업2</a></li>
              <li><a href="#">상업3</a></li>
            </ul>
          </li>
          <li><a href="#">문의하기</a></li>
          <li>
			<a href="#" class="openbtn">메뉴 열기</a>
			<div class="sidebar">
				<a href="#" class="closebtn">×</a> <a href="#">회사소개</a>
				<a href="#">연혁</a> 
				<a href="#">비즈니스</a> 
				<a href="#">연락처</a>
				<a href="/workProject/member?action=memberlist" class="forlink">전체 회원 보기</a>
			</div>
          </li>
        </ul>
      </nav>
    </header>
<script type="text/javascript">
	var open = document.querySelector('.openbtn');
	var close = document.querySelector('.closebtn');
	open.addEventListener('click', () => {
		document.querySelector('.sidebar').style.width = "226px";
		document.querySelector('.openbtn').style.display = 'none';
	});
	close.addEventListener('click', () => {
		document.querySelector('.sidebar').style.width = "0";
		document.querySelector('.openbtn').style.display = 'block';
	});
</script>
</body>
</html>