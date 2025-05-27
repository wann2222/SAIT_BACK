<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="/WEB-INF/views/fragments/header.jsp"%>




    <h1>핫플레이스 등록</h1>
    <form action="${root}/hotplace/regist-hotplace" method="post" class="m-3">

        <div class="mb-3 row">
            <label for="name" class="col-sm-2 col-form-label">장소명</label>
            <div class="col-sm-10">
                <input type="text" name="title" id="title" class="form-control" required />
            </div>
        </div>

        <div class="mb-3 row">
            <label for="date" class="col-sm-2 col-form-label">방문일</label>
            <div class="col-sm-10">
                <input type="date" name="date" id="date" class="form-control" required />
            </div>
        </div>
          <div class="mb-3 row">
          <label for="contentType" class="col-sm-2 col-form-label">장소유형</label>
			<select name="contentType" id="contentType"> 
				<option value="" selected disabled>관광타입 선택</option>
				<option value="관광지">관광지</option>
				<option value="문화시설">문화시설</option>
				<option value="축제공연행사">축제공연행사</option>
				<option value="여행코스">여행코스</option>
				<option value="레포츠">레포츠</option>
				<option value="숙박">숙박</option>
				<option value="쇼핑">쇼핑</option>
				<option value="음식점">음식점</option>
			</select>
		</div>
		<div class="mb-3 row">
            <label for="lat" class="col-sm-2 col-form-label">위도</label>
            <div class="col-sm-10">
                <input type="text" name="lat" id="lat" class="form-control" required />
            </div>
        </div><div class="mb-3 row">
            <label for="lon" class="col-sm-2 col-form-label">경도</label>
            <div class="col-sm-10">
                <input type="text" name="lon" id="lon" class="form-control" required />
            </div>
        </div>
        <div class="mb-3 row">
            <label for="image" class="col-sm-2 col-form-label">이미지</label>
            <div class="col-sm-10">
                <input type="text" name="image" id="image" class="form-control" required />
            </div>
        </div>

        <button type="submit" class="btn btn-primary">등록</button>
        
    </form>
    <c:if test="${!empty error }">
        <div class="alert alert-danger" role="alert">${error }</div>
    </c:if>

	<%@ include file="/WEB-INF/views/fragments/footer.jsp"%> 
</body>

<script src="https://sgisapi.kostat.go.kr/OpenAPI3/auth/javascriptAuth?consumer_key=1edd5447a93540bab079"></script>
<script src="${root }/js/common.js"></script>


<script>
const addrInfos = [];
/* <c:forEach var="addr" items="${hList}">
addrInfos.push({
	ano : "${addr.ano}",
    title: "${addr.title}",
    x: ${addr.x},
    y: ${addr.y},
    img : "${addr.image}",
});
</c:forEach>

//콘솔로 확인
console.log("주소 정보 배열:", addrInfos); */
</script>


</html>
