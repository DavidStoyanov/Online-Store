function checkVisible(elm) {
    var rect = elm.getBoundingClientRect();
    var viewHeight = Math.max(document.documentElement.clientHeight, window.innerHeight);
    return !(rect.bottom < 0 || rect.top - viewHeight >= 0);
}

let goUpBtn = document.getElementById('scroll-to-top');
let inner = document.getElementById('head-inner');

document.addEventListener("scroll", () => {
    goUpBtn.style.display = checkVisible(inner) ? 'none' : 'block'
});