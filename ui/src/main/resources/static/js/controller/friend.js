"use strict";
app.controller('friend', function($rootScope, $scope, $http, $location,$document,$interval,$timeout, $route, $cacheFactory,
		$window , $cookies ,superCache,placeService,markerService,httpq) {
	
	var scope = $scope;
	
	$http.get('/friends/list/').success(function(data) {
		   console.log(data);
		   $scope.results = data;
		   if(data.status != null){
			   $scope.results = data.status;
		   }
		   console.log($scope.results);
		  }).error(function(data) {
		   console.log(data);	
		  });
	
});