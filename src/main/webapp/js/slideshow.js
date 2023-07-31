var slides = document.querySelectorAll("#slides > img");
var prev = document.getElementById("prev");
var next = document.getElementById("next");
var current = 0;
var slideInterval = setInterval(nextSlide, 3000); // 3초마다 다음 슬라이드로 전환

showSlides(current);
prev.onclick = prevSlide;
next.onclick = nextSlide;

function showSlides(n) {
  for (let i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";
  }
  slides[n].style.display = "block";
}

function prevSlide() {
  if (current > 0) current -= 1;
  else current = slides.length - 1;
  showSlides(current);
}

function nextSlide() {
  if (current < slides.length - 1) current += 1;
  else current = 0;
  showSlides(current);
}

function resetInterval() {
  clearInterval(slideInterval); // 이전 타이머를 초기화
  slideInterval = setInterval(nextSlide, 2000); // 2초마다 다음 슬라이드로 전환
}
// 마우스가 슬라이드쇼 영역 위로 올라갔을 때, 자동 이동을 멈춤
document.getElementById("slides").addEventListener("mouseover", function() {
  clearInterval(slideInterval);
});
// 마우스가 슬라이드쇼 영역에서 벗어났을 때, 자동 이동을 다시 시작
document.getElementById("slides").addEventListener("mouseout", function() {
  resetInterval();
});