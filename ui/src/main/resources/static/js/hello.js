var app =angular.module('hello', [ 'ngRoute','ngCookies' ]).config(function($routeProvider,$cookiesProvider, $httpProvider) {

	$routeProvider
	.when('/home', {
		templateUrl : 'home.html',
		controller : 'home'
	})
	.when('/route', {
		templateUrl : 'route',
		controller : 'route'
	})
	.otherwise('/');

	/*.otherwise({
	    redirectTo: '/home'
	  });*/

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
});




app.factory('superCache', ['$cacheFactory', function($cacheFactory) {
    return $cacheFactory('super-cache');
  }])
.controller('navigation', function($rootScope, $scope, $http, $location, $route, $cacheFactory, $window , $cookies ,superCache) {

	  superCache.removeAll();
	  
	  var JSESSIONID = $cookies.get('JSESSIONID');
	  // Setting a cookie
	  $cookies.put('JSESSIONID', '');
	

	$http.get('user').success(function(data) {
		if (data.name) {
			$rootScope.authenticated = true;
		} else {
			$window.location.href='/login';
			$rootScope.authenticated = false;
		}
	}).error(function() {
		$window.location.href='/login';
		$rootScope.authenticated = false;
	});

	$scope.credentials = {};


		
		 $scope.logout = function() {
			  $http.post('logout', {}).success(function() {
			    $rootScope.authenticated = false;
			    $window.location.href='/';
			  }).error(function(data) {
			    $rootScope.authenticated = false;
			  });
			}
	
	
	
	
	

}).controller('home', function($scope, $http) {
	$http.get('resource/').success(function(data) {
		$scope.greeting = data;
	})
}).controller('logout',  ['$cookies', '$scope', function($scope, $cookies, $http) {
	
	var favoriteCookiex = $cookies.get('JSESSIONID');
	
}]);




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

/* angular redirection http://en.proft.me/2014/08/1/how-work-location-url-javascript-jquery-angularjs/
*/
