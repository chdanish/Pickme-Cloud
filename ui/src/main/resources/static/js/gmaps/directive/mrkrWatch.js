   
app.directive('demoMap',function(Map,mapdisplaySER,markerService,directionService,$interval){
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
		        	console.log("click listner");
		        	
		        	if ( markerService.markers.length<=1){
		        		var marker1 = markerService.secureAddMarker(e,googleMap);		        			        		 
		        	}
		        	
		        	if (markerService.markers && markerService.markers.length == 2) {
		        		directionService.getPath(markerService.markers,googleMap);		        		
		        	} 
		        	
		            });
		        
		        
		        $('#myMapModal').on('show.bs.modal', function (e) {
		        	 stopTime = $interval(scope.$digest(),1000);
		        	 if (google.maps && googleMap) {
		        		 
		                 setTimeout(mapdisplaySER.resizeMap(googleMap), 300);
		             } else {
		            	alert("Lazy loading Google map...");                          
		             }         	
		        	  
		        	});

		       /* searchMarker = new google.maps.Marker({
		          position: searchLatLng,
		          map: googleMap,
		          draggable: true
		        });
		        
		        google.maps.event.addListener(searchMarker, 'dragend', function(){
		          scope.$apply(function(){
		            scope.myModel.latitude = searchMarker.getPosition().lat();
		            scope.myModel.longitude = searchMarker.getPosition().lng();
		          });
		        }.bind(this));*/
		        
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
	                        console.log( "Function watched" );
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
		  
		      
		      /*scope.$watch('myModel', function(value){
		        var myPosition = new google.maps.LatLng(scope.myModel.latitude, scope.myModel.longitude);
		        searchMarker.setPosition(myPosition);
		      }, true);*/
		    }      
		  }
		});


