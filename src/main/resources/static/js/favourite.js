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
    let url="http://localhost:8080/ads/"+id+"/getButton";
    $.get(url, function (response){
        if(response==true){
            btn.innerHTML="Убрать из избранного";
        }
        else btn.innerHTML="Добавить в избранное";
    });
}

drawButton();
