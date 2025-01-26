function showPlaceModal(title, id, address, phone, x, y) {
    let placeModal = document.getElementById('placeModal');
    placeModal.style.display = '';

    document.getElementById('placeName').innerText = title;
    document.getElementById('placeAddress').innerText = address;

    document.getElementById('name').value = title;
    document.getElementById('no').value = id;
    document.getElementById('address').value = address;
    document.getElementById('phone').value = phone;
    document.getElementById('x').value = x;
    document.getElementById('y').value = y;
}

function addPlace() {
    document.getElementById('searchForm').submit();

    closePlaceModal();
}

function closePlaceModal() {
    document.getElementById('placeModal').style.display = 'none';
}
