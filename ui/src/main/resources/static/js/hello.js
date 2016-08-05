var app = angular.module('hello', [ 'ngRoute','ngCookies','ngMaterial','ngAnimate', 'ui.bootstrap' ]);

app.config(function($routeProvider, $locationProvider,$cookiesProvider, $httpProvider,MapProvider) {

	$routeProvider
	.when('/home', {
		templateUrl : 'home',
		controller : 'home'
	})
	.when('/route', {
		templateUrl : 'route',
		controller : 'route'
	})
	.when('/sharedroutes', {
		templateUrl : 'sharedroutes',
		controller : 'sharedroutes'
	})
	.when('/friend', {
		templateUrl : 'friend',
		controller : 'friend'
	})
	.when('/request', {
		templateUrl : 'request',
		controller : 'request'
	})
	.when('/findfriend', {
		templateUrl : 'find',
		controller : 'find'
	})
	.otherwise('/home');

	/*.otherwise({
	    redirectTo: '/home'
	  });*/
	
	/*$locationProvider.html5Mode({
		  enabled: true,
		  requireBase: false
		});*/

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	
	var setMapType = function(){
		MapProvider.setMapType("GoogleMaps");
	}();
	
});




app.factory('superCache', ['$cacheFactory', function($cacheFactory) {
    return $cacheFactory('super-cache');
  }])
/*.controller('navigation', function($rootScope, $scope, $http, $location,$document,$interval, $route, $cacheFactory, $window , $cookies ,superCache,dirService,delService ,markerFactory, popupSER,Map) {*/
	.controller('navigation', function($rootScope, $scope, $http, $location,$document,$interval, $route, $cacheFactory, $window , $cookies ,superCache,Map) {
  
		 $scope.$route = $route;
		 $scope.name = "navigation";
		 $('#myMapModal').on('show.bs.modal', function (e) {
	        	$location.path('/route');
	        	console.log("navigation controller");
	     });
	  
	  
		 $http.get('userx').then(function successCallback(data) {
			    // this callback will be called asynchronously
			    // when the response is available
			 
			 if (data.data.name) {
					$rootScope.authenticated = true;
				} else {
					$window.location.href='/login';
					$rootScope.authenticated = false;
				}
			  }, function errorCallback(data) {
				  console.log("UserX error: "+data);
				  $window.location.href='/login';
				  $rootScope.authenticated = false;
			  });

	/*$http.get('userx').then(function successCallback(data) {
		if (data.name) {
			$rootScope.authenticated = true;
		} else {
			$window.location.href='/login';
			$rootScope.authenticated = false;
		}
	},).error(function() {
		$window.location.href='/login';
		$rootScope.authenticated = false;
	});*/

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
        '$rootScope','Map',
        function($rootScope,Map) {
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
