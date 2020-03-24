(function(){
	var waReportsApp = angular
		.module('waReports')
		.controller("ProfileController", ['$scope', '$state', 'UserFormFactory', '$http', function($scope, $state, UserFormFactory, $http){
			UserFormFactory.isLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
			})

			$http.get(backend_root + 'wa/user/profile')
			.then(function(result){
				$scope.user = result.data;

				$scope.populateLists()
				$scope.resetContacts();

				// console.log($scope.user);
			})

			$scope.populateLists = function(){
				$scope.stateList = [];
				$scope.stateList.push($scope.user.state);

				$scope.districtList = [];
				$scope.districtList.push($scope.user.district);
				
				$scope.blockList = [];
				$scope.blockList.push($scope.user.block);
				
				$scope.accessTypeList = [];
				$scope.accessTypeList.push($scope.user.accessType);
				
				$scope.accessLevelList = [];
				$scope.accessLevelList.push($scope.user.accessLevel);
				
				// $scope.stateList = [];
				// $scope.stateList.push($scope.user.state);
			}

			$scope.resetContacts = function(){
				$scope.contact = {};
				$scope.contact.phoneNumber = $scope.user.phoneNumber;
				$scope.contact.email = $scope.user.email;
			}

			$scope.updateContactsSubmit = function(){
				if ($scope.contactsForm.$valid) {
					delete $scope.contact.$$hashKey;
					$scope.contact.userId = $scope.user.id;
					// console.log($scope.contact);
					$http({
						method  : 'POST',
						url     : backend_root + 'wa/user/updateContacts',
						data    : $scope.contact, //forms user object
						headers : {'Content-Type': 'application/json'} 
					}).then(function(result){
                        if(UserFormFactory.isInternetExplorer()){
                            alert(result.data['0'])
                             return;
                        }
                        else{
                            UserFormFactory.showAlert(result.data['0'])
                            return;
                        }

					})
				}
				else{
					angular.forEach($scope.contactsForm.$error, function (field) {
						angular.forEach(field, function(errorField){
							errorField.$setDirty();
						})
					});
				}
			}

			$scope.clearForm = function(){
				$scope.password = {};
				$scope.confirmPassword = null;
				$scope.changePasswordForm.$setPristine();
			}

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
                            alert(result.data['0'])
                             return;
                        }
                        else{
                            UserFormFactory.showAlert(result.data['0'])
                            return;
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

		}])
})()