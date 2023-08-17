<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>

<meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>DreamHome</title>
  
</head>
 
<body>
  <div id="container"> 
  <%@ include file="header.jsp" %>
    <div id="slideShow">
      <div id="slides">
        <img src="<c:url value='/images/a-1.jpg'/>" alt="">
        <img src="<c:url value='/images/a-2.jpg'/>" alt="">
        <img src="<c:url value='/images/a-3.jpg'/>" alt="">
        <img src="<c:url value='/images/a-4.jpg'/>" alt="">
        <button id="prev">&lang;</button>
        <button id="next">&rang;</button>
      </div>
    </div>
    <div id="contents">
      <div id="tabMenu">
        <input type="radio" id="tab1" name="tabs" checked>
        <label for="tab1"><a href="<c:url value='/board/boardList.do?board_type=20'/>">공지사항</a></label>
        <input type="radio" id="tab2" name="tabs">
        <label for="tab2">갤러리</label>
       	<input type="radio" id="tab3" name="tabs">
        <label id = "boardTab" for="tab3"><a href="<c:url value='/board/boardList.do?board_type=10'/>">게시판</a></label>
        <div id="notice" class="tabContent">
          <ul>
          <c:forEach var="notice" items="${noticeList }">           
            <li><a href="<c:url value='/board/boardInfo.do?board_num=${notice.board_num }&board_type=${notice.board_code}'/>">${notice.title }</a></li>
          </c:forEach> 
          </ul>
        </div>
        <div id="gallery" class="tabContent">
          <ul>
            <li><img src="<c:url value='/images/c1.jpg'/>" ></li>
            <li><img src="<c:url value='/images/c2.jpg'/>" ></li>
            <li><img src="<c:url value='/images/c3.jpg'/>" ></li>
            <li><img src="<c:url value='/images/c4.jpg'/>" ></li>
            <li><img src="<c:url value='/images/f8.jpg'/>" ></li>  
            <li><img src="<c:url value='/images/f9.jpg'/>" ></li>                  
          </ul>
        </div>
        <div id="board" class="tabContent">
          <ul>            
            <c:forEach var="nomal" items="${nomalList }">           
            <li><a href="<c:url value='/board/boardInfo.do?board_num=${nomal.board_num }&board_type=${nomal.board_code}'/>">${nomal.title }</a></li>
          </c:forEach> 
          </ul>
        </div>
        <div id="lightbox">
			<img src="<c:url value='/images/c1.jpg'/>" alt="" id="lightboxImage">			
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
	        <form action="memberLogin.do" method="post" onsubmit="return false;">
	        	<div class="form-group">
	        		<h2>로그인</h2>
	        	</div>
	            <div id="form-group">
	            	<label for="username">아이디:</label>
	                <input type="text" name="memberid" id="memberid" placeholder="enter your id" required>
	            	
	            </div>
	            <div id="form-group">
	            	<label for="password">비밀번호:</label>
	                <input type="password" name="pwd" id="pwd" placeholder="enter your password" required>
	            	
	            </div>
	            <div id="form-group">
	                <input type="submit" id="memlogin" onclick="login()" value="로그인">              
	            </div>
	            <div id="userInsert">
	            	<a href="#">아이디 찾기</a>｜
	            	<a href="#">비밀번호 찾기</a>｜
	            	<a href="#">회원 가입</a>
	            </div>
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
	        			<li><a href="<c:url value='/member/memberFavorite.do?'/>" class="forlink">사진모음</a></li>
	        			<li>나의문의내역</li>
	        		</ul>
	        	        	
	        	</div>
	          	<div id="userInfo">
	            	<a href="<c:url value='/member/memberInfo.do'/>" class="forlink"><span>나의 상세 정보 보기</span></a>
	            </div>       
	            <div id="loginOut">
	            	<a href="#" onclick="logout()" class="forlink"><span>로그 아웃</span></a>
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
          	<li><a href="#"><img src="<c:url value='/images/sns-1.png'/>"></a></li>
            <li><a href="#"><img src="<c:url value='/images/sns-2.png'/>"></a></li>
            <li><a href="#"><img src="<c:url value='/images/sns-3.png'/>"></a></li>
          </ul>
        </div>
      </div>
      <div id="company">
        <p>제주특별자치도 ***동 ***로 *** (대표전화) 123-456-7890</p> 
      </div>     
    </footer>  
  </div>
	<script type="text/javascript">
	
	
	function login() {
		const param = {
	    		memberid: memberid.value,
		        pwd: pwd.value
		};
	
	    fetch("/workProject/member/memberLogin.do", {
	      method: "POST",
	      headers: {
	        "Content-Type": "application/json; charset=UTF-8",
	      },
	      body: JSON.stringify(param),
	    })
	    .then((response) => response.json())
	    .then((json) => {
	  	    alert(json.message);
	        if (json.status) {
	      	  location.href = "<c:url value='mainIndex.do'/>"; 
	        }
	    });
	}
/* 	document.querySelector("#memlogin").addEventListener("click", e => {
	    
		
	}); */

	//로그아웃
	function logout() {
		fetch("/workProject/member/memberLogout.do", {
	        method: "GET",
	        headers: {
	          "Content-Type": "application/json; charset=UTF-8",
	        },
	      })
	      .then((response) => response.json())
	      .then((json) => {
	         alert(json.message);
	         console.log(json);
	         location.href = "<c:url value='mainIndex.do'/>"; 
	      });
		
	}
	/* document.querySelector("#loginOut").addEventListener("click", e => {
	    
	}); */
	
	//iframe 닫기 메시지 받는곳
	window.addEventListener("message", (event) => {
	    if (event.data === "closeIframe") {
	        var registrationIframe = document.getElementById("iframeForm");
	        if (registrationIframe) {
	            document.body.removeChild(registrationIframe);
	        }
	    }
	});
	
	var showIframe = (href) => {
		var iframe = document.createElement("iframe");
		iframe.src = href;
		iframe.style.border = "1px solid gray";
		iframe.width = "355px";
		iframe.height = "480px";
		
		var closeButton = document.createElement("button");
        closeButton.className = "close-button";
        closeButton.textContent = "X";
        closeButton.style.position = "absolute";
        closeButton.style.top = "10px";
        closeButton.style.right = "10px";
        closeButton.addEventListener("click", () => {
            document.body.removeChild(iframeContainer);
        });
		
		var iframeContainer = document.createElement("div");
        iframeContainer.id = "iframeForm";
        iframeContainer.style.position = "fixed";
        iframeContainer.style.top = "50%";
        iframeContainer.style.left = "50%";
        iframeContainer.style.transform = "translate(-50%, -50%)";
        iframeContainer.style.backgroundColor = "#fff";
        
        iframeContainer.appendChild(iframe);
        iframeContainer.appendChild(closeButton);
        
        document.body.appendChild(iframeContainer);
		
		
	}
	
	
	var hrefArr = ["<c:url value='/member/memberSearchMove.do?chkMem=findid'/>", 
		"<c:url value='/member/memberSearchMove.do?chkMem=findpwd'/>",
		"<c:url value='/member/memberWrite.do'/>"];
	var links = document.querySelectorAll("#userInsert a");
	
	for(let i = 0; i < links.length; i++) {
		let link = links[i];
		link.addEventListener("click", createClickHandler(hrefArr[i]));
	}
	
	function createClickHandler(href) {
		return () => {
			showIframe(href);
		}
	}
	
	
	
	var imgs = document.querySelectorAll("#gallery ul li img");
	var lightbox = document.getElementById("lightbox");
	var lightboxImage = document.getElementById("lightboxImage");
	var footer = document.querySelector("footer");
	var sns = document.querySelector("#sns");
	
	for (let i = 0; i < imgs.length; i++) {
		imgs[i].addEventListener("click", (event) => {
			lightbox.style.display = "block";
			lightboxImage.src = event.target.src;
			footer.style.display = "none";
			sns.style.display = "none";
		});
		
	}

	lightbox.onclick = () => {  
		lightbox.style.display = "none";
		footer.style.display = "block";
		sns.style.display = "block";
}
	
</script>
<script src="<c:url value='/js/slideshow.js'/>"></script>
</body>
</html>