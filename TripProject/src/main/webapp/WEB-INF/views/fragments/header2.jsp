<!-- /fragments/header.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%-- 페이지들이 가져갈 공통적인 내용들 위치 --%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>


.container{
    display:flex;
    height:65px;

}

.container .Saffylogo{
    height:100%;
    display:flex;
}

.container  .img{
    width:auto;
    height:100%;
}

.container .items{

    margin-left:auto;
}



</style>
</head>
<body>

    <div class="container">
    
    
          <div class="Saffylogo">
              <img src="${root}/img/ssafylogo.jpg" alt="ssafy logo">
              <div class="AI">AI 싸피 여행</div>
          </div>
          
          
          <div class="items">
            <button class="button"><div class="text-wrapper">고객센터</div></button>
            <button class="button"><div class="text-wrapper">싸피 여행 소개</div></button>
            <button class="button"><div class="text-wrapper">로그인</div></button>
          </div>
          
    </div>
</body>
</html>





