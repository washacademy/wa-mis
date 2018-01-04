(function(){
	var waReportsApp = angular
		.module('waReports')
		.factory( 'AuthFactory', ['$http', function($http) {
			return {
				logout: function(){
					return $http.get(backend_root + 'wa/logout');
				},
			}
		}])
})()