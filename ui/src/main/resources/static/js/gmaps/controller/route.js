"use strict";
app.controller('route', function($rootScope, $scope, $http, $location,$document,$interval,$timeout, $route, $cacheFactory,
		$window , $cookies ,superCache,placeService,markerService,httpq) {
	
	var scope = $scope;
	$scope.markers = markerService.markers;
	$scope.edit =true;
	$scope.saverouteDTO = {
			tName	: null,
			dName	: null,
			stpName	: null,
			dstpName: null,
			stp_LAT	: null,
			stp_LNG	: null,
			dstp_LAT: null,
			dstp_LNG: null
			
	} 
	

	
	
	
	
	$scope.$watch('saverouteDTO.tName', function (newValue, oldValue) {
		if (newValue === "") {
			$scope.saverouteDTO.tName = null;
		} 	
		    
		});
	
	/*====Watch for Markers Change starts here====*/
	 $scope.myModel = {
		      latitude: 48.137273, 
		      longitude: 11.575251 
		    }
		
		
		/*====Watch for Markers Change ends here====*/
	
	 	$scope.deleteAll = function(){
	        console.log("Delete btn hit!!");
	        if(  markerService.markers){
	        	markerService.removeMarker(markerService.getMarkerById("marker_1"));
	        	markerService.removeMarker(markerService.getMarkerById("marker_2"));	        	
	        }
	    };
	    $scope.deleteSTP = function(){
	        console.log("Delete btn hit!!");
	        var m1 =  markerService.getMarkerById("marker_1");
	        if( m1 ){
	        	markerService.removeMarker(m1);
	        }
	    };
	    $scope.deleteDSTP = function(){
	    	console.log("deleteDSTP Delete btn hit!!");
	    	 var m1 =  markerService.getMarkerById("marker_2");
		        if( m1 ){
		        	markerService.removeMarker(m1);
		        }
	    };
	    
	    
	    /* ==============Save Modal listener starts here============== */
	    $('#mySaveModal').on('show.bs.modal', function (e) {
	    	var m1 =  markerService.getMarkerById("marker_1");
	    	var m2 =  markerService.getMarkerById("marker_2");
       	 if ( m1 && m2) {    	    
       		placeService.getplaceID(m1,placeService)
       					.then(function(data){
       						console.log(data);
       						//getting address after retrieval of  place ID for starting point
       						placeService.getplaceADD(data).then(function(data){$scope.saverouteDTO.stpName=data;console.log(data);},function(error){console.log("error"+error);});
       					},function(error){
       						console.log("error"+error);
       					});
       		placeService.getplaceID(m2,placeService)
       					.then(function(data){       						
       						console.log(data);
       					//getting address after retrieval of  place ID for destination point
       						placeService.getplaceADD(data).then(function(data){$scope.saverouteDTO.dstpName=data;console.log(data);},function(error){console.log("error"+error);});
       					},function(error){
       						console.log("error"+error);
       					});
            } else {
           	console.log("Markers are not placed over map...");                          
            }         	
       	  
       	});
	    /* ==============Save Modal listener ends here============== */
	    
	    /* ==============Save Route ============== */
	    
	    $scope.save = function(){
	    	
	    	
	        console.log("save btn hit!!");
	        var m1 =  markerService.getMarkerById("marker_1");
	    	var m2 =  markerService.getMarkerById("marker_2");
       	 	if ( m1 && m2) { 
	        	$scope.saverouteDTO.stp_LAT	= m1.getPosition().lat()
				$scope.saverouteDTO.stp_LNG	= m1.getPosition().lng()
				$scope.saverouteDTO.dstp_LAT= m2.getPosition().lat()
				$scope.saverouteDTO.dstp_LNG= m2.getPosition().lng()
	        	console.log($scope.saverouteDTO);
	        }
       	 	
       	 httpq.postJSON('saveroute', $scope.saverouteDTO)
         .errorMessage("MainCtrl -> addNews")
         .then(function(response) {
              console.log(response);
              $scope.myroutes =response;
              loadroutes();
              $route.reload();
         });
       		
       	 	
	    };
	
	    /* ==============Save Route ends here ============== */
	    
	    /* ==============Get Saved Route ============== */
	
	    
	    loadroutes();
	    function loadroutes (){
	    	console.log("Route update request recieved");
	    	 httpq.getJSON('getmyroutes/ownedbyme', $scope.saverouteDTO)
	         .errorMessage("MainCtrl -> addNews")
	         .then(function(response) {
	              console.log(response);
	              $scope.myroutes = response; 
	              
	         });
	    	 
	    	
	    }
	    
	  
	    	
	    
	    
	    /* ==============Get Saved Route ends here ============== */
	    /* ==============Delete Route ============== */
	    
	  
		
		$scope.deleteroute = function(id){
			console.log("Delete Route with ID: " + id);
	    	$http.delete('getmyroutes/'+id).success(function(data) {
	    		loadroutes();
			})
	    }
	    
	    /* ==============Get Save Route ends here ============== */
	    
	    
	    
	
});