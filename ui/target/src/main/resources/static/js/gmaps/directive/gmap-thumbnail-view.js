"use strict";
app.directive('mapTh', function(mapdisplaySER) {
    return {
        restrict: 'E',
        replace: true,
        template: '<div></div>',
        scope: {
            format: '@'
        },
        link: function(scope, element, attrs) {
            console.log(element);
            
            
            var myOptions = {
                zoom: 6,
                center: new google.maps.LatLng(46.87916, -3.32910),
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                disableDefaultUI: true,
                disableDoubleClickZoom:true,
                draggable: false,
            };
            element[0].style.height="200px";
            element[0].style.width="200px";
            element[0].style.visibility="visible";
            element[0].style.display="block";
      
            var map = new google.maps.Map(element[0], myOptions);
            map.setOptions({disableDoubleClickZoom:true});
            
            
            google.maps.event.clearListeners(map, 'click');
            google.maps.event.clearListeners(map, 'mousedown');
            google.maps.event.clearListeners(map, 'dblclick');
            google.maps.event.addDomListener(map, 'mousedown', cancelEvent);   // cancels drag/click 
        	google.maps.event.addDomListener(map, 'click', cancelEvent);              // cancels click 
        	google.maps.event.addDomListener(map, 'dblclick', cancelEvent);          // cancels double click 
        	google.maps.event.addDomListener(map, 'contextmenu', cancelEvent);  //cancels double right click 
            function cancelEvent(ev) { 
            	console.log("Map event hit");
            	ev.cancelBubble = true;
            	 
                // does not exist on the passed in event object
                ev.stopPropagation && ev.stopPropagation();
             
                // does not exist on the passed in event object
                ev.preventDefault && ev.preventDefault();
                
                // what Google devs proposed but also did not work
                ev.stop();
            		if (ev.stopPropagation) ev.stopPropagation(); 
            	} 

           
           
            setTimeout(mapdisplaySER.resizeMap(map ), 300);
            
            if (attrs.mapid && attrs.dlat && attrs.dlong && attrs.slat && attrs.slong) {
            	console.log(attrs.mapid +" : "+attrs.dlat +" : "+attrs.dlong +" : "+attrs.slat +" : "+ attrs.slong);
            	var  directionsService = new google.maps.DirectionsService();
            	var directionsDisplay = new google.maps.DirectionsRenderer();
            	var smarker = new google.maps.Marker({
            	    // The below line is equivalent to writing:
            	    // position: new google.maps.LatLng(-34.397, 150.644)
            		icon:{
        		        path: 'M 12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zm0 9.5c-1.38 0-2.5-1.12-2.5-2.5s1.12-2.5 2.5-2.5 2.5 1.12 2.5 2.5-1.12 2.5-2.5 2.5 z',
        		        strokeColor: 'blue',
        		        scale: 1.5,
        		        strokeWeight: 2,
        		        origin: new google.maps.Point(0, 0),
        		        anchor: new google.maps.Point(10, 20),
        		        fillColor: "red",
        		        fillOpacity: 1,

        		    },
            	    position: {lat: Number(attrs.slat), lng: Number(attrs.slong)},
            	    clickable: false,
            	    draggable: false,
            	    disableDoubleClickZoom:true,
            	    map: map
            	  });
            	var dmarker = new google.maps.Marker({
            	    // The below line is equivalent to writing:
            	    // position: new google.maps.LatLng(-34.397, 150.644)
            		icon:{
        		        path: 'M 12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zm0 9.5c-1.38 0-2.5-1.12-2.5-2.5s1.12-2.5 2.5-2.5 2.5 1.12 2.5 2.5-1.12 2.5-2.5 2.5 z',
        		        strokeColor: 'blue',
        		        scale: 1.5,
        		        strokeWeight: 2,
        		        origin: new google.maps.Point(0, 0),
        		        anchor: new google.maps.Point(10, 20),
        		        fillColor: "yellow",
        		        fillOpacity: 1,

        		    },
            	    position: {lat: Number(attrs.dlat), lng: Number(attrs.dlong)},
            	    clickable: false,
            	    draggable: false,
            	    disableDoubleClickZoom:true,
            	    map: map
            	  });
            	google.maps.event.addDomListener(smarker, 'click',cancelEvent);
            	google.maps.event.addDomListener(dmarker, 'click',cancelEvent);
            	var request = {
    	                origin:  smarker.getPosition() ,
    	                destination:  dmarker.getPosition() ,
    	                travelMode: google.maps.DirectionsTravelMode.DRIVING
    	            };
            	
            	directionsService.route(request, function(response, status) {
	                if (status == google.maps.DirectionsStatus.OK) {
	                    console.log("warnings: "+ response.routes[0].warnings );
	                    directionsDisplay.setDirections(response);
	                    directionsDisplay.setMap(map);
	                    directionsDisplay.setOptions( { 
	                    	suppressMarkers: true,
	                    	clickable: false,
	                	    draggable: false,
	                	    disableDoubleClickZoom:true,
	                    	} );
	                    
	                    
	                } else {
		    	    	console.log('Direction service of google in thumbnail directive failed due to: ' + status);
		    	    	
				       }	         
	            });
			} else {

			}
            
            
            
            
        }
    };
});
