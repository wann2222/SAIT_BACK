<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="/WEB-INF/views/fragments/header.jsp"%>

	<c:if test="${!empty member}">
		<h2>현재 접속중인 회원</h2>
		<h3>${member}</h3>
	</c:if>
	
	<h1>회원 목록</h1>
	<c:forEach var="member" items="${memberList}" varStatus="status">
		${status.index} : ${member} <br>
	</c:forEach>
	<%@ include file="/WEB-INF/views/fragments/footer.jsp"%> 
</body>
</html>
