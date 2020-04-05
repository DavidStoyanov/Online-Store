(function($) {
    const photosWrapper = $('.all-photos');
    const smallPhotos = $('.photo.small-photo');
    const largePhotos = $('.photo.large-photo');

    const mousePos = {};

    photosWrapper.mousemove(function (event) {
        mousePos.x = event.clientX;
        mousePos.y = event.clientY;
    });

    smallPhotos.mouseenter(onMouseEnter);
    photosWrapper.scroll(onScroll);

    function onMouseEnter() {
        const id = $(this).prop('id');
        findLargePhotoId(id);
    }

    function onScroll() {
        if (mousePos.x && mousePos.y) {
            const elementBelow = document.elementFromPoint(mousePos.x, mousePos.y);
            const parent = $(elementBelow).parent();
            if (!parent.hasClass('photo')) return;
            const id = parent.prop('id');
            findLargePhotoId(id);
        }
    }

    function findLargePhotoId(smallPhotoId) {
        const smallPhotoNum = smallPhotoId.replace( /^\D+/g, '');
        const largePhotoId = `#photo${smallPhotoNum}`;
        showPhoto(largePhotoId)
    }

    function showPhoto(photoId) {
        largePhotos.css('opacity', 0);
        $(photoId).css('opacity', 1);
    }
}(jQuery));