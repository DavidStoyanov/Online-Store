const URLS = {
    upload: '/api/photo/upload',
};

const upload = (formData, readyCallBack) => {
    fetch(URLS.upload, {
        method: 'POST',
        body: formData
    })
    .then((response) => response.json())
    .then((result) => {
        readyCallBack(result);
    })
    .catch((error) => {
        console.error('Error:', error);
    });
};

$(() => {
    const input = $('#photosInput');
    const photoContainer = $('.all-photos');
    const photosData = $('#photos');
    const allPhotoSet = new Set();

    $('#addPhoto').on('click', () => input.click());

    function refreshPhotos() {
        const result = [];

        for (let photoUrl of allPhotoSet) {
            let photo = {url: photoUrl};
            result.push(photo);
        }

        photosData.val(JSON.stringify(result));
    }

    function generatePhotoSection(photoUrl) {
        const url = 'https://p-sf1.pcloud.com' + photoUrl;

        const imageBox = $('<seciton>')
            .addClass('photo-box');

        const image = $('<img>')
            .attr('id', 'blah')
            .attr('src', url)
            .attr('alt', 'offer-photo')
            .appendTo(imageBox);

        return imageBox;
    }

    input.on('change', function () {
        let formData = new FormData();
        formData.append('file', this.files[0]);
        upload(formData, data => {
            allPhotoSet.add(data[0].url);
            refreshPhotos();

            let section = generatePhotoSection(data[0].url)[0];
            $('#addPhoto').before(section);
        });
    });

});