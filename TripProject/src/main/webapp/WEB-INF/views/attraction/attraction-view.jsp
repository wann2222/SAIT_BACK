<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <script src="${root}/js/common.js"></script>
</head>
<%@ include file="/WEB-INF/views/fragments/header.jsp"%>


<body>
	 

	<h1>관광지 목록 : ${param.sido} ${param.sigungu} - ${param.contentType}</h1>
	<a href="${root}/main?action=search">다시 검색</a> <br><br>
	<%-- <c:forEach var="item" items="${alist}" varStatus="status">
		<p id="${item.ano}">${status.index} : ${item}\
		
		
		
		<form action="${root}/main?action=addToPlan" method="post">
            <input type="hidden" name="ano" value="${item.ano}">
            <button type="submit">여행 계획에 저장</button>
        </form></p>
	</c:forEach> --%>
	
	
	<div id="map" style="width:100%-20px;height:480px"></div>
	
	<table border="1" style="width:100%; margin-top:20px;" id="addrTable">
        <thead>
            <tr>
                <th>No</th>
                <th>장소</th>
                <th>주소</th>
                <th>Y 좌표</th>
            </tr>
        </thead>
        <tbody>
            데이터 행이 여기에 추가됨
        </tbody>
    </table>
	
	<%@ include file="/WEB-INF/views/fragments/footer.jsp"%> 
</body>

<script>

const key_sgis_security = "e8e8587126e14f179b18"; // 보안 key

</script>


<script src="https://sgisapi.kostat.go.kr/OpenAPI3/auth/javascriptAuth?consumer_key=2ae4315b5ef0419fa28c"></script>

<script>
const map = sop.map("map");
map.mks = []; // marker 정보 관리

const addrInfos = []; // 지도 정보를 저장할 배열

<c:forEach var="addr" items="${aList}">
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
// 마커를 지도에 표시하는 함수
addrInfos.forEach(function(item,index) {
	var utmkXY = new sop.LatLng (item.x, item.y);
	var utmkXY = new sop.LatLng (item.x, item.y);
	
	 /* var contents =  "";
     contents += "<div style='font-family: dotum, arial, sans-serif;font-size: 18px; font-weight: bold;margin-bottom: 5px;'>"+item.name+"</div>"; */
	
	//
	const table = document.getElementById("addrTable").getElementsByTagName("tbody")[0];
	const newRow = table.insertRow();
	const cell1 = newRow.insertCell(0);
	const cell2 = newRow.insertCell(1);
	const cell3 = newRow.insertCell(2);
	const cell4 = newRow.insertCell(3);
	const cell5 = newRow.insertCell(4); // 버튼 영역 추가
	
	cell1.innerHTML = index + 1;
	cell2.innerHTML = item.title;
	cell3.innerHTML = item.address;
	/* cell5.innerHTML = `
	    <button type = "submit">상세 정보 보기</button>
	`;
	cell6.innerHTML = `
	    <button type = "submit">관심 장소 등록</button>
	`; */
	 // 상세 정보 보기 버튼
     const detailButton = document.createElement("button");
    detailButton.textContent = "상세 정보 보기";
    detailButton.onclick = () => {
        showCustom(item); // 상세 정보 보기 클릭 시 해당 정보 전달
    };
    cell4.appendChild(detailButton);

    // 관심 장소 등록 버튼
   cell5.innerHTML = `
	    <form action="${root}/main" method="POST">
	    	<input type="hidden" name="action" value="add-to-plan">
	    	<input type="hidden" name="ano" value="\${item.ano}">
	        <select name="pid">
	        	<option value="">여행 계획 선택</option>
	            <c:forEach var="item" items="${pList}">
	                <option value="${item.pid}">${item.name}</option>
	            </c:forEach>
	        </select>
	        <button type="submit">여행 계획에 추가</button>
	    </form>
	`;
	
	addR.push({
        title: item.title,
        x: utmkXY.x,
        y: utmkXY.y,
        address: item.address,
        Img : item.img,
        Overview : item.overview
    });
	
	
});




// 지도 갱신 함수 호출
updateMap(map, addR);

function showCustom(info) {
    
	console.log(info.x);
	console.log(info.y);
    let contents = "";
    contents += "<div style='font-family: dotum, arial, sans-serif;font-size: 18px; font-weight: bold;margin-bottom: 5px;'>" + info.title + "</div>";
    contents += "<table style='border-spacing:2px;border:0px;'>";
    contents += "<tbody>";
    contents += "<tr>";
    contents += "<td style='width:40px;color:#767676;padding-right:12px'>주소</td>";
    contents += "<td><span>" + info.address + "</span></td>";
    contents += "</tr>";
    contents += "<tr>";
    contents += "<td style='color:#767676;padding-right:12px'>이미지</td>";
    contents += "<td><img src='" + info.img + "' alt='이미지' style='width:150px; height:auto;'></td>";
    contents += "</tr>";
    contents += "</tbody>";
    contents += "</table>";
    var utmkXY = new sop.LatLng (info.x,info.y);
    const infoWindow = sop.infoWindow();
    infoWindow.setUTMK([utmkXY.x, utmkXY.y]);
    infoWindow.setContent(contents);
    infoWindow.openOn(map);
    
    const mapElement = document.getElementById("map");
    mapElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
    
}

</script>


</html>
