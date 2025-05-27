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
	
	<h1>비밀번호 찾기</h1>
    <form action="${root}/member/findPassword" method="post" class="m-3">

        <div class="mb-3 row">
            <label for="email" class="col-sm-2 col-form-label">이메일</label>
            <div class="col-sm-10">
                <input type="email" name="email" id="email" class="form-control" required />
            </div>
        </div>

        <button type="submit" class="btn btn-primary">비밀번호 찾기</button>
    </form>
    <!-- 에러 출력 -->
    <c:if test="${!empty error }">
        <div class="alert alert-danger" role="alert">${error }</div>
    </c:if>
    
	<%@ include file="/WEB-INF/views/fragments/footer.jsp"%> 
</body>
</html>