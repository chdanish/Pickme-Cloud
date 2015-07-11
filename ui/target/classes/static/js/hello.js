var app =angular.module('hello', [ 'ngRoute' ]).config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/', {
		templateUrl : 'home.html',
		controller : 'home'
	}).otherwise({
	    controller : function(){
	        window.location.replace('/');
	    }, 
	    template : "<div></div>"
	});

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	
	

});

app.factory('superCache', ['$cacheFactory', function($cacheFactory) {
    return $cacheFactory('super-cache');
  }])
.controller('navigation',

function($rootScope, $scope, $http, $location, $route, $cacheFactory,$window ,superCache) {


	  superCache.removeAll();
	
	
	$scope.tab = function(route) {
		/*$window.alert("route current: "+$route.current);
		$window.alert("route current controller: "+$route.current.controller);*/
		console.log("route current: "+$route.current);
		console.log("route current controller: "+$route.current.controller);
		return $route.current && route === $route.current.controller;
	};

	$http.get('user').success(function(data) {
		if (data.name) {
			$rootScope.authenticated = true;
		} else {
			$rootScope.authenticated = false;
		}
	}).error(function() {
		$rootScope.authenticated = false;
	});

	$scope.credentials = {};

	$scope.logout = function() {
		$http.post('logout', {}).success(function() {
			$rootScope.authenticated = false;
			$location.path("/");
		}).error(function(data) {
			console.log("Logout failed")
			$rootScope.authenticated = false;
		});
	}

}).controller('home', function($scope, $http) {
	$http.get('resource/').success(function(data) {
		$scope.greeting = data;
	})
});

