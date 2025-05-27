<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/fragments/header.jsp"%>

    <h1>현재 로그인된 회원 정보</h1>
    <h2>${member}</h2>

	<%@ include file="/WEB-INF/views/fragments/footer.jsp"%> 
</body>
</html>
