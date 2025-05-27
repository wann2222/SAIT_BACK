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


.select {
  display: flex;
  gap: 25px; /* 요소들 간의 간격 */
}

.selectS a{
  display: flex;
  align-items: center; /* 수직 정렬 */
  gap: 8px; /* 이미지와 텍스트 사이 간격 */
  text-decoration: none;
  margin-top: 50px;
  margin-left: 50px;
  margin-bottom: 25px;
}
.selectS img {
  width: 35px;
  height: 35px;
}

.word{
	width:90px;
	height:35px;
	font-family:"Inter-Medium", Helvetica;
	font-weight: 500;
	color : #000000;
	font-size:20px;
	letter-spacing:0;
	line-height:30px;
	white-space:nowrap;
	top:0;
	left:0;
	
}

.search-form {
  display: flex;
  border: 1px solid #ccc;
  border-radius: 15px;
  padding: 6px 10px;
  width: 525px;
  margin-left:50px;
}

.search-form input {
  border: none;
  outline: none;
  flex: 1;
  font-size: 14px;
}

.search-form button {
  background: none;
  border: none;
  cursor: pointer;
}

.search-form button img {
  width: 18px;
  height: 18px;
}

.view{
	display:flex;
	gap:50px;
	margin-top:40px;
	margin-left:50px;
}

.plan-list{
	width:400px;
	height:780px;
	background-color:#d9d9d9;
}

.map{
	width:1200px;
	height:780px;
	background-color:#d9d9d9;
}

</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/fragments/header.jsp"%>
	<div class="select">
		<div class="selectS">
			<a href="${root}/search?place=jeju" class="place-card">
				<img src="${root}/img/ssafy_logo.png"></img>
				<div class="word">Title 검색</div>
			</a>
		</div>
		
		<div class="selectS">
			<a href="${root}/search?place=jeju" class="place-card">
				<img src="${root}/img/ssafy_logo.png"></img>
				<div class="word">시군구 코드</div>
			</a>
		</div>
		
		<div class="selectS">
			<a href="${root}/search?place=jeju" class="place-card">
				<img src="${root}/img/ssafy_logo.png"></img>
				<div class="word">좋아요</div>
			</a>
		</div>
				
	</div>
	
	<!--<div class="search">
	adsfa
	</div>-->
	
	<form action="${root}/search" method="get" class="search-form">
	    <input type="text" name="query" placeholder="검색" />
	    <button type="submit">
	      <img src="${root}/img/search.png" alt="돋보기" />
	    </button>
	 </form>
	 
	 
	 <div class="view">
		 <div class="plan-list">
			
		 </div>
		
		
		 <div class="map">
			
		 </div>
	</div>
</body>
</html>
