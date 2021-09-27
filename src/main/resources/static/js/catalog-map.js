let myRenderer = L.canvas({ padding: 0.5 });
let map = L.map('map', {
    center: [55.75, 37.61],//Moscow
    zoom: 11
});

let map_modal = L.map('map-modal', {
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
L.esri.basemapLayer('Streets').addTo(map_modal);

function openModal(){
    $('#map-modal').on('shown.bs.modal', function(event) {
        print("123");
        map_modal.invalidateSize();
        print("321");
    });
}


function loadingaddressesOfAds(){
    $.get("http://localhost:8080/api/get_addresses",function (response){
        let addresses = response;
        for(let i = 0; i<addresses.length;i++){
            L.esri.Geocoding.geocode().
            city("Moscow").address(addresses[i][1]).run(function (err, response) {
                if (err) {
                    console.log(err);
                    return;
                }
                addingPopupsOnMap(response,addresses[i][0], addresses[i][2],addresses[i][3]);
            });
        }
    });
}
function addingPopupsOnMap(resp,id,name, price){
    let array = resp['results'];//55.75, 37.61
    let link = "/ads/"+id;
    $.get("http://localhost:8080/api/get_transport_name/"+name,function (response){
        let fullName = response;
        L.marker([array[0].latlng.lat,array[0].latlng.lng], {
            icon: carIcon,
            renderer: myRenderer
        }).addTo(map).bindPopup('<p>Машина: '+fullName+'</p><p>Цена: '+price+' рублей</p><a href='+link+'>Посмотреть Объявление</a>',id);
        L.marker([array[0].latlng.lat,array[0].latlng.lng], {
            icon: carIcon,
            renderer: myRenderer
        }).addTo(map_modal).bindPopup('<p>Машина: '+fullName+'</p><p>Цена: '+price+' рублей</p><a href='+link+'>Посмотреть Объявление</a>',id);
    })
    //  let marker = L.marker([array[0].latlng.lat,array[0].latlng.lng]).addTo(map);
    // marker.bindPopup("<b>Hello world!</b><br>I am a popup.\n"+array[0].latlng.toString()).openPopup();
}

loadingaddressesOfAds();