Controllers.controller("notificationCtrl", function($scope, $rootScope, restservice, $cookies, $window, utilservice) {
	$scope.isproceed = false;
	$scope.notification = [];
	$scope.formSubmitted = false;
	$scope.responseMessage = "";
	$scope.filter = {};
	$scope.filter.page = 1;
	$scope.notificationPage = 1;
	$scope.filter.moreLink=true;
	$scope.notificationCount = {};
	$scope.loadMoreNotifications = function() {
		$scope.filter.page = $scope.notificationPage;
		$scope.filter.moreLink = true;
		restservice.get( '', "api/v1/notification/getNotifications?page="+$scope.filter.page).then(function(response) {
			if (response != null) {
				for (var i = 0; i < response.length; i++) {
					$scope.notification.push(response[i]);
				}
				
				if(response.length < 5){
					$scope.filter.moreLink = false;				
				}else{
					 $scope.notificationPage += 1;
					
				}
				
        	} else {
        		//$scope.responseMessage = response.message;
        		$scope.filter.moreLink = false;
        	}
        });	
    };
    
    $scope.loadMoreNotifications();
	
	
	$scope.createNotification = function(){

		restservice.post('', "api/v1/notification/createNotification").then(function(response) {
			if (response != null) {
				$scope.isproceed = true;
			}
		});
	};
	

	$scope.updateSeenNotification = function(id) {

		restservice.post( '', "api/v1/notification/updateSeenNotification?id=" +id).then(function(response) {
			if (response != null && response.success) {
				$scope.isproceed = false;
				$scope.responseMessage = response.message;
			}
		});
	};
    
	$scope.deleteNotification = function(id) {
	restservice.post( '', "api/v1/notification/deleteNotification?id=" +id).then(function(response) {
			if (response != null && response.success) {
				$scope.isproceed = false;
				$scope.responseMessage = response.message;
			}
		});
	};
	
	$scope.countHasSeeNotification = function() {
		
		restservice.get( '', "api/v1/notification/countHasSeeNotification").then(function(response) {
			if (response.response == 0 ) {
				$scope.notificationCount = 0;
        	} else {
        		$scope.notificationCount = response;
				$scope.responseMessage = response.message;
			}
        });	
    };
    
    $scope.countHasSeeNotification();
    
});