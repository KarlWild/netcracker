$(document).ready(
    function () {
        $('.kv-ltr-theme-fas-star').rating({
            theme: 'krajee-fas'
        });
    });

let tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
let tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl)
})

// let btn = document.getElementById("btn-compare");
// $('#favourite').submit(function (e) {
//     btn.title = "Убрать из сравнения";
//     e.preventDefault();
//     $.ajax({
//         url: $(this).attr('action'),
//         type: $(this).attr('method'),
//         cache: false,
//         processData: false
//     }).done(function (data) {
//         console.log(data);
//     })
// })

function compare(id) {
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/ads/" + id + "/addComparison",
    })
        .done(function () {
            drawCompare(id);
        });
}

let compareButton;
function drawCompare(id) {
    compareButton = document.getElementById("btn-compare"+id);
    url = "http://localhost:8080/ads/" + id + "/getComparison";
    $.get(url, function (response) {
        if (response === true) {
            compareButton.style["filter"] = "invert(8%) sepia(100%) saturate(7195%) hue-rotate(14deg) brightness(102%) contrast(105%)";
            compareButton.title = "Убрать из сравнения";
        } else {
            compareButton.style["filter"] = "none";
            compareButton.title = "Добавить в сравнения";
        }
        compareButton.onclick = function (){
            compare(id);
        }
        // compareButton.addEventListener("onclick",compare(id));
    });
}
function initialisationDrawCompare(id){

}
// drawCompare(id);
