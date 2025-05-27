<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="/WEB-INF/views/fragments/header.jsp"%>

    <h1>게시물 수정</h1>
    <form action="${root}/board/update" method="post" class="m-3">


        <div class="mb-3 row">
            <label for="title" class="col-sm-2 col-form-label">제목</label>
            <div class="col-sm-10">
                <input type="text" name="title" id="title" class="form-control" value="${post.title }" required />
            </div>
        </div>

        <div class="mb-3 row">
            <label for="content" class="col-sm-2 col-form-label">내용</label>
            <div class="col-sm-10">
                <textarea name="content" placeholder="내용 작성"
                style="width:100%;height:400px">${post.content } </textarea>
            </div>
        </div>
        <div class="d-flex justify-content-end" style="margin:5px">
            <button type="submit" class="btn btn-primary">수정</button>
            <button type="button" class="btn btn-primary"
            onclick="location.href='${root}/board/delete'">삭제</button>
        </div>
    </form>
    <c:if test="${!empty error }">
        <div class="alert alert-danger" role="alert">${error }</div>
    </c:if>

<%@ include file="/WEB-INF/views/fragments/footer.jsp"%> 
</body>
</html>