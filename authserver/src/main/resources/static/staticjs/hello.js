var app = angular.module('hello', [ 'ngRoute','ngCookies' ]).config(function($routeProvider, $httpProvider,$cookiesProvider) {

	/*$routeProvider.when('/', {
		templateUrl : 'login',
		controller : 'navigation'
	})
	.otherwise('uaa/login');*/
	
	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

});
app.controller('navigation',

function($rootScope, $scope, $http, $location, $route ,$window, $routeParams,$cookieStore) {

/*	$scope.tab = function(route) {
		return $route.current && route === $route.current.controller;
	};

	 $scope.logintab = false;
	 $scope.signuptab= false;
	 
	    $scope.showlogin = function() {
	        $scope.logintab = true;
	        $scope.signuptab= false;
	    }
	    $scope.showsignup = function() {
	    	$scope.signuptab= true;
	        $scope.logintab = false;
	        $scope.edit = true;
	    }*/
	    
	    this.tab = 1;
	    $scope.edit = true;
	   
	    
	    this.setTab = function (tabId) {
            this.tab = tabId;
        };

        this.isSet = function (tabId) {
            return this.tab === tabId;
        };
        
        


        	  var authenticate = function(credentials, callback) {

        	    var headers = credentials ? {authorization : "Basic "
        	        + btoa(credentials.username + ":" + credentials.password)
        	    } : {};

        	    $http.post('login', {headers : headers}).success(function(data) {
        	      if (data.name) {
        	        $rootScope.authenticated = true;
        	      } else {
        	        $rootScope.authenticated = false;
        	      }
        	      callback && callback();
        	    }).error(function() {
        	      $rootScope.authenticated = false;
        	      callback && callback();
        	    });

        	  }
        
        $scope.credentials = {};
        $scope.login = function() {
            authenticate($scope.credentials, function() {
              if ($rootScope.authenticated) {
            	console.log("success!!");
                $location.path("/");
                $scope.error = false;
              } else {
            	  console.log("Failed!!");
                $location.path("/login");
                $scope.error = true;
              }
            });
        };
	    
	    $scope.submit = function() {
	    	
	    	 var SignupDTO = { firstName: $scope.fName, lastName: $scope.lName, username: $scope.uName, password: $scope.pw2, email: $scope.email,role : $scope.role};
	    	 $window.alert($scope.fName+" " + $scope.lName);
	    	 $http.post('signup/register', SignupDTO).success(function() {
	    		 this.tab = 1;
	 		}).error(function(data) {
	 			 this.tab = 2;
	 		});
	    }
	    
	   /* ---------------------------------------------------------------------------------------------------*/
	    
	    
		  $scope.facebookauth = function (){
			 
			  console.log("Looking for cookie");
			 if( $cookieStore.get('SESSION')){
				 console.log("Cookie found");
				 $cookieStore.remove('JSESSIONID');
					$cookieStore.remove('SESSION');
				 
			 }
			
				
				$cookieStore.remove('JSESSIONID');
				$cookieStore.remove('SESSION');
				  
				  
				  
				  $window.location.href='http://localhost:8080/facebookauth';
			  
		  }
	    
	    

}).controller('login', function($scope, $http) {
	$http.get('resource/').success(function(data) {
		$scope.greeting = data;
	})
});


 
app.directive('pwCheck', [function () {
    return {
        require: 'ngModel',
        link: function (scope, elem, attrs, ctrl) {
          var firstPassword = '#' + attrs.pwCheck;
          elem.add(firstPassword).on('keyup', function () {
            scope.$apply(function () {
              var v = elem.val()===$(firstPassword).val();
              ctrl.$setValidity('pwmatch',elem.val() === scope[attrs.pwCheck]);
            });
          });
        }
      }
    }]);

//-------------------------------------------------------------------------------------
app.controller('facebookauth',

		function( $http, $httpProvider,$cookies) {

	  
	  $scope.facebookauth = function (){
		  
			$httpProvider.defaults.withCredentials = false;
			superCache.removeAll();
			  
			  var JSESSIONID = $cookies.get('JSESSIONID');
			  // Setting a cookie
			  $cookies.put('JSESSIONID', '');
			  
			  var JSESSIONID = $cookies.get('SESSION');
			  // Setting a cookie
			  $cookies.put('SESSION', '');
			  
			  $window.location.href='/facebookauth';
		  
	  }
	
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
app.run([
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
	  ]);

