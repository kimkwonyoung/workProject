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
  <%@ include file="header.jsp" %>
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
        <label for="tab1"><a href="/workProject/board/boardList?board_type=notice">공지사항</a></label>
        <input type="radio" id="tab2" name="tabs">
        <label for="tab2">갤러리</label>
       	<input type="radio" id="tab3" name="tabs">
        <label id = "boardTab" for="tab3"><a href="/workProject/board/boardList?board_type=nomal">게시판</a></label>
        <!-- <a href="/workProject/board?action=Boardlist&board_type=nomal"> -->
        <div id="notice" class="tabContent">
          <ul>
          <c:forEach var="notice" items="${noticeList }">           
            <li><a href="/workProject/board?action=BoardInfo&board_num=${notice.board_num }">${notice.title }</a></li>
          </c:forEach> 
          </ul>
        </div>
        <div id="gallery" class="tabContent">
          <ul>
            <li><img src="/workProject/images/c1.jpg" ></li>
            <li><img src="/workProject/images/c2.jpg" ></li>
            <li><img src="/workProject/images/c3.jpg" ></li>
            <li><img src="/workProject/images/c4.jpg" ></li>
            <li><img src="/workProject/images/f8.jpg" ></li>  
            <li><img src="/workProject/images/f5.jpg" ></li>                  
          </ul>
        </div>
        <div id="board" class="tabContent">
          <ul>            
            <c:forEach var="nomal" items="${nomalList }">           
            <li><a href="/workProject/board?action=BoardInfo&board_num=${nomal.board_num }">${nomal.title }</a></li>
          </c:forEach> 
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
	        <form action="/workProject/member" method="post">
	        	<div class="form-group">
	        		<h2>로그인</h2>
	        	</div>
	            <div id="form-group">
	            	<label for="username">아이디:</label>
	                <input type="text" name="memberid" placeholder="enter your id" required>
	            	
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
	                <label>${loginMember.memberid} 회원님</label>
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
	        			<li><a href="/workProject/member/favoriate.jsp" class="forlink">사진모음</a></li>
	        			<li>나의문의내역</li>
	        		</ul>
	        	        	
	        	</div>
	          	<div id="userInfo">
	            	<a href="/workProject/member?action=memberInfo&memberid=${loginMember.memberid}" class="forlink"><span>나의 상세 정보 보기</span></a>
	            </div>       
	            <div id="loginOut">
	            	<a href="/workProject/member?action=logout" class="forlink"><span>로그 아웃</span></a>
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
	/* var mem = '${loginMember.memberid}';
	테스트
	var lnk = document.querySelectorAll('.forlink');
	
	var lnkArr = [
				'/workProject/member?action=memberlist',
				'/workProject/member/favoriate.jsp',
				'/workProject/member?action=memberInfo&memberid='+mem,
				'/workProject/member?action=logout']

	for(let i = 0; i < lnk.length; i++) {
		lnk[i].addEventListener('click', createClickHandler(lnkArr[i]));
			
	}
	function createClickHandler(href) {
		return () => {
			window.location.href = href;
		}
	} */

	
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
	
	
	var hrefArr = ['/workProject/member/member_search.jsp?dis=searchId', 
				   '/workProject/member/member_search.jsp?dis=searchPwd',
				   '/workProject/member/member_insert.jsp'];
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