(function(){
	var waReportsApp = angular
		.module('waReports')
		.controller("UserManagementController", ['$scope', '$state', 'UserFormFactory', function($scope, $state, UserFormFactory){
		
			UserFormFactory.isAdminLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
			})

		}])
})()