$( document ).on( "pagecreate", function() {


    $( ".map.win" ).width($( window ).width()-20);
    $( ".map.win" ).height($( window ).height()-20);



    $('#myMapModal').on('show.bs.modal', function (e) {
        console.log("modal showup fired")
        setTimeout(resizeMap(), 300);
    })

  


});
//making modal responsive
$(window).resize(function() {
    console.log("Win Height"+$(window).height()+"Win Width"+$(window).width());
    $( ".map.win" ).width($( window ).width()-20);
    $( ".map.win" ).height($( window ).height()-20);
    /*$('body').prepend('<div>' + $(window).width() + '</div>');*/
});

var src = 0,trg = 0;
var map,markercount= 0,markers = [], lat_lng = [],srclat_lng=0,destlat_lng=0;
var srcmarker = new google.maps.Marker();
var destmarker = new google.maps.Marker();
var infowindow1 = new google.maps.InfoWindow();
var infowindow2 = new google.maps.InfoWindow();
var markerArray = [];
var poly = new google.maps.Polyline({ strokeColor: '#4986E7' });
var path = new google.maps.MVCArray();
var rendererOptions = {
    map: map
}
var directionsDisplay = new google.maps.DirectionsRenderer(rendererOptions);
var myCenter=new google.maps.LatLng(53, -1.33);
var marker=new google.maps.Marker({
    position:myCenter
});


google.maps.event.addDomListener(window, 'load', initialize);

google.maps.event.addDomListener(window, "resize", resizingMap());

function resizeMap() {
    console.log("resizemap fired");
    if(typeof map =="undefined") return;
    setTimeout( function(){resizingMap();} , 400);
}

function resizingMap() {
    console.log("resizingmap fired");
    if(typeof map =="undefined") return;
    var center = map.getCenter();
    document.getElementById("map-canvas").style.visibility="visible";
    document.getElementById("map-canvas").style.display="block";
    google.maps.event.trigger(map, "resize");
    map.setCenter(center);
}
/*init*/
function initialize() {

    getLocation();

     directionsService = new google.maps.DirectionsService();
     routeservice = new google.maps.DirectionsService();

    var rendererOptions = {
        map: map
    }
    directionsDisplay = new google.maps.DirectionsRenderer(rendererOptions)

    // Instantiate an info window to hold step text.
    stepDisplay = new google.maps.InfoWindow();
}

function getLocation(){
    if(navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(successp, error);
    } else {
        // default location
        console.log("firing navigator else")

    }
}



function successp(position){
    var latlng2;
    if(!position ){

        latlng2 = new google.maps.LatLng(53, -1.33);

    }else{
        console.log(position);
        latlng2 = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
    }

    options2 = { zoom: 15, center: latlng2,disableDefaultUI:true, mapTypeId: google.maps.MapTypeId.ROADMAP };

    map=new google.maps.Map(document.getElementById("map-canvas"), options2);
    var centerControlDiv = document.getElementById("mapclose");
    map.controls[google.maps.ControlPosition.TOP_RIGHT].push(centerControlDiv);
    var centerControlDiv2 = document.getElementById("mapnavbar");
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(centerControlDiv2);
    google.maps.event.addListener(map, 'click', function(event) {


        if(markercount>1){

            //alert("Only two markers allowed");
            markercount=2;
            return;
            event.preventDefault();
        }
        else
        {

           /* if(src===0){placeMarker(event.latLng);}
            else if (trg===0){placeMarker(event.latLng);}*/

            placeMarker(event.latLng);

            /*placeMarker(event.latLng);*/
            console.log("pre "+markercount);
            markercount = markercount+1;
            console.log("post "+markercount);
            lat_lng [markercount] = event.latLng;
            if( markercount==2 ){
                console.log("markercount from inside if"+markercount);
              /*  drawRoute();*/
                calcRoute();
                path = []
                 path = new google.maps.MVCArray();
                poly.setPath(path);
                poly.setMap(map);

            }
        }
    });

}



function error(msg){

    if (msg.code == 1) {
        //PERMISSION_DENIED
        successp();
    } else if (msg.code == 2) {
        //POSITION_UNAVAILABLE
    } else {
    }   //TIMEOUT
}


    function placeMarker(location) {

        var src_trg, contentString1,contentString2 ;

        if(src===0){
            src=1;
            src_trg ="Source";
            srclat_lng = location;
            srcmarker.setPosition(location);
            srcmarker.setMap(map);
            google.maps.event.addListener(srcmarker, 'click', function() {
                infowindow1.open(map,srcmarker);
            })
             contentString1 = '<div id="container" style="width: 150px;color: #ffffff;background-color: crimson;font-size:10px;font-family:Arial, Helvetica, sans-serif;" >'+'<h5>'+src_trg+'</h5>'+'Latitude: ' + location.lat() + '<br></br> Longitude: ' + location.lng()+
                '<div id="content">'+'<button class="btn-xs" style="color: green;font-weight: bold" href="#">'+'Dragable'+'</button>'+'<button class="btn-xs" style="color: crimson;font-weight: bold" onclick="removemarker(1)">'+'delete'+'</button>'+
                '</div>'+
                '</div>';
            infowindow1 = new google.maps.InfoWindow({

                content: contentString1
            });

        }
        else if(trg===0){
            trg=1;
            src_trg ="Destination";
            destlat_lng = location;
            destmarker.setPosition(location);
            destmarker.setMap(map);
            google.maps.event.addListener(destmarker, 'click', function() {
                infowindow2.open(map,destmarker);
            })
            contentString2 = '<div id="container" style="width: 150px;color: #ffffff;background-color: crimson;font-size:10px;font-family:Arial, Helvetica, sans-serif;" >'+'<h5>'+src_trg+'</h5>'+'Latitude: ' + location.lat() + '<br></br> Longitude: ' + location.lng()+
                '<div id="content">'+'<button class="btn-xs" style="color: green;font-weight: bold" href="#">'+'Dragable'+'</button>'+'<button class="btn-xs" style="color: crimson;font-weight: bold" onclick="removemarker(2)">'+'delete'+'</button>'+
                '</div>'+
                '</div>';
            infowindow2 = new google.maps.InfoWindow({

                content: contentString2
            });

        }
        console.log("setting marker for markercount : "+markercount);
        viewMarkers();
    }

    function viewMarkers() {
        console.log("src position: " + srcmarker.position);
        for (x in markers) {
            console.log(markers[x].position+" src position: " + srcmarker.position + " dest position: "+ destmarker.position);
            console.log("markers.length : "+markers.length +" lat_lng.length :"+ lat_lng.length )

        }
    }

    function clearall(){
        removemarker(1);
        removemarker(2);
    }
    function removemarker(src_trg){
        /*var markerid=id;*/
        console.log("my src\trg: "+src_trg);
        if(src_trg===1){
            /*delete (srclat_lng);*/
            src = 0;
            srcmarker.setMap(null);
            delete (srcmarker);
            srcmarker === new google.maps.Marker();

        }
        else if (src_trg===2) {
            trg = 0;
            delete (destlat_lng);
            destmarker.setMap(null);
            delete (destmarker);
            destmarker === new google.maps.Marker();
        }

        console.log("lat-long array lenght"+lat_lng.length);
        /*viewMarkers();*/
        markercount=markercount-1;
        //resetting direction markers
        for (var i = 0; i < markerArray.length; i++) {
            markerArray[i].setMap(null);
        }
        directionsService = new google.maps.DirectionsService();
        poly.setPath(new google.maps.MVCArray());
        poly.setMap(null);
        /*drawRoute();*/
    }

function calcRoute() {

    // First, remove any existing markers from the map.
    for (var i = 0; i < markerArray.length; i++) {
        markerArray[i].setMap(null);
    }
    for (var i = 0; i == path.length; i++) {
        path.pop();
    }

    path = []
    path = new google.maps.MVCArray();
    // Now, clear the array itself.
    markerArray = [];

    // Retrieve the start and end locations and create
    // a DirectionsRequest using WALKING directions.
    var request = {
        origin: srclat_lng ,
        destination: destlat_lng,
        travelMode: google.maps.DirectionsTravelMode.DRIVING
    };
  /*  path.push(src);*/

    // Route the directions and pass the response to a
    // function to create markers for each step.
    directionsService.route(request, function(response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
            console.log("warnings: "+ response.routes[0].warnings );
            directionsDisplay.setDirections(response);
            showSteps(response);
        }
    });

    routeservice.route(request,function (response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
            for (var i = 0, len = response.routes[0].overview_path.length; i < len; i++) {
                path.push(response.routes[0].overview_path[i]);
            }
        }
    });

}

function showSteps(directionResult) {
    // For each step, place a marker, and add the text to the marker's
    // info window. Also attach the marker to an array so we
    // can keep track of it and remove it when calculating new
    // routes.
    var myRoute = directionResult.routes[0].legs[0];

    for (var i = 0; i < myRoute.steps.length; i++) {
        var marker = new google.maps.Marker({
            position: myRoute.steps[i].start_location,
            map: map
        });
        attachInstructionText(marker, myRoute.steps[i].instructions);
        markerArray[i] = marker;
    }
}

function attachInstructionText(marker, text) {
    google.maps.event.addListener(marker, 'click', function() {
        // Open an info window when the marker is clicked on,
        // containing the text of the step.
        stepDisplay.setContent(text);
        stepDisplay.open(map, marker);
    });
}



