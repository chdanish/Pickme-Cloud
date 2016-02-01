app.factory('httpq', ['$http', '$q', function($http, $q) {

	function createValidJsonRequest(httpRequest) {
		return {
			errorMessage: function (errorMessage) {
				var deferred = $q.defer();

				httpRequest
					.success(function (response) {
						if (response != undefined && typeof response == "object") {
							deferred.resolve(response);
						} else {
							alert(errorMessage + ": Result is not JSON type");
						}
					})
					.error(function(data) {
						deferred.reject(data);
						alert(errorMessage + ": Server Error");
					});

				return deferred.promise;
			}
		};
	}

	return {
		getJSON: function() {
			return createValidJsonRequest($http.get.apply(null, arguments));
		},
		postJSON: function() {
			return createValidJsonRequest($http.post.apply(null, arguments));
		}
	}
}]);