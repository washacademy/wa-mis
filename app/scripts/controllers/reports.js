(function(){
	var waReportsApp = angular
		.module('waReports')
		.controller("ReportsController", ['$scope', '$state', '$http', 'UserFormFactory','$window','$q','uiGridConstants','uiGridExporterConstants','uiGridExporterService', function($scope, $state, $http, UserFormFactory,$window,$q,uiGridConstants,uiGridExporterConstants,uiGridExporterService){

			UserFormFactory.isLoggedIn()
			.then(function(result){
				if(!result.data){
					$state.go('login', {});
				}
				else{
					UserFormFactory.downloadCurrentUser()
					.then(function(result){
						UserFormFactory.setCurrentUser(result.data);

						$scope.getStates();
					})
				}
			})

			var reportRequest = {};
            $scope.sundays = [];
 			$scope.reports = [];
 			$scope.dt1 = null;
 			$scope.dt2 = null;
			$scope.states = [];
			$scope.districts = [];
			$scope.blocks = [];
			$scope.circles = [];
			$scope.datePickerContent = "Select Month";
			$scope.reportDisplayType = 'TABLE';
			$scope.gridOptions = {};
			$scope.gridOptions1 = {};
			$scope.WA_Performance_Column_Definitions = [];
			$scope.WA_Cumulative_Column_Definitions = [];
			$scope.WA_Subscriber_Column_Definitions = [];
			$scope.WA_Anonymous_Column_Definitions = [];
			$scope.hideGrid = true;
			$scope.hideDate = true;
			$scope.hideMessageMatrix = true;
			$scope.showEmptyData = false;
			$scope.content = "There is no data available for the selected inputs";
			$scope.periodType = ['Year','Month','Quarter', 'Custom Range'];
			$scope.quarterType = ['Q1 (Jan to Mar)','Q2 (Apr to Jun)','Q3 (Jul to Sep)', 'Q4 (Oct to Dec)'];
			$scope.periodDisplayType = "";
			$scope.dataPickermode = "";
			$scope.periodTypeContent = "";
            $scope.dateFormat = '';
            $scope.reportBreadCrumbData = [];
            $scope.headerFromDate = '';
            $scope.headerToDate = '';
            $scope.matrixContent1 = '';
            $scope.matrixContent2 = '';
            var parentScope = $scope.$parent;
            parentScope.child = $scope;

            $scope.popup2 = {
                opened: false
            };

            $scope.popup3 = {
                opened: false
            };

            $scope.open2 = function() {
                $scope.popup2.opened = true;
            };

            $scope.open3 = function() {
                 $scope.popup3.opened = true;
            };

            $scope.changeStartDate = function() {
                $scope.dt1=this.dt1;
            };

            $scope.changeEndDate = function() {
                $scope.dt2=this.dt2;
            };

            $scope.changeMonth = function() {
                $scope.dt=this.dt;
            };

            $scope.open3 = function() {
                $scope.popup3.opened = true;
            };

            $scope.setDate = function(year, month, day) {
                $scope.dt1 = new Date(year, month, day);
            };

            $scope.setDate = function(year, month, day) {
                $scope.dt2 = new Date(year, month, day);
            };

            $scope.hideDates = function(){
                $scope.hideDate = true;
            }

            $scope.showDates = function(){
                $scope.hideDate = false;
            }

			$scope.disableReportCategory = function(){
				return $scope.reports[0] == null;
			}
			$scope.disableReport = function(){
				return $scope.reportCategory == null;
			}

			$scope.disableDate = function(){
				return $scope.report == null || $scope.report.reportEnum == null;
			}
			$scope.disableState = function(){
				return $scope.states[0]  == null || $scope.userHasState() || $scope.report == null;
			}

			$scope.disableStateIfNoLocation = function(){
                return $scope.states[0]  == null || $scope.userHasState() || $scope.report == null;
            }

			$scope.disableDistrict = function(){
				return $scope.districts[0]  == null || $scope.userHasDistrict();	
			}
			$scope.disableBlock = function(){
				return $scope.blocks[0]  == null || $scope.userHasBlock();
			}
			$scope.disableCircle = function(){
				return $scope.circles[0]  == null || $scope.userHasOneCircle();
			}

			$scope.disablePeriodType = function(){
            	return ($scope.report == null) || (($scope.report!=null) && ($scope.report.name == 'Cumulative Summary Report'));
            }


			$scope.userHasState = function(){
				return UserFormFactory.getCurrentUser().stateId != null;
			}
			$scope.userHasDistrict = function(){
				return UserFormFactory.getCurrentUser().districtId != null;
			}
			$scope.userHasBlock = function(){
				return UserFormFactory.getCurrentUser().blockId != null;
			}
			$scope.userHasOneCircle = function(){
				return $scope.circles.length == 1;
			}
			
			$scope.reportsLoading = true;
			UserFormFactory.getReportsMenu()
			.then(function(result){
				$scope.reports = result.data;
				$scope.reportsLoading = false;
				if($scope.reports.length == 1){
				    $scope.selectReportCategory($scope.reports[0]);
				}
			})

			$scope.reportCategory=null;

			$scope.selectReportType = function(item){
			    $scope.reportDisplayType = item;
			    $scope.hideGrid = true;
			    $scope.showEmptyData = false;
			    $scope.hideMessageMatrix = true;
			}

			$scope.selectPeriodType = function(item){
			    $scope.finalDateOptions = {};
                $scope.periodDisplayType = item;
                $scope.dt1 = null;
                $scope.dt2 = null;
                $scope.quarterDisplayType = '';
                $scope.hideGrid = true;
                $scope.hideMessageMatrix = true;
                $scope.showEmptyData = false;
                if($scope.periodDisplayType == 'Year' || $scope.periodDisplayType == 'Quarter' ){
                    $scope.periodTypeContent = "Select Year";
                    $scope.dateFormat = "yyyy";
                    $scope.datePickerOptions.minMode = '';
                    $scope.datePickerOptions.datepickerMode = 'year';
                    $scope.datePickerOptions.minMode = 'year';
                    $scope.datePickerOptions.maxDate = new Date().setYear(new Date().getFullYear() -1);
                }
                if($scope.periodDisplayType == 'Month'){
                    $scope.periodTypeContent = "Select Month";
                    $scope.dateFormat = "yyyy-MM";
                    $scope.datePickerOptions.minMode = '';
                    $scope.datePickerOptions.datepickerMode = 'month';
                    $scope.datePickerOptions.minMode ='month';
                    $scope.datePickerOptions.maxDate = new Date().setMonth(new Date().getMonth() -1);
                }
                if($scope.periodDisplayType == 'Custom Range'){
                    $scope.periodTypeContent = "Start Date";
                    $scope.dateFormat = "yyyy-MM-dd";
                    delete $scope.datePickerOptions.datepickerMode;
                    $scope.datePickerOptions.minMode = '';
                    $scope.datePickerOptions.maxDate = new Date().setDate(new Date().getDate() - 1);
                }

            }

            $scope.selectQuarterType = function(item){
                $scope.quarterDisplayType = item;
                $scope.hideGrid = true;
                $scope.hideMessageMatrix = true;
                $scope.showEmptyData = false;
            }


			$scope.selectReportCategory = function(item){
				$scope.reportCategory = item.name;
				$scope.reportNames = item.options;

				$scope.report = null;
				
				if(!$scope.userHasState()){
					$scope.clearState();
				}
				if(!$scope.userHasDistrict()){
					$scope.clearDistrict();
				}
				if(!$scope.userHasBlock()){
					$scope.clearBlock();
				}
				$scope.clearCircle();
				$scope.dt == null;
				$scope.periodDisplayType = '';
				$scope.dt1 = null;
				$scope.dt2 = null;
				$scope.hideGrid = true;
				$scope.hideMessageMatrix = true;
				$scope.showEmptyData = false;
				$scope.clearFile();

				$scope.getStates();
				$scope.getCircles();
			}

			$scope.selectReport = function(item){
				$scope.report = item;
				if(!$scope.userHasState()){
					$scope.clearState();
				}
				if(!$scope.userHasDistrict()){
					$scope.clearDistrict();
				}
				if(!$scope.userHasBlock()){
					$scope.clearBlock();
				}
				$scope.clearCircle();
				$scope.clearFile();
				$scope.dt = null;
				$scope.dt1 = null;
                $scope.dt2 = null;
                $scope.setDate();
				$scope.setDateOptions();
				if($scope.userHasOneCircle()){
                	$scope.selectCircle($scope.circles[0]);
                }
                else
                    $scope.periodType = ['Year','Month','Quarter', 'Custom Range'];
                if(($scope.reportCategory == 'Wash Academy Reports') &&  (angular.lowercase($scope.report.name).indexOf(angular.lowercase("rejected")) > -1)  ){
                	$scope.datePickerContent = "Select Week";
                }
                else
                    $scope.datePickerContent = "Select Month";
                $scope.periodDisplayType = '';
                $scope.dt1 = null;
                $scope.dt2 = null;
                $scope.hideGrid = true;
                $scope.hideMessageMatrix = true;
                $scope.showEmptyData = false;
                if($scope.report.name == 'Cumulative Summary Report'){
                    $scope.dateFormat = 'yyyy-MM-dd';
                }
                $scope.getCircles();
                $scope.getStates();
            }

			$scope.crop = function(name){
				if(name == null){
					return "";
				}
				if(name.length > 14){
					return name.substring(0, 13) + "..."
				}
				return name;
			}


			$scope.cropAggregate = function(name){
                if(name == null){
                    return "";
                }
                if(name.length > 30){
                    return name.substring(0, 27) + "..."
                }
                return name;
            }

            $scope.cropState = function(name){
                if(name == null){
                    return "";
                }
                if(name.length > 12){
                    return name.substring(0, 10) + ".."
                }
                return name;
            }

			$scope.isCircleReport = function(){
				return $scope.report != null && (($scope.report.reportEnum == 'WA_Circle_Wise_Anonymous_Users')||($scope.report.reportEnum == 'WA_Anonymous_Users_Summary'));
			}

            $scope.isAggregateReport = function(){
            	return $scope.report != null && $scope.report.icon == 'images/drop-down-2.png';
            }

            $scope.isLineListingReport = function(){
                return $scope.report != null && $scope.report.icon == 'images/drop-down-3.png';
            }

			$scope.reportTypes = ['TABLE'];

			$scope.getStates = function(){
            	$scope.statesLoading = true;
            	$scope.states = [];
            	$scope.clearState();
                return UserFormFactory.getStates()
                .then(function(result){
                $scope.states = result.data;
                                    $scope.districts = [];
                                    $scope.blocks = [];
                                    $scope.statesLoading = false;

                                    if($scope.userHasState()){
                                        $scope.selectState($scope.states[0]);
                                    }
                                });

            }
			
			$scope.getDistricts = function(stateId){
				$scope.districtsLoading = true;
				return UserFormFactory.getDistricts(stateId)
				.then(function(result){
					$scope.districts = result.data;
					$scope.blocks = [];
					$scope.districtsLoading = false;

					if($scope.userHasDistrict()){
						$scope.selectDistrict($scope.districts[0]);
					}
				});
			}

			$scope.getBlocks =function(districtId){
				$scope.blocksLoading = true;
				return UserFormFactory.getBlocks(districtId)
				.then(function(result){
					$scope.blocks = result.data;
					$scope.blocksLoading = false;

					if($scope.userHasBlock()){
						$scope.selectBlock($scope.blocks[0]);
					}
				});
			}

			$scope.getCircles = function(){
            				$scope.circlesLoading = true;
            				return UserFormFactory.getCircles()
            				.then(function(result){
            					$scope.circles = result.data;

            					$scope.circlesLoading = false;

            					if($scope.userHasOneCircle()){
            						$scope.selectCircle($scope.circles[0]);
            					}
            				});
            }

			$scope.isClickAllowed=function(name){
			    if(name == 'BAR GRAPH' || name == 'PIE CHART'){
			        return false;
			     }
			    else {
			         true
			    }
			}

			$scope.minModePick = function(){
            				    if(($scope.periodDisplayType == 'Year')||($scope.periodDisplayType == 'Quarter'))
                                {
                                    return "year";
                                }
                                else if($scope.periodDisplayType == 'Month')
                                {
                                    return "month";
                                }
                                else return "day";
            }

			$scope.setDateOptions =function(){
			    if($scope.isAggregateReport()){
			        var minDate = new Date(2016, 11, 01);
			    }
                else{
                    var minDate = new Date(2015, 09, 01);
                }
				if($scope.report != null){
					minDate = new Date(2015, 10, 01);
				}
				if($scope.report != null && $scope.report.reportEnum == 'WA_Cumulative_Inactive_Users'){
                	minDate = new Date(2017, 04, 30);
                }
                if($scope.report != null && $scope.report.reportEnum == 'WA_Circle_Wise_Anonymous_Users'){
                    minDate = new Date(2017, 04, 30);
                }

                //In case of change in minDate for rejection reports, please change startMonth and startDate variable accordingly
                if($scope.report != null && $scope.report.reportEnum == 'WA_Swachchagrahi_Import_Rejects'){
                    minDate = new Date(2017,2, 01);
                }
                if($scope.report != null && $scope.report.reportEnum == 'WA_Cumulative_Course_Completion'){
                    minDate = new Date(2017,2, 01);
                }
				if(!$scope.isCircleReport() && $scope.state != null && Date.parse($scope.state.serviceStartDate) > minDate){
					minDate = $scope.state.serviceStartDate;
				}
				if($scope.isCircleReport() && $scope.circle != null && Date.parse($scope.circle.serviceStartDate) > minDate){
					minDate = $scope.circle.serviceStartDate;
				}

                $scope.datePickerOptions = {
                    formatYear: 'yyyy',
                    maxDate: new Date(),
                    minDate: minDate,
                    startingDay: 1
                };

				$scope.dateOptions = {
					minMode: 'month',
					dateDisabled: disabled,
					formatYear: 'yyyy',
					maxDate: new Date().setMonth(new Date().getMonth() -1),
					minDate: minDate,
					startingDay: 1
				};

				$scope.endDatePickerOptions = {
                    formatYear: 'yyyy',
                    maxDate: new Date() - 1,
                    minDate: minDate,
                    startingDay: 1
                };
			}

			$scope.selectState = function(state){
				if(state != null){
					$scope.getDistricts(state.stateId);
					$scope.clearState();
					$scope.state = state;
				}
				$scope.periodDisplayType = '';
                $scope.dt1 = null;
                $scope.dt2 = null;
                $scope.hideGrid = true;
                $scope.hideMessageMatrix = true;
                $scope.showEmptyData = false;
				$scope.setDateOptions();
				$scope.clearFile();
			}
			$scope.clearState = function(){
				$scope.state = null;
				$scope.clearDistrict();
				$scope.districts = [];
				$scope.periodDisplayType = '';
                $scope.dt1 = null;
                $scope.dt2 = null;
                $scope.hideGrid = true;
                $scope.hideMessageMatrix = true;
                $scope.showEmptyData = false;
                $scope.setDateOptions();
                $scope.clearFile();
			}
			$scope.selectDistrict = function(district){
				if(district != null){
					$scope.getBlocks(district.districtId);
					$scope.clearDistrict()
					$scope.district = district;
				}
                $scope.periodDisplayType = '';
				$scope.dt1 = null;
				$scope.dt2 = null;
				$scope.hideGrid = true;
				$scope.hideMessageMatrix = true;
				$scope.showEmptyData = false;
				$scope.clearFile();
			}
			$scope.clearDistrict = function(){
				$scope.district = null;
				$scope.clearBlock();
				$scope.blocks = [];
				$scope.periodDisplayType = '';
                $scope.dt1 = null;
                $scope.dt2 = null;
                $scope.hideGrid = true;
                $scope.hideMessageMatrix = true;
                $scope.showEmptyData = false;
                $scope.setDateOptions();
                $scope.clearFile();
			}
			$scope.selectBlock = function(block){
				if(block != null){
					$scope.clearBlock();
					$scope.block = block;
				}
				$scope.periodDisplayType = '';
                $scope.dt1 = null;
                $scope.dt2 = null;
                $scope.hideGrid = true;
                $scope.hideMessageMatrix = true;
                $scope.showEmptyData = false;
                $scope.clearFile();
			}
			$scope.clearBlock = function(){
				$scope.block = null;
				$scope.periodDisplayType = '';
                $scope.dt1 = null;
                $scope.dt2 = null;
                $scope.hideGrid = true;
                $scope.hideMessageMatrix = true;
                $scope.showEmptyData = false;
                $scope.setDateOptions();
                $scope.clearFile();
			}
			$scope.selectCircle = function(circle){
				if(circle != null){
//					$scope.clearBlock();
					$scope.circle = circle;
				}
                $scope.periodDisplayType = '';
				$scope.dt1 = null;
				$scope.dt2 = null;
				$scope.hideGrid = true;
				$scope.showEmptyData = false;
				$scope.clearFile();
			}
			$scope.clearCircle = function(){
				$scope.circle = null;
			}

			$scope.waiting = false;
			$scope.wasSundaySelected = false;

			$scope.fileName = "";

			$scope.$watch('dt', function(newDate){
                if($scope.wasSundaySelected){
                $scope.format = 'yyyy-MM-dd';
                $scope.wasSundaySelected = false;
                 return;
                }
                $scope.format = 'yyyy-MM';
			    if(($scope.reportCategory == 'Wash Academy Reports') &&  (angular.lowercase($scope.report.name).indexOf(angular.lowercase("rejected")) > -1) && $scope.dt != null) {
			    	 $scope.getSundays($scope.dt);
                     $scope.sundaysTable = true;
			    	 $scope.popup1.opened = true;
			    }

                if(!$scope.wasSundaySelected){
                    if((newDate != null) && newDate.getDate() == 1){
                        $scope.dt = new Date($scope.dt.getFullYear(), $scope.dt.getMonth() + 1, 0, 23, 59, 59);
                     }
                }
			});

			$scope.serviceStarted = function(state){
				if($scope.dt == null){
					return true;
				}
				return new Date(state.serviceStartDate) < $scope.dt ;
			}

			$scope.getReport = function(){

                /*if($scope.reportCategory == null){
                    if(UserFormFactory.isInternetExplorer()){
                        alert("Please select a report category")
                         return;
                    }
                    else{
                        UserFormFactory.showAlert("Please select a report category")
                        return;
                    }
                }*/

				if($scope.report == null){
				    if(UserFormFactory.isInternetExplorer()){
                        alert("Please select a report")
                         return;
                    }
                    else{
                        UserFormFactory.showAlert("Please select a report")
                        return;
                    }
				}
				/*if($scope.dt == null && (angular.lowercase($scope.report.name).indexOf(angular.lowercase("rejected")) > -1) ){
					if(UserFormFactory.isInternetExplorer()){
                        alert("Please select a week")
                         return;
                    }
                    else{
                        UserFormFactory.showAlert("Please select a week")
                        return;
                    }

				}*/

				if($scope.dt == null && (!$scope.isAggregateReport() )){
                	if(UserFormFactory.isInternetExplorer()){
                         alert("Please select a month")
                         return;
                    }
                    else{
                        UserFormFactory.showAlert("Please select a month")
                        return;
                    }
				}
				else if($scope.periodDisplayType == '' && ($scope.isAggregateReport() )
				&& ($scope.report.name != 'Cumulative Summary Report')){
                   if(UserFormFactory.isInternetExplorer()){
                       alert("Please select a period type")
                       return;
                   }
                   else{
                       UserFormFactory.showAlert("Please select a period type")
                       return;
                   }
                }
				else if($scope.dt1 == null && ($scope.isAggregateReport()) && ($scope.periodDisplayType != 'Custom Range' && ($scope.periodDisplayType != 'Quarter') && ($scope.report.name != 'Cumulative Summary Report')) ){
                    if(UserFormFactory.isInternetExplorer()){
                          alert("Please select a " +  $scope.periodDisplayType)
                          return;
                    }
                    else{
                          UserFormFactory.showAlert("Please select a " +  $scope.periodDisplayType)
                          return;
                    }
                }
                else if($scope.dt1 == null && ($scope.isAggregateReport() ) && ($scope.periodDisplayType == 'Custom Range')){
                   if(UserFormFactory.isInternetExplorer()){
                      alert("Please select a start date")
                      return;
                   }
                   else{
                      UserFormFactory.showAlert("Please select a start date")
                      return;
                   }
                }
                 else if($scope.dt1 == null && ($scope.isAggregateReport() ) && ($scope.periodDisplayType == 'Quarter')){

                   if(UserFormFactory.isInternetExplorer()){
                         alert("Please select a year")
                         return;
                   }
                   else{
                     UserFormFactory.showAlert("Please select a year")
                     return;
                   }

                }
                else if($scope.dt2 == null && ($scope.periodDisplayType == 'Custom Range' || $scope.report.name == 'Cumulative Summary Report')){
                   if(UserFormFactory.isInternetExplorer()){
                         alert("Please select an end date")
                         return;
                   }
                   else{
                         UserFormFactory.showAlert("Please select an end date")
                         return;
                   }

                }

                else if($scope.periodDisplayType == 'Quarter' && $scope.quarterDisplayType == '' && ($scope.isAggregateReport() )){
                   if(UserFormFactory.isInternetExplorer()){
                         alert("Please select a quarter type")
                         return;
                   }
                   else{
                         UserFormFactory.showAlert("Please select a quarter type")
                         return;
                   }

                }
                else if( ($scope.periodDisplayType == 'Custom Range') && ($scope.dt2 < $scope.dt1)){
                   if(UserFormFactory.isInternetExplorer()){
                         alert("End date should be greater than start date")
                         return;
                   }
                   else{
                         UserFormFactory.showAlert("End date should be greater than start date")
                         return;
                   }

                }


			    reportRequest.reportType = $scope.report.reportEnum;

			    reportRequest.stateId = 0;
			    reportRequest.districtId = 0;
			    reportRequest.blockId = 0;
                reportRequest.circleId = 0;
			    
			    if(!$scope.isCircleReport() ){

			    	if(!$scope.isAggregateReport())
			    	{
                        if($scope.state != null){
                            reportRequest.stateId = $scope.state.stateId;
                        }
                        else{
                               if(UserFormFactory.isInternetExplorer()){
                                     alert("Please select a state")
                                     return;
                               }
                               else{
                                 UserFormFactory.showAlert("Please select a state")
                                 return;
                               }
                        }
                    }
                    else
                    {
                        if($scope.state != null){
                            reportRequest.stateId = $scope.state.stateId;
                        }
                    }
				    if($scope.district != null){
				    	reportRequest.districtId = $scope.district.districtId;
				    }
				    if($scope.block != null){
				    	reportRequest.blockId = $scope.block.blockId;
				    }
		    	}
		    	/*else{
		    		if($scope.circle != null ){
				    	reportRequest.circleId = $scope.circle.circleId;
				    }
                    else{
                           if(UserFormFactory.isInternetExplorer()){
                                 alert("Please select a circle")
                                 return;
                           }
                           else{
                             UserFormFactory.showAlert("Please select a circle")
                             return;
                           }

                    }
		    	}*/

		    	/*if((angular.lowercase($scope.report.name).indexOf(angular.lowercase("rejected")) > -1) && $scope.format == 'yyyy-MM'){
                   if(UserFormFactory.isInternetExplorer()){
                         alert("Please select a week")
                         return;
                   }
                   else{
                     UserFormFactory.showAlert("Please select a week")
                     return;
                   }
		    	}*/

                if(!$scope.isAggregateReport())
                {
                    reportRequest.fromDate = $scope.dt;
                }
                else
                {

                    reportRequest.periodType = $scope.periodDisplayType;

                    if($scope.periodDisplayType == 'Year' ){
                         reportRequest.fromDate = new Date($scope.dt1.getFullYear(),0,1);
                         reportRequest.toDate = new Date($scope.dt1.getFullYear(),11,31);
                    }

                    else if($scope.periodDisplayType == 'Month' ){
                            reportRequest.fromDate = new Date($scope.dt1.getFullYear(),$scope.dt1.getMonth(),1);
                            reportRequest.toDate = new Date($scope.dt1.getFullYear(),$scope.dt1.getMonth() + 1,0);
                    }
                    else if($scope.periodDisplayType == 'Quarter' ){
                         if($scope.quarterDisplayType == 'Q1 (Jan to Mar)'){
                         reportRequest.fromDate = new Date($scope.dt1.getFullYear(),0,1);
                         reportRequest.toDate = new Date($scope.dt1.getFullYear(),2,31);
                         }
                         if($scope.quarterDisplayType == 'Q2 (Apr to Jun)'){
                         reportRequest.fromDate = new Date($scope.dt1.getFullYear(),3,1);
                         reportRequest.toDate = new Date($scope.dt1.getFullYear(),5,30);
                         }
                         if($scope.quarterDisplayType == 'Q3 (Jul to Sep)'){
                         reportRequest.fromDate = new Date($scope.dt1.getFullYear(),6,1);
                         reportRequest.toDate = new Date($scope.dt1.getFullYear(),8,30);
                         }
                         if($scope.quarterDisplayType == 'Q4 (Oct to Dec)'){
                         reportRequest.fromDate = new Date($scope.dt1.getFullYear(),9,1);
                         reportRequest.toDate = new Date($scope.dt1.getFullYear(),11,31);
                         }

                    }
                    else{
                        reportRequest.fromDate = $scope.dt1;
                        reportRequest.toDate = $scope.dt2;
                    }
                }

                console.log(reportRequest);
			    $scope.waiting = true;

                $scope.headerFromDate = reportRequest.fromDate;
                $scope.headerToDate = reportRequest.toDate;

				$http({
					method  : 'POST',
					url     : $scope.getReportUrl,
					data    : reportRequest, //forms user object
					headers : {'Content-Type': 'application/json'} 
				})
				.then(function(result){
					if($scope.isLineListingReport()){
					    $scope.waiting = false;
                        $scope.status = result.data.status;
                        if($scope.status == 'success'){
                            $scope.fileName = result.data.file;
                            $scope.pathName = result.data.path;
                            angular.element('#downloadReportLink').trigger('click');
                        }
                        if($scope.status == 'fail'){
                        console.log("failed")

                        }

					}

					if($scope.isAggregateReport()){
					    $scope.waiting = false;

					    if($scope.report.reportEnum == 'WA_Cumulative_Summary'){
					        $scope.gridOptions1.columnDefs = $scope.WA_Cumulative_Column_Definitions;
					    }
					    else if($scope.report.reportEnum == 'WA_Performance_Report'){
					        $scope.gridOptions1.columnDefs = $scope.WA_Performance_Column_Definitions;
					    }
					    else if($scope.report.reportEnum == 'WA_Subscriber_Report'){
					        $scope.gridOptions1.columnDefs = $scope.WA_Subscriber_Column_Definitions;
					    }
					    else if($scope.report.reportEnum == 'WA_Anonymous_Users_Summary'){
                            $scope.gridOptions1.columnDefs = $scope.WA_Anonymous_Column_Definitions;
                        }


                         $scope.gridOptions = $scope.gridOptions1;
                         $scope.gridOptions_Message_Matrix = $scope.gridOptions2;
                         $scope.gridOptions.data = result.data.tableData;
                         $scope.breadCrumbData = result.data.breadCrumbData;
                         $scope.hideGrid = false;

                    }
                }

				)
			}

			$scope.exportCSV = function(reportName){
                 var exportService = uiGridExporterService;
                 var grid = $scope.gridApi.grid;
                 var fileName = reportName.split(" ").join("")+ ".csv";

                 exportService.loadAllDataIfNeeded(grid, uiGridExporterConstants.ALL, uiGridExporterConstants.VISIBLE).then(function() {
                     var exportColumnHeaders = exportService.getColumnHeaders(grid, uiGridExporterConstants.VISIBLE);
                     var exportData = exportService.getData(grid, uiGridExporterConstants.ALL, uiGridExporterConstants.VISIBLE);
                     var csvContent = exportService.formatAsCsv(exportColumnHeaders, exportData, grid.options.exporterCsvColumnSeparator);
                         exportService.downloadFile(fileName, csvContent, grid.options.exporterOlderExcelCompatibility);
                   });
            }

            /*$scope.renderPdf = function(){
                var exportService = uiGridExporterService;
                var grid = $scope.gridApi.grid;
                exportService.loadAllDataIfNeeded(grid, uiGridExporterConstants.ALL, uiGridExporterConstants.VISIBLE).then(function() {
                  });
                  var exportColumnHeaders = exportService.getColumnHeaders(grid, uiGridExporterConstants.VISIBLE);
                  var exportData = exportService.getData(grid, uiGridExporterConstants.ALL, uiGridExporterConstants.VISIBLE);

                return exportService.renderAsPdf(grid,exportColumnHeaders,exportData);

            }*/

            $scope.exportPdf = function(reportName) {
                var exportService = uiGridExporterService;
                var grid = $scope.gridApi.grid;
                var fileName = reportName.split(" ").join("")+ ".pdf";
                var docDefinition = { content: 'This is an sample PDF printed with pdfMake' };

                //exportService.pdfExport(uiGridExporterConstants.VISIBLE,uiGridExporterConstants.ALL);

                 pdfMake.createPdf(docDefinition).download(fileName);
            }

            $scope.exportExcel = function(reportName){
                             var exportService = uiGridExporterService;
                             var grid = $scope.gridApi.grid;
                             var fileName = reportName.split(" ").join("")+ ".xlsx";

                            exportService.loadAllDataIfNeeded(grid, uiGridExporterConstants.ALL, uiGridExporterConstants.VISIBLE).then(function() {
                                 var exportColumnHeaders = exportService.getColumnHeaders(grid, uiGridExporterConstants.VISIBLE);
                                 var exportData = exportService.getData(grid, uiGridExporterConstants.ALL, uiGridExporterConstants.VISIBLE);
                                 var content = exportService.formatAsCsv(exportColumnHeaders, exportData, grid.options.exporterCsvColumnSeparator);
                                     exportService.downloadFile(fileName, content, grid.options.exporterOlderExcelCompatibility);
                               });
                        }

			$scope.getReportUrl = backend_root + 'wa/user/getReport';
			$scope.$watch('pathName', function(){
			    $scope.downloadReportUrl = backend_root + 'wa/user/downloadReport?fileName='+
            			        $scope.fileName+'&rootPath='+$scope.pathName;
			})
			$scope.$watch('fileName', function(){
            			    $scope.downloadReportUrl = backend_root + 'wa/user/downloadReport?fileName='+
                        			        $scope.fileName+'&rootPath='+$scope.pathName;
            			})


			$scope.clearFile = function(){
				$scope.status = "";
				$scope.fileName = "";
			}

			$scope.reset =function(){

				$scope.report = null;
				$scope.reportCategory = null;

				if(!$scope.userHasState()){
					$scope.clearState();
				}
				if(!$scope.userHasDistrict()){
					$scope.clearDistrict();
				}
				if(!$scope.userHasBlock()){
					$scope.clearBlock();
				}
				$scope.clearCircle();
				$scope.clearFile();
				$scope.dt = null;
				$scope.datePickerContent = "Select Month";
				$scope.dt1 = null;
				$scope.dt2 = null;
				$scope.hideGrid = true;
				$scope.hideMessageMatrix = true;
				$scope.periodDisplayType = '';
				$scope.showEmptyData = false;
			}

			// datepicker stuff

			// $scope.select = function(date) {
			// 	$scope.dt = date;
			// }

			// $scope.today = function() {
			// 	$scope.dt = new Date();
			// };

			$scope.clearMonth = function() {
				$scope.dt = null;
			};

			$scope.inlineOptions = {
				customClass: getDayClass,
				minDate: new Date(),
				showWeeks: true
			};

			// Disable weekend selection
			function disabled(data) {
				var date = data.date,
				mode = data.mode;
				return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
			}

            var startMonth = 10 //September
            var startDate = 18 //Start Date

			$scope.open1 = function() {
				$scope.popup1.opened = true;
				var currentDate = new Date();

				console.log(currentDate.getMonth() + " " + currentDate.getDate() + " " +currentDate.getFullYear());
				if((angular.lowercase($scope.report.name).indexOf(angular.lowercase("rejected")) > -1) ){
				    if(currentDate.getMonth() == startMonth && currentDate.getDate() >= startDate && currentDate.getFullYear() == 2017 && $scope.getSundays(currentDate) > 0){
				        $scope.dateOptions.maxDate = new Date().setMonth(new Date().getMonth());
				    }
				    else if(currentDate.getMonth() == startMonth && currentDate.getDate() >= startDate && currentDate.getFullYear() == 2017 && $scope.getSundays(currentDate) == 0){
                    	$scope.dateOptions.maxDate = new Date().setMonth(new Date().getMonth() - 1);
                     }
				    else if(currentDate.getMonth() == startMonth && currentDate.getDate() < startDate && currentDate.getFullYear() == 2017){
				         $scope.dateOptions.maxDate = new Date().setMonth(new Date().getMonth() - 1);
				    }
				    else {
				        if($scope.getSundays(currentDate) >0){
                            $scope.dateOptions.maxDate = new Date().setMonth(currentDate.getMonth());
                        }
                        else
                            $scope.dateOptions.maxDate = new Date().setMonth(currentDate.getMonth() - 1);
                        $scope.sundays = null;

				    }

				}

				if((angular.lowercase($scope.report.name).indexOf(angular.lowercase("rejected")) > -1) && ($scope.format == 'yyyy-MM-dd' || $scope.format == 'yyyy-MM' )){
                    $scope.getSundays($scope.dt);
                    $scope.sundaysTable = true;

                }
			};

			$scope.setDate = function(year, month, day) {
				$scope.dt = new Date(year, month, day);
			};

			$scope.format = 'yyyy-MM'
			$scope.altInputFormats = ['yyyy-M!'];

			$scope.popup1 = {
				opened: false
			};

			var tomorrow = new Date();
			tomorrow.setDate(tomorrow.getDate() + 1);
			var afterTomorrow = new Date();
			afterTomorrow.setDate(tomorrow.getDate() + 1);
			$scope.events = [
				{
					date: tomorrow,
					status: 'full'
				},
				{
					date: afterTomorrow,
					status: 'partially'
				}
			];

			function getDayClass(data) {
				var date = data.date,
				 	mode = data.mode;
				if (mode === 'day') {
				  	var dayToCheck = new Date(date).setHours(0,0,0,0);

				  	for (var i = 0; i < $scope.events.length; i++) {
						var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

						if (dayToCheck === currentDay) {
							return $scope.events[i].status;
						}
					}
				}

				return '';
			}
            $scope.sundays = null;

			$scope.getSundays = function(d){

                if( d  === undefined || d == null){
                    return;
                }
                var getTot = null;
                var sun = new Array(); //Declaring array for inserting Sundays
                var today = new Date();
                if(d.getMonth() == startMonth && d.getFullYear() == 2017 ){
                    if(d.getMonth() == today.getMonth()){
                        for(var i=startDate;i<=today.getDate()-1;i++){    //looping through days in month
                            var newDate = new Date(d.getFullYear(),d.getMonth(),i)
                            if(newDate.getDay()==0){   //if Sunday
                                sun.push(i);
                            }
                        }
                    }

                    else{
                        for(var i=startDate;i<=30;i++){    //looping through days in month
                            var newDate = new Date(d.getFullYear(),d.getMonth(),i)
                            if(newDate.getDay()==0){   //if Sunday
                                sun.push(i);
                            }
                        }
                    }

                 $scope.sundays = sun;
                 return ($scope.sundays.length);

                }
                else{

                    if(d.getMonth() == today.getMonth() && d.getFullYear() == today.getFullYear() )
                    {
                      var getTot = (today.getDate()) - 1;
                    }
                    else{
                      var getTot = new Date(d.getFullYear(),d.getMonth()+1,0).getDate();
                    }

                    console.log(getTot);
                     //Get total days in a month
                    var sun = new Array();   //Declaring array for inserting Sundays
                    for(var i=1;i<=getTot;i++){    //looping through days in month
                        var newDate = new Date(d.getFullYear(),d.getMonth(),i)
                        if(newDate.getDay()==0){   //if Sunday
                            sun.push(i);
                        }
                    }
                    $scope.sundays = sun;
                    return ($scope.sundays.length);
                }
			}

			$scope.sundaysTable = false;

			$scope.closeSundaysTable = function(date) {
            	$scope.sundaysTable = false;
            	$scope.sundays = [];
            	$scope.dt = new Date($scope.dt.getFullYear(),$scope.dt.getMonth(),date);
            	$scope.wasSundaySelected = true;
            };

            $window.addEventListener('click', function() {
              if($scope.sundaysTable)
                $scope.sundaysTable = false;
            });

            var canceler = $q.defer();

            $scope.gridOptions1 = {
                enableSorting: true,
                showColumnFooter: true,
                enableVerticalScrollbar : 0,
                excessRows :1000,
                onRegisterApi: function(gridApi){
                      $scope.gridApi = gridApi;
                    },
               /* exporterPdfDefaultStyle: {fontSize: 9},
                exporterPdfTableStyle: {margin: [30, 30, 30, 30]},
                exporterPdfTableHeaderStyle: {fontSize: 10, bold: true, italics: true, color: 'red'},
                exporterPdfHeader: { text: "Header", style: 'headerStyle' },
                exporterPdfFooter: function ( currentPage, pageCount ) {
                      return { text: currentPage.toString() + ' of ' + pageCount.toString(), style: 'footerStyle' };
                },
                exporterPdfCustomFormatter: function ( docDefinition ) {
                      docDefinition.styles.headerStyle = { fontSize: 22, bold: true };
                      docDefinition.styles.footerStyle = { fontSize: 10, bold: true };
                      return docDefinition;
                },
                    exporterPdfOrientation: 'portrait',
                    exporterPdfPageSize: 'LETTER',
                    exporterPdfMaxGridWidth: 500,
                    exporterCsvLinkElement: angular.element(document.querySelectorAll(".custom-csv-link-location"))*/
                };

            $scope.gridOptions2 = {
                enableSorting: true,
                enableVerticalScrollbar : 0,
                excessRows :1000,
                onRegisterApi: function(gridApi){
                      $scope.gridApi = gridApi;
                    }
              };

            $scope.WA_Cumulative_Column_Definitions =[
                                                       {name: 'S No.', displayName: 'S No.',width:"6%",enableSorting: false, cellTemplate: '<p class="serial-no" >{{rowRenderIndex+1}}</p>'},
                                                       { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents" >Total</div>', sort: { direction: 'asc', priority: 0 },
                                                         cellTemplate:'<a class="btn aggregate-location" title="{{COL_FIELD}}"  ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a>',
                                                         width: '12%', enableHiding: false,
                                                       },
                                                       { field: 'swachchagrahisRegistered', displayName : 'No of Registered Swachchagrahi', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                       { field: 'swachchagrahisStarted', displayName : 'Swachchagrahi Started Course',  aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                       { field: 'swachchagrahisNotStarted', displayName : 'Swachchagrahi Not Started Course', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                       { field: 'swachchagrahisCompleted' , displayName : 'Swachchagrahi Successfully Completed the Course', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"13%", enableHiding: false},
                                                       { field: 'swachchagrahisFailed' , displayName : 'Swachchagrahi who failed the course', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                       { field: 'notStartedpercentage' , displayName : '% Not Started Course', footerCellTemplate: '<div class="ui-grid-cell-contents" >{{(grid.columns[4].getAggregationValue()/grid.columns[2].getAggregationValue()) *100 | number:4}}</div>', width:"*", enableHiding: false},
                                                       { field: 'completedPercentage' , displayName : '% Successfully Completed', footerCellTemplate: '<div class="ui-grid-cell-contents" >{{(grid.columns[5].getAggregationValue()/grid.columns[2].getAggregationValue())*100 | number:4}}</div>', width:"*", enableHiding: false},
                                                       { field: 'failedpercentage' , displayName : '% Failed the course', footerCellTemplate: '<div class="ui-grid-cell-contents" >{{(grid.columns[6].getAggregationValue()/grid.columns[2].getAggregationValue()) *100 | number:4}}</div>', width:"*", enableHiding: false},
                                                      ]



            $scope.WA_Performance_Column_Definitions =[
                                                         {name: 'S No.', displayName: 'S No.',width:"6%", enableSorting: false, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                         { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents">Total</div>',sort: { direction: 'asc', priority: 0 },
                                                            cellTemplate:'<a class=" btn aggregate-location" title="{{COL_FIELD}}" ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a>',
                                                            enableHiding: false, width:"12%",

                                                         },
                                                         { field: 'swachchagrahisStartedCourse', displayName: 'No. of Swachchagrahi Started Course', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*",enableHiding: false },
                                                         { field: 'swachchagrahisPursuingCourse', displayName: 'No. of Swachchagrahi Pursuing Course', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                         { field: 'swachchagrahisNotPursuingCourse', displayName: 'No. of Swachchagrahi not Pursuing Course', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                         { field: 'swachchagrahisCompletedCourse', displayName: 'No. of Swachchagrahi Successfully Completed Course', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"18%",enableHiding: false},
                                                         { field: 'swachchagrahisFailedCourse',  displayName: 'No. of Swachchagrahi who Failed the Course', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                        ],

            $scope.WA_Subscriber_Column_Definitions =[
                                                         {name: 'S No.', displayName: 'S No.',width:"5%",enableSorting: false, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                         { field: 'locationName', footerCellTemplate: '<div class="ui-grid-cell-contents" >Total</div>',sort: { direction: 'asc', priority: 0 },
                                                            cellTemplate:'<a class="btn aggregate-location" title="{{COL_FIELD}}" ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a>',
                                                            enableHiding: false,width:"14%",

                                                         },
                                                         { field: 'registeredNotCompletedStart', displayName: 'No. of Swachchagrahi Registered But Not Completed the Course(Period Start)', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"16%", enableHiding: false },
                                                         { field: 'recordsReceived', displayName: 'No. of Swachchagrahi Records Received Through Web Service', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                         { field: 'recordsRejected', displayName: 'No. of Swachchagrahi Records Rejected', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                         { field: 'swachchagrahisRegistered', displayName: 'No. of Swachchagrahi Subscriptions Added', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                         { field: 'successfullyFirstCompleted',  displayName: 'No. of Swachchagrahi Successfully Completed the Course', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                         { field: 'registeredNotCompletedEnd',  displayName: 'No. of Swachchagrahi Registered But Not Completed the Course (Period End)', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"16%", enableHiding: false},
                                                        ],

            $scope.WA_Anonymous_Column_Definitions =[
                                                                     {name: 'S No.', displayName: 'S No.',width:"5%",enableSorting: false, cellTemplate: '<p class="serial-no">{{rowRenderIndex+1}}</p>'},
                                                                     { field: 'circleName', footerCellTemplate: '<div class="ui-grid-cell-contents" >Total</div>',sort: { direction: 'asc', priority: 0 },
                                                                        cellTemplate:'<a class="btn aggregate-location" title="{{COL_FIELD}}" ng-click="grid.appScope.drillDownData(row.entity.locationId,row.entity.locationType,row.entity.locationName)">{{ COL_FIELD }}</a>',
                                                                        enableHiding: false,width:"14%",

                                                                     },
                                                                     { field: 'anonUsersStartedCourse', displayName: 'No. of anonymous users started Course', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                                     { field: 'anonUsersPursuingCourse', displayName: 'No. of anonymous users pursuing Course', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false },
                                                                     { field: 'anonUsersNotPursuingCourse', displayName: 'No. of anonymous users Not pursuing Course', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},
                                                                     { field: 'anonUsersCompletedCourse', displayName: 'No. of anonymous users successfully completed Course', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"16%", enableHiding: false},
                                                                     { field: 'anonUsersFailedCourse',  displayName: 'No. of anonymous users failed Course', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true, width:"*", enableHiding: false},

                                                                    ],



            /*$scope.drillDownData = function(locationId,locationType){

                  if(angular.lowercase(locationType) == "state"){
                    reportRequest.stateId = locationId;
                    reportRequest.districtId = 0;
                    reportRequest.blockId = 0;
                    $scope.waiting = true;

                    $http({
                            method  : 'POST',
                            url     : $scope.getReportUrl,
                            data    : reportRequest, //forms user object
                            headers : {'Content-Type': 'application/json'}
                        })
                        .then(function(result){

                            if($scope.isAggregateReport()){
                                $scope.waiting = false;
                                if(result.data.tableData.length >0){
                                    $scope.gridOptions1.data = result.data.tableData;
                                    $scope.reportBreadCrumbData = result.data.breadCrumbData;
                                    $scope.hideGrid = false;
                                    $scope.gridOptions1.columnDefs[1].displayName = 'District';
                                    $scope.gridApi.core.notifyDataChange( uiGridConstants.dataChange.COLUMN );
                                }
                                else{
                                    $scope.showEmptyData = true;
                                    $scope.hideGrid = true;
                                }
                                $scope.gridOptions = $scope.gridOptions1;
                            }

                        })
                  }
                  else if(angular.lowercase(locationType) == "NATIONAL"){
                       reportRequest.stateId = 0;
                       reportRequest.districtId = 0;
                       reportRequest.blockId = 0;
                       $scope.waiting = true;
                       $http({
                               method  : 'POST',
                               url     : $scope.getReportUrl,
                               data    : reportRequest,
                               headers : {'Content-Type': 'application/json'}
                           })
                           .then(function(result){

                               if($scope.isAggregateReport()){
                                   $scope.waiting = false;
                                   if(result.data.tableData.length >0){
                                      $scope.gridOptions1.data = result.data.tableData;
                                      $scope.reportBreadCrumbData = result.data.breadCrumbData;
                                       $scope.hideGrid = false;
                                       $scope.gridOptions1.columnDefs[1].displayName = 'State';
                                       $scope.gridApi.core.notifyDataChange( uiGridConstants.dataChange.COLUMN );
                                   }
                                   else{
                                       $scope.showEmptyData = true;
                                       $scope.hideGrid = true;
                                   }
                                   $scope.gridOptions = $scope.gridOptions1;
                               }

                           })
                     }
                  else if(angular.lowercase(locationType) == "district"){
                     reportRequest.districtId = locationId;
                     reportRequest.blockId = 0;
                     $scope.waiting = true;
                     $http({
                             method  : 'POST',
                             url     : $scope.getReportUrl,
                             data    : reportRequest,
                             headers : {'Content-Type': 'application/json'}
                         })
                         .then(function(result){

                             if($scope.isAggregateReport()){
                                 $scope.waiting = false;
                                 if(result.data.tableData.length >0){
                                    $scope.gridOptions1.data = result.data.tableData;
                                    $scope.reportBreadCrumbData = result.data.breadCrumbData;
                                     $scope.hideGrid = false;
                                     $scope.gridOptions1.columnDefs[1].displayName = 'Block';
                                     $scope.gridApi.core.notifyDataChange( uiGridConstants.dataChange.COLUMN );
                                 }
                                 else{
                                     $scope.showEmptyData = true;
                                     $scope.hideGrid = true;
                                 }
                                 $scope.gridOptions = $scope.gridOptions1;
                             }

                         })
                  }
                  else if(angular.lowercase(locationType) == "block"){
                    reportRequest.blockId = locationId;
                    $scope.waiting = true;
                    $http({
                            method  : 'POST',
                            url     : $scope.getReportUrl,
                            data    : reportRequest, //forms user object
                            headers : {'Content-Type': 'application/json'}
                        })
                        .then(function(result){

                            if($scope.isAggregateReport()){
                                $scope.waiting = false;
                                if(result.data.tableData.length >0){
                                    $scope.gridOptions1.data = result.data.tableData;
                                    $scope.reportBreadCrumbData = result.data.breadCrumbData;
                                    $scope.hideGrid = false;
                                     $scope.gridOptions1.columnDefs[1].displayName = 'Subcenter';
                                     $scope.gridApi.core.notifyDataChange( uiGridConstants.dataChange.COLUMN );
                                }
                                else{
                                     $scope.showEmptyData = true;
                                     $scope.hideGrid = true;
                                }
                                $scope.gridOptions = $scope.gridOptions1;
                            }

                        })
                  }
            }

		}])
})()*/

                $scope.lastBread = function(reportBreadCrumbData){
                    var length = reportBreadCrumbData.length;
                    return reportBreadCrumbData[length-1].locationType;
                }

                $scope.drillDownData = function(locationId,locationType,locationName){

                  if(angular.lowercase(locationType) == "state"){
                    reportRequest.stateId = locationId;
                    reportRequest.districtId = 0;
                    reportRequest.blockId = 0;
                    $scope.waiting = true;

                    $http({
                            method  : 'POST',
                            url     : $scope.getReportUrl,
                            data    : reportRequest, //forms user object
                            headers : {'Content-Type': 'application/json'}
                        })
                        .then(function(result){

                            if($scope.isAggregateReport()){
                                $scope.waiting = false;
                                if(result.data.tableData.length >0){
                                    $scope.gridOptions1.data = result.data.tableData;
                                    $scope.reportBreadCrumbData = result.data.breadCrumbData;
                                    $scope.hideGrid = false;
                                    $scope.gridOptions1.columnDefs[1].displayName = 'District';
                                    $scope.gridApi.core.notifyDataChange( uiGridConstants.dataChange.COLUMN );
                                    fileName = $scope.report.reportEnum + "_" + $scope.reportBreadCrumbData[$scope.reportBreadCrumbData.length -1].locationName ;
                                    $scope.gridOptions1.exporterExcelFilename = fileName + "_" + dateString;

                                }
                                else{
                                    $scope.showEmptyData = true;
                                    $scope.hideGrid = true;
                                }
                                $scope.gridOptions = $scope.gridOptions1;
                                excelHeaderName.stateName = locationName;
                                excelHeaderName.districtName = "ALL";
                                excelHeaderName.blockName = "ALL";
                            }

                        })
                  }
                  else if(angular.lowercase(locationType) == "national"){
                       reportRequest.stateId = 0;
                       reportRequest.districtId = 0;
                       reportRequest.blockId = 0;
                       $scope.waiting = true;
                       $http({
                               method  : 'POST',
                               url     : $scope.getReportUrl,
                               data    : reportRequest, //forms user object
                               headers : {'Content-Type': 'application/json'}
                           })
                           .then(function(result){

                               if($scope.isAggregateReport()){
                                   $scope.waiting = false;
                                   if(result.data.tableData.length >0){
                                      $scope.gridOptions1.data = result.data.tableData;
                                      $scope.reportBreadCrumbData = result.data.breadCrumbData;
                                       $scope.hideGrid = false;
                                       $scope.gridOptions1.columnDefs[1].displayName = 'State';
                                       $scope.gridApi.core.notifyDataChange( uiGridConstants.dataChange.COLUMN );
                                       fileName = $scope.report.reportEnum + "_" + $scope.reportBreadCrumbData[$scope.reportBreadCrumbData.length -1].locationName ;
                                       $scope.gridOptions1.exporterExcelFilename = fileName + "_" + dateString;

                                   }
                                   else{
                                       $scope.showEmptyData = true;
                                       $scope.hideGrid = true;
                                   }
                                   $scope.gridOptions = $scope.gridOptions1;
                                    excelHeaderName.stateName = "ALL";
                                    excelHeaderName.districtName = "ALL";
                                    excelHeaderName.blockName = "ALL";
                               }

                           })
                     }
                  else if(angular.lowercase(locationType) == "district"){
                     reportRequest.districtId = locationId;
                     reportRequest.blockId = 0;
                     $scope.waiting = true;
                     $http({
                             method  : 'POST',
                             url     : $scope.getReportUrl,
                             data    : reportRequest, //forms user object
                             headers : {'Content-Type': 'application/json'}
                         })
                         .then(function(result){

                             if($scope.isAggregateReport()){
                                 $scope.waiting = false;
                                 if(result.data.tableData.length >0){
                                    $scope.gridOptions1.data = result.data.tableData;
                                    $scope.reportBreadCrumbData = result.data.breadCrumbData;
                                     $scope.hideGrid = false;
                                     $scope.gridOptions1.columnDefs[1].displayName = 'Block';
                                     $scope.gridApi.core.notifyDataChange( uiGridConstants.dataChange.COLUMN );
                                     fileName = $scope.report.reportEnum + "_" + $scope.reportBreadCrumbData[$scope.reportBreadCrumbData.length -1].locationName ;
                                     $scope.gridOptions1.exporterExcelFilename = fileName + "_" + dateString;

                                 }
                                 else{
                                     $scope.showEmptyData = true;
                                     $scope.hideGrid = true;
                                 }
                                 $scope.gridOptions = $scope.gridOptions1;
                                 excelHeaderName.districtName = locationName;
                                 excelHeaderName.blockName = "ALL";
                             }

                         })
                  }
                  else if(angular.lowercase(locationType) == "block"){
                    reportRequest.blockId = locationId;
                    $scope.waiting = true;
                    $http({
                            method  : 'POST',
                            url     : $scope.getReportUrl,
                            data    : reportRequest, //forms user object
                            headers : {'Content-Type': 'application/json'}
                        })
                        .then(function(result){

                            if($scope.isAggregateReport()){
                                $scope.waiting = false;
                                if(result.data.tableData.length >0){
                                    $scope.gridOptions1.data = result.data.tableData;
                                    $scope.reportBreadCrumbData = result.data.breadCrumbData;
                                    $scope.hideGrid = false;
                                     $scope.gridOptions1.columnDefs[1].displayName = 'Subcenter';
                                     $scope.gridApi.core.notifyDataChange( uiGridConstants.dataChange.COLUMN );
                                     fileName = $scope.report.reportEnum + "_" + $scope.reportBreadCrumbData[$scope.reportBreadCrumbData.length -1].locationName ;
                                     $scope.gridOptions1.exporterExcelFilename = fileName + "_" + dateString;
                                }
                                else{
                                     $scope.showEmptyData = true;
                                     $scope.hideGrid = true;
                                }
                                $scope.gridOptions = $scope.gridOptions1;
                                excelHeaderName.blockName = locationName;
                            }

                        })
                  }
            }

		}])
})()

