// @charset "UTF-8";
const keyVworld = "363BF87E-7C72-30E5-980D-6D78D2FFA50E";
const keySgisServiceId = "2ae4315b5ef0419fa28c"; // 서비스 id
const keySgisSecurity = "08c4e7cf84714c089a64" // 보안 key
const keyData = "h/JXQyx83XfSgvlPuNUZ/Sh0KCGQFJo1lwrBi+gN85Z6xjR9JBmJATnlzT5lTIKtVHoV1FA3O0Er5zxAEVWE8A=="; // data.go.kr 인증키


const getFetch = async (url, param, isXml) => {
    try {
        const queryString = new URLSearchParams(param).toString();
        const response = await fetch(url + "?" + queryString);
        let result = "";
        if (isXml) {
            result = await response.text();
        } else {
            result = await response.json();
        }
        console.log("요청 URL: " + url, param, result);
        return result;
    } catch (e) {
        console.log(e);
        throw e;
    }
};
/**
 * post 처리를 위한 메서드로 
 */
const postFetch = async (url,body) => {
    try {
        const response = await fetch(url, {
            method: "post",
            headers:{
                "Content-Type":"application/x-www-form-urlencoded",    
            },            
            body: body
        });
        
        let result =  await response.json();
        console.log("요청 URL: " + url, body, result);
        return result;
    } catch (e) {
        console.log(e);
        throw e;
    }
};

// access token 가져오기;
const getAccessToken = async () => {
    try {
        const json = await getFetch("https://sgisapi.kostat.go.kr/OpenAPI3/auth/authentication.json", {
            consumer_key: keySgisServiceId, // 서비스 id
            consumer_secret: keySgisSecurity, // 보안 key
        });
        localStorage.setItem("SGIS_ACCESS_TOKEN", json.result.accessToken);
    } catch (e) {
        console.log(e);
    }
};
getAccessToken();

// 주소를 UTM-K좌표로 변환해서 반환: - json의 errCd ==-401에서 access token 확보!!
const getCoords = async (address) => {
    try {
        const json = await getFetch("https://sgisapi.kostat.go.kr/OpenAPI3/addr/geocode.json", {
            accessToken: localStorage.getItem("SGIS_ACCESS_TOKEN"),
            address: address,
            resultcount: 1,
        });
        if (json.errCd === -401) {
            await getAccessToken();
            return await getCoords(address);
        } else if (json.errMsg !== 'Success') {
            return json;
        } else {
            return json.result.resultdata[0];
        }
    } catch (e) {
        console.log(e);
    }
};

const getLatlon = async (x, y) => {
    try {
        const json = await getFetch("https://sgisapi.kostat.go.kr/OpenAPI3/transformation/transcoord.json", {
            accessToken: localStorage.getItem("SGIS_ACCESS_TOKEN"),  // 액세스 토큰
            src: "EPSG:5179",   // 현재 좌표계: UTM-K (GRS80)
            dst: "EPSG:4326",   // 변환 대상: WGS84 경/위도
            posX: x.toString(), // X 좌표 (동경)
            posY: y.toString()  // Y 좌표 (북위)
        });

        // 인증 실패 시 토큰 갱신
        if (json.errCd === -401) {
            await getAccessToken();
            return await getLatlon(x, y);  // 재시도
        } else if (json.errMsg !== 'Success') {
            return json;  // 에러 메시지 반환
        } else {
            return {
                lat: parseFloat(json.result.posY),
                lon: parseFloat(json.result.posX)
            };
        }
    } catch (e) {
        console.error("좌표 변환 중 오류 발생:", e);
    }
};

// 지도 정보에 주소 업데이트
const updateMap = (map, infos) => {
  const bounds = [];
  try {
    map.mks.forEach(marker => marker.remove() ); // 기존 마커 지우기
    for (let i = 0; i < infos.length; i++) {
      const info = infos[i];
      const marker = sop.marker([info.x, info.y]);
	  
	  
      /*marker.addTo(map).bindInfoWindow(info.title);
      map.mks.push(marker);
      bounds.push([info.x, info.y]);*/
	  
	  // 인포윈도우 콘텐츠 생성
	  
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
	        contents += "<td><img src='" + info.Img + "' alt='이미지' style='width:100px; height:auto;'></td>";
	        contents += "</tr>";
	        contents += "</tbody>";
	        contents += "</table>";

	        // 커스텀 인포윈도우 생성
	        const infoWindow = sop.infoWindow();
	        infoWindow.setUTMK([info.x, info.y]);
	        infoWindow.setContent(contents);

	        // 마커 클릭 시 인포윈도우 열기
	        marker.addTo(map).on('click', function() {
	          infoWindow.openOn(map);
	        });

	        map.mks.push(marker);
	        bounds.push([info.x, info.y]);
    }
    // 경계를 기준으로 map을 중앙에 위치하도록 함
    if (bounds.length > 1) {
      map.setView(map._getBoundsCenterZoom(bounds).center, map._getBoundsCenterZoom(bounds).zoom);
    } else {
      map.setView(map._getBoundsCenterZoom(bounds).center, 9);
    }
  } catch (e) {
    console.log(e);
  }
};



// 거리 계산
const calculateDistance = (point1, point2) => {
  const dx = point2.x - point1.x;
  const dy = point2.y - point1.y;
  const distance = Math.sqrt(dx * dx + dy * dy) / 1000; // m → km 변환
  return distance.toFixed(2);
};

// 소요 시간 계산
const estimateTime = (distance) => {
  const walkingSpeed = 4; // 도보 기준은 시속 4km
  const cyclingSpeed = 15; // 자전거는 시속 15km
  const carSpeed = 60; // 자동차는 시속 60km

  return {
    walk: Math.ceil((distance / walkingSpeed) * 60), // 분 단위
    bike: Math.ceil((distance / cyclingSpeed) * 60),
    car: Math.ceil((distance / carSpeed) * 60),
  };
};

let planOverlays = [];

let polyline = null;

const updateMap1 = (map, infos) => {
  const bounds = [];
  let polyline = null;

  try {
    map.mks.forEach(marker => marker.remove()); // 기존 마커 제거
    map.mks = [];

    for (let i = 0; i < infos.length; i++) {
      const info = infos[i];
      const marker = sop.marker([info.x, info.y]);

      let contents = `
        <div style='font-family: dotum, arial, sans-serif;font-size: 18px; font-weight: bold;margin-bottom: 5px;'>${info.title}</div>
        <table style='border-spacing:2px;border:0px;'>
          <tbody>
            <tr>
              <td style='width:40px;color:#767676;padding-right:12px'>주소</td>
              <td><span>${info.address}</span></td>
            </tr>
            <tr>
              <td style='color:#767676;padding-right:12px'>이미지</td>
              <td><img src='${info.Img}' alt='이미지' style='width:100px; height:auto;'></td>
            </tr>
          </tbody>
        </table>
      `;

      const infoWindow = sop.infoWindow();
      infoWindow.setUTMK([info.x, info.y]);
      infoWindow.setContent(contents);

      marker.addTo(map).on('click', () => {
        infoWindow.openOn(map);
      });

      map.mks.push(marker);
      bounds.push([info.x, info.y]);
    }

    // 🔽 경로 라인 추가 (infos → utmk 기준)
    const polylines = infos.map(info => [info.x, info.y]);

    polyline = sop.polyline(polylines, {
      stroke: true,
      color: "black",
      weight: 3,
      opacity: 1,
      fill: false,
    });

    polyline.addTo(map);
    map.fitBounds(polyline);

    // 🔽 총 거리 및 예상 시간 infoWindow 마지막 지점에 표시
    let totalDistance = 0;
    for (let i = 1; i < infos.length; i++) {
      const prev = infos[i - 1];
      const curr = infos[i];
      const dx = curr.x - prev.x;
      const dy = curr.y - prev.y;
      totalDistance += Math.sqrt(dx * dx + dy * dy) / 1000;
    }

    const totalTime = estimateTime(totalDistance);
    const finalContent = `
      <div style="font-size:14px; padding:5px;">
        <strong>🚩 총 이동 거리</strong><br>
        📏 거리: ${totalDistance.toFixed(2)} km<br>
        🚶 도보: ${totalTime.walk}분 ⏳<br>
        🚴 자전거: ${totalTime.bike}분 🚲<br>
        🚗 자동차: ${totalTime.car}분 🚗
      </div>
    `;
	
	let rightmost = infos[0];
	for (let i = 1; i < infos.length; i++) {
	  if (infos[i].x > rightmost.x) {
	    rightmost = infos[i];
	  }
	}

    const finalInfoWindow = sop.infoWindow();
    finalInfoWindow.setContent(finalContent);
    //finalInfoWindow.setUTMK([infos[infos.length - 1].x, infos[infos.length - 1].y]);
	finalInfoWindow.setUTMK([rightmost.x, rightmost.y]);
    finalInfoWindow.openOn(map);

  } catch (e) {
    console.log(e);
  }
};
window.getCoords = getCoords;
window.getLatlon = getLatlon;


/*const updateMap1 = (map, infos) => {
  const bounds = [];
  try {
    map.mks.forEach(marker => marker.remove()); // 기존 마커 제거
    let totalDistance = 0;

    for (let i = 0; i < infos.length; i++) {
      const info = infos[i];
      const marker = sop.marker([info.x, info.y]);

      let contents = "";
      contents += "<div style='font-family: dotum, arial, sans-serif;font-size: 18px; font-weight: bold;margin-bottom: 5px;'>" + info.title + "</div>";
      contents += "<table style='border-spacing:2px;border:0px;'><tbody>";
      contents += "<tr><td style='width:40px;color:#767676;padding-right:12px'>주소</td><td><span>" + info.address + "</span></td></tr>";
      contents += "<tr><td style='color:#767676;padding-right:12px'>이미지</td><td><img src='" + info.Img + "' alt='이미지' style='width:100px; height:auto;'></td></tr>";

      // 거리 계산
      if (i > 0) {
        const prev = infos[i - 1];
        const dx = info.x - prev.x;
        const dy = info.y - prev.y;
        const dist = Math.sqrt(dx * dx + dy * dy) / 1000;
        totalDistance += dist;

        const times = estimateTime(dist);

        contents += `<tr><td colspan="2" style='padding-top:8px'>
                      📏 거리: ${dist.toFixed(2)} km<br>
                      🚶 도보: ${times.walk}분 ⏳<br>
                      🚴 자전거: ${times.bike}분 🚲<br>
                      🚗 자동차: ${times.car}분 🚗
                     </td></tr>`;
      }

      contents += "</tbody></table>";

      const infoWindow = sop.infoWindow();
      infoWindow.setUTMK([info.x, info.y]);
      infoWindow.setContent(contents);

      marker.addTo(map).on('click', function () {
        infoWindow.openOn(map);
      });

      map.mks.push(marker);
      bounds.push([info.x, info.y]);
    }

    // 마지막 지점에 총 거리 정보 인포윈도우 표시
    if (infos.length > 1) {
      const last = infos[infos.length - 1];
      const totalTimes = estimateTime(totalDistance);

      const summary = `<div style="font-size:14px; padding:5px;">
        <strong>🚩 총 이동 거리</strong><br>
        📏 거리: ${totalDistance.toFixed(2)} km<br>
        🚶 도보: ${totalTimes.walk}분 ⏳<br>
        🚴 자전거: ${totalTimes.bike}분 🚲<br>
        🚗 자동차: ${totalTimes.car}분 🚗
      </div>`;

      const finalWindow = sop.infoWindow();
      finalWindow.setUTMK([last.x, last.y]);
      finalWindow.setContent(summary);
      finalWindow.openOn(map);
    }

    if (bounds.length > 1) {
      map.setView(map._getBoundsCenterZoom(bounds).center, map._getBoundsCenterZoom(bounds).zoom);
    } else {
      map.setView(map._getBoundsCenterZoom(bounds).center, 9);
    }
  } catch (e) {
    console.log(e);
  }
};*/

