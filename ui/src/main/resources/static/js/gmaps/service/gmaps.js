/*
 Following are the functionalities completed till now :
 Loading maps through angular
 Allow no more than two markers
 Show route between markers
 Remove route along with markers.
 following are remaining:
 delete source marker
 delete destination marker
 toggle between src and dst markers
 */

"use strict";
console.log("gmaps Script loaded");

app.service('mapService', function($window,$rootScope,$timeout,Map,mapdisplaySER ){
	var map;
	//Initializer.mapsInitialized.then(function(){  Uncomment this line for lazy google maps init
        var myLatLng =  new google.maps.LatLng(51.817116, 4.780616);
        var mapOptions = {
            zoom: 17,
            center: myLatLng,
            mapTypeId: google.maps.MapTypeId.ROADMAP,
            panControl: true,
            zoomControl: true,
            mapTypeControl: true,
            scaleControl: false,
            streetViewControl: false,
            navigationControl: true,
            disableDefaultUI: true,
            overviewMapControl: true
        };
        map = Map.initMap("map-canvas4");
        
      
        map.controls[google.maps.ControlPosition.TOP_RIGHT].push(document.getElementById("mapclose"));
        map.controls[google.maps.ControlPosition.TOP_LEFT].push(document.getElementById("mapnavbar"));
        
        $('#myMapModal').on('show.bs.modal', function (e,$window) {
        	 if (google.maps && map) {
                 setTimeout(mapdisplaySER.resizeMap(map), 300);
             } else {
            	alert("Lazy loading Google map...");                          
             }         	
        	  
        	});
        
      
    //});   Uncomment this line for lazy google maps init
	
	this.map = map;
    this.markers = [];
	
});



app.service('infoService', function(){


    // http://stackoverflow.com/questions/5634991/styling-google-maps-infowindow
    // http://google-maps-utility-library-v3.googlecode.com/svn/trunk/infobubble/examples/example.html

    var ctxSTP = document.createElement("div");
    var pop_up_info = "border-radius: 25px; border: 2px solid #73AD21;padding: 10px; width: 200px;  background:#333; color:#FFF;border-bottom-left-radius: 0;"
    ctxSTP.style.cssText = pop_up_info;

    ctxSTP.innerHTML = '<div class="infobox-wrapper">'+
        '<div id="infoboxA">' +
        ' This is Source Marker.'
    '</div>' +
    '</div>';

//Sets up the configuration options of the pop-up info box.

    this.stpInfowin = new InfoBox({
        content:ctxSTP,
        disableAutoPan: false,
        maxWidth: 150,
        alignBottom : true,
        pixelOffset: new google.maps.Size(10, -30),
        zIndex: null,
        boxStyle: {
            background: "#7777",
            opacity: 1,
            width: "280px"
        },
        closeBoxMargin: "-5px 50px -5px -5px",
        closeBoxURL: "http://www.google.com/intl/en_us/mapfiles/close.gif",
        infoBoxClearance: new google.maps.Size(2, 2)
    });

    var ctxDSTP = document.createElement("div");
    var pop_up_info = "border-radius: 25px; border: 2px solid #73AD21;padding: 10px; width: 200px;  background:#333; color:#FFF;border-bottom-left-radius: 0;"
    ctxDSTP.style.cssText = pop_up_info;

    ctxDSTP.innerHTML = '<div class="infobox-wrapper">'+
        '<div id="infoboxA">' +
        ' This is Destination Marker.'
    '</div>' +
    '</div>';

   this.dstpInfowin =  new InfoBox({
       content:ctxDSTP,
       disableAutoPan: false,
       maxWidth: 150,
       alignBottom : true,
       pixelOffset: new google.maps.Size(10, -30),
       zIndex: null,
       boxStyle: {
           background: "#7777",
           opacity: 1,
           width: "280px"
       },
       closeBoxMargin: "-5px 50px -5px -5px",
       closeBoxURL: "http://www.google.com/intl/en_us/mapfiles/close.gif",
       infoBoxClearance: new google.maps.Size(2, 2)
   });


});

app.service('dirService', function(mapService) {
	
    	
    this.directionsService = new google.maps.DirectionsService();
    this.routeservice = new google.maps.DirectionsService();
    var stepDisplay = new google.maps.InfoWindow();
    this.request = {};
    this.rendererOptions = {
        map: mapService.map
    }


    var path = new google.maps.MVCArray();
    var directionsDisplay = new google.maps.DirectionsRenderer(this.rendererOptions);
    var markerArray = [];

    this.getPath = function(request,mrkrSTP,mrkrDSTP ){
        this.request = request;
        if(mrkrSTP != null && mrkrDSTP != null) {
            console.log( "if checked 2nd TRUE");

        /*To add direction arrows below is the example
        http://jsfiddle.net/doktormolle/9gJjj/*/

            this.directionsService.route(this.request, function(response, status) {
                if (status == google.maps.DirectionsStatus.OK) {
                    console.log("warnings: "+ response.routes[0].warnings );
                    directionsDisplay.setDirections(response);
                    directionsDisplay.setMap(mapService.map);
                    showSteps(response);
                }
            });

            this.routeservice.route(this.request,function (response, status) {
                if (status == google.maps.DirectionsStatus.OK) {
                    for (var i = 0, len = response.routes[0].overview_path.length; i < len; i++) {
                        path.push(response.routes[0].overview_path[i]);
                    }
                }
            });
        }
    }


    this.clearallDir = function(){
        console.log("Clear request recieved");
        directionsDisplay.setMap(null);

        for (var i = 0; i < markerArray.length; i++) {
            markerArray[i].setMap(null);
        }
        for (var i = 0; i <  mapService.markers.length; i++) {
            mapService.markers[i].setMap(null);
        }
        for (var i = 0; i == path.length; i++) {
            path.pop();
        }
        markerArray=[];
        mapService.markers=[];
    }

    function showSteps(directionResult) {
        // For each step, place a marker, and add the text to the marker's
        // info window. Also attach the marker to an array so we
        // can keep track of it and remove it when calculating new
        // routes.
        // For Icon path go to github rep of google material Icons and show it in RAW.
        // https://design.google.com/icons/
        // http://leafletjs.com/examples/quick-start.html
        var myRoute = directionResult.routes[0].legs[0];
        var dirsymbol = {
            path: 'M 21.71 11.29l-9-9c-.39-.39-1.02-.39-1.41 0l-9 9c-.39.39-.39 1.02 0 1.41l9 9c.39.39 1.02.39 1.41 0l9-9c.39-.38.39-1.01 0-1.41zM14 14.5V12h-4v3H8v-4c0-.55.45-1 1-1h5V7.5l3.5 3.5-3.5 3.5 z',
            fillColor: 'yellow',
            fillOpacity: 0.8,
            scale: 1,
            strokeColor: 'blue',
            inlineBoxAlign:true,
            strokeWeight: 1
        };

        for (var i = 0; i < myRoute.steps.length; i++) {
            var marker = new google.maps.Marker({
                icon:dirsymbol,
                position: myRoute.steps[i].start_location,
                map: mapService.map
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
            stepDisplay.open(mapService.map, marker);
        });
    }

});

app.service('delService', function(dirService,markerFactory){


    this.clearALL= function(){
        console.log("Delete btn hit-clrall!!");
        dirService.clearallDir();
        markerFactory.stp.setMap(null);
        markerFactory.dstp.setMap(null);
        markerFactory.stp = null;
        markerFactory.dstp = null;
    }
    this.clearSTP= function(){
        dirService.clearallDir();
        markerFactory.stp.setMap(null);
        markerFactory.stp = null;
    }
    this.clearDSTP= function(){
        dirService.clearallDir();
        markerFactory.dstp.setMap(null);
        markerFactory.dstp = null;
    }
});

app.factory('markerFactory', function(mapService , dirService ,infoService ,$mdIcon,$rootScope,placeService){

    var markerFactory = {}; // constructor for factory (** Not required for service)

    /* stp = start point for travel
     dstp = destination point for travel
     */
  
    
    markerFactory.stp = null;
    markerFactory.dstp = null;

    var labels = 'SD';
    var labelIndex = 0;
    var pin = {
        path: 'M 12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zm0 9.5c-1.38 0-2.5-1.12-2.5-2.5s1.12-2.5 2.5-2.5 2.5 1.12 2.5 2.5-1.12 2.5-2.5 2.5 z',
        strokeColor: 'blue',
        scale: 1.5,
        strokeWeight: 2,
        origin: new google.maps.Point(0, 0),
        anchor: new google.maps.Point(10, 20),
        fillColor: 'yellow',
        fillOpacity: 1,

    };
    //http://kml4earth.appspot.com/icons.html
    markerFactory.getstpMrkr = function() { return markerFactory.stp; };
    markerFactory.getdstpMrkr = function() { return markerFactory.dstp; };
    
    markerFactory.setstpMrkr = function(mrkr) {
    	
    	markerFactory.stp=mrkr;
    	};
    markerFactory.setdstpMrkr = function(mrkr) {
    	markerFactory.dstp=mrkr;
    	};
    

    
    markerFactory.pushMarker2 = function(mark){
        /* working var image = new google.maps.MarkerImage('https://raw.githubusercontent.com/google/material-design-icons/master/maps/2x_web/ic_place_black_36dp.png', null, null, null, new google.maps.Size(40,52)); // Create a variable for our marker image.
*/

        var marker = new google.maps.Marker({
            icon : pin,
            map: mapService.map,
        });
        marker.setPosition(mark.latLng);

        if( markerFactory.stp === null && markerFactory.dstp === null){
            console.log("setting stp mrkr1");
            markerFactory.stp =  marker;
        }
        else if (markerFactory.stp != null && markerFactory.dstp === null){
            console.log("setting dstp mrkr2");
            markerFactory.dstp = marker;
        }
        else if (markerFactory.stp === null && markerFactory.dstp != null){
            console.log("setting stp mrkr2");
            markerFactory.stp = marker;
        }

        if (markerFactory.stp != null ) {
            google.maps.event.addListener(markerFactory.stp, 'click', function () {
                infoService.stpInfowin.open(mapService.map, markerFactory.stp);
            });
        }
        if ( markerFactory.dstp != null) {
            google.maps.event.addListener(markerFactory.dstp, 'click', function () {
                infoService.dstpInfowin.open(mapService.map, markerFactory.dstp);
            });
        }
    }
    
   

    markerFactory.listener = google.maps.event.addListener(mapService.map, 'click', function(event){
        if( markerFactory.stp === null || markerFactory.dstp === null) {
            markerFactory.pushMarker2(event);
        }
        if( markerFactory.stp != null && markerFactory.dstp != null) {
            console.log("mapfac if is true");
            var request = {
                origin:  markerFactory.stp.getPosition() ,
                destination:  markerFactory.dstp.getPosition() ,
                travelMode: google.maps.DirectionsTravelMode.DRIVING
            };
            dirService.getPath(request,markerFactory.stp,markerFactory.dstp);

        }


    });

    /*fac.users = ['John', 'James', 'Jake'];*/

    return markerFactory;

});

app.controller('MapCtrl', function($scope, mapService,dirService,delService ,markerFactory, popupSER) {

    /*  $scope.map = mapService.map;*/

    $scope.deleteAll = function(){
        console.log("Delete btn hit!!");
        if(  markerFactory.stp != null && markerFactory.dstp != null){
            delService.clearALL();
        }
    }
    $scope.deleteSTP = function(){
        console.log("Delete btn hit!!");
        if(  markerFactory.stp != null ){
            delService.clearSTP();
        }
    }
    $scope.deleteDSTP = function(){
        console.log("Delete btn hit!!");
        if(  markerFactory.dstp != null){
            delService.clearDSTP();
        }
    }


})/*.config(function($mdIconProvider) {
    // Register icon IDs with sources. Future $mdIcon( <id> ) lookups
    // will load by url and retrieve the data via the $http and $templateCache
    $mdIconProvider
        .iconSet('core', 'https://material.angularjs.org/latest/img/icons/sets/core-icons.svg',24)
        .icon('social:cake', 'https://material.angularjs.org/latest/img/icons/cake.svg',24)
        .icon('android', 'https://material.angularjs.org/latest/img/icons/android.svg',24);
})
    .run(function($http, $templateCache) {
        var urls = [
            'https://material.angularjs.org/latest/img/icons/sets/core-icons.svg',
            'https://material.angularjs.org/latest/img/icons/cake.svg',
            'https://material.angularjs.org/latest/img/icons/android.svg'
        ];
        // Pre-fetch icons sources by URL and cache in the $templateCache...
        // subsequent $http calls will look there first.
        angular.forEach(urls, function(url) {
            $http.get(url, {cache: $templateCache});
        });
    })*/
;