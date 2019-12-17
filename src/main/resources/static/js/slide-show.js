function cuicleImgs(n) {
    let images = document.getElementsByClassName('show-offer');
    if (currentImg > images.length) currentImg = 1;
    if (currentImg < 1) currentImg = images.length;

    for (let i = 0; i < images.length; i++) {
        images[i].style.opacity = '0';
    }

    images[currentImg - 1].style.opacity = '1';

    try {
        clearInterval(autoSlide)
        autoSlide = setInterval(() => { nextImg(+1); }, 3000)
    } catch (e) { console.log(e.toString()) }
}

function prevImg(n) { currentImg += n; cuicleImgs(currentImg)}
function nextImg(n) { currentImg += n; cuicleImgs(currentImg)}

let currentImg = 1;
cuicleImgs(currentImg);

let autoSlide = setInterval(nextImg(+1), 3000);