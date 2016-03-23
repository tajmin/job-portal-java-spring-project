Controllers.controller("jobDetailsCtrl", function($scope, $rootScope, restservice, $cookies, $window, utilservice) {
	$scope.isproceed = false;
	$scope.job = {};
	$scope.employer = {};
	$scope.formSubmitted = false;
	$scope.responseMessage = "";
	$scope.id = utilservice.getParameterByName("id");
	
	$window.map = new google.maps.Map(document.getElementById('g-map'), {
        center: {
            lat: -34.397,
            lng: 150.644
        },
        zoom: 8
    });
	
	//Show job details
	$scope.jobDetailsById = function(jobId) {			
		restservice.get( '', "api/v1/job/jobDetailsById?jobID=" + jobId).then(function(response) {
			if (response != null) {
				$scope.job = response;
				$scope.showJobInMap();
        	}
        });
	
    };
    $scope.jobDetailsById($scope.id);
    
    
	$scope.getUserByJobId = function(jobId) {			
		restservice.get( '', "api/v1/user/getUserByJobId?jobID=" + jobId).then(function(response) {
			if (response != null) {
				$scope.employer = response;
        	}
        });	
    };
    $scope.getUserByJobId($scope.id);
    
    
    $scope.showJobInMap = function(){
    	var bounds = new google.maps.LatLngBounds();
		if($scope.job && $scope.job.title){
			var latLng = new google.maps.LatLng($scope.job.latitude, $scope.job.longitude); 
	        var marker = new google.maps.Marker({
	            position: latLng,
	            map: $window.map,
	            title: $scope.job.title
	        });
	        bounds.extend(marker.position);
		}
    	$window.map.fitBounds(bounds);
    };    
    
});