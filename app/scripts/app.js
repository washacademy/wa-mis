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
	.module('waReports', ['ui.bootstrap', 'ui.validate', 'ngMessages','ngAnimate','ui.router', 'ui.grid', 'ui.grid.exporter','ngMaterial', 'BotDetectCaptcha','ng.deviceDetector'])
	.run( ['$rootScope', '$state', '$stateParams','$window',
		function ($rootScope, $state, $stateParams,$window) {
			$rootScope.$state = $state;
			$rootScope.$stateParams = $stateParams;
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
	.config(function ($stateProvider, $urlRouterProvider, $httpProvider,captchaSettingsProvider) {
		$stateProvider
		
			.state('userManagement', {
				url: '/userManagement',
				abstract: true,
				templateUrl: 'views/userManagement.html',
			})
			.state('userManagement.userTable', {
				url: '',
				templateUrl: 'views/userTable.html'
			})
			.state('userManagement.bulkUpload', {
				url: '/bulkUpload',
				templateUrl: 'views/bulkUser.html'
			})
			.state('userManagement.createUser', {
				url: '/create',
				templateUrl: 'views/createUser.html'
			})
			.state('userManagement.editUser', {
				url: '/edit/{id}',
				templateUrl: 'views/editUser.html'
			})

			.state('login', {
				url: '/login',
				templateUrl: 'views/login.html',
			})

			.state('reports', {
				url: '/reports',
				templateUrl: 'views/reports.html',
			})
			
			.state('profile', {
				url: '/profile',
				templateUrl: 'views/profile.html',
			})

			.state('forgotPassword', {
            	url: '/forgotPassword',
            	templateUrl: 'views/forgotPassword.html',
            })

            .state('changePassword', {
                url: '/changePassword',
                templateUrl: 'views/changePassword.html',
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
	});