window.onunload = function () {
    sessionStorage.setItem("brand", document.getElementById("brandSelect").value);
    sessionStorage.setItem("model", document.getElementById("modelSelect").value);
    sessionStorage.setItem("yearStart", document.getElementById("yearStartSelect").value);
    sessionStorage.setItem("yearEnd", document.getElementById("yearEndSelect").value);
    sessionStorage.setItem("priceStart", document.getElementById("priceStartSelect").value);
    sessionStorage.setItem("priceEnd", document.getElementById("priceEndSelect").value);
    sessionStorage.setItem("ownersCount", document.getElementById("ownersSelect").value);
}

window.onload = function () {
    let brand = sessionStorage.getItem("brand");
    let model = sessionStorage.getItem("model");
    let yearStart = sessionStorage.getItem("yearStart");
    let yearEnd = sessionStorage.getItem("yearEnd");
    let priceStart = sessionStorage.getItem("priceStart");
    let priceEnd = sessionStorage.getItem("priceEnd");
    let ownersCount = sessionStorage.getItem("ownersCount");

    if (brand) $('#brandSelect').val(brand);
    if (model) $('#modelSelect').val(model);
    if (yearStart) $('#yearStartSelect').val(yearStart);
    if (yearEnd) $('#yearEndSelect').val(yearEnd);
    if (priceStart) $('#priceStartSelect').val(priceStart);
    if (priceEnd) $('#priceEndSelect').val(priceEnd);
    if (ownersCount) $('#ownersSelect').val(ownersCount);
}
