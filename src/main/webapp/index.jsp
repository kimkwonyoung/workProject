<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>

<meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>DreamHome</title>
  <link rel="stylesheet" href="css/style.css">
  
</head>

<body>
  <div id="container">    
    <header>
      <div id="logo">
        <a href="index.jsp">
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
				<a href="/project/main?action=memberlist" class="forlink">전체 회원 보기</a>
			</div>
          </li>
          
          <!-- <li><a href="/project/main?action=memberlist">전체 회원 보기</a></li> -->
        </ul>
      </nav>
    </header>
    <div id="slideShow">
      <div id="slides">
        <img src="images/a-1.jpg" alt="">
        <img src="images/a-2.jpg" alt="">
        <img src="images/a-3.jpg" alt="">
        <img src="images/a-4.jpg" alt="">
        <button id="prev">&lang;</button>
        <button id="next">&rang;</button>
      </div>
    </div>
    <div id="contents">
      <div id="tabMenu">
        <input type="radio" id="tab1" name="tabs" checked>
        <label for="tab1">공지사항</label>
        <input type="radio" id="tab2" name="tabs">
        <label for="tab2">갤러리</label>      
        <div id="notice" class="tabContent">
          <h2>공지사항 내용입니다.</h2>
          <ul>            
            <li>미니멀 하고 내츄럴한 18평형 인테리어</li>
            <li>모던 & 화이트 조합의 포근한 가구</li>
            <li>심플하고 세련된 그레이 패턴의 인테리어</li>
            <li>다청림 라인시티 사무실의 내부 분위기 화이트 변신</li>
          </ul>
        </div>
        <div id="gallery" class="tabContent">
          <h2>갤러리 내용입니다.</h2>
          <ul>
            <li><img src="/project/images/c1.jpg" ></li>
            <li><img src="/project/images/c2.jpg" ></li>
            <li><img src="/project/images/c3.jpg" ></li>
            <li><img src="/project/images/c4.jpg" ></li>
            <li><img src="/project/images/f8.jpg" ></li>  
            <li><img src="/project/images/f5.jpg" ></li>                  
          </ul>
        </div>
        <div id="lightbox">
			<img src="images/c1.jpg" alt="" id="lightboxImage">			
    	</div>        
      </div>
      <div id="links">
        <ul>
          <li>
              <span id="quick-icon1"></span>
              <p>의자</p>
          </li>
          <li>
              <span id="quick-icon2"></span>
              <p>쇼파</p>
          </li>
          <li>
              <span id="quick-icon3"></span>
              <p>침대</p>
          </li>
        </ul>
      </div>
      <c:if test="${empty loginMember}">
	      <div id="login">
	        <form action="/project/main" method="post">
	        	<div class="form-group">
	        		<h2>로그인</h2>
	        	</div>
	            <div id="form-group">
	            	<label for="username">아이디:</label>
	                <input type="text" name="uid" placeholder="enter your id" required>
	            	
	            </div>
	            <div id="form-group">
	            	<label for="password">비밀번호:</label>
	                <input type="password" name="pwd" placeholder="enter your password" required>
	            	
	            </div>
	            <div id="form-group">
	                <input type="submit" value="로그인">              
	            </div>
	            <div id="userInsert">
	            	<a href="#">아이디 찾기</a>｜
	            	<a href="#">비밀번호 찾기</a>｜
	            	<a href="#">회원 가입</a>
	            </div>
	            <input type="hidden" name="action" value="memberLogin">        
	        </form>
	    	</div>
    	</c:if>
    	<c:if test="${not empty loginMember}">
    		<div id="loginmember">
	            <div id="loginmessage">
	                <label>${loginMember.uid} 회원님</label>
	            </div>
	            <div id="date">
	            	<ul>
	            	<li>진행 중 주문 0</li>
	            	<li>내 쿠폰 0</li>
	            	<li>포인트 0</li>
	            	</ul>
	            </div>
	            
	        	<div id="mlist">
	        		<ul>
	        			<li>장바구니</li>
	        			<li><a href="/project/member/favoriate.jsp" class="forlink">사진모음</a></li>
	        			<li>나의문의내역</li>
	        		</ul>
	        	        	
	        	</div>
	          	<div id="userInfo">
	            	<a href="/project/main?action=memberInfo&uid=${loginMember.uid}" class="forlink"><span>나의 상세 정보 보기</span></a>
	            </div>       
	            <div id="loginOut">
	            	<a href="/project/main?action=logout" class="forlink"><span>로그 아웃</span></a>
	            </div>
	           
	    	</div>
	    	
    	</c:if>
    	
     
    </div>  
    <footer>    
      <div id="bottomMenu">
        <ul>
          <li><a href="#">회사 소개</a></li>
          <li><a href="#">개인정보처리방침</a></li>
          <li><a href="#">가구약관</a></li>
          <li><a href="#">사이트맵</a></li>
        </ul>
        <div id="sns">
          <ul>
          	<li><a href="#"><img src="images/sns-1.png"></a></li>
            <li><a href="#"><img src="images/sns-2.png"></a></li>
            <li><a href="#"><img src="images/sns-3.png"></a></li>
          </ul>
        </div>
      </div>
      <div id="company">
        <p>제주특별자치도 ***동 ***로 *** (대표전화) 123-456-7890</p> 
      </div>     
    </footer>  
  </div>
	<script type="text/javascript">
	/* var mem = '${loginMember.uid}';
	
	var lnk = document.querySelectorAll('.forlink');
	
	var lnkArr = [
				'/project/main?action=memberlist',
				'/project/member/favoriate.jsp',
				'/project/main?action=memberInfo&uid='+mem,
				'/project/main?action=logout']

	for(let i = 0; i < lnk.length; i++) {
		lnk[i].addEventListener('click', createClickHandler(lnkArr[i]));
			
	}
	function createClickHandler(href) {
		return () => {
			window.location.href = href;
		}
	} */
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
	
	var message = '${alertmessage}';
	if(message) {
		alert(message);
	}

	var showIframe = (href) => {
		var iframe = document.createElement('iframe');
		iframe.src = href;
		iframe.style.border = '1px solid gray';
		iframe.width = '355px';
		iframe.height = '480px';
		
		var closeButton = document.createElement('button');
        closeButton.className = 'close-button';
        closeButton.textContent = 'X';
        closeButton.style.position = 'absolute';
        closeButton.style.top = '10px';
        closeButton.style.right = '10px';
        closeButton.addEventListener('click', () => {
            document.body.removeChild(iframeContainer);
        });
		
		var iframeContainer = document.createElement('div');
        iframeContainer.id = 'iframeForm';
        iframeContainer.style.position = 'fixed';
        iframeContainer.style.top = '50%';
        iframeContainer.style.left = '50%';
        iframeContainer.style.transform = 'translate(-50%, -50%)';
        iframeContainer.style.backgroundColor = '#fff';
        
        iframeContainer.appendChild(iframe);
        iframeContainer.appendChild(closeButton);
        
        document.body.appendChild(iframeContainer);
		
		
	}
	
	
	var hrefArr = ['/project/member/member_search.jsp?dis=searchId', 
				   '/project/member/member_search.jsp?dis=searchPwd',
				   '/project/member/member_insert.jsp'];
	var links = document.querySelectorAll('#userInsert a');
	
	for(let i = 0; i < links.length; i++) {
		let link = links[i];
		link.addEventListener('click', createClickHandler(hrefArr[i]));
	}
	
	function createClickHandler(href) {
		return () => {
			showIframe(href);
		}
	}
	
	var imgs = document.querySelectorAll('#gallery ul li img');
	var lightbox = document.getElementById('lightbox');
	var lightboxImage = document.getElementById('lightboxImage');
	var footer = document.querySelector('footer');
	var sns = document.querySelector("#sns");
	
	for (let i = 0; i < imgs.length; i++) {
		imgs[i].addEventListener('click', (event) => {
			lightbox.style.display = 'block';
			lightboxImage.src = event.target.src;
			footer.style.display = 'none';
			sns.style.display = 'none';
		});
		
	}

	lightbox.onclick = () => {  
		lightbox.style.display = 'none';
		footer.style.display = 'block';
		sns.style.display = 'block';
}
	
</script>
<script src="js/slideshow.js"></script>
</body>
</html>