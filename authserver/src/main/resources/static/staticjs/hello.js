var app = angular.module('hello', [ 'ngRoute' ]).config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/', {
		templateUrl : 'login',
		controller : 'login'
	})
	.otherwise('/');
	
	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

});
app.controller('navigation',

function($rootScope, $scope, $http, $location, $route ,$window, $routeParams) {

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
        
	    
	    
	    $scope.submit = function() {
	    	
	    	 var SignupDTO = { firstName: $scope.fName, lastName: $scope.lName, username: $scope.uName, password: $scope.passw1, email: $scope.email,role : $scope.role};
	    	 $window.alert($scope.fName+" " + $scope.lName);
	    	 $http.post('signup/register', SignupDTO).success(function() {
	    		 this.tab = 1;
	 		}).error(function(data) {
	 			 this.tab = 2;
	 		});
	    }
	    
	    

}).controller('login', function($scope, $http) {
	$http.get('resource/').success(function(data) {
		$scope.greeting = data;
	})
});

