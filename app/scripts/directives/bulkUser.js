(function(){
	var waReportsApp = angular
		.module('waReports')
		.directive('bulkUser', function() {
			return {
				restrict: 'AC',
				templateUrl: '../views/bulkUser.html',

			};
		})
})()