const apiKey ="AAPK41e2df5b09f24f1fb7d09a8b29b71552G6LcxcbxNpObN-BHLepUcB-LQK2vm5y0Op9ijhvsFjztrUO4ocBEP5z8s1Xsz0iy";
let myRenderer = L.canvas({ padding: 0.5 });
let map = L.map('map', {
    center: [55.75, 37.61],//Moscow
    zoom: 12
});

let carIcon = L.icon({
    iconUrl: '../images/map_car.png',
    // shadowUrl: 'leaf-shadow.png',

    iconSize:     [32, 37], // size of the icon
    //shadowSize:   [50, 64], // size of the shadow
    iconAnchor:   [22, 94], // point of the icon which will correspond to marker's location
    //shadowAnchor: [4, 62],  // the same for the shadow
    popupAnchor:  [-3, -76] // point from which the popup should open relative to the iconAnchor
});
L.esri.basemapLayer('Streets').addTo(map);
const geocoder = L.esri.Geocoding.geocodeService({
    apikey: apiKey
});

/*let mapModal = L.map('map2', {
    center: [55.75, 37.61],//Moscow
    zoom: 12
});

L.esri.basemapLayer('Streets').addTo(mapModal);
const geocoder = L.esri.Geocoding.geocodeService({
    apikey: apiKey
});*/

function loadingaddressesOfAds(){
    $.get("http://localhost:8080/api/get_addresses",function (response){
        let addresses = response;
        for(let i = 0; i<addresses.length;i++){
            //"AAPKef2fa5e336fd40bc8ef6e1d9cfa5f6aeuxjzVTNKmXB3tv9yJCHj-33w83iWQwWZEGFOVTvPyycgbZ1z7TthcCe-tiEgEtFs"}).city("Moscow").address(addresses[i][1])
                //geocoder.geocode().city("Moscow").text(addresses[i][1]).run(function (err, response) {//.forStorage(false)
            L.esri.Geocoding.geocode({apikey: apiKey}).
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
        icon: carIcon,
        renderer: myRenderer
    }).addTo(map).bindPopup('<a href='+link+'>Посмотреть Объявление</a>',id);

    /*L.marker([array[0].latlng.lat,array[0].latlng.lng], {
        icon: carIcon,
        renderer: myRenderer
    }).addTo(mapModal).bindPopup('<a href='+link+'>Посмотреть Объявление</a>',id);*/

   //  let marker = L.marker([array[0].latlng.lat,array[0].latlng.lng]).addTo(map);
   // marker.bindPopup("<b>Hello world!</b><br>I am a popup.\n"+array[0].latlng.toString()).openPopup();
}

loadingaddressesOfAds();
