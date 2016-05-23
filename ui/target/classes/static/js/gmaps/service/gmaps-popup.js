"use strict";
console.log("Script loaded");

app.service('mapdisplaySER',[ '$document','$window', function($document, $window){
	console.log("Script loaded 2");
	console.log($document[0].title);

	 this.resizeMap = function(map) {
         console.log("resizemap fired");
         if(typeof map =="undefined") return;
         setTimeout( function(){resizingMap(map);} , 400);
     }

     function resizingMap(map) {
         console.log("resizingmap fired");
         if(typeof map =="undefined") return;
         var center = map.getCenter();
         document.getElementById("map-canvas4").style.visibility="visible";
         document.getElementById("map-canvas4").style.display="block";
         google.maps.event.trigger(map, "resize");
         map.setCenter(center);
     }
	
	
	
	
		
}]);