(function(){
	var waReportsApp = angular
		.module('waReports')
		.directive('createUser', function() {
			return {
				restrict: 'AC',
				templateUrl: '../views/createUser.html',
				scope:{
					'newUser':'='
				}
			};
		})
})()