"use strict";
app.controller('request', function($rootScope, $scope, $http, $location,$document,$interval,$timeout, $route, $cacheFactory,
		$window , $cookies ,superCache,placeService,markerService,httpq) {
	
	var scope = $scope;
	
	 $http.get('/friendrequest/list/').success(function(data) {
		   console.log(data);
		   $scope.results = data;
		   if(data.status != null){
			   $scope.results = data.status;
		   }
		   console.log($scope.results);
		  }).error(function(data) {
		   console.log(data);
		  });
	 
	 $scope.addfriend = function (id){
			console.log("addfriend value: "+$scope.textvalue);
			
			  $http.post('/friends/addfriend/'+id, {}).success(function(data) {
				   console.log(data);
				   if(data.status != null){
					   $scope.addfriendreqresults = data;
				   }
				   console.log($scope.results);
				  }).error(function(data) {
				   console.log(data);
				  });
			
		}
	 
	 $scope.delfriendrequest = function (id){
			console.log("addfriend value: "+$scope.textvalue);
			
			  $http.delete('/friendrequest/del/'+id, {}).success(function(data) {
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