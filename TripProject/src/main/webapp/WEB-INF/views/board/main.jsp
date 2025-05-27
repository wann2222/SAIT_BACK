<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="container">
<%@ include file="/WEB-INF/views/fragments/header.jsp"%>
    <h1>게시판</h1>
<form class="row mb-3" action="${root }/board/post-list" method="post" id="search-form">
    
        <input type="hidden" id="currentPage" name="currentPage" value="1" />
        <div class="d-flex justify-content-end">
            <select class="form-select w-25" name="key">
                <option value="">검색항목 선택</option>
                    <option value="1" ${param.key=='1'?'selected':'' }>이름</option>
                    <option value="2" ${param.key=='2'?'selected':'' }>제목</option>
                    <option value="3" ${param.key=='3'?'selected':'' }>본문</option>
            </select>
            <input type="text" class="form-control w-25" name="word" value="${param.word}">
            <button type="submit" class="btn btn-primary">검색</button>
        </div>
        <div class="d-flex justify-content-end" style="margin:5px">
            <button type="button" class="btn btn-primary"
             onclick="location.href='${root}/board/regist-post'">글 작성</button>
        </div>
    </form>
    <table class="table">
        <tbody>
            <tr>
                <td>no</td>
                <td>작성자</td>
                <td>제목</td>
                <td>작성일</td>
            </tr>
            <c:forEach items="${page.list }" var="item" varStatus="status">
                <tr>
                    <td>${status.index+1+(page.condition.currentPage-1)*page.condition.itemsPerPage}</td>
                    <td><a href="${root }/board/read?no=${item.no}">${item.name }</a></td>
                    <td>${item.title}</td>
                    <td>${item.date}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <nav class="d-flex justify-content-center">
        <ul class="pagination">
            <!-- 이전 버튼 -->
            <c:if test="${page.hasPre}">
                <li class="page-item"><a class="page-link" href="#" data-page="${ page.startPage-1}">이전</a></li>
            </c:if>

            <!-- 페이지 번호 -->
            <c:forEach begin="${page.startPage}" end="${page.endPage}" var="item">
                <li class="page-item ${page.condition.currentPage == item ? 'active' : ''}"><a class="page-link" href="#"
                        data-page="${ item}">${item}</a></li>
            </c:forEach>

            <!-- 다음 버튼 -->
            <c:if test="${page.hasNext}">
                <li class="page-item"><a class="page-link" href="#" data-page="${ page.endPage+1}">다음</a></li>
            </c:if>
        </ul>
    </nav>
	<%@ include file="/WEB-INF/views/fragments/footer.jsp"%> 
    </div>
</body>
<script>
const pageLinks = document.querySelectorAll(".pagination a");
pageLinks.forEach(link =>{
  link.addEventListener("click", (e)=>{
    e.preventDefault(); //a 링크의 기본 동작 중지
    document.querySelector("#currentPage").value=link.dataset.page; // 링크에 설정된 data 속성으로 form의 page 수정
    document.querySelector("#search-form").submit(); // form sumbit
  })
})
</script>
</html>
