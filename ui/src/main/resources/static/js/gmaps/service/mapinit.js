
app.service('GoogleMapsService', GoogleMapsService);


function GoogleMapsService() {

    var initMap = function(mapId){
        return new google.maps.Map( document.getElementById(mapId),{
            center: { lat: 45.279642, lng: 11.652564},
            zoom: 14,
            mapTypeId: google.maps.MapTypeId.TERRAIN,
            streetViewControl: false,
            mapTypeControl: false
        });
    };
    
    var initMapElement = function(mapId){
        return new google.maps.Map( mapId,{
            center: { lat: 48.137273, lng: 11.575251},
            zoom: 12,
            mapTypeId: google.maps.MapTypeId.TERRAIN,
            streetViewControl: false,
            mapTypeControl: false
        });
    };

    var initMapFromPerimeter = function(mapId, ne, sw){
        var perimeter = {};
        if (ne.hasOwnProperty('lat')) {
            perimeter = new google.maps.LatLngBounds(
                new google.maps.LatLng(ne.lat, ne.lng),
                new google.maps.LatLng(sw.lat, sw.lng));
        } else {
            perimeter = new google.maps.LatLngBounds(
                new google.maps.LatLng(ne.latitude, ne.longitude),
                new google.maps.LatLng(sw.latitude, sw.longitude));
        }
        var map = new google.maps.Map( document.getElementById(mapId),{
            center: perimeter.getCenter(),
            mapTypeId: google.maps.MapTypeId.TERRAIN,
            streetViewControl: false,
            mapTypeControl: false
        });
        map.fitBounds(perimeter);
        return map;
    };
    
    var resizeMap = function(_map,$window){
    	var map = _map;
    	var win = $window;
    	
    	 google.maps.event.addDomListener(win, "load", function() {
        	 var center = map.getCenter();
        	 google.maps.event.trigger(map, "resize");
        	 map.setCenter(center); 
        	});
    	 console.log("restting map");
    	 /*return map;*/
    };

    var drawPath = function(map, name, points){
        var lineSymbol = {
            path: 'M 0,-1 0,1',
            strokeOpacity: 1,
            scale: 4
        };
        var gmapPoints = [];
        points.forEach(function(p){
            gmapPoints.push(new google.maps.LatLng(p.latitude, p.longitude));
        });
        drawLine(map, gmapPoints, '#994C00', 0, [{
            icon: lineSymbol,
            offset: '0',
            repeat: '20px'
        }]);
    };

    var drawMarker = function(map, position, icon, draggable, title){
        return new google.maps.Marker({
            position: position,
            icon: icon,
            title: title,
            draggable: draggable,
            map: map
        });
    };



    var getMarkerPosition = function(marker){
        var p = marker.getPosition();
        return {
            lat: p.lat(),
            lng: p.lng()
        };
    };



    var removeMarkerFromMap = function(marker){
        removeComponentFromMap(marker);
    };


    return {
        initMap				: initMap,
        initMapElement		: initMapElement,
        initMapFromPerimeter: initMapFromPerimeter,
        resizeMap			: resizeMap,
        drawPath			: drawPath,

    };
}


// lazy init

app.factory('Initializer', function($window, $q){

    //Google's url for async maps initialization accepting callback function
    var asyncUrl = 'https://maps.googleapis.com/maps/api/js?callback=',
        mapsDefer = $q.defer();

    //Callback function - resolving promise after maps successfully loaded
    $window.googleMapsInitialized = mapsDefer.resolve; // removed ()

    //Async loader
    var asyncLoad = function(asyncUrl, callbackName) {
      var script = document.createElement('script');
      //script.type = 'text/javascript';
      script.src = asyncUrl + callbackName;
      document.body.appendChild(script);
    };
    //Start loading google maps
    asyncLoad(asyncUrl, 'googleMapsInitialized');

    //Usage: Initializer.mapsInitialized.then(callback)
    return {
        mapsInitialized : mapsDefer.promise
    };
})
