'use strict';

/**
 * @ngdoc overview
 * @name WA Reporting
 * @description
 * # WA Reporting UI app
 *
 * Main module of the application.
 */
var waReportsApp = angular
	.module('waReports', ['ui.bootstrap', 'ui.validate', 'ngMessages','ngAnimate','ui.router', 'ui.grid', 'ui.grid.exporter','ngMaterial', 'BotDetectCaptcha','ng.deviceDetector','$idle','mdo-angular-cryptography'])
	.run( ['$rootScope', '$state', '$stateParams','$window','$idle',
		function ($rootScope, $state, $stateParams,$window,$idle) {
			$rootScope.$state = $state;
			$rootScope.$stateParams = $stateParams;
			$idle.watch();
			$rootScope.online = navigator.onLine;
			$window.addEventListener("offline", function() {
				$rootScope.$apply(function() {
					$rootScope.online = false;
				});
			}, false);
			$window.addEventListener("online", function() {
				$rootScope.$apply(function() {
					$rootScope.online = true;
				});
			}, false);
		}
	])
	.factory('authorization', ['$http', '$state',
		function($http, $state) {
			return {
				authorize: function() {
					return $http.post(backend_root + 'wa/user/isLoggedIn')
						.then(function(result){
							console.log("You are here")
							if(!result.data){
								$state.go('login', {});
							}
						});
				}
			};
		}
	])
	.factory('authorizationRole', ['$http', '$state',
		function($http, $state) {
			return {
				authorize: function() {
					return $http.post(backend_root + 'wa/user/isLoggedIn')
						.then(function(result){
							console.log("You are here")
							if(!result.data){
								$state.go('login', {});
							}
							else {
								return $http.post(backend_root + 'wa/user/currentUser')
									.then(function(result1){
										if(result1.data.roleName == 'USER'){
											$state.go('reports', {});
										}
									});

							}
						});
				}
			};
		}
	])

	.config(function ($stateProvider, $urlRouterProvider, $httpProvider,captchaSettingsProvider) {
		$stateProvider
		
			.state('userManagement', {
				url: '/userManagement',
				abstract: true,
				templateUrl: 'views/userManagement.html',
				resolve : {
					user : function (authorizationRole) {
						return authorizationRole.authorize();
					}
				}
			})
			.state('userManagement.userTable', {
				url: '',
				templateUrl: 'views/userTable.html',
				resolve : {
					user : function (authorizationRole) {
						return authorizationRole.authorize();
					}
				}
			})
			.state('userManagement.bulkUpload', {
				url: '/bulkUpload',
				templateUrl: 'views/bulkUser.html',
				resolve : {
					user : function (authorizationRole) {
						return authorizationRole.authorize();
					}
				}
			})
			.state('userManagement.createUser', {
				url: '/create',
				templateUrl: 'views/createUser.html',
				resolve : {
					user : function (authorizationRole) {
						return authorizationRole.authorize();
					}
				}
			})
			.state('userManagement.editUser', {
				url: '/edit/{id}',
				templateUrl: 'views/editUser.html',
				resolve : {
					user : function (authorizationRole) {
						return authorizationRole.authorize();
					}
				}
			})

			.state('login', {
				url: '/login',
				templateUrl: 'views/login.html'
			})

			.state('reports', {
				url: '/reports',
				templateUrl: 'views/reports.html',
				resolve : {
					user : function ( authorization) {
						return authorization.authorize();
					}
				}
			})
			
			.state('profile', {
				url: '/profile',
				templateUrl: 'views/profile.html',
				resolve : {
					user : function ( authorization) {
						return authorization.authorize();
					}
				}
			})

			.state('forgotPassword', {
            	url: '/forgotPassword',
            	templateUrl: 'views/forgotPassword.html'
            })

            .state('changePassword', {
                url: '/changePassword',
                templateUrl: 'views/changePassword.html',
				resolve : {
					user : function ( authorization) {
						return authorization.authorize();
					}
				}
            })
		$urlRouterProvider
			.otherwise('/login')

        captchaSettingsProvider.setSettings({
            captchaEndpoint: backend_root + 'botdetectcaptcha',
          })

			
		$httpProvider.defaults.headers.common = {};
		$httpProvider.defaults.headers.post = {};
		$httpProvider.defaults.headers.put = {};
		$httpProvider.defaults.headers.patch = {};
	})
	.config(['$cryptoProvider', function($cryptoProvider) {
		$cryptoProvider.setCryptographyKey('ABCD123');
	}]);