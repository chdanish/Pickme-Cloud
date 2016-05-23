"use strict";
/*console.log("gmaps Script loaded");*/

app.service('directionService', function($q){
	
	
	
	var markerArray = [];
	var stepDisplay = new google.maps.InfoWindow();
	var directionsDisplay = new google.maps.DirectionsRenderer();
 
	
	/*========================Promise Direction==================================*/
	/*Get Path from Markers*/
	
	
	 var getPath = function (markers,mymap) {
		 
		 if (markers && markers.length == 2) {
			 
			var request = {
	                origin:  markers[0].getPosition() ,
	                destination:  markers[1].getPosition() ,
	                travelMode: google.maps.DirectionsTravelMode.DRIVING
	            };
			var  directionsService = new google.maps.DirectionsService();
			/*var rendererOptions = {
			        map: mymap
		    }
			var directionsDisplay = new google.maps.DirectionsRenderer(rendererOptions);*/
			var deferred = $q.defer();
			 
			 directionsService.route(request, function(response, status) {
	                if (status == google.maps.DirectionsStatus.OK) {
	                    console.log("warnings: "+ response.routes[0].warnings );
	                    directionsDisplay.setDirections(response);
	                    directionsDisplay.setMap(mymap);
	                    showSteps(response,mymap);
	                   
	                    deferred.resolve(getEncodedPath(response));
	                    
	                } else {
		    	    	console.log('Direction service of google  failed due to: ' + status);
		    	    	return deferred.reject();
				       }	         
	            });
			 
			 return deferred.promise;
			
		} else {
			return deferred.promise;

		}
		 
	 }
	 
	 
	 /*Show Steps and attach instructions*/
	 
	 function showSteps(directionResult,mymap) {
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
	                map: mymap
	            });
	            attachInstructionText(marker, myRoute.steps[i].instructions,mymap);
	            markerArray[i] = marker;
	        }
	    }

	    function attachInstructionText(marker, text,mymap) {
	        google.maps.event.addListener(marker, 'click', function() {
	        	
	            // Open an info window when the marker is clicked on,
	            // containing the text of the step.
	            stepDisplay.setContent(text);
	            stepDisplay.open(mymap, marker);
	        });
	    }
	 
	 
	    /*Display Path*/
	 
	
	   function getEncodedPath(directionResult) {
		   var myRoute = directionResult.routes[0].legs[0];
		   var poly = new google.maps.Polyline({
			    strokeColor: '#000000',
			    strokeOpacity: 1,
			    strokeWeight: 3,
			  });
		   var mypath = poly.getPath();
		   
		   for (var i = 0; i < myRoute.steps.length; i++) {
			   mypath.push(myRoute.steps[i].start_location);
	        }
		    
	    	var encodeString = google.maps.geometry.encoding.encodePath(mypath);
	    	 console.log("My encoded path: is"+encodeString);
	    	return encodeString;
	    }

	
	/*==========================================================*/
	    // Remove Direction from display
	    
	    var clearDir = function(){
	        console.log("Clear request recieved");
	        directionsDisplay.setMap(null);

	        for (var i = 0; i < markerArray.length; i++) {
	            markerArray[i].setMap(null);
	        }
	        
	        markerArray=[];
	        
	    }
	

	    
	    
	
	    return {
	    	
	    	 getPath		:	getPath,
	    	 getEncodedPath	:	getEncodedPath,
	    	 clearDir		:	clearDir,
	    	
	    };
});