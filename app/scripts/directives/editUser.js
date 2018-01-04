(function(){
	var waReportsApp = angular
		.module('waReports')
		.directive('editUser', function() {
			return {
				restrict: 'AC',
				templateUrl: '../views/editUser.html',
				scope:{
					'user':'='
				}
			};
		})
})()