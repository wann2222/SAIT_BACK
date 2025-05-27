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

	<h1>당신의 여행 계획 목록</h1>
	<ul>
		<c:forEach var="item" items="${pList}" varStatus="status">
			<li><a href="${root}/main?action=plan-detail&pid=${item.pid}"> 
			${status.index} : ${item.name}, 여행 출발: ${item.start }, 여행 종료 : ${item.end }</a> </li> 
		</c:forEach>
	</ul>
	<button type="button" onclick="location.href='${root}/main?action=make-plan-form'">새로 만들기</button>
	
	<%@ include file="/WEB-INF/views/fragments/footer.jsp"%> 
</body>
</html>
