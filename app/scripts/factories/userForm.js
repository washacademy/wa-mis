(function(){
	var waReportsApp = angular
		.module('waReports')
		.factory( 'UserFormFactory', ['$http','$mdDialog','$window','$document','deviceDetector', function($http,$mdDialog,$window,$document,deviceDetector) {
			var users = [];
			var currentUser = {};

			return {
				downloadCurrentUser: function(){
					return $http.get(backend_root + 'wa/user/currentUser');
				},
				setCurrentUser: function(user){
					currentUser = user;
				},
				getCurrentUser: function(){
					return currentUser;
				},

				deactivateUser: function(userId){
					return $http.get(backend_root + 'wa/user/deleteUser/' + userId);
				},

				isLoggedIn: function(){
					return $http.get(backend_root + 'wa/user/isLoggedIn');
				},

				isAdminLoggedIn: function(){
					return $http.get(backend_root + 'wa/user/isAdminLoggedIn');
				},

				getRoles: function() {
					return $http.get(backend_root + 'wa/user/roles');
				},
				logoutUser: function(){
					return $http.get(backend_root + 'wa/logout');
				},

				downloadUsers: function(id){
					return $http.get(backend_root + 'wa/user/list');
				},
				setUsers: function(value){
					users = value;
				},

				getStates: function(){
					return $http.get(backend_root + 'wa/location/states');
				},
				getStatesByService: function(service){
					return $http.get(backend_root + 'wa/location/state/' + service);
				},

				getDistricts: function(stateId){
					return $http.get(backend_root + 'wa/location/districts/' + stateId);
				},

				getBlocks: function(districtId){
					return $http.get(backend_root + 'wa/location/blocks/' + districtId);
				},

				getCircles: function(){
					return $http.get(backend_root + 'wa/location/circles');
				},
				getCirclesByService: function(service){
					return $http.get(backend_root + 'wa/location/circle/' + service);
				},

				getReportsMenu: function(){
				   return $http.get(backend_root + 'wa/user/reportsMenu/');
//                   return $http.get("scripts/json/reportDetails.json");
				},

				getUser: function(id){
					return $http.get(backend_root + 'wa/user/user/' + id);
				},

				getCourseList: function(){
					return $http.get(backend_root + 'wa/user/courses/')
				},

				getUserDto: function(id){
					return $http.get(backend_root + 'wa/user/dto/' + id);
				},

				showAlert : function(message) {
                    $mdDialog.show(
                      $mdDialog.alert()
                        .parent(angular.element(document.querySelector('#popupContainer')))
                        .clickOutsideToClose(true)
                        .title('MIS Alert!!')
                        .textContent(message)
                        .ariaLabel('Alert Dialog Demo')
                        .ok('Ok!')
                    );
                },

				showAlert2 : function (message) {
					var confirmAlert = $mdDialog.show(
						$mdDialog.alert()
							.parent(angular.element(document.querySelector('#popupContainer')))
							//.clickOutsideToClose(true)
							.title('Session Timeout')
							.textContent(message)
							.ariaLabel('Alert Dialog Demo')
							.ok('OK')
					);
					return confirmAlert;
				},

                isInternetExplorer : function(){
                    if(deviceDetector.browser == "ie" && (deviceDetector.browser_version = "10.0"))
                     return true;
                    else
                     return false;
                }




			};
		}]);
})();