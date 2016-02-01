"use strict";
/*console.log("gmaps Script loaded");*/

app.service('placeService', function($q){
	
	
	
	
 
	
	/*========================Promise Geocoder==================================*/
	/*LATLNG to ADDRESS*/
	
	var getplaceID = function (marker) {
        var geocoder = new google.maps.Geocoder();
        var deferred = $q.defer();

        
		var latlng = {lat: marker.getPosition().lat(), lng: marker.getPosition().lng()};

		 geocoder.geocode({'location': latlng}, function(results, status) {
            if (status === google.maps.GeocoderStatus.OK)
            {
                return deferred.resolve(results[1].place_id);
            }
            return deferred.reject();
        });
        return deferred.promise;
    };
    
    /*ADDRESS to LATLNG*/
	this.codeAddress = function (address) {
        var geocoder = new google.maps.Geocoder();
        var deferred = $q.defer();

        geocoder.geocode({'address': address}, function (results, status) {
            if (status === google.maps.GeocoderStatus.OK)
            {
                return deferred.resolve(results[0].geometry.location);
            }
            return deferred.reject();
        });
        return deferred.promise;
    };
    
	/*PLaceID to ADDRESS*/
		
		var getplaceADD = function (pid) {
			var map = new google.maps.Map(document.createElement('div'));
			var service = new google.maps.places.PlacesService(map);
	        var deferred = $q.defer();
	
			service.getDetails({
	    	    placeId: pid
	    	  }, function(place, status) {
	    	    if (status === google.maps.places.PlacesServiceStatus.OK) {
	    	    return deferred.resolve(place.formatted_address);
	    	    
	    	    }else {
	    	    	console.log('Address service of google places failed due to: ' + status);
	    	    	return deferred.reject();
			       }
	    	  });
	        return deferred.promise;
	    };
	    
	    /*Marker to ADDRESS NOT working need a re-do*/
	    
	    var getmrkrtoADD = function (marker) {
			var map = new google.maps.Map(document.createElement('div'));
			var service = new google.maps.places.PlacesService(map);
	        var deferred = $q.defer();
	        
	        var latlng = {lat: marker.getPosition().lat(), lng: marker.getPosition().lng()};
	
			service.getDetails({'location': latlng}, function(place, status) {
	    	    if (status === google.maps.places.PlacesServiceStatus.OK) {
	    	    return deferred.resolve(place.formatted_address);
	    	    
	    	    }else {
	    	    	console.log('Address service of google places failed due to: ' + status);
	    	    	return deferred.reject();
			       }
	    	  });
	        return deferred.promise;
	    };
	
	
	/*==========================================================*/
	
	

	    
	    
	
	    return {
	    	
	    	getplaceID  : getplaceID,
	    	getplaceADD : getplaceADD,
	    	getmrkrtoADD: getmrkrtoADD,
	    };
});