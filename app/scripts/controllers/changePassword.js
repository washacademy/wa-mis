(function(){
	var waReportsApp = angular
		.module('waReports')
		.controller("ChangePassword", ['$scope', '$state','$http', 'UserFormFactory', function($scope, $state, $http, UserFormFactory){

			UserFormFactory.isLoggedIn()
            .then(function(result){
                if(!result.data){
                    $state.go('login', {});
                }
            })

            $http.get(backend_root + 'wa/user/profile')
            .then(function(result){
                $scope.user = result.data;
            })

			$scope.changePasswordSubmit = function(){
                if ($scope.changePasswordForm.$valid) {
                    delete $scope.password.$$hashKey;
                    $scope.password.userId = $scope.user.id;
                    $http({
                        method  : 'POST',
                        url     : backend_root + 'wa/user/resetPassword',
                        data    : $scope.password, //forms user object
                        headers : {'Content-Type': 'application/json'}
                    }).then(function(result){
                        if(UserFormFactory.isInternetExplorer()){

                            if(result.data['0'] != "Current Password is incorrect"){
                                alert(result.data['0']);
                                $state.go('login', {});
                                return;
                            }
                            else{
                                alert(result.data['0']);
                                $scope.password = {};
                                $scope.password.newPassword = null;
                                $scope.confirmPassword = null;
                                $scope.changePasswordForm.$setPristine();
                            }
                        }
                        else{

                            if(result.data['0'] != "Current Password is incorrect"){
                                UserFormFactory.showAlert(result.data['0'])
                                $state.go('login', {});
                                return;
                            }
                            else{
                                 UserFormFactory.showAlert(result.data['0'])
                                 $scope.password = {};
                                 $scope.password.newPassword = null;
                                 $scope.confirmPassword = null;
                                 $scope.changePasswordForm.$setPristine();
                            }

                        }

                    })
                }
                else{
                    angular.forEach($scope.changePasswordForm.$error, function (field) {
                        angular.forEach(field, function(errorField){
                            errorField.$setDirty();
                        })
                    });
                }
            }

			$scope.clearForm = function(){

                if($scope.currentUser.default || $scope.currentUser.default == null){
                    if(UserFormFactory.isInternetExplorer()){
                         alert("Login and Change your Password");
                         $state.go('login', {});
                    }
                    else{
                         UserFormFactory.showAlert("Login and Change your Password")
                         $state.go('login', {});
                    }


                }else {
                    $scope.password = {};
                    $scope.password.newPassword = null;
                    $scope.confirmPassword = null;
                    $scope.changePasswordForm.$setPristine();
                }
            }

            UserFormFactory.downloadCurrentUser()
            .then(function(result){
                UserFormFactory.setCurrentUser(result.data);
                $scope.currentUser = UserFormFactory.getCurrentUser();
                console.log($scope.currentUser);
            })


		}])
})()