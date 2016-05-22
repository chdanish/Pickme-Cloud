"use strict";
app.controller('find', function($rootScope, $scope, $http, $location,$document,$interval,$timeout, $route, $cacheFactory,
		$window , $cookies ,superCache,placeService,markerService,httpq) {
	
	var scope = $scope;
	
	$scope.names = ["Name", "Email", "Username"];
	$scope.selectedValue = "Username";
	$scope.results = "";
	
	$scope.finduser = function (){
		console.log("current value: "+$scope.textvalue);
		
		 if ($scope.selectedValue === "Username") {
		  $http.post('findfriends/findfriendbyusername/'+$scope.textvalue, {}).success(function(data) {
			   console.log(data);
			   $scope.results =[];
			   if(data.status != null){
				   
				   $scope.results = data;
			   }
			   console.log($scope.results);
			  }).error(function(data) {
			   console.log(data);
			  });
		 }
		  
		  if ($scope.selectedValue === "Name") {
			  $http.post('findfriends/findfriendbyname/'+$scope.textvalue, {}).success(function(data) {
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
	
	$scope.addfriendreq = function (id){
		console.log("addfriend value: "+$scope.textvalue);
		
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