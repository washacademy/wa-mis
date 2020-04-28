(function(){
	var waReportsApp = angular
		.module('waReports')
		.controller("CreateUserController", ['$scope', '$state', 'UserFormFactory', '$http', function($scope, $state, UserFormFactory, $http,$location){

			UserFormFactory.isLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
			})

			UserFormFactory.downloadCurrentUser()
			.then(function(result){
				UserFormFactory.setCurrentUser(result.data);
				// $scope.currentUser = result.data;
				// console.log($scope.currentUser);
			})

			$scope.roleLoading = true;
			UserFormFactory.getRoles()
			.then(function(result){
				$scope.accessTypeList = result.data;
				$scope.roleLoading = false;
			});

			$scope.accessLevelList = ["NATIONAL", "STATE", "DISTRICT", "BLOCK"]

			

			$scope.getAccessLevel = function(level){
			
				var list = $scope.accessLevelList;
				var item = $scope.newUser.accessLevel
				var off = 4 - list.length;
				return list.indexOf(item) + off < level;
			}

			$scope.getStates = function(){
				$scope.stateLoading = true;
				return UserFormFactory.getStates()
				.then(function(result){
					$scope.stateLoading = false;
					$scope.states = result.data;
					$scope.districts = [];
					$scope.blocks = [];
				});
			}
			
			$scope.getDistricts = function(stateId){
				$scope.districtLoading = true;
				return UserFormFactory.getDistricts(stateId)
				.then(function(result){
					$scope.districtLoading = false;
					$scope.districts = result.data;
					$scope.blocks = [];
				});
			}

			$scope.getBlocks =function(districtId){
				$scope.blockLoading = true;
				return UserFormFactory.getBlocks(districtId)
				.then(function(result){
					$scope.blockLoading = false;
					$scope.blocks = result.data;
				});
			}

			$scope.newUser = {};
			
			// $scope.clearForm = function(){
			// 	$scope.newUser = {};
			// 	$scope.createUserForm.$setPristine();

			// 	$scope.$parent.currentPage = "user-table";
			// 	delete $scope.$parent.currentPageTitle;
			// }

			$scope.filterAccessType = function(accessType){
				if($scope.newUser.accessLevel == 'BLOCK' || 
					UserFormFactory.getCurrentUser().accessLevel == 'DISTRICT' ||
					(UserFormFactory.getCurrentUser().accessLevel == $scope.newUser.accessLevel && UserFormFactory.getCurrentUser().roleId != 1)){
					return accessType.roleId != 2;
				}
				else{
					return true;
				}
			}

			$scope.filterAccessLevel = function(accessLevel){
				var temp = false;
				if($scope.newUser.roleId == 2 && UserFormFactory.getCurrentUser().roleId != 1){ // admin
					temp = accessLevel != "BLOCK" && accessLevel != UserFormFactory.getCurrentUser().accessLevel;
				}
				else{
					temp = true;
				}

				var levelIndex = $scope.accessLevelList.indexOf(UserFormFactory.getCurrentUser().accessLevel);
				return ($scope.accessLevelList.indexOf(accessLevel) >= levelIndex) && temp;
			}

			$scope.adminCourses = UserFormFactory.getAccessibleCourses();
			$scope.selectableCourses = (($scope.adminCourses).toString()).split(',');
			var selectableCourses = (($scope.adminCourses).toString()).split(',');
			console.log($scope.selectableCourses);
			console.log(selectableCourses.length);
			$scope.selectedAtLeastOneCourse = false;
			$scope.selectedCourses = "";

			setTimeout(check, 50);
			function check() {
				if(selectableCourses.length ==1){
					document.getElementById(selectableCourses[0]).checked = true;
					document.getElementById(selectableCourses[0]).disabled = true;
					// console.log(document.getElementById(selectableCourses[0]));
				}
			}



			// $scope.showAccess = function(level){
			// 	var levelIndex = $scope.accessLevelList.indexOf(UserFormFactory.getCurrentUser().accessLevel);
			// 	return ($scope.accessLevelList.indexOf(level) >= levelIndex)
			// }


			$scope.createUserSubmit = function() {
				for (var i =0; i < selectableCourses.length; i++){
					var x = document.getElementById(selectableCourses[i]).checked;
					console.log(x);
					if (x == true){
						$scope.selectedAtLeastOneCourse = true;
						if($scope.selectedCourses == ""){
							$scope.selectedCourses = $scope.selectableCourses[i]
						}
						else {
							$scope.selectedCourses = $scope.selectedCourses + "," + selectableCourses[i]
						}
					}

				}


				if ($scope.createUserForm.$valid && $scope.selectedAtLeastOneCourse) {
					delete $scope.newUser.$$hashKey;
					console.log("printing user form details");
					$scope.newUser.accessibleCourses = $scope.selectedCourses;
					console.log($scope.newUser);

					$http({
						method  : 'post',
						url     : backend_root + 'wa/user/createUser',
						data    : $scope.newUser, //forms user object
						headers : {'Content-Type': 'application/json'} 
					}).then(function(result){
					    if(UserFormFactory.isInternetExplorer()){
                            alert(result.data['0']);
                        }
                        else{
                            UserFormFactory.showAlert(result.data['0']);
                        }
						if(result.data['0'] == 'User Created'){
							$state.go('userManagement.userTable', {});
						}
						$location.url('/userManagement');

					})
				}
				else if($scope.createUserForm.$valid && !$scope.selectedAtLeastOneCourse){
					console.log($scope.selectedCourses);
					window.alert("Select at least One Course");

				}
				else{
					angular.forEach($scope.createUserForm.$error, function (field) {
						angular.forEach(field, function(errorField){
							errorField.$setDirty();
						})
					});
				}
			};

			$scope.onAccessLevelChanged = function(){

				$scope.getStates();
				$scope.place = {};

				$scope.newUser.stateId = null;
				$scope.newUser.districtId = null;
				$scope.newUser.blockId = null;

				$scope.createUserForm.state.$setPristine(false);
				$scope.createUserForm.district.$setPristine(false);
				$scope.createUserForm.block.$setPristine(false);
			}

			$scope.$watch('newUser.accessLevel', function(oldValue, newValue){
				if(oldValue !== newValue){
					$scope.onAccessLevelChanged();
				}
			});

			$scope.$watch('newUser.stateId', function(value){
				if(value != null){
					$scope.newUser.district = null;
					$scope.newUser.block = null;

					$scope.getDistricts(value)
				}
			});

			$scope.$watch('newUser.districtId', function(value){
				if(value != null){
					$scope.newUser.block = null;

					$scope.getBlocks(value);
				}
			});

			$scope.$watch('newUser.blockId', function(value){
				if(value != null){

				}
			});

		}])
})()