(function(){
	var waReportsApp = angular
		.module('waReports')
		.controller("UserTableController", ['$scope', '$state', 'UserTableFactory', function($scope, $state, UserTableFactory){

			$scope.numPerPageList = [10, 20];

			$scope.init = function(){
				$scope.currentPageNo = 1;
				$scope.numPerPage = $scope.numPerPageList[0];
			}

			$scope.init();

			UserTableFactory.clearAllUsers();

			// if(UserTableFactory.getAllUsers().length == 0){
			$scope.waiting = UserTableFactory.getAllUsers().length == 0;
			UserTableFactory.getUsers()
			.then(function(result){
				UserTableFactory.setUsers(result.data)
				$scope.init();
				$scope.waiting = false;
			});

			$scope.resetPage = function(){
				$scope.currentPageNo = 1;
			}
			$scope.resetDistrict = function(){
				$scope.districtName = "";
			}
			$scope.resetBlock = function(){
				$scope.blockName = "";
			}

			$scope.getAllUsers = function(){
				return UserTableFactory.getAllUsers();
			}

			$scope.crop = function(name){
				if(name.length > 13){
					return name.substring(0, 10) + "..."
				}
				return name;
			}

			$scope.getUniqueAccessTypes = function(){
				var aT = [];
				var users = $scope.getAllUsers();
				for(var i=0;i<users.length;i++){
					if(aT.indexOf(users[i].accessType)==-1) {
						aT.push(users[i].accessType);
					}
				}
				return aT;
			}
			$scope.setUniqueAccessTypes = function(accessType){
                $scope.accType = accessType;
            }
			$scope.getUniqueAccessLevels = function(){
				var aL = [];
				var users = UserTableFactory.getAllUsers();
				for(var i=0;i<users.length;i++){
					if(aL.indexOf(users[i].accessLevel)==-1) {
						aL.push(users[i].accessLevel);
					}
				}
				return aL;
			}
			$scope.setUniqueAccessLevels = function(accessLevel){
				$scope.accLevel = accessLevel;
			}
			$scope.getUniqueStates = function(){
				var states = [];
				var users = UserTableFactory.getAllUsers();
				for(var i=0;i<users.length;i++){
					if((users[i].state!="")&&(states.indexOf(users[i].state)==-1)) {
						states.push(users[i].state);
					}
				}
			return states;
			}
			$scope.setUniqueStates = function(state){
				$scope.stateName = state;
			}
			$scope.$watch('stateName', $scope.resetDistrict);
			$scope.$watch('stateName', $scope.resetBlock);
			$scope.getUniqueDistricts = function(){
				var districts = [];
				var users = UserTableFactory.getAllUsers();
				for(var i=0;i<users.length;i++){
					if((((users[i].state).toLowerCase()).indexOf(($scope.stateName).toLowerCase() > -1 ||''))&&
					(((users[i].block).toLowerCase).indexOf(($scope.blockName).toLowerCase() > -1 ||''))&&
					((users[i].district!="")&&(districts.indexOf(users[i].district)==-1))) {
						districts.push(users[i].district);
					}
				}
			return districts;
			}
			$scope.setUniqueDistricts = function(district){
				$scope.districtName = district;
			}
			$scope.$watch('districtName', $scope.resetBlock);
			$scope.getUniqueBlocks = function(){
				var blocks = [];
				var users = UserTableFactory.getAllUsers();
				for(var i=0;i<users.length;i++){
					if((((users[i].state).toLowerCase()).indexOf(($scope.stateName).toLowerCase() > -1 ||'')) &&
					(((users[i].district).toLowerCase()).indexOf(($scope.districtName).toLowerCase() > -1 ||''))&&
					((users[i].block!="")&&(blocks.indexOf(users[i].block)==-1))) {
						blocks.push(users[i].block);
					}
				}
			return blocks;
			}
			$scope.setUniqueBlocks = function(block){
				$scope.blockName = block;
			}

			$scope.exists = function(value){
				return value != '';
			}

			$scope.resetFilters = function(){
				$scope.stateName = '';
				$scope.districtName = '';
				$scope.blockName = '';
				$scope.accType = '';
				$scope.accLevel = '';

				$scope.filterText = '';

				$scope.sorter = 'id';
				$scope.reverse = false;
			}
			$scope.resetFilters();

			$scope.$watch('numPerPage', $scope.resetPage);
//            $scope.$watch('filterData', function(value){
//            console.log(value);
//            });
			
			$scope.dropdownOpen =function(){
				return (
					$scope.exists($scope.accType) ||
					$scope.exists($scope.accLevel) ||
					$scope.exists($scope.stateName) ||
					$scope.exists($scope.districtName) ||
					$scope.exists($scope.blockName)
				);
			}

			$scope.search = function (row) {
			    if(row.state == null)
			    {
			        row.state = "";
			    }
			    if(row.district == null)
			    {
			        row.district = "";
			    }
			    if(row.block == null)
			    {
			        row.block = "";
			    }
				return (((row.name).toLowerCase()).indexOf(($scope.filterText).toLowerCase() || '') > -1 ||
					((row.username).toLowerCase()).indexOf(($scope.filterText).toLowerCase() || '') > -1 ||
					((row.phoneNumber).toLowerCase()).indexOf(($scope.filterText).toLowerCase()  || '')> -1 ||
					((row.email).toLowerCase()).indexOf(($scope.filterText).toLowerCase() || '')> -1  ||
					((row.accessLevel).toLowerCase()).indexOf(($scope.filterText).toLowerCase()  || '')> -1 ||
					((row.state).toLowerCase()).indexOf(($scope.filterText).toLowerCase()  || '')> -1 ||
					((row.district).toLowerCase()).indexOf(($scope.filterText).toLowerCase()  || '')> -1 ||
					((row.block).toLowerCase()).indexOf(($scope.filterText).toLowerCase() || '') > -1 ) &&(
					((row.accessType).toLowerCase()).indexOf(($scope.accType).toLowerCase()  ||'')> -1 )&&(
					((row.accessLevel).toLowerCase()).indexOf(($scope.accLevel).toLowerCase() ||'') > -1 )&&(
					((row.state).toLowerCase()).indexOf(($scope.stateName).toLowerCase()  ||'') > -1)&&(
					((row.district).toLowerCase()).indexOf(($scope.districtName).toLowerCase()  ||'')> -1 )&&(
					((row.block).toLowerCase()).indexOf(($scope.blockName).toLowerCase()  ||'')> -1
						);
			};
			$scope.sorter = 'id';
			$scope.sort_by = function(newSortingOrder) {
					if ($scope.sorter == newSortingOrder)
						$scope.reverse = !$scope.reverse;

					$scope.sorter = newSortingOrder;
			}
			

			$scope.editUser = function(userId){
				console.log("edit");
				$state.go('userManagement.editUser');
			}

			$scope.deleteUser = function(user){
				console.log("delete");
				console.log(user);
			}
		}])
})()