<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>

	#map {
	        width: 100%;
	      }
	      #plans-map{
	        display: flex;
	        gap:30px;
	      }
	      #planContainer {
	        width: 250px;
	        background: white;
	        border-radius: 10px;
	        box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.2);
	        padding: 10px;
	        max-height: 400px;
	        overflow-y: auto;
	      }

</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="/WEB-INF/views/fragments/header.jsp"%>

	<h1>여행 계획 정보</h1>
	
	${plan }
	여기에 지도 띄워주면 될 것 같아요
	
	<section id="plans-map">
          <div id="planContainer" style="height:500px">
            <ul id="planList"></ul>
          </div>
          <div id="map" style="width: 80%; height: 500px"></div>
        </div>
   </section>
	
	
	
	<h3>여행 경로</h3>
	
	<ul>
		<c:forEach items="${sList }" var="item" varStatus="status">
			<li> ${status.index } : ${item } </li>
		</c:forEach>
	</ul>
	<%@ include file="/WEB-INF/views/fragments/footer.jsp"%> 
</body>

<script src="https://sgisapi.kostat.go.kr/OpenAPI3/auth/javascriptAuth?consumer_key=1edd5447a93540bab079"></script>
<script src="${root }/js/common.js"></script>

<script>
const map = sop.map("map");
map.mks = []; // marker 정보 관리

const addrInfos = []; // 지도 정보를 저장할 배열

<c:forEach var="addr" items="${sList}">
    addrInfos.push({
    	ano : "${addr.ano}",
        title: "${addr.name}",
        x: ${addr.lat},
        y: ${addr.lon},
        address : "${addr.address}",
        img : "${addr.img}",
        overview :"${addr.overview}"
    });
    
    
    
</c:forEach>

// 콘솔로 확인
console.log("주소 정보 배열:", addrInfos);


const addR = [];
//마커를 지도에 표시하는 함수
addrInfos.forEach(function(item,index) {
	var utmkXY = new sop.LatLng (item.x, item.y);
	var utmkXY = new sop.LatLng (item.x, item.y);
	
	
	addR.push({
	     title: item.title,
	     x: utmkXY.x,
	     y: utmkXY.y,
	     address: item.address,
	     Img : item.img,
	     Overview : item.overview
 	});
	console.log(item.title);
	const li = document.createElement("li");
	const div = document.createElement("div");
	const strong = document.createElement("strong");
	strong.textContent = item.title;

	div.appendChild(strong);
	div.appendChild(document.createElement("br"));
	li.appendChild(div);
	li.appendChild(document.createElement("hr"));

	planList.appendChild(li);
    
    
    
});




//지도 갱신 함수 호출
updateMap1(map, addR);

</script>



<!-- <script
      type="text/javascript"
      src="https://sgisapi.kostat.go.kr/OpenAPI3/auth/javascriptAuth?consumer_key=2ae4315b5ef0419fa28c">
</script> -->

    <!-- 맵관련 -->
    <!-- <script src="js/enjoytrip.js"></script>
    <script>
      const init = async () => {
        areaCode1(); // enjoytrip.js
        //{ address: address, utmk: getCoords(address), label: aptNm }
        
        
        
        
        
      };
      init();
    </script>
    <script src="/js/plan.js"></script> -->

</html>
