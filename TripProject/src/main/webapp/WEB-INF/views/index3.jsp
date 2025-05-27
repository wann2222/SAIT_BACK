<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="root" value="${pageContext.servletContext.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>

.first-page{
	display:block;
}

.blue-annie{
	color: #69d1f4;
	font-family: "Inter", Helvetica;
	font-size: 64px;
	font-weight: 700;
	height: 78px;
	letter-spacing: -1.28px;
	line-height: normal;
	text-align: center;
	margin-top:300px;
	margin-bottom:100px;
}

.mid{
	display:flex;
	align-items:center;
	gap:50px;
	flex-direction: row; 
	justify-content: center;   /* 가운데 정렬 */
}


.J-top{
	color: #000;
	font-family: Inter;
	font-size: 40px;
	font-style: normal;
	font-weight: 600;
	line-height: normal;
	letter-spacing: -0.96px;
}

.J-bottom{
	color: #000;
	font-family: Inter;
	font-size: 40px;
	font-style: normal;
	font-weight: 400;
	line-height: normal;
	letter-spacing: -0.96px;
}

.MY{
	width:120px;
	height:50px;
	border-radius: 8px;
	background: #000;
	box-shadow: 0px 1px 2px 0px rgba(0, 0, 0, 0.05);
	color: white;
	margin-top:30px;
	margin-left:20px;
}


.words{
	color: #000;
	font-family: Inter;
	font-size: 48px;
	font-style: normal;
	font-weight: 600;
	line-height: normal;
	letter-spacing: -0.96px;
	text-align: right;
	margin-top:200px;
	margin-right:150px;
}

.hot-place-text h2{
	color: #000;
	font-family: Inter;
	font-size: 48px;
	font-style: normal;
	font-weight: 600;
	line-height: normal;
	letter-spacing: -0.96px;
	margin-top:350px;
}

.travel-container {
  display: grid;
  grid-template-columns: repeat(5, 1fr); /* 4열 */
  gap: 24px;
  justify-items: center;
  gap:50px;
}
.travel-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-decoration: none;
  width: 300px;
  transition: transform 0.2s;
}

.travel-card:hover {
  transform: scale(1.05);
}

.travel-card img {
  width: 300px;
  height: 200px;
  border-radius: 12px;
  object-fit: cover;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}

.label {
  margin-top: 10px;
  font-size: 20px;
  color: #111;
}

</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/fragments/header.jsp"%>
	<div class="first-page">
		<div class="blue-annie"></div>
		<div class="mid">
			<div class="J"><span class="J-top">J들아 모여 너희 들을 위한 여행 계획</span><br>
				<span class="J-bottom">지금 계획을 세우세요.</span>
			</div>
			<div class="my-plan">
				<a href="${root}/main/PlanTest"><button type="button" class="MY">마이로 시작</button>
				</a>
			</div>
		</div>
		<div class="words">할인</div>	
	</div>
	
	<div class="second-page">
		<div class="hot-place-text">
			<h2>핫플 여행지 둘러보기</h2>	
		</div>
	</div>
		<div class="travel-container">
		  <a href="#?place=Jeju" class="travel-card">
		    <img src="${root}/img/Jeju.png" alt="제주도">
		    <div class="label">제주도</div>
		  </a>
	
		  
		  <a href="#?place=sokcho" class="travel-card">
		    <img src="${root}/img/sokcho.png" alt="속초">
		    <div class="label">속초</div>
		  </a>
	
		  <a href="#?place=Yeosu" class="travel-card">
		    <img src="${root}/img/Yeosu.png" alt="여수">
		    <div class="label">여수</div>
		  </a>
			
		  <a href="#?place=gapung" class="travel-card">
	  	    <img src="${root}/img/gapung.png" alt="가평">
	  	    <div class="label">가평</div>
		  </a>
		  
		  <a href="#?place=seoul" class="travel-card">
	  	    <img src="${root}/img/seoul.png" alt="서울">
	  	    <div class="label">서울</div>
		  </a>
		  
		  <a href="#?place=haeundae" class="travel-card">
		  	  <img src="${root}/img/haeundae.png" alt="해운대">
		  	  <div class="label">해운대</div>
		  </a>
		  
		  <a href="#?place=gungju" class="travel-card">
		  	  <img src="${root}/img/gungju.png" alt="경주">
		  	  <div class="label">경주</div>
	  	  </a>
		  
		  <a href="#?place=incheun" class="travel-card">
	  	  	  <img src="${root}/img/incheun.png" alt="인천">
	  	  	  <div class="label">인천</div>
		  </a>
		  
		  <a href="#?place=gangreung" class="travel-card">
		  	  <img src="${root}/img/gangreung.png" alt="강릉">
		  	  <div class="label">강릉</div>
		  </a>
		  
		  <a href="#?place=gujea" class="travel-card">
	  	  	  <img src="${root}/img/gujea.png" alt="거제">
	  	  	  <div class="label">거제</div>
	  	  </a>
		  <!-- 다른 지역들도 동일하게 추가 -->
		  
		  
		  
		</div>
</body>


<script>

// info 페이지 애니메이션
const title = document.querySelector(".blue-annie");

const text = "AI SSAFY TRIP";
let currentIndex = 0;

const intervalId = setInterval(() => {
    if (currentIndex <= text.length) {
        title.innerHTML = text.substring(0, currentIndex);
        currentIndex++;
        //console.log(currentIndex);
    } else {
        //clearInterval(intervalId);
        currentIndex = 0;
        //console.log(currentIndex);
    }
}, 150);


</script>



</html>
