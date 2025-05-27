<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>회원가입 | Trip</title>
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="${root}/css/bootstrap.min.css" />
  <!-- common.js (getFetch, getAccessToken, getCoords, getLatlon 등) -->
  <script src="${root}/js/common.js"></script>
</head>
<body>
  <jsp:include page="/WEB-INF/views/fragments/header.jsp" />

  <div class="container mt-4">
    <h2>회원가입</h2>
    <form id="registerForm" action="${root}/member/regist-member" method="post" class="mt-3">
      <!-- 이름 -->
      <div class="mb-3 row">
        <label for="name" class="col-sm-2 col-form-label">이름</label>
        <div class="col-sm-10">
          <input type="text" id="name" name="name" class="form-control" required />
        </div>
      </div>

      <!-- 이메일 -->
      <div class="mb-3 row">
        <label for="email" class="col-sm-2 col-form-label">이메일</label>
        <div class="col-sm-10">
          <input type="email" id="email" name="email" class="form-control" required />
        </div>
      </div>

      <!-- 비밀번호 -->
      <div class="mb-3 row">
        <label for="password" class="col-sm-2 col-form-label">비밀번호</label>
        <div class="col-sm-10">
          <input type="password" id="password" name="password" class="form-control" required />
        </div>
      </div>

      <!-- hidden 좌표 필드 -->
      <input type="hidden" id="coordX" name="x" />
      <input type="hidden" id="coordY" name="y" />
      <input type="hidden" id="latitude" name="lat" />
      <input type="hidden" id="longitude" name="lon" />

      <!-- 주소 선택 -->
      <div class="mb-3 row">
        <label for="areaCode" class="col-sm-2 col-form-label">주소</label>
        <div class="col-sm-10 d-flex">
          <select id="areaCode" name="sido" class="form-select me-2" required>
            <option value="" disabled selected hidden>시도 선택</option>
          </select>
          <select id="sigunguCode" name="sigungu" class="form-select me-2" required>
            <option value="" disabled selected hidden>시군구 선택</option>
          </select>
          <input
            type="text"
            id="detailAddress"
            name="address"
            class="form-control"
            placeholder="상세주소 입력"
            autocomplete="off"
            required />
        </div>
      </div>

      <!-- 등록 버튼 -->
      <div class="mb-3 row">
        <div class="col-sm-10 offset-sm-2">
          <button type="submit" class="btn btn-primary">등록</button>
        </div>
      </div>
    </form>

    <c:if test="${not empty error}">
      <div class="alert alert-danger mt-3">${error}</div>
    </c:if>
  </div>

  <jsp:include page="/WEB-INF/views/fragments/footer.jsp" />

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

      // 5) 폼 제출 전 유효성 검사 및 좌표 계산
      form.addEventListener("submit", async (e) => {
        e.preventDefault();
        const sidoVal    = areaSelect.value.trim();
        const sigunguVal = sigunguSelect.value.trim();
        const detailVal  = detailInput.value.trim();
        
        console.log("[DEBUG] sidoVal   =", sidoVal);
        console.log("[DEBUG] sigunguVal=", sigunguVal);
        console.log("[DEBUG] detailVal =", detailVal);
        
        if (!sidoVal || !sigunguVal || !detailVal) {
          alert("시도·시군구·상세주소를 모두 입력해주세요.");
          return;
        }

        const fullAddr = `\${sidoVal} \${sigunguVal} \${detailVal}`;
        console.log("[DEBUG] fullAddr =", fullAddr);

        try {
          const utmk = await getCoords(fullAddr);
          
          console.log("[DEBUG] utmk =", utmk);
          
          if (!utmk || !utmk.x || !utmk.y) throw new Error("UTM-K 좌표를 가져올 수 없습니다.");
          const { lat, lon } = await getLatlon(utmk.x, utmk.y);
          if (lat == null || lon == null) throw new Error("WGS84 좌표 변환에 실패했습니다.");

          document.getElementById("coordX").value   = utmk.x;
          document.getElementById("coordY").value   = utmk.y;
          document.getElementById("latitude").value = lat;
          document.getElementById("longitude").value= lon;
          form.submit();
        } catch (err) {
          console.error(err);
          alert(err.message || "주소 변환 중 오류가 발생했습니다.");
        }
      });
    });
  </script>
</body>
</html>