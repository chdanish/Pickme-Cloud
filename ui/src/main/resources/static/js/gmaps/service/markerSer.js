
app.service('markerService', GoogleMarkerService);


function GoogleMarkerService() {

    var markers = [];
    
    var encodedpath = "";
    
    var getMarkers = function() {
    	console.log("markers current length: " + markers.length);
    	return markers;
    }
    
    var addMarker = function(coords,map,markerid,color) {
    	console.log("click listner");
        try {
        	 var pin = {
        		        path: 'M 12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zm0 9.5c-1.38 0-2.5-1.12-2.5-2.5s1.12-2.5 2.5-2.5 2.5 1.12 2.5 2.5-1.12 2.5-2.5 2.5 z',
        		        strokeColor: 'blue',
        		        scale: 1.5,
        		        strokeWeight: 2,
        		        origin: new google.maps.Point(0, 0),
        		        anchor: new google.maps.Point(10, 20),
        		        fillColor: color,
        		        fillOpacity: 1,

        		    };
	          var point = new google.maps.LatLng(coords.latitude, coords.longitude);
	          var marker = new google.maps.Marker({
	            map: map,
	            id: markerid ,
	            position: coords.latLng,
	            animation: google.maps.Animation.DROP,
	            icon: pin,
	            zindex: markers.length-1,
	       });
          markers.push(marker);
          console.log("markers length after add1: " + markers.length+" Marker ID: " + 'marker_' + markers.length);
          // markers[markerId] = marker;  cache marker in markers
          return marker;
        } catch (error) {
          console.warn("Map.addMarker()::Failure"+ error);
        }
      }
    
    var secureAddMarker = function(coords,map) {
    	var mrkr;
    	mrkr1=getMarkerById("marker_1");
    	mrkr2=getMarkerById("marker_2");
    	
    	if (mrkr1 && !mrkr2 && markers.length<=1) {
    		 console.log("markers length after add1: " + markers.length);
    		mrkr = addMarker(coords,map,"marker_2","red"); 
    		bindMarkerEvents(mrkr);
    		return mrkr;
			
		} else if (mrkr2 && !mrkr1 && markers.length<=1) {
			 console.log("markers length after add2: " + markers.length);
			mrkr = addMarker(coords,map,"marker_1","yellow");
			bindMarkerEvents(mrkr);
			return mrkr;

		} else if (!mrkr1 && !mrkr2 && markers.length<=1) {
			 console.log("markers length after add3: " + markers.length);
			mrkr = addMarker(coords,map,"marker_1","yellow");
			bindMarkerEvents(mrkr);
			return mrkr;
		}
    	
    	
    	
    }
    
    var removeMarker = function(marker) {
        try {
          var index = markers.indexOf(marker);
          markers.splice(index, 1);
          marker.setMap(null);
          console.warn("markers length after remove: "+markers.length);
        } catch (error) {
          console.warn("Map.removeMarker()::Failure");
        }
      }
    var removeMarkerbyID = function(markerId) {
    	getMarkerById(markerId).setMap(null); // set markers setMap to null to remove it from map
        delete markers[markerId]; // delete marker instance from markers object
    };
    var removeallMarker = function() {
        if (markers) {
            for (i in markers) {
              markers[i].setMap(null);
            }
            markers.length = 0;
          }
        }
    
    var	getMarkerById = function(id) {
        try {
          var marker;
          angular.forEach(markers, function(key){
        	  console.log("key: "+ key.id);
            if (key.id == id) {
              marker = key;
            }
          });
          console.warn("getMarkerById()::ID found: "+ marker.id);
          return marker;
        } catch (error) {
          console.warn("getMarkerById()::Failure" + error);
        }
      }
    
    var bindMarkerEvents = function(marker) {
        google.maps.event.addListener(marker, "rightclick", function (point) {
        	console.log(this.id);
            removeMarker(this); // remove it
        });
    };
    

    return {
    	markers			:	markers,
    	getMarkers		:	getMarkers,
    	addMarker		:	addMarker,
    	secureAddMarker	:	secureAddMarker,
    	removeMarker	:	removeMarker,
    	getMarkerById	:	getMarkerById,
    	bindMarkerEvents:	bindMarkerEvents,
    };
}


