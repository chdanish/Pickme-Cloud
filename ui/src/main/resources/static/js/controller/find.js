"use strict";
app.controller('find', function($rootScope, $scope, $http, $location,$document,$interval,$timeout, $route, $cacheFactory,
		$window , $cookies ,superCache,placeService,markerService,httpq) {
	
	var scope = $scope;
	
	$scope.names = ["Name", "Email", "Username"];
	$scope.selectedValue = "Username";
	$scope.results = "";
	
	$scope.finduser = function (){
		console.log("current value: "+$scope.myInput);
		
		 if ($scope.selectedValue === "Username") {
			 console.log("username function" );
			 
		  $http.post('findfriends/findfriendbyusername/'+$scope.myInput, {}).success(function(data) {
			   console.log(data);
			   $scope.results =[];
			   if(data.status != null){
				   $scope.results = data;
			   }
			   console.log("My recieved"+$scope.results);
			  }).error(function(data) {
			   console.log(data);
			  });
		 }
		  
		  if ($scope.selectedValue === "Name") {
			  console.log("name function: " );
			  
			  $http.post('findfriends/findfriendbyname/'+$scope.myInput, {}).success(function(data) {
				   console.log(data);
				   if(data.Status != null){
					   $scope.results =[];
					   $scope.results = data.Status;
				   }
				  
				   console.log("recieved data: " +$scope.results);
				   
				  }).error(function(data) {
				   console.log(data);
				  });
			
		}
		
	}
	
	$scope.sendfriendreq = function (id){
		console.log("addfriend value: "+$scope.myInput);
		
		  $http.post('friendrequest/addfriendreq/'+id, {}).success(function(data) {
			   console.log(data);
			   if(data.status != null){
				   $scope.addfriendreqresults = data;
			   }
			   console.log($scope.results);
			  }).error(function(data) {
			   console.log(data);
			  });
		
	}
	
	
});