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
	
	<h1>핫플레이스 목록</h1>
	<c:forEach var="item" items="${hlist}" varStatus="status">
		<p id="${item.hid}">${status.index+1} : ${item} <img src=${item.image}></p>
	</c:forEach>
	<%@ include file="/WEB-INF/views/fragments/footer.jsp"%> 
</body>
</html>
