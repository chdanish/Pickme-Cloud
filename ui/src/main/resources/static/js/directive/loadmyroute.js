"use strict";
app.directive('eventListner',function($rootScope){
  return {
	    restrict: 'A',
	    link: function(scope, element, attrs){
	     element.bind('drop',onDrop);
	      var drop = element[0];
	      
	      function onDrop(e) {
	     alert("test success");
	  		}
	      function onDropX(e) {
	     alert("test successX");
	  		}
	      //drop.addEventListener('drop', onDrop, false);
	      scope.$on('dropx',onDropX)
	    }	
	  };
	});
//http://codepen.io/chdanish/pen/XKdgBZ?editors=1011

/*function MyCtrl($scope,$rootScope) {
    $scope.test= function(){
      $scope.$emit('dropx');
      $scope.$broadcast('dropx');
      $rootScope.$emit('dropx');
      $rootScope.$broadcast('dropx');
      angular.element('#mybttn').triggerHandler('drop');
      console.log("buttonpress!!");
    }
}*/

/*<div ng-app="myApp">
<div event-listner ng-controller="route"  >
  <p>hello!</p>
  <button id='mybttn' event-listner ng-click="test()">ClickMe</button>
</div>
</div>*/