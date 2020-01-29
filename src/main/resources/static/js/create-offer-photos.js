(function ($) {
    const URLS = {
        upload: '/api/photo/upload',
        destroy: '/api/photo/destroy',
    };

    const upload = (data) => {
        return fetch(URLS.upload, {
            method: 'POST',
            body: data
        })
        .then((response) => response.json())
        .then((result) => {
            return result;
        })
        .catch((error) => {
            return error;
        });
    };

    const destroy = (data) => {
        fetch(URLS.destroy, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: serialize(data),
        })
        .then((response) => response.json())
        .then((data) => {
            console.log('Success:', data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
    };

    const serialize = (obj) => {
        return Object.keys(obj).reduce(function (a, k) {
            a.push(k + '=' + encodeURIComponent(obj[k]));
            return a;
        }, []).join('&');
    };


    $(() => {
        const body = $('body');
        const input = $('#photosInput');
        const photoContainer = $('.all-photos');
        const photosData = $('#photos');
        const addPhoto = $('#addPhoto');

        const allPhotoMap = new Map();

        addPhoto.on('click', () => input.click());

        input.on('change', function () {
            const formData = new FormData();
            formData.append('file', this.files[0]);

            const section = $('<seciton>')
                .addClass('box')
                .append(
                    $('<img src="/img/gifs/ajax-loader.gif" alt="loading" title="loading">')
                        .css('transform', 'scale(0.5)')
                );
            addPhoto.before(section);

            upload(formData).then(response => {
                allPhotoMap.set(response[0].id, {
                    id: response[0].id,
                    name: response[0].name,
                    url: response[0].url,
                    format: response[0].format,
                    degree: 0
                });

                const generatedSection = generatePhotoSection(response[0].id);
                section.replaceWith(generatedSection);

                refreshInputData();
            })
            .catch(error => {
                console.warn(error);
                section.remove()
            });

            $(this).val('');
        });

        function refreshInputData() {
            const result = [];

            const photos = photoContainer.find('.box.photo-box');
            for (let i = 0; i < photos.length; i++) {
                const photoId = getPhotoId($(photos[i]));
                const photoData = allPhotoMap.get(photoId);
                photoData['position'] = i + 1;
                result.push(photoData);
            }

            photosData.val(JSON.stringify(result));
        }

        function generatePhotoSection(photoId) {
            const photo = allPhotoMap.get(photoId);

            const image = $('<img>')
                .attr('src', photo.url)
                .attr('alt', 'offer-photo');

            const rotateAnchor = $('<a>')
                .addClass('rotatePhoto')
                .attr('href', '#')
                .attr('title', 'rotate')
                .on('click', photoRotateFunc)
                .append($('<span>').text('rotate'));

            const deleteAnchor = $('<a>')
                .addClass('deletePhoto')
                .attr('href', '#')
                .attr('title', 'delete')
                .on('click', photoDeleteFunc)
                .append($('<span>').text('delete'));

            return $('<seciton>')
                .addClass('box photo-box')
                .attr('data-id', photo.id)
                .append(image)
                .append(rotateAnchor)
                .append(deleteAnchor)
                .on('dragstart', onDragStart)
                .on('mousedown', onMouseDown)
        }

        function photoRotateFunc(event) {
            const parent = $(this).parent();
            const photoId = parent.attr('data-id');
            const photoImg = parent.find('img');

            const degree = getDegrees(allPhotoMap.get(photoId).degree);
            allPhotoMap.get(photoId).degree = degree;
            photoImg.css('transform', `rotate(${degree}deg)`);

            function getDegrees(currentDegrees) {
                const degreeArr = [0, 90, 180, 270];
                const index = degreeArr.indexOf(currentDegrees);
                return (index >= 0 && index < 3) ?
                    degreeArr[index + 1] : 0;
            }

            refreshInputData();

            event.preventDefault();
            event.stopPropagation();
        }

        function photoDeleteFunc(event) {
            const parent = $(this).parent();
            const photoId = parent.attr('data-id');

            destroy(allPhotoMap.get(photoId));
            allPhotoMap.delete(photoId);

            parent.fadeOut(500);
            setTimeout(function () {
                parent.remove();
            }, 500);

            refreshInputData();

            event.preventDefault();
            event.stopPropagation();
        }

        function onDragStart() {
            return false;
        }

        function onMouseDown (event) {
            if(event.target === this || event.target.tagName === 'IMG') {
                const selectedPhoto = $(this);
                const shadowPhoto = $('<seciton>').addClass('box');

                const shiftX = event.pageX - selectedPhoto.position().left;
                const shiftY = event.pageY - selectedPhoto.position().top;

                let currentDroppable = null;
                let photoPosition = getPhotoPos(selectedPhoto);

                selectedPhoto.css('position', 'absolute');
                selectedPhoto.css('z-index', '2000');
                photoContainer.find(`.box.photo-box:eq(${photoPosition})`).before(shadowPhoto);
                body.append(selectedPhoto);

                moveAt(event.pageX, event.pageY);

                function moveAt(pageX, pageY) {
                    selectedPhoto.css('left', pageX - shiftX + 'px');
                    selectedPhoto.css('top', pageY - shiftY + 'px');
                }

                const onMouseMove = function (event) {
                    moveAt(event.pageX, event.pageY);

                    selectedPhoto.hide();
                    let elementBelow = document.elementFromPoint(event.clientX, event.clientY);
                    selectedPhoto.show();

                    if (!elementBelow) return;

                    let droppablePhoto = elementBelow.closest(".box.photo-box");

                    if (currentDroppable !== droppablePhoto) {

                        if (currentDroppable) {
                            leaveDroppable(currentDroppable);
                        }

                        currentDroppable = droppablePhoto;
                        if (currentDroppable) {
                            enterDroppable(currentDroppable);
                        }
                    }

                    function leaveDroppable(currentDroppable) {
                        $(currentDroppable).css('background-color', '');
                    }

                    function enterDroppable(currentDroppable) {
                        $(currentDroppable).css('background-color', '#98F884');
                    }
                };

                document.addEventListener('mousemove', onMouseMove);

                selectedPhoto.get(0).onmouseup = function () {
                    document.removeEventListener('mousemove', onMouseMove);
                    selectedPhoto.get(0).onmouseup = null;

                    photoContainer.find(`.box:eq(${photoPosition})`).before(selectedPhoto);
                    selectedPhoto.css('position', 'relative');
                    selectedPhoto.css('top', '0');
                    selectedPhoto.css('left', '0');
                    selectedPhoto.css('z-index', '0');
                    $(currentDroppable).css('background-color', '');

                    if (currentDroppable) {
                        swapNodes(selectedPhoto.get(0), currentDroppable);
                        refreshInputData();
                    }

                    shadowPhoto.remove();
                    currentDroppable = null;
                };

                event.preventDefault();
                event.stopPropagation();
            }
        }


        function swapNodes(a, b) {
            let aparent = a.parentNode;
            let asibling = a.nextSibling === b ? a : a.nextSibling;
            b.parentNode.insertBefore(a, b);
            aparent.insertBefore(b, asibling);
        }

        function getPhotoPos(selectedPhoto) {
            const allPhotos = photoContainer.find('.box.photo-box');
            for (let i = 0; i < allPhotos.length; i++) {
                if (getPhotoId($(allPhotos[i])) === getPhotoId(selectedPhoto)) {
                    return i;
                }
            }

            return 0;
        }

        function getPhotoId(photoSection) {
            return photoSection.attr('data-id')
        }
    });
}(jQuery));