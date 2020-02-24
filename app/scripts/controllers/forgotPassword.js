(function(){
	var waReportsApp = angular
		.module('waReports')
        .controller("ForgotPasswordController", ['$scope', '$state', 'UserFormFactory', '$http', function($scope, $state, UserFormFactory, $http){

            $scope.forgotPassword = {};
            $scope.forgotPassword.captchaCode = '';
            $scope.waiting = false;


            $scope.forgotPasswordSubmit = function(){

                if(grecaptcha.getResponse() === ""){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please tick the checkbox showing 'I'm not a robot'");
                        $scope.reverse();
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please tick the checkbox showing 'I'm not a robot'");
                        $scope.reverse();
                        return;
                    }
                }

                var captchaResponse =  grecaptcha.getResponse();

                var data = {
                    "username": $scope.forgotPassword.username,
                    "captchaResponse" : captchaResponse,
                };

                if ($scope.forgotPasswordForm.$valid) {
                    $scope.waiting = true;
                    $http({
                        method  : 'POST',
                        url     : backend_root + 'wa/user/forgotPassword',
                        data: JSON.stringify(data),
                        headers : {'Content-Type': 'application/json'}
                    }).then(function (result){
                        $scope.waiting = false;
                        if(UserFormFactory.isInternetExplorer()){
                            if(result.data=="success") {
                                alert("Password changed successfully. Please check your e-mail for further instructions.");
                                $state.go('login', {});                                }
                            else if(result.data=="invalid user"){
                                alert("No user with provided username");

                            }
                            else if(result.data=="invalid captcha"){
                                alert("Invalid captcha. Please refresh or try again");
                            }
                        }
                        else{
                            if(result.data=="success") {
                                UserFormFactory.showAlert("Password changed successfully. Please check your e-mail for further instructions.");
                                $state.go('login', {});                                }
                            else if(result.data=="invalid user"){
                                UserFormFactory.showAlert("No user with provided username");

                            }
                            else if(result.data=="invalid captcha"){
                                UserFormFactory.showAlert("Invalid captcha. Please refresh or try again");
                            }
                        }
                        $scope.forgotPassword = {};
                        $scope.forgotPasswordForm.$setPristine();
                        $scope.forgotPasswordForm.$setUntouched();

                    },function (result){
                        if(UserFormFactory.isInternetExplorer()){
                            alert("something went wrong please try again");
                            return;
                        }
                        else{
                            UserFormFactory.showAlert("something went wrong please try again");
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