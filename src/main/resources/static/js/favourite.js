function changeAction() {
    let btn = document.getElementById("favouriteButton");
    $.ajax({
        url: "http://localhost:8080/ads/" + id + "/favourite",
        type: "post",
        cache: false
    }).done(function (data) {
            drawButton();
        }
    );
}

function drawButton() {
    let btn = document.getElementById("favouriteButton");
    let url = "http://localhost:8080/ads/" + id + "/getButton";
    let favIcon = document.getElementById("favourite-icon");
    $.get(url, function (response) {
        if (response == true) {
            btn.style["filter"] = "invert(8%) sepia(100%) saturate(7195%) hue-rotate(14deg) brightness(102%) contrast(105%)";
            btn.title = "Убрать из избранного";
            favIcon.src = "/images/heart-solid.svg";
        } else {
            btn.style["filter"] = "none";
            btn.title = "Добавить в избранное";
            favIcon.src = "/images/heart-regular.svg";
        }
    });
}

drawButton();
