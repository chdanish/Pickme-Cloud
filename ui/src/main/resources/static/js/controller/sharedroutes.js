"use strict";
app.controller('sharedroutes',['$rootScope', '$scope', '$http', function($rootScope, $scope, $http) {
	
	var scope = $scope;

	$http({
		  method: 'GET',
		  url: '/shareroute/mysharedroutes'
		}).then(function successCallback(response) {
		    // this callback will be called asynchronously
		    // when the response is available
			console.log("/shareroute/mysharedroutes response:");
			angular.forEach(response.data, function(value, key) {
				console.log("key"+key);
				console.log(value);
				$scope.results =value;
			});
			//$scope.results = response.data;
			
		  }, function errorCallback(response) {
		    // called asynchronously if an error occurs
		    // or server returns response with an error status.
		});
	
}]);