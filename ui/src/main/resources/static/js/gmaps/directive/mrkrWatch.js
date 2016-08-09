   
app.directive('demoMap',function(Map,mapdisplaySER,markerService,directionService,infoService,$interval,$location,$route){
	  return {
		    restrict: 'EA',
		    require: '?ngModel',
		    
		    link: function(scope,element,attrs,ngModel){
		        
		      var mapOptions;
		      var googleMap;
		      var searchMarker;
		      var searchLatLng;
		      var stopTime;
		      
		      ngModel.$render = function(){
		        searchLatLng = new google.maps.LatLng(scope.myModel.latitude, scope.myModel.longitude);

		        var mapclose = document.createElement("div");
		        var mapclose_css = "-webkit-box-sizing: border-box; -moz-box-sizing: border-box;"+
									"box-sizing: border-box;	-webkit-appearance: none;"+
									"-moz-appearance: none;	width: 20px;" +	
									"right: 20px;    top: 20px;"+
									"padding: 5px 5px !important;	margin: 20px 20px !important;"+
									"background-color: cornflowerblue;"
		        
				mapclose.style.cssText = mapclose_css;
		        
		        mapclose.innerHTML = '<div class="mapclose">'+
		        						'<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'+
		        					 '</div>'
		        
		        // Initialize map
		        googleMap = Map.initMapElement(element[0]);		
		        
		        // Custom controls to map
		        googleMap.controls[google.maps.ControlPosition.TOP_RIGHT].push(mapclose);
		        googleMap.controls[google.maps.ControlPosition.TOP_LEFT].push(document.getElementById("mapnavbar"));
		        
		        google.maps.event.addListener(googleMap, 'click', function(e) {
		        	if ( markerService.markers.length<=1){
		        		var marker1 = markerService.secureAddMarker(e,googleMap);		        			        		 
		        	}
		        	
		        	if (markerService.markers && markerService.markers.length == 2) {
		        		var path = directionService.getPath(markerService.markers,googleMap).then(function(response) {
		        			console.log("my encoded path after deffer resolve: "+ response);
		        			markerService.encodedpath=response.encpath;
		        			infoService.distance=response.distance;
		        			infoService.duration=response.duration;
		        		});
		        	}		        	
		            });
		        
		        
		        $('#myMapModal').on('show.bs.modal', function (e) {
		        	$location.path('/route');
		        	 //stopTime = $interval(scope.$digest(),10000);
		        	 if (google.maps && googleMap) {
		        		 
		                 setTimeout(mapdisplaySER.resizeMap(googleMap), 300);
		             } else {
		            	alert("Lazy loading Google map...");                          
		             }         	
		        	  
		        	});
		      };
		      
		      $('#myMapModal').on('hidden.bs.modal', function () {
		    	// listen on DOM hidden  event, and cancel the next UI update
		        // to prevent watcher after the DOM element hidden.
		    	    $interval.cancel(stopTime);
		    	});
		      
		      

	          
		      
		      element.on('click', function() {
		    	  scope.$digest();
		    	 });
		     
		      
		      scope.$watch(
	                    function( scope ) {
	                       // console.log( "Function watched" );
	                        // This becomes the value we're "watching".
	                        return( markerService.markers.length );
	                    },
	                    function( newValue ) {
	                      if(newValue == 2){scope.disable = false;
	                      }  else {scope.disable = true;}
	                      
	                      if(newValue<2){ directionService.clearDir(); }
	                        console.log( newValue );
	                    }
	                );
		    }      
		  }
		});


