Controllers.controller("UserCtrl", function($scope, $rootScope, restservice, $cookies) {
	$scope.user = {};
	$scope.addUser = function() {		
		var d = new Date();
		var n = d.getTime();
		
		restservice.get('', "api/v1/user").then(function(data) {
			console.log( data );
        });
		
		restservice.post( $scope.user, "api/v1/user").then(function(data) {
			if (data != null) {
				console.log( data );
        	}
        });
    };   
    
    $scope.login = function() {
		restservice.get('', "api/v1/user").then(function(data) {
			console.log( data );
        });
		
		restservice.post( $scope.user, "api/v1/user").then(function(data) {
			if (data != null) {
				console.log( data );
        	}
        });
    };
});

Controllers.controller("signupCtrl", function($scope, $rootScope, restservice, $cookies) {
	$scope.isproceed = false;
	$scope.user = {};
	$scope.formSubmitted = false;
	$scope.responseMessage = "";
	$scope.signup = function(isValid) {
		if(!isValid) return;
		
		restservice.post( $scope.user, "api/v1/user/signup").then(function(response) {
			if (response != null && response.success) {
				$scope.isproceed = true;
				$scope.responseMessage = response.message;				
        	}
        });
    };
});

Controllers.controller("loginCtrl", function($scope, $rootScope, restservice, $cookies) {console.log("isValid:::::::::::::::::: ");
	$scope.formSubmitted = false;
	$rootScope.userinfos = {};
	$scope.user = {};
	$scope.login = function(isValid) {
		
		if(!isValid) return;
		
		console.log($scope.user);
		restservice.post($scope.user, "api/v1/user/login").then(function(response) {
			if (response != null && response.success) {
				$scope.isproceed = true;
				$scope.userinfos = response.response;
				console.log($scope.userinfos );
        	}
        });
    };
});