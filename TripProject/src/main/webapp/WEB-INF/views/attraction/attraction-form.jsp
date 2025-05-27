<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="com.ssafy.trip.model.service.AttractionService, java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>



</head>
<body>
 	<%@ include file="/WEB-INF/views/fragments/header.jsp"%> 

	<h1>여행지 검색</h1>
	<form action="${root}/main/attraction-view?" class="m-3" method="post">

		<div class="mb-3" style="display:flex">
			<select name="sido" id="areaCode">
				<option value="" selected disabled>시도 선택</option>
			</select> <select name="sigungu" id="sigunguCode">
				<option value="" selected disabled>시군구 선택</option>
			</select> <select name="contentType" id="contentType">
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

		<button type="submit" class="btn btn-primary">검색</button>
	</form>
	<%@ include file="/WEB-INF/views/fragments/footer.jsp"%> 
</body>
  <script>
    document.addEventListener("DOMContentLoaded", () => {
      // 1) 서버에서 준비된 시도·시군구 데이터(JS 리터럴)
      const sidoList = [
        <c:forEach var="sido" items="${sidoList}" varStatus="st">
          '${sido}'<c:if test="${!st.last}">, </c:if>
        </c:forEach>
      ];
      const sigunguMap = {
        <c:forEach var="sido" items="${sidoList}" varStatus="st">
          '${sido}': [
            <c:forEach var="sg" items="${sigunguMap[sido]}" varStatus="sgSt">
              '${sg}'<c:if test="${!sgSt.last}">, </c:if>
            </c:forEach>
          ]<c:if test="${!st.last}">, </c:if>
        </c:forEach>
      };

      // 2) DOM 요소
      const areaSelect    = document.getElementById("areaCode");
      const sigunguSelect = document.getElementById("sigunguCode");
      const detailInput   = document.getElementById("detailAddress");
      const form          = document.getElementById("registerForm");
      
      

      // 3) 시도 옵션 채우기
      sidoList.forEach(sido => {
        const opt = document.createElement("option");
        opt.value = sido; opt.text = sido;
        areaSelect.appendChild(opt);
      });

      // 4) 시도 변경 시 시군구 옵션
      areaSelect.addEventListener("change", () => {
        sigunguSelect.innerHTML = '<option value="" disabled selected hidden>시군구 선택</option>';
        (sigunguMap[areaSelect.value] || []).forEach(name => {
          const o = document.createElement("option");
          o.value = name; o.text = name;
          sigunguSelect.appendChild(o);
        });
      });

    });
  </script>
<%-- 
<% 
    // 서비스 객체 생성
    AttractionService aService = AttractionService.getService();
    
    // 시도 리스트 가져오기
    List<String> sidoList = aService.getSidoList();

    // 시도별 시군구 맵 생성
    Map<String, List<String>> sigunguMap = new HashMap<>();
    for (String sido : sidoList) {
        sigunguMap.put(sido, aService.getSigunguList(sido));
    }
%>
<script>
document.addEventListener("DOMContentLoaded", () => {
    const area = document.querySelector("#areaCode");
    const sigungu = document.querySelector("#sigunguCode");

    // 시도 데이터 (JSP에서 직접 JavaScript 배열로 변환)
    const sidoList = <%
        out.print("[");
        for (int i = 0; i < sidoList.size(); i++) {
            out.print("\"" + sidoList.get(i) + "\"");
            if (i < sidoList.size() - 1) out.print(",");
        }
        out.print("]");
    %>;

    // 시도별 시군구 데이터 (JSP에서 직접 JavaScript 객체로 변환)
    const sigunguData = {
        <% for (String sido : sidoList) { 
            List<String> sigunguList = sigunguMap.getOrDefault(sido, new ArrayList<>());
        %>
            "<%= sido %>": [
                <% for (int i = 0; i < sigunguList.size(); i++) { %>
                    "<%= sigunguList.get(i) %>"<%= (i < sigunguList.size() - 1) ? "," : "" %>
                <% } %>
            ],
        <% } %>
    };

    // 시도 드롭다운 초기화
    sidoList.forEach(sido => {
    	console.log(sido);
        area.innerHTML += `<option value="\${sido}">\${sido}</option>`;
    });

    // 시도 선택 시, 해당 시군구 목록 변경
    area.addEventListener("change", function () {
        const selectedSido = area.value;
        const sigunguList = sigunguData[selectedSido] || [];

        // 시군구 드롭다운 초기화
        sigungu.innerHTML = `<option value="" selected disabled>시군구 선택</option>`;
        sigunguList.forEach(sigunguName => {
            sigungu.innerHTML += `<option value="\${sigunguName}">\${sigunguName}</option>`;
        });
    });
});
</script>

</html>
 --%>