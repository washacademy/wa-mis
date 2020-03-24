(function(){
	var waReportsApp = angular
		.module('waReports')
		.controller("LoginController", ['$scope', '$http','$window', '$location','Captcha','UserFormFactory', function($scope, $http, $window, $location, Captcha,UserFormFactory){

            // UserFormFactory.isLoggedIn()
            //         .then(function(result){
            //             if(result.data){
            //                 $http.get(backend_root + 'wa/logout');
            //             }
            //         })
            //
            // UserFormFactory.isAdminLoggedIn()
            //         .then(function(result){
            //              if(result.data){
            //                  $http.get(backend_root + 'wa/logout');
            //              }
            //         })

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
            else if (error === 'blocked'){
                $scope.errorMessage = "3 unsuccessful attempts.Please try again in 24 hrs.";
            }
			else{
				$scope.errorMessage = "Invalid Username/Password";
			}

            UserFormFactory.downloadCurrentUser()
                .then(function(result){
                    if(!(typeof(result.data) == "string")){
                        if(UserFormFactory.isInternetExplorer()){
                            $window.location.replace('#!/reports');
                            return;
                        }
                        else{
                            $window.location.replace('#!/reports');
                            return;
                        }
                    }
                })

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
			    // console.log($scope.user);
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
                var captchaCode = ($scope.user.captchaCode).toUpperCase();
                var encrypted = CryptoJS.AES.encrypt($scope.user.captchaCode, 'ABCD123');
                var cipherTextHex = encrypted.ciphertext.toString();
                var saltHex = encrypted.salt.toString();
                var mistokencap = cipherTextHex + "||" +saltHex;
                var mistokencap1 = (window.btoa(mistokencap)).slice(0,-1);


                var postData = {
                  captchaId: captchaId,
                  captchaCode: mistokencap1
                };

                $http({
                    method  : 'POST',
                    url     : backend_root + 'wa/captcha',
                    data    : postData, //forms user object
                    headers : {'Content-Type': 'application/json'}
                }).then(function(response){
                      // console.log(response.data.success);
                      if(response.data.success){
                          // console.log("captcha approved");

                          var encrypted = CryptoJS.AES.encrypt($scope.user.password, 'ABCD123');
                          var cipherTextHex = encrypted.ciphertext.toString();
                          var saltHex = encrypted.salt.toString();
                          var mistoken = cipherTextHex + "||" +saltHex;
                          var mistoken1 = (window.btoa(mistoken)).slice(0,-1);

                          var data = {
                              "username": $scope.user.username,
                              "password" : mistoken1
                          }
                          // console.log("sending login request");

                          $http({
                              method: 'POST',
                              url: $scope.loginUrl,
                              data: JSON.stringify(data),
                              headers: {'Content-Type': 'application/json'}
                          }).then(function(success) {
                              // console.log("request successful");
                              // console.log(success.data);
                              var url = success.data.split('redirect:')[1];
                              // console.log(url);
                              var error = url.split('?')[1];
                              // console.log(error);
                              $scope.errorMessage = "";
                              if(error != null){
                                  if (error == 'blocked'){
                                      $scope.errorMessage = "3 unsuccessful attempts.Please try again in 24 hrs.";
                                  } else {
                                      $scope.errorMessage = "Invalid Username/Password";
                                  }
                              }
                              else {
                                  $scope.errorMessage = '';
                                  location.reload();
                              }

                          })

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

