<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="/WEB-INF/views/fragments/header.jsp"%>

    <h1>여행 만들기</h1>
    <form action="${root}/main" method="post" class="m-3">
        <input type="hidden" name="action" value="make-plan" />

        <div class="mb-3 row">
            <label for="name" class="col-sm-2 col-form-label">여행 이름</label>
            <div class="col-sm-10">
                <input type="text" name="name" id="name" class="form-control" required />
            </div>
        </div>

        <div class="mb-3 row">
            <label for="startDate" class="col-sm-2 col-form-label">시작일</label>
            <div class="col-sm-10">
                <input type="date" name="startDate" id="startDate" class="form-control" required />
            </div>
        </div>

        <div class="mb-3 row">
            <label for="endDate" class="col-sm-2 col-form-label">종료일</label>
            <div class="col-sm-10">
                <input type="date" name="endDate" id="endDate" class="form-control" required />
            </div>
        </div>

        <button type="submit" class="btn btn-primary">등록</button>
    </form>
    <!--TODO: 12-1. 페이지에서 발생한 에러를 출력하는 영역-->
    <c:if test="${!empty error }">
        <div class="alert alert-danger" role="alert">${error }</div>
    </c:if>

	<%@ include file="/WEB-INF/views/fragments/footer.jsp"%> 
</body>
</html>
