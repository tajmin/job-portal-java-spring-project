Controllers.controller("jobOutCtrl", function($scope, $rootScope, restservice, $cookies, $window, utilservice) {
	$scope.isproceed = false;
	$scope.interesteduserlist = [];
	$scope.message = {};
	$scope.formSubmitted = false;
	$scope.user={};
	$scope.responseMessage = "";
	$scope.jobdetails={};
	$scope.selectinteresteduser={};
	$scope.selecteduser="";
	$scope.jobId=utilservice.getParameterByName("jobId");
	//$scope.jobId="46002544-3650-uuid-8f9c-8b0641fd1fea";
	$scope.jobOutLists = function() {
		
		console.log("Loading Job out List");
		restservice.get('', "api/v1/jobOut/getJobOutListById?jobId=" +$scope.jobId).then(function(response) {
			if (response != null) {
				for (var i = 0; i < response.length; i++) {
					$scope.jobdetails=response[0];
					$scope.interesteduserlist.push(response[i]);
				}
				$scope.responseMessage = response.message;
			} 
		});

	};
	$scope.jobOutLists();
	
	$scope.selectinteresteduser= function(id){
		$scope.isproceed = false;
		$scope.selecteduser=id;
		alert("www"+id);
	}
});