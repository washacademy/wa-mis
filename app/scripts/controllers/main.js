 (function(){
	var waReportsApp = angular
		.module('waReports')
		.controller("MainController", ['$scope', '$state', '$http', 'UserFormFactory','$rootScope','$window',function($scope, $state, $http, UserFormFactory,$rootScope,$window){

			$scope.logoutUrl = backend_root + "wa/logout";
			$scope.ondropdown = false;
			$scope.breadCrumbDict = {
				'userManagement.userTable': [
					{
						'name': 'User Management',
						'sref': 'userManagement.userTable',
						'active': false,
					}
				],
				'userManagement.createUser': [
					{
						'name': 'User Management',
						'sref': 'userManagement.userTable',
						'active': true,
					},
					{
						'name': 'Create User',
						'sref': 'userManagement.createUser',
						'active': false,
					},
				], 
				'userManagement.editUser': [
					{
						'name': 'User Management',
						'sref': 'userManagement.userTable',
						'active': true,
					},
					{
						'name': 'Edit User',
						'sref': 'userManagement.editUser',
						'active': false,
					},
				], 
				'userManagement.bulkUpload': [
					{
						'name': 'User Management',
						'sref': 'userManagement.userTable',
						'active': true,
					},
					{
						'name': 'Bulk Upload',
						'sref': 'userManagement.bulkUpload',
						'active': false,
					},
				],
				'profile': [
					{
						'name': 'Profile',
						'sref': 'profile',
						'active': false,
					},
				],
				'reports': [
					{
						'name': 'Reports',
						'sref': 'reports',
						'active': false,
					},
				],
				'login': [
					{
						'name': 'Login',
						'sref': 'login',
						'active': false,
					},
				],
				'forgotPassword': [
                	{
                		'name': 'ForgotPassword',
                		'sref': 'forgotPassword',
                		'active': false,
                	},
                ],
                'changePassword': [
                    {
                        'name': 'ChangePassword',
                        'sref': 'changePassword',
                        'active': false,
                    },
                ]
			}

            $scope.child = {};

			$scope.checkLogin = function(){
				return (($state.current.name=="login" || $state.current.name=="forgotPassword" || !$scope.currentUser));
			};
			$scope.checkProfile = function(){
				return (($state.current.name=="profile" || $state.current.name=="changePassword" ));
			};

			UserFormFactory.downloadCurrentUser()
				.then(function(result){
					UserFormFactory.setCurrentUser(result.data);
					$scope.currentUser = UserFormFactory.getCurrentUser();
					window.localStorage.setItem('prev_userId', $scope.currentUser.userId);
					console.log($scope.currentUser);
				})

			$scope.getBreadCrumb = function(state){
				return $scope.breadCrumbDict[state];
			}

			$scope.getTitle = function(state){
				var states = $scope.getBreadCrumb(state)
				if(states != null){
					return states[states.length - 1].name;
				}
				else{
					return "Please Wait...";
				}
			}

			$scope.activeTab = function(tabName){
				if($state.current.name.indexOf(tabName) > -1){
				    return true;
				}
				else
				    return false;
			}

			$scope.goToLogout = function () {

				UserFormFactory.logoutUser().then(function(result){
					if(result.data){
						window.localStorage.setItem('logged_in', false);
						UserFormFactory.downloadCurrentUser().then(function(result){
							UserFormFactory.setCurrentUser(result.data);
							$scope.currentUser = UserFormFactory.getCurrentUser();
						});
						$scope.show = false;
						$state.go('login');
					}
				});

			};

			$scope.$watch('online', function(newStatus) {
				if($rootScope.online){
				}
				else{
					if (UserFormFactory.isInternetExplorer()) {
						alert("You are offline");
						$scope.goToLogout();
						return;
					} else {
						UserFormFactory.showAlert("You are offline");
						$scope.goToLogout();
						return;
					}
				}
			});

			// $scope.hovered = function () {
			// 	$scope.ondropdown = true;
			// 	$scope.show = !$scope.show;
			// }
			// $scope.ondropdownfn = function () {
			// 	$scope.ondropdown = false;
			// }
			// $scope.removed = function () {
			// 	$scope.show = false;
			// }
			$window.addEventListener('click', function() {
				localStorage.setItem('lastEventTime', new Date().getTime());
				if($scope.show&&!$scope.ondropdown){
					$scope.removed();
				}
			});

			$scope.$on('$userIdle', function () {
				if(new Date().getTime()-localStorage.lastEventTime>1800000){
					if ($scope.currentUser){

						if(UserFormFactory.isInternetExplorer()){
							alert("Sorry, Your Session timed out after a long time of inactivity. Please, login again");
							$scope.goToLogout();
							return;
						}
						else{
							var a= UserFormFactory.showAlert2("Sorry, Your Session timed out after a long time of inactivity. Please, login in again");
							a.then(function () {
								$scope.goToLogout();
								return;
							});

						}
					}}

			});

			// $scope.breadCrumb = ['User Management', 'Create User'];

		}
	])
	.config(function ($idleProvider, $keepaliveProvider) {
		$idleProvider.setIdleTime(1800);
		$idleProvider.setTimeoutTime(10);
	})
 }
)()