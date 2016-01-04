Controllers.controller("UserCtrl", function($scope, $rootScope, restservice, $cookies) {
	$scope.user = {};
	$scope.addUser = function() {
		$scope.user.firstName = "test f aj";
		$scope.user.middleName = "test l aj";
		$scope.user.preferredName = "test l aj";
		
		var d = new Date();
		var n = d.getTime();
		
		$scope.user.email = n + "aj@selvesperer.com";
		$scope.user.password = "test l aj";
		
		restservice.get('', "api/v1/user").then(function(data) {
			console.log( data );
        });
		
		restservice.put( $scope.user, "api/v1/user").then(function(data) {
			if (data != null) {
				console.log( data );
        	}
        });
    };   
});