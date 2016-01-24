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

//@author SHIFAT application controller for sending invitation 
Controllers.controller("invitationCtrl", function($scope, $rootScope, restservice, $cookies) {
	$scope.isproceed = false;
	$scope.user = {};
	$scope.formSubmitted = false;
	$scope.responseMessage = "";
	$scope.invite = function(isValid) {
		if(!isValid) return;
		
		restservice.post('', "api/v1/user/invite?email="+$scope.user.email).then(function(response) {
			if (response != null && response.success) {
				$scope.isproceed = true;
				$scope.responseMessage = response.message;				
        	}
        });
    };
});
//@author SHIFAT - Ends



//@author SHIFAT controller for user settings
Controllers.controller("settingsCtrl", function($scope, $rootScope, restservice, $cookies) {
	$scope.isproceed = false;
	$scope.user = {};
	$scope.responseMessage = "";
	$scope.settings = function() {
		
		
		restservice.post( $scope.user, "api/v1/user/settings").then(function(response) {
			if (response != null && response.success) {
				$scope.isproceed = true;
				$scope.responseMessage = response.message;				
        	}
        });
    };
});

//@author SHIFAT ends
Controllers.controller("loginCtrl", function($scope, $rootScope, restservice, $cookies) {console.log("isValid:::::::::::::::::: ");
	$scope.formSubmitted = false;
	$rootScope.userinfos = {};
	$scope.user = {};
	$scope.login = function(isValid) {		
		if(!isValid) return;		
		restservice.post($scope.user, "api/v1/user/login").then(function(response) {
			$scope.isproceed = true;
			$rootScope.userinfos = response;
			console.log($rootScope.userinfos);
			window.open("http://localhost:8080/knoeien/home.xhtml","_self");			
        });
    };
});

Controllers.controller("resetPasswordCtrl", function($scope, $rootScope, restservice, $cookies) {
	$rootScope.restMessages = {};
	$scope.isproceed = false;
	$scope.username = "";
	$scope.formSubmitted = false;
	$scope.responseMessage = "";
	$scope.user = {};
	
	$scope.sendChangePasswordEmail = function(isValid) {
		if(!isValid) return;
		$scope.responseMessage = "";
		
		restservice.post($scope.user, "api/v1/user/sendChangePasswordEmail?email="+$scope.user.username).then(function(response) {
			if (response != null && response.success) {
				$scope.isproceed = true;
				$scope.responseMessage = response.message;
	    	} 
	    });
	};
	
	$scope.resetPassword = function(isValid) {		
		if(!isValid) return; 
		$scope.responseMessage = "";
		
		var key = getParameterByName("key");
		$scope.user.passwordResetToken = key;
		restservice.post($scope.user, "api/v1/user/resetPassword").then(function(response) {
			if (response != null && response.success) {
				$scope.isproceed = true; 
				$scope.responseMessage = response.message;
	    	} 
	    });
	};
	
	function getParameterByName(name) {
	    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
	        results = regex.exec(location.search);
	    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
	}
});

Controllers.controller("logoutCtrl", function($scope, $rootScope, restservice, $cookies) { 	
	$scope.logout = function() {
		restservice.get('', "api/v1/user/logout").then(function(response) {
			window.open("http://localhost:8080/knoeien/index.xhtml","_self");			
        });
    };
});

Controllers.controller("editProfileCtrl", function($scope, $rootScope, restservice, $cookies) {
	$scope.isproceed = false;
	$rootScope.userinfos = {};
	$scope.user = {};
	$scope.formSubmitted = false;
	$scope.responseMessage = "";
	
	$scope.profileInfo = function() {	
		console.log("loading show profile");
		restservice.get( '', "api/v1/user/profileInfo").then(function(response) {
			if (response != null) {
				$scope.user = response;	
        	} else {
        		$scope.responseMessage = response.message;	
        	}
        });
	
    };
    $scope.profileInfo();
    
	
	$scope.editProfile = function(isValid) {
		if(!isValid) return;
		
		restservice.post( $scope.user, "api/v1/user/editProfile").then(function(response) {
			if (response != null && response.success) {
				$scope.isproceed = true;
				$scope.responseMessage = response.message;				
        	}
        });
		
    };   
    
});