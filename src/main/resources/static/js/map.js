let myRenderer = L.canvas({ padding: 0.5 });
let map = L.map('map', {
    center: [55.75, 37.61],//Moscow
    zoom: 12
});
L.esri.basemapLayer('Streets').addTo(map);

function loadingaddressesOfAds(){
    $.get("http://localhost:8080/api/get_addresses",function (response){
        let addresses = response;
        for(let i = 0; i<addresses.length;i++){
            L.esri.Geocoding.geocode({apikey: "AAPKef2fa5e336fd40bc8ef6e1d9cfa5f6aeuxjzVTNKmXB3tv9yJCHj-33w83iWQwWZEGFOVTvPyycgbZ1z7TthcCe-tiEgEtFs"}).
                city("Moscow").address(addresses[i][1]).run(function (err, response) {
                if (err) {
                    console.log(err);
                    return;
                }
                addingPopupsOnMap(response,addresses[i][0]);
            });
        }
    });
}
function addingPopupsOnMap(resp,id){
    let array = resp['results'];//55.75, 37.61
    let link = "/ads/"+id;
    L.marker([array[0].latlng.lat,array[0].latlng.lng], {
        renderer: myRenderer
    }).addTo(map).bindPopup('<a href='+link+'>Посмотреть Объявление</a>',id);
    // let marker = L.marker([array[0].latlng.lat,array[0].latlng.lng]).addTo(map);
    // marker.bindPopup("<b>Hello world!</b><br>I am a popup.\n"+array[0].latlng.toString()).openPopup();
}

loadingaddressesOfAds();