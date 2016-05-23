"use strict";
app.controller('route', function($rootScope, $scope, $http, $location,$document,$interval,$timeout, $route, $cacheFactory,
		$window , $cookies ,superCache,placeService,markerService,httpq) {
	
	var scope = $scope;
	var m1,m2;
	$scope.name = "route";
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
	        m1 = m2 = null ;
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
	    	 m1 =  markerService.getMarkerById("marker_1");
	    	 m2 =  markerService.getMarkerById("marker_2");
	    	console.log("Save route modal show up");
       	 if ( m1 && m2) {   
       		$scope.saverouteDTO.stpName="";
       		$scope.saverouteDTO.dstpName="";
       		placeService.getplaceID(m1)
       					.then(function(data){
       						console.log(data);
       						//getting address after retrieval of  place ID for starting point
       						placeService.getplaceADD(data).then(function(data){$scope.saverouteDTO.stpName=data;console.log(data);},function(error){console.log("error"+error);});
       					},function(error){
       						console.log("error"+error);
       					});
       		placeService.getplaceID(m2)
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
              console.log("pre-current location: "+$location.path());
              console.log("pre-current template: "+$route.current.templateUrl);
              console.log("pre-current scope: "+$route.current.scope.name);
              //$route.reload();
              console.log("post-current location: "+$location.path());
              console.log("post-current template: "+$route.current.templateUrl);
              console.log("post-current scope: "+$route.current.scope.name);
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
		
		 /* ==============Share route/pickup request Modal listener starts here============== */
	    $('#myRouteShareModal').on('show.bs.modal', function (e) {
	    	console.log("curent date"+ new Date());
	    	$http.get('/friends/list/').success(function(data) {
	 		   console.log(data);
	 		   $scope.friendlist = [];
	 		   if(data.Status != null){
	 			  $scope.friendlist = data.Status;
	 		   }
	 		   console.log($scope.friendlist);
	 		  }).error(function(data) {
	 		   console.log(data);	
	 		  });
	    
	    });
	    
	    $scope.thumbnail={};
	    /* ==============Share route/pickup request Modal listener ends here============== */
	    var today = new Date();
	    var dd = today.getDate();
	    var mm = today.getMonth()+1; //January is 0!
	    var yyyy = today.getFullYear();
	    var hh = today.getHours();
	    var mn = today.getMinutes();
	    var moddd = (dd.toString().length<2)?"0"+dd:dd;
	    var modmm = (mm.toString().length<2)?"0"+mm:mm;
	    var modhh = (hh.toString().length<2)?"0"+hh:hh;
	    var modmn = (mn.toString().length<2)?"0"+mn:mn;
	    
	    //min="2001-01-01T00:00:00"
	    $scope.mintime=""+yyyy+"-"+modmm+"-"+dd+"T"+modhh+":"+modmn+":00";
	    $scope.start = {
	            value: new Date(yyyy, mm-1, dd, hh+1, mn),
	    		valid:"",
	    
	          };
	    
	    
	    $scope.end = {
	            value: new Date(yyyy, mm-1, dd, hh+1, mn-30),
	    		valid:"",
	          };
	    $scope.currentdatetime= new Date(yyyy, mm-1, dd, hh, mn);
	    
	    
	    
	    $scope.shareroute ={
	    		routeID :"",
	    }
	    
	    $scope.setsharerouteID = function(routeID){
	    	console.log("RouteID set for sharing: "+routeID);
	    	$scope.shareroute.routeID = routeID;
	    }
	    
	    $scope.$watch('shareroute.routeID', function (newValue, oldValue) {
			if (newValue != oldValue) {
				console.log("start trip time change:"+ newValue)
				$scope.shareroute.routeID = newValue;
			} 	
			    
			});
	   
	    $scope.$watch('start.value', function (newValue, oldValue) {
			if (newValue != oldValue) {
				console.log("start trip time change:"+ newValue)
				$scope.start.value = newValue;
			} 	
			    
			});
	    
	    $scope.pickmereq = function(toUserID){
	    	console.log("pickme test routeID: "+$scope.shareroute.routeID+"\n to UserID: "+toUserID+
	    			"\n trip start time: "+$scope.start.value+"\n Comit deadline: "+$scope.end.value);
	    	var ShareRouteDTO = {
	    			routeID:$scope.shareroute.routeID,
	    			toID:toUserID,
	    			tripstarttime:$scope.start.value, 	
	    			deadlinetocommit:$scope.end.value,	
	    			timezone:"",
	    	}
	    	$http.post('/shareroute/sharemyroute', ShareRouteDTO).success(function(data) {
				   console.log(data);
				   if(data.status != null){
					  
				   }
				   console.log($scope.results);
				  }).error(function(data) {
				   console.log(data);
				  });
	    }
	
});