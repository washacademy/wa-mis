(function(){
	var waReportsApp = angular
		.module('waReports')
		.factory( 'UserTableFactory', ['$http', function($http) {
			var users = []
			// $http.get(backend_root + 'wa/user/list')
			// .then(function(data){
			// 	console.log(JSON.stringify(data));
			// })
			return {
				getUsers: function() {
					return $http.get(backend_root + 'wa/user/tableList')
					// return $http.get('newUser.json');
				},
				setUsers: function(values){
					users = values;
				},
				getAllUsers: function() {   
					return users;
				},
				clearAllUsers: function(){
					users = [];
				}
			};
		}]);
})();