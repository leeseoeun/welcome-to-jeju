// 마커를 담을 배열입니다
var markers = [];

var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
var options = { //지도를 생성할 때 필요한 기본 옵션
    center: new kakao.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표.
    level: 10 //지도의 레벨(확대, 축소 정도)
};

var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
var infowindow = new kakao.maps.InfoWindow({zIndex:1});

// 검색 결과 목록과 마커를 표출하는 함수입니다
function displayPlaces(places) {
    performance.mark('marker-render-start'); // 시작 시점 기록

    var listEl = document.getElementById('placesList'),
        fragment = document.createDocumentFragment(),
        bounds = new kakao.maps.LatLngBounds(),
        listStr = '';

    var batchSize = 30; // 한번에 처리할 장소 수 (프레임마다 처리할 마커 수)
    var index = 0; // 현재 처리할 시작 인덱스

    // 실제 마커 및 리스트 아이템을 배치하는 로직을 일정 단위로 나눠 처리하는 함수
    function renderChunk() {
        // 현재 인덱스부터 batchSize만큼 마커와 리스트를 처리
        var end = Math.min(index + batchSize, places.length);

        for (var i = index; i < end; i++) {

            // 마커를 생성하고 지도에 표시합니다
            var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
                marker = addMarker(placePosition, places[i].no),
                itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

            // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
            // LatLngBounds 객체에 좌표를 추가합니다
            bounds.extend(placePosition);

            // 마커와 검색결과 항목에 mouseover 했을때
            // 해당 장소에 인포윈도우에 장소명을 표시합니다
            // mouseout 했을 때는 인포윈도우를 닫습니다
            (function (marker, title) {
                kakao.maps.event.addListener(marker, 'mouseover', function () {
                    displayInfowindow(marker, title);
                });

                kakao.maps.event.addListener(marker, 'mouseout', function () {
                    infowindow.close();
                });

                itemEl.onmouseover = function () {
                    displayInfowindow(marker, title);
                };

                itemEl.onmouseout = function () {
                    infowindow.close();
                };
            })(marker, places[i].name);

            fragment.appendChild(itemEl);
        }

        index = end; // 다음 루프의 시작 인덱스를 업데이트

        if (index < places.length) {
            // 아직 처리할 항목이 남았으면 다음 프레임에서 계속 처리
            requestAnimationFrame(renderChunk);
        } else {
            // 검색결과 항목들을 검색결과 목록 Element에 추가합니다
            listEl.appendChild(fragment);

            // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
            map.setBounds(bounds);

            performance.mark('marker-render-end'); // 종료 시점 기록
            performance.measure('marker-render', 'marker-render-start', 'marker-render-end');

            const duration = performance.getEntriesByName('marker-render')[0].duration;

            console.log('마커 개수 : ', places.length);
            console.log('DOM에 추가된 <li> 개수 : ', document.querySelectorAll('#placesList .item').length);
            console.log('총 마커 객체 수 : ', Object.keys(markers).length);
            console.log(`프레임 분산 방식 적용 후 마커 렌더링 시간 : ${duration.toFixed(2)}ms`);
        }
    }

    // 첫 프레임부터 마커 렌더링을 시작
    // 시스템이 프레임을 그릴 준비가 되면 애니메이션 프레임을 호출하여 애니메이션 웹페이지를 보다 원활하고 효율적으로 생성할 수 있도록 해줌
    // 실제 화면이 갱신되어 표시되는 주기에 따라 함수를 호출해주기 때문에 자바스크립트가 프레임 시작 시 실행되도록 보장해주어 위와 같은 밀림 현상을 방지
    requestAnimationFrame(renderChunk);
}

// 검색결과 항목을 Element로 반환하는 함수입니다
function getListItem(index, places) {
    var el = document.createElement('li'),
        itemStr = '';

    if (places.isDelete == 1) {
        itemStr += '<div class="wtj-icon">' +
            '<a onclick="deletePlace(' + themeNo + ', ' + places.no + ')">' +
            '<img src="/images/delete_icon.png" class="wtj-icon-image">' +
            '</a>' +
            '</div>';
    }

    itemStr += '<a class="wtj-place">' +
        '<span class="markerbg-star"></span>' +
        '<div class="info">' +
        '<span>' + places.name + '</span>' +
        '<span>' +  places.address + '</span>' +
        '<span class="tel">' + places.phone + '</span>' +
        '</div>' +
        '</a>';

    el.innerHTML = itemStr;
    el.id = places.no;
    el.className = 'item';

    return el;
}

// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
function addMarker(position, placeNo) {
    // 마커 이미지의 이미지 주소입니다
    var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png",
        // 마커 이미지의 이미지 크기 입니다
        imageSize = new kakao.maps.Size(24, 35),
        // 마커 이미지를 생성합니다
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize),
        // 마커를 생성합니다
        marker = new kakao.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage
        });

    marker.setMap(map); // 지도 위에 마커를 표출합니다
    markers[placeNo] = marker;  // 배열에 생성된 마커를 추가합니다

    return marker;
}

// 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
// 인포윈도우에 장소명을 표시합니다
function displayInfowindow(marker, title) {
    var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';

    infowindow.setContent(content);
    infowindow.open(map, marker);
}
