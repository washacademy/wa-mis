(function(){
	var waReportsApp = angular
		.module('waReports')
		.controller("ForgotPasswordController", ['$scope', '$state', 'UserFormFactory', '$http', function($scope, $state, UserFormFactory, $http){


			$scope.forgotPasswordSubmit = function(){
				if ($scope.forgotPasswordForm.$valid) {
					$http({
						method  : 'POST',
						url     : backend_root + 'wa/user/forgotPassword',
						data    : $scope.forgotPassword, //forms user object
						headers : {'Content-Type': 'application/json'}
					}).then(function (result){
                        if(UserFormFactory.isInternetExplorer()){
                            alert(result.data['0'])
                             return;
                        }
                        else{
                            UserFormFactory.showAlert(result.data['0'])
                            return;
                        }
                        $scope.forgotPassword = {};
                        $scope.forgotPasswordForm.$setPristine();
                        $scope.forgotPasswordForm.$setUntouched();
                        $state.go('login', {});

                     },function (result){
                        if(UserFormFactory.isInternetExplorer()){
                            alert(result.data['0'])
                             return;
                        }
                        else{
                            UserFormFactory.showAlert(result.data['0'])
                            return;
                        }

                     });
				}
				else{
					angular.forEach($scope.forgotPasswordForm.$error, function (field) {
						angular.forEach(field, function(errorField){
							errorField.$setDirty();
						})
					});
				}

			}

		}])
})()