Controllers.controller("UserCtrl", function($scope, $rootScope, restservice, $cookies) {
	console.log("In cotroller");
	$scope.user = {};
	$scope.addUser = function() {
		/*$scope.user.firstName = "test f aj";
		$scope.user.middleName = "test l aj";
		$scope.user.preferredName = "test l aj";*/
		
		var d = new Date();
		var n = d.getTime();
		
		//$scope.user.email = n + "aj@selvesperer.com";
		//$scope.user.password = "test l aj";
		
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

Controllers.controller("CompanyCtrl", function($scope, $rootScope, restservice, $cookies) {
	$scope.company = {};
	$scope.addCompany = function() {
		
		//$scope.user.middleName = "test l aj";
		//$scope.user.preferredName = "test l aj";
		
		var d = new Date();
		var n = d.getTime();
		
		//$scope.user.email = n + "aj@selvesperer.com";
		//$scope.user.password = "test l aj";
		
		restservice.get('', "api/v1/company").then(function(data) {
			console.log( data );
        });
		
		restservice.post( $scope.company, "api/v1/company").then(function(data) {
			if (data != null) {
				console.log( data );
        	}
        });
    };   
});