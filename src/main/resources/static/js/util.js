$(document).ready(
    function () {
        $('.kv-ltr-theme-fas-star').rating({
            theme: 'krajee-fas'
        });
    });

var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl)
})

let btn = document.getElementById("btn-compare");
$('#favourite').submit(function (e) {
    btn.title = "Убрать из сравнения";
    e.preventDefault();
    $.ajax({
        url: $(this).attr('action'),
        type: $(this).attr('method'),
        cache: false,
        processData: false
    }).done(function (data) {
        console.log(data);
    })
})
