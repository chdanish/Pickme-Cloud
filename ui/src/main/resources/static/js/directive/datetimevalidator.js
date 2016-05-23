"use strict";
app.directive('vclock', ['dateFilter', '$timeout', function(dateFilter, $timeout){
    return {
        restrict: 'EA',
        scope: {
            format: '@'
        },
        link: function(scope, element, attrs){
        	
        	
        	
           
        }
    };
}]);