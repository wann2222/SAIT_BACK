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

	<h1>여행 계획 리스트</h1>
	<c:forEach var="item" items="${alist}" varStatus="status">
		<p id="${item.ano}">${status.index} : ${item}\
		<form action="${root}/main?action=addToPlan" method="post">
            <input type="hidden" name="ano" value="${item.ano}">
            <button type="submit">여행 계획에 저장</button>
        </form></p>
	</c:forEach>
	<%@ include file="/WEB-INF/views/fragments/footer.jsp"%> 
</body>
</html>
