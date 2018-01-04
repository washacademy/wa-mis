(function(){
	var waReportsApp = angular
		.module('waReports')
		.controller("BulkUserController", ['$scope', '$state', 'UserFormFactory', '$http', function($scope, $state, UserFormFactory, $http){
			
			UserFormFactory.isLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
			})

			var formdata = new FormData();
			$scope.getTheFiles = function ($files) {
				angular.forEach($files, function (value, key) {
					formdata.append(key, value);
				});
			};

			$scope.uploadFile = function(){
				var file = $scope.myFile;
				var fd = new FormData();

				if(file == null){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please select a CSV file")
                         return;
                    }
                    else{
                        UserFormFactory.showAlert("Please select a CSV file")
                        return;
                    }
				}

				fd.append('bulkCsv', file);
	//We can send anything in name parameter, 
//it is hard coded to abc as it is irrelavant in this case.
				var uploadUrl = backend_root + "wa/admin/uploadFile";
				$http.post(uploadUrl, fd, {
					transformRequest: angular.identity,
					headers: {'Content-Type': undefined}
				})
				.then(function(result){
					$scope.listErrors(result.data)
				})
				// .error(function(){
				// });
			}

			$scope.downloadTemplateUrl = backend_root + 'wa/admin/getBulkDataImportCSV'
			$scope.downloadTemplate = function(){
				$http({
					url: $scope.downloadTemplateUrl, 
					method: "GET",
				});
			}

			$scope.errorsObj = [];
            $scope.uploadedFlag = false;
			$scope.listErrors = function(errors){
				$scope.errorsObj = [];
				$scope.uploadedFlag = true;
				for(i in errors){
					var error = {};
					error.line = i;
					error.name = errors[i];

					$scope.errorsObj.push(error);
				}
			}
		}])
		// .directive('ngFiles', ['$parse', function ($parse) {
		// 	function fn_link(scope, element, attrs) {
		// 		var onChange = $parse(attrs.ngFiles);
		// 		element.on('change', function (event) {
		// 			onChange(scope, { $files: event.target.files });
		// 		});
		// 	};

		// 	return {
		// 		link: fn_link
		// 	}
		// } ])
		.directive('fileModel', ['$parse', function ($parse) {
			return {
				restrict: 'A',
				link: function(scope, element, attrs) {
					var model = $parse(attrs.fileModel);
					var modelSetter = model.assign;

					element.bind('change', function(){
						scope.$apply(function(){
							modelSetter(scope, element[0].files[0]);
						});
					});
				}
			};



		}])
})()