<!-- /fragments/header.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%-- 페이지들이 가져갈 공통적인 내용들 위치 --%>
<link
  href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
  rel="stylesheet"
/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<style>
  header > #logo {
    width: 90px;
    margin-bottom: 8px;
    margin-left: 10px;
  }

  header > h1 {
    line-height: 50px;
    display: inline-block;
    height: 50px;
  }
  
  .d-flex{
  	gap:10px;
  }
</style>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="root" value="${pageContext.servletContext.contextPath }" />
<div class="container">
  <!-- 전체 .container 시작 -->
  <header class="d-flex justify-content-center my-5 align-items-center">
    <h1 class="text-center">Welcome To</h1>
    <img src="${root }/img/ssafy_logo.png" id="logo" />
    <h1> Trip!</h1>
  </header>
  <div class="d-flex justify-content-end">
    <a href="${root }/main" class="mx-3">메인으로</a>|  
    <!-- TODO: 10-2. 회원가입을 위한 페이지를 요청하는 링크(action=regist-member-form)를 만들어보자. -->
    <%-- 로그인하지 않은 경우 --%>
	<c:if test="${empty SPRING_SECURITY_CONTEXT}">
		<a href="${root}/member/login-form">로그인</a>|  
		<a href="${root}/member/regist-member-form">회원가입</a> |
		<a href="${root}/member/findPassword-member-form">비밀번호 찾기</a>
	</c:if>
	<c:if test="${!empty SPRING_SECURITY_CONTEXT}">
		<a href="${root}/member/logout-form">로그아웃</a> |
		<a href="${root}/member/detail">회원정보 상세 보기</a> |
		<a href="${root}/member/update-form">회원정보 수정</a>  |  
		<a href="${root}/member/delete">회원 탈퇴</a> |
	</c:if>
    <!-- END -->
    
  </div>
  <hr />
  <!-- TODO: 07-2. <script>에서 파라미터 또는 attribute에 alertMsg가이 있다면 alert으로 출력하세요. -->
  <script>
  	const msg = `${alertMsg}` || `${param.alertMsg}`;
  	if(msg) {
  		alert(msg)
  	}
  </script>

  <!--END-->

  <!--TODO: 11-2. session 영역에 저장된 alertMsg를 삭제하세요.-->
  <script>
  <c:remove var="alertMsg"/>
  </script>

  <!-- END -->