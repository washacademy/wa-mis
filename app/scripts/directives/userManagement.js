(function(){
	var waReportsApp = angular
		.module('waReports')
		.directive('userManagement', function() {
			return {
				restrict: 'AC',
				templateUrl: '../views/userManagement.html',
			};
		})
		
})()