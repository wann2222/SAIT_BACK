<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="root" value="${pageContext.servletContext.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.main {
	display: flex;
	gap: 20px;
}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/fragments/header.jsp"%>
	<div>
		
		<%-- 로그인된 경우 --%>
		<c:if test="${!empty SPRING_SECURITY_CONTEXT.authentication}">
			<h3>회원 관리</h3>
			<a href="${root}/member/member-list">회원 목록 보기</a>
			<hr/>
			
			<h3>관광지, 여행계획</h3>
			<a href="${root}/main/search">관광지 목록</a>  |
			<a href="${root}/main/plan-list">여행 계획</a>  | 
			<hr/>
			
			<h3>핫플레이스</h3>
			<a href="${root}/hotplace/search-hotplace">핫플레이스 보기</a>  |
			<a href="${root}/hotplace/register-form">핫플레이스 등록</a>  
			<hr/>
			
			<h3>게시판</h3>
			<a href="${root}/board/post-list?currentPage=1">여행정보공유</a> 
			<hr/>
		</c:if>
		<a href ="${root }/hotplace/test">좋아요 테스트입니당</a>
		<a href = "${root }/hotplace/test2">사용자별 좋아요 어트랙션 리스트 조회 테스트</a>

	</div>
	<%@ include file="/WEB-INF/views/fragments/footer.jsp"%> 
</body>
</html>
