(function(){
	var waReportsApp = angular
		.module('waReports')
		.controller("EditUserController", ['$scope', 'UserFormFactory', '$http', '$state', '$stateParams', function($scope, UserFormFactory, $http, $state, $stateParams,$location){

			UserFormFactory.isLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
			})

			UserFormFactory.downloadCurrentUser()
			.then(function(result){
				UserFormFactory.setCurrentUser(result.data);

				UserFormFactory.getUser($stateParams.id)
				.then(function(result){
					$scope.editUser = result.data;
				});
			})

			$scope.editUser = {};
			$scope.place = {};

				
			

			$scope.accessLevelList = ["NATIONAL", "STATE", "DISTRICT", "BLOCK"];

			$scope.showAccess = function(level){
				var levelIndex = $scope.accessLevelList.indexOf(UserFormFactory.getCurrentUser().accessLevel);
				if($scope.accessLevelList.indexOf(level) >= levelIndex){
					return true;
				}else{
					return false;
				}
			}

			$scope.filterAccessType = function(accessType){
				if($scope.editUser.accessLevel == 'BLOCK' || 
					UserFormFactory.getCurrentUser().accessLevel == 'DISTRICT' ||
					(UserFormFactory.getCurrentUser().accessLevel == $scope.editUser.accessLevel && UserFormFactory.getCurrentUser().roleId != 1)){
					return accessType.roleId != 2;
				}
				else{
					return true;
				}
			}

			$scope.filterAccessLevel = function(accessLevel){
				var temp = false;
				if($scope.editUser.roleId == 2 && UserFormFactory.getCurrentUser().roleId != 1){ // admin
					temp = accessLevel != "BLOCK" && accessLevel != UserFormFactory.getCurrentUser().accessLevel;
				}
				else{
					temp = true;
				}

				var levelIndex = $scope.accessLevelList.indexOf(UserFormFactory.getCurrentUser().accessLevel);
				return ($scope.accessLevelList.indexOf(accessLevel) >= levelIndex) && temp;
			}


			$scope.getAccessLevel = function(level){
				var list = $scope.accessLevelList;
				var item = $scope.editUser.accessLevel
				var off = 4 - list.length;
				return list.indexOf(item) + off < level;
			}
			
			$scope.roleLoading = true;
			UserFormFactory.getRoles()
			.then(function(result){
				$scope.accessTypeList = result.data;
				$scope.roleLoading = false;
			});

			$scope.getStates = function(){
				$scope.stateLoading = true;
				return UserFormFactory.getStates()
				.then(function(result){
					$scope.stateLoading = false;
					$scope.states = result.data;
					$scope.districts = [];
					$scope.blocks = [];
					if($scope.editUser.stateId != null){
						$scope.place.stateId = $scope.editUser.stateId;
						$scope.editUser.stateId = null;
					}
				});
			}
			
			$scope.getDistricts = function(stateId){
				$scope.districtLoading = true;
				return UserFormFactory.getDistricts(stateId)
				.then(function(result){
					$scope.districtLoading = false;
					$scope.districts = result.data;
					$scope.blocks = [];
					if($scope.editUser.districtId != null){
						$scope.place.districtId = $scope.editUser.districtId;
						$scope.editUser.districtId = null;
					}
				});
			}

			$scope.getBlocks =function(districtId){
				$scope.blockLoading = true;
				return UserFormFactory.getBlocks(districtId)
				.then(function(result){
					$scope.blockLoading = false;
					$scope.blocks = result.data;
					if($scope.editUser.blockId != null){
						$scope.place.blockId = $scope.editUser.blockId;
						$scope.editUser.blockId = null;
					}
				});
			}

			$scope.onAccessLevelChanged = function(){
				$scope.getStates()
				$scope.editUserForm.state.$setPristine(false);
				$scope.editUserForm.district.$setPristine(false);
				$scope.editUserForm.block.$setPristine(false);
			}

			$scope.$watch('editUser.accessLevel', function(oldValue, newValue){
				if(oldValue !== newValue && oldValue != null){
					$scope.onAccessLevelChanged();
				}
			});

			$scope.$watch('place.stateId', function(value){
				if(value != null){
					$scope.getDistricts(value)
				}
			});
			$scope.$watch('place.districtId', function(value){
				if(value != null){
					$scope.getBlocks(value);
				}
			});
			$scope.$watch('place.blockId', function(value){
				if(value != null){

				}
			});

			$scope.editUserSubmit = function() {
				if ($scope.editUserForm.$valid) {
					$scope.editUser.stateId = $scope.place.stateId;
					$scope.editUser.districtId = $scope.place.districtId;
					$scope.editUser.blockId = $scope.place.blockId;
					delete $scope.editUser.$$hashKey;
					$http({
						method  : 'POST',
						url     : backend_root + 'wa/user/updateUser',
						data    : $scope.editUser, //forms user object
						headers : {'Content-Type': 'application/json'} 
					}).then(function(result){
                        if(UserFormFactory.isInternetExplorer()){
                            alert(result.data['0']);
                        }
                        else{
                            UserFormFactory.showAlert(result.data['0']);
                        }
						// $scope.open()
						if(result.data['0'] == 'User Updated'){
							$state.go('userManagement.userTable', {});
						}
						$location.url('/userManagement');
					})
				}
				else{
					angular.forEach($scope.editUserForm.$error, function (field) {
						angular.forEach(field, function(errorField){
							errorField.$setDirty();
						})
					});
				}
			};

			$scope.resetPasswordSubmit = function() {
			    var password = {};
			    password.userId = $scope.editUser.userId;
			    password.oldPassword = "";
			    password.newPassword = "";
                $http({
                    method  : 'POST',
                    url     : backend_root + 'wa/admin/changePassword',
                    data    : password, //forms user object
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

            };

            $scope.deactivateUserSubmit = function() {
                UserFormFactory.deactivateUser($scope.editUser.userId)
                .then(function(result){
                    if(UserFormFactory.isInternetExplorer()){
                        alert(result.data['0']);
                    }
                    else{
                        UserFormFactory.showAlert(result.data['0']);
                    }
                    $state.go('userManagement.userTable', {});
                    $location.url('/userManagement');

                });
            };

			// $scope.open = function () {
			// 	$modal.open({
			// 		templateUrl: 'myModalContent.html', // loads the template
			// 		backdrop: true, // setting backdrop allows us to close the modal window on clicking outside the modal window
			// 		windowClass: 'modal', // windowClass - additional CSS class(es) to be added to a modal window template
			// 		controller: function ($scope, $modalInstance) {
			// 			$scope.submit = function () {
			// 				$modalInstance.dismiss('cancel'); // dismiss(reason) - a method that can be used to dismiss a modal, passing a reason
			// 			}
			// 			$scope.cancel = function () {
			// 				$modalInstance.dismiss('cancel'); 
			// 			};
			// 		},
			// 	});//end of modal.open
			// }; // end of scope.open function
		
		}]);
})()