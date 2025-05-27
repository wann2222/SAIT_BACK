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

	<h1>${post.title}</h1>
	<div>
	${post.content }
	</div>
	<button type="button" onclick="location.href='${root}/board/update-form'">수정하기</button>
	
<%-- 	<%@ include file="/fragments/footer.jsp"%> --%>
</body>
</html>
