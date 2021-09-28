let btn;
let url;
let favIcon;
function changeAction(id) {
    btn = document.getElementById("favouriteButton"+id);
    $.ajax({
        url: "http://localhost:8080/ads/" + id + "/favourite",
        type: "post",
        cache: false
    }).done(function (data) {
            drawButton(id);
        }
    );
}

function drawButton(id) {
    btn = document.getElementById("favouriteButton"+id);
    url = "http://localhost:8080/ads/" + id + "/getButton";
    favIcon = document.getElementById("favourite-icon");
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

drawButton(id);
