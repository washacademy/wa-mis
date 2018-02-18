(function(){
	var waReportsApp = angular
		.module('waReports')
		.controller("LoginController", ['$scope', '$http', '$location','Captcha','UserFormFactory', function($scope, $http, $location, Captcha,UserFormFactory){

            UserFormFactory.isLoggedIn()
                    .then(function(result){
                        if(result.data){
                            $http.get(backend_root + 'wa/logout');
                        }
                    })

            UserFormFactory.isAdminLoggedIn()
                    .then(function(result){
                         if(result.data){
                             $http.get(backend_root + 'wa/logout');
                         }
                    })

			$scope.user = {};
			$scope.user.rememberMe = false;

			$scope.loginUrl = backend_root + 'wa/login';

			var url = $location.absUrl();
			var error = url.split('?')[1]

			console.log(url.split('?')[0])
			console.log(error)

			$scope.errorMessage = "";
			if(error == null){
				$scope.errorMessage = "";
			}
			else{
				$scope.errorMessage = "Invalid Username/Password";
			}

//			$scope.login = function(){
//				$http.post($scope.loginUrl,
//					angular.toJson($scope.user),
//					{
//						headers: {
//							'Content-Type': 'application/json'
//						}
//					})
//					.success(function (data, status, headers, config) {
//						console.log(data);
//					})
//					.error(function (data, status, header, config) {
//						console.log(data);
//					});
//				}
//			}

			$scope.login = function(e){
			    console.log($scope.user);
			    if($scope.user.username == null || $scope.user.username == ""){
			        if(UserFormFactory.isInternetExplorer()){
                        alert("Please specify a username")
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please specify a username")
                        return;
                    }

			    }
			    if($scope.user.password == null || $scope.user.password == ""){
			        if(UserFormFactory.isInternetExplorer()){
                        alert("Please specify a password")
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please specify a password")
                        return;
                    }
                }
                if($scope.user.captchaCode == null || $scope.user.captchaCode == ""){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please enter the captchaCode")
                        return;
                    }
                    else{
                        UserFormFactory.showAlert("Please enter the captchaCode")
                        return;
                    }
                }

                var captcha = new Captcha();

                // captcha id for validating captcha at server-side
                var captchaId = captcha.captchaId;

                // captcha code input value for validating captcha at server-side
                var captchaCode = angular.uppercase($scope.user.captchaCode);

                var postData = {
                  captchaId: captchaId,
                  captchaCode: captchaCode
                };

                $http({
                    method  : 'POST',
                    url     : backend_root + 'wa/captcha',
                    data    : postData, //forms user object
                    headers : {'Content-Type': 'application/json'}
                }).then(function(response){
                      console.log(response.data.success);
                      if(response.data.success){
                         var formElement = angular.element(e.target);
                         console.log(formElement);
                         formElement.attr("action", $scope.loginUrl);
                         formElement.attr("method", "post");
                         formElement[0].submit();

                      }
                      else{
                        if(UserFormFactory.isInternetExplorer()){
                            alert("Incorrect Captcha")
                            return;
                        }
                        else{
                            UserFormFactory.showAlert("Incorrect Captcha")
                            return;
                        }

                      }
                })

            }

		}])
})()

