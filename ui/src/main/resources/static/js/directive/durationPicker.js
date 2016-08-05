"use strict";
app.directive('durationPicker', ['dateFilter', '$timeout','infoService', function(dateFilter, $timeout,infoService){
    return {
        restrict: 'E',
        template:   'Days:<input  readonly="readonly" id="days" value=0 size=3/>'+
              'Hours:<input readonly="readonly" id="hours" value=0 size=3/>'+
           'Min:<input readonly="readonly" id="minutes" value=0  size=2/>'+
           'Sec:<input readonly="readonly" id="seconds" value=0 size=2/>'+
           ' <!--<button ng-click="myOtherClick()">From Directive</button> --> ',
        scope: {
            duration: '@',
            'days' : '=',
            'hours': '=',
            'mins' : '=',
            'secs' : '=',
        },
        link: function(scope, elm, attrs){
           /* As $digest is already running in another directive so we dont need scope.$apply()
            *  Reference: http://jsfiddle.net/xHzMw/1/
            * http://stackoverflow.com/questions/9043981/how-to-add-minutes-to-my-date */ 
        	
        	
        	scope.myOtherClick = function() {
           
              console.log(scope.secs);
            }
         
         var seconds = $( '#seconds' );
         var minutes = $( '#minutes' );
         var hours   = $( '#hours' );
         var days    = $( '#days' );
         
         var jqueryElm0 = $(elm.find(seconds));
         var jqueryElm1 = $(elm.find(minutes));
         var jqueryElm2 = $(elm.find(hours));
         var jqueryElm3 = $(elm.find(days));
         
         scope.$watch(function() {
       
          return infoService.duration;
          }, function(newValue){
           console.log (newValue)
           var dur= SecondsToddhhmmss(newValue);
           if(jqueryElm0.spinner){
        	   jqueryElm0.spinner( "value",String(dur.s ));
               jqueryElm1.spinner( "value",String(dur.m ));
               jqueryElm2.spinner( "value",String(dur.h ));
               jqueryElm3.spinner( "value",String(dur.d ));
               scope.secs= dur.s;
               scope.mins= dur.m;
               scope.hours= dur.h;
               scope.days= dur.d;        	   
           }
           
           console.log("h: "+dur.h+" m: "+dur.m+" s: "+dur.s);
          });
         
         //fore reference jqueryElm3.attr( "value",String(dur.d ));
         var SecondsToddhhmmss = function(totalSeconds) {
           var days    = Math.floor(totalSeconds / 86400);
           var hours   = Math.floor((totalSeconds - (days  *86400)) / 3600);
           var minutes = Math.floor((totalSeconds - (hours * 3600)) / 60);
           var seconds = totalSeconds - (hours * 3600) - (minutes * 60);
           // round seconds
           seconds = Math.round(seconds * 100) / 100
           return  {
        	   d:days,
        	   h:hours,
        	   m:minutes,
        	   s:seconds,
        	   } 
           }
         
         
         //min-value = 50;
         jqueryElm0.spinner({
     	 	min:0,
            spin: function (event, ui) {
            	scope.secs = ui.value;
                if (ui.value >= 60) {
                    $(this).spinner('value', ui.value - 60);
                    scope.secs = ui.value;
                    jqueryElm1.spinner('stepUp');
                    return false;
                } else if (ui.value < 0) {
                    $(this).spinner('value', ui.value + 60);
                    scope.secs = ui.value;
                    jqueryElm1.spinner('stepDown');
                    return false;
                }
            }
        });
         
         
         jqueryElm1.spinner({
        	 	min:0,
                spin: function (event, ui) {
                	scope.mins = ui.value;
                    if (ui.value >= 60) {
                        $(this).spinner('value', ui.value - 60);
                        scope.mins = ui.value;
                        jqueryElm2.spinner('stepUp');
                        return false;
                    } else if (ui.value < 0) {
                        $(this).spinner('value', ui.value + 60);
                        scope.mins = ui.value;
                        jqueryElm2.spinner('stepDown');
                        return false;
                    }
                }
            });
         
         jqueryElm2.spinner({
        	    min:0,
                spin: function (event, ui) {
                	scope.hours = ui.value;
                    if (ui.value >= 23) {
                        $(this).spinner('value', ui.value - 23);
                        scope.hours = ui.value;
                        jqueryElm3.spinner('stepUp');
                        return false;
                    } else if (ui.value < 0) {
                        $(this).spinner('value', ui.value + 23);
                        scope.hours = ui.value;
                        jqueryElm3.spinner('stepDown');
                        return false;
                    }
                }
            });
         

jqueryElm3.spinner({
          min:0,
          max:30,
           spin: function (event, ui) {
            scope.days = ui.value;
           },
         });
        }
    };
}]);
