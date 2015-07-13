var app =angular.module('hello', [ 'ngRoute' ]).config(function($routeProvider, $httpProvider) {

	$routeProvider
	.when('/home', {
		templateUrl : 'home.html',
		controller : 'home'
	})
	.when('/route', {
		templateUrl : 'route',
		controller : 'route'
	})
	/*.otherwise('/')*/
	;
	
	/*.otherwise({
	    redirectTo: '/home'
	  });*/

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	
	
	
	

});

app.factory('superCache', ['$cacheFactory', function($cacheFactory) {
    return $cacheFactory('super-cache');
  }])
.controller('navigation',function($rootScope, $scope, $http, $location, $route, $cacheFactory,$window ,superCache) {


	  superCache.removeAll();
	
	
/*	$scope.tab = function(route) {
		$window.alert("route current: "+$route.current);
		$window.alert("route current controller: "+$route.current.controller);
		console.log("route current: "+$route.current);
		console.log("route current controller: "+$route.current.controller);
		return $route.current && route === $route.current.controller;
	};*/

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
}).controller('route', function($scope, $http) {
	$http.get('resource/').success(function(data) {
		$scope.routedata = data;
	})
	
});




//-Debug Code Starts here-------------------------------------------------------------------------

app.run([
        '$rootScope',
        function($rootScope) {
          // see what's going on when the route tries to change
          $rootScope.$on('$locationChangeStart', function(event, newUrl, oldUrl) {
            // both newUrl and oldUrl are strings
            console.log('locationChangeStart--Starting to leave %s to go to %s', oldUrl, newUrl);
          });
        }
      ]);
/*app.run([
	    '$rootScope', '$location', function ($rootScope, $location)  {
	      // see what's going on when the route tries to change
	      $rootScope.$on('$routeChangeStart', function(event, current, next, rejection) {
	        // next is an object that is the route that we are starting to go to
	        // current is an object that is the route where we are currently
	        var currentPath = current.$$route.originalPath;
	        var nextPath = next.$$route.originalPath;
	
	        console.log('routeChangeStart--Starting to leave %s to go to %s', currentPath, nextPath);
	      });
	    }
	  ]);*/

