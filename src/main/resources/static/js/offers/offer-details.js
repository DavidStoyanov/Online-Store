var scrollPhotosDown, scrollPhotosUp;

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

    photosWrapper.bind('mousewheel DOMMouseScroll', function(e) {
        var scrollTo = null;

        if (e.type == 'mousewheel') {
            scrollTo = (e.originalEvent.wheelDelta * -1);
        }
        else if (e.type == 'DOMMouseScroll') {
            scrollTo = 40 * e.originalEvent.detail;
        }

        if (scrollTo) {
            e.preventDefault();
            $(this).scrollTop(scrollTo + $(this).scrollTop());
        }
    });



    class ListCounter {
        constructor(counterMove = 0, listSize = 0) {
            this.position = 0;
            this.counterMove = counterMove;
            this.listSize = listSize;
        }

        moveDown = () => {
            this.position += this.counterMove;
            if(this.position >= this.listSize) {
                this.position = this.listSize - 1;
            }
            // this.printPosition()
        };

        moveUp = () => {
            this.position -= this.counterMove;
            if(this.position < 0) {
                this.position = 0;
            }
            // this.printPosition()
        };

        getPosition = () => {
            return this.position;
        };

        printPosition = () => {
            console.log(this.position);
        };
    }

    class ScrollPhotos {
        constructor(photosWrapper, photoList) {
            const counterMove = 3;
            this.photosWrapper = photosWrapper;
            this.photoList = photoList;
            this.counter = new ListCounter(counterMove, photoList.length);
        }

        getCurrentPhoto = () => {
            return this.photoList[this.counter.getPosition()];
        };

        scrollDown = () => {
            this.counter.moveDown();
            const offsetTop = this.getCurrentPhoto().offsetTop;
            this.photosWrapper.get(0).scrollBy(0, offsetTop);
        };

        scrollUp = () => {
            const offsetTop = this.getCurrentPhoto().offsetTop;
            this.counter.moveUp();
            const newOffsetTop = this.getCurrentPhoto().offsetTop;
            this.photosWrapper.get(0).scrollBy(0, newOffsetTop - offsetTop);
        };

    }

    const scrollPhotos = new ScrollPhotos(photosWrapper, smallPhotos);
    scrollPhotosDown = () => scrollPhotos.scrollDown();
    scrollPhotosUp = () => scrollPhotos.scrollUp();

}(jQuery));