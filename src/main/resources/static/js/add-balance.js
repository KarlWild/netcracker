function addBalance() {
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/ads/" + id + "/addComparison",
    })
        .done(function () {
            drawCompare();
        });
}

function drawCompare() {
    let compareButton = document.getElementById("btn-compare");
    let url = "http://localhost:8080/ads/" + id + "/getComparison";
    $.get(url, function (response) {
        if (response === true) {
            compareButton.style["filter"] = "invert(8%) sepia(100%) saturate(7195%) hue-rotate(14deg) brightness(102%) contrast(105%)";
            compareButton.title = "Убрать из сравнения";
        } else {
            compareButton.title = "Добавить в сравнения";
            compareButton.style["filter"] = "none";
        }
    });
}

drawCompare();
