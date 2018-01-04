(function(){
	var waReportsApp = angular
		.module('waReports')
		.directive('reports', function() {
			return {
				restrict: 'AC',
				templateUrl: '../views/reports.html',
			};
		})
		
})()