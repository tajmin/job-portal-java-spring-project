Controllers.controller("JobLookupCtrl", function($scope, $rootScope, restservice, $cookies) {
	$scope.filter = {};
	$scope.filter.searchText = "";
});

Controllers.controller("UserCtrl", function($scope, $rootScope, restservice, $cookies) {
	$scope.user = {};
	$scope.addUser = function() {
		var d = new Date();
		var n = d.getTime();

		restservice.get('', "api/v1/user").then(function(data) {
			console.log(data);
		});

		restservice.post($scope.user, "api/v1/user").then(function(data) {
			if (data != null) {
				console.log(data);
			}
		});
	};

	$scope.login = function() {
		restservice.get('', "api/v1/user").then(function(data) {
			console.log(data);
		});

		restservice.post($scope.user, "api/v1/user").then(function(data) {
			if (data != null) {
				console.log(data);
			}
		});
	};
});

Controllers.controller("signupCtrl", function($scope, $rootScope, restservice, $cookies) {
	$scope.isproceed = false;
	$scope.user = {};
	$scope.user.day = "";
	$scope.user.month = "";
	$scope.user.year = "";
	$scope.user.dateOfBirth = "";
	$scope.formSubmitted = false;
	$scope.responseMessage = "";
	
	$scope.signup = function(isValid) {
		if (!isValid) return;		
		$scope.user.dateOfBirth = $scope.user.year + "-" + $scope.user.month + "-" + $scope.user.day;
		
		restservice.post($scope.user, "api/v1/user/signup").then(function(response) {
			if (response != null && response.success) {
				$scope.isproceed = true;
				$scope.responseMessage = response.message;
			}
		});
	};
	
	$scope.sendVerificationCode = function() {
		
		restservice.post("", "api/v1/user/sendVerificationCode?mobileNumber=" + $scope.user.contact).then(function(response) {
			if (response != null) {
				
			}
			console.log(response);
		});
	};		
	$scope.enteredVerificationCode=function(keyEvent){
		 if (keyEvent.which === 13){				 
			console.log('Key press'+$scope.user.verificationCode);		
					restservice.post("", "api/v1/user/verifyNumber?verificationCode=" + $scope.user.verificationCode).then(function(response) {
						console.log('After entering controller ');
						if (response != null && response == true) {
							$("#verificationCodeId").removeClass("verificationCode0");
							$("#verificationCodeId").addClass("verificationCode1");
						}else{
							
							$("#verificationCodeId").removeClass("verificationCode1");
							$("#verificationCodeId").addClass("verificationCode0");
						}
						console.log(response);
						});
		 }
	}
});

//Controllers.controller("invitationCtrl", function($scope, $rootScope, restservice, $cookies) {
//	if(!isValid) return;
//	
//	restservice.post( $scope.user, "api/v1/user/signup").then(function(response) {
//		if (response != null && response.success) {
//			$scope.isproceed = true;
//			$scope.responseMessage = response.message;				
//    	}
//    });    
//});

Controllers.controller("invitationCtrl", function($scope, $rootScope, restservice, $cookies) {
	$scope.isproceed = false;
	$scope.user = {};
	$scope.formSubmitted = false;
	$scope.responseMessage = "";
	
	$scope.invite = function(isValid) {
		if (!isValid) return;

		restservice.post('', "api/v1/user/invite?email=" + $scope.user.email).then(function(response) {
			if (response != null && response.success) {
				$scope.isproceed = true;
				$scope.responseMessage = response.message;
			}
		});
	};
});

Controllers.controller("saveUserSettingCtrl", function($scope, $rootScope, restservice, $cookies) {
	$scope.isproceed = false;
	$scope.user = {};
	$scope.responseMessage = "";
		
	$scope.loadUserSetting = function() {
		console.log("show user setting");
		restservice.get('', "api/v1/user/loadUserSetting").then(function(response) {
			if (response != null) {
				$scope.user = response;
			} else {
				$scope.responseMessage = response.message;
			}
		});
	};
	$scope.loadUserSetting();
	
	$scope.settings = function(settingName) {
	//console.log($scope.user.epost);
		restservice.post($scope.user, "api/v1/user/saveUserSetting?name=" + settingName).then(function(response) {
			if (response != null && response.success) {
				$scope.isproceed = false;
				$scope.responseMessage = response.message;
			}
		});
	};
});



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

Controllers.controller("loginCtrl", function($scope, $rootScope, restservice, $cookies) {
	$scope.formSubmitted = false;
	$rootScope.userinfos = {};
	$scope.user = {};
	
	$scope.login = function(isValid) {
		if (!isValid) return;
		
		restservice.post($scope.user, "api/v1/user/login").then(function(response) {
			$scope.isproceed = true;
			$rootScope.userinfos = response;
			console.log($rootScope.userinfos);
			window.open("http://localhost:8080/knoeien/home.xhtml",	"_self");
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
		if (!isValid)
		return;
		$scope.responseMessage = "";
		
		restservice.post($scope.user,"api/v1/user/sendChangePasswordEmail?email=" + $scope.user.username).then(function(response) {
			if (response != null && response.success) {
				$scope.isproceed = true;
				$scope.responseMessage = response.message;
			}
		});
	};

	$scope.resetPassword = function(isValid) {
		if (!isValid) return;
		
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
		var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), results = regex
				.exec(location.search);
		return results == null ? "" : decodeURIComponent(results[1].replace(
				/\+/g, " "));
	}
});

Controllers.controller("logoutCtrl", function($scope, $rootScope, restservice,$cookies) {
	$scope.logout = function() {
		restservice.get('', "api/v1/user/logout").then(function(response) {
			window.open("http://localhost:8080/knoeien/index.xhtml", "_self");
		});
	};
});

Controllers.controller("editProfileCtrl", function($scope, $rootScope, restservice, $cookies) {
	$scope.isproceed = false;
	$rootScope.userinfos = {};
	$scope.user = {};
	$scope.balance = {};
	$scope.formSubmitted = false;
	$scope.responseMessage = "";

	$scope.profileInfo = function() {
		console.log("loading show profile");
		console.log($scope.user);
		restservice.get('', "api/v1/user/profileInfo").then(function(response) {
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
		
		console.log($scope.user.firstname);
		restservice.post( $scope.user, "api/v1/user/editProfile").then(function(response) {
			if (response != null && response.success) {
				$scope.isproceed = true;
				$scope.responseMessage = response.message;				
        	}
        });
		
    };   
    
//	$scope.editProfile = function(isValid) {
//		if (!isValid) return;
//		restservice.post($scope.user, "api/v1/balance/editProfile").then(
//		function(response) {
//			if (response != null && response.success) {
//				$scope.isproceed = true;
//				$scope.responseMessage = response.message;
//			}
//		});
//	};

});


//@author SHIFAT controller for BALANCE
Controllers.controller("transactionCtrl", function($scope, $rootScope,restservice, $cookies) {	
	$scope.isproceed = false;
	$scope.transactionHistory = {};
	$scope.formSubmitted = false;
	$scope.responseMessage = "";

	$scope.transactionInfo = function() {
		console.log("loading user transaction");
		restservice.get('', "api/v1/transaction/transactionInfo").then(function(response) {
			if (response != null) {
				$scope.transactionHistory = response;
			} else {
				$scope.responseMessage = response.message;
			}
		});

	};
	$scope.transactionInfo();
});


Controllers.controller("addJobCtrl", function($scope, $rootScope, restservice, $cookies, utilservice, $http, authService) {
	$scope.isproceed = false;
	$scope.job = {};
	$scope.formSubmitted = false;
	$scope.responseMessage = "";
	$scope.cover_image = "";
	$scope.imageFile;
	$scope.tempUploadedFilePath = "";
	
	var title = utilservice.getParameterByName("title");
	if(!utilservice.isUndefinedOrNull(title)){
		$scope.job.title = title;
	}
	
	
	$scope.draftJob = function(isValid) {
		if(!isValid) return;
		
		$scope.job.draft = true;
		$scope.job.latitude = $("#latitude").val();
		$scope.job.longitude = $("#longitude").val();
		$scope.job.addressLine1 = $("#placesearch").val();
		$scope.job.imageUrl = $scope.tempUploadedFilePath;
		
		restservice.post($scope.job, "api/v1/job/addjob").then(function(response) {
			if (response != null) {
				$scope.job = response;
        	}
        });
		
    };
    
    
    $scope.postJob = function(isValid) {
    	$scope.job.draft = false;
		$scope.job.latitude = $("#latitude").val();
		$scope.job.longitude = $("#longitude").val();
		$scope.job.addressLine1 = $("#placesearch").val();
		$scope.job.imageUrl = $scope.tempUploadedFilePath;
		
		restservice.post($scope.job, "api/v1/job/addjob").then(function(response) {
			if (response != null) {
				$scope.job = response;
        	}
        });
		
    };
    
    
    $scope.imageUpload = function(input){
    	if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('#job_thumbnail_image').attr('src', e.target.result);
                $('#job_cover_image').css('background-image', "url(" + e.target.result + ")");
            };
            $scope.imageFile = input.files[0];
            reader.readAsDataURL(input.files[0]);
            
            // save file as temporary
            var uploadUrl = "api/v1/job/uploadimage";
            var fd = new FormData();
            fd.append('file', $scope.imageFile);
    		 
            $http.post(uploadUrl, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            }).success(function(succResponse){
            	$rootScope.restMessages = succResponse.message;
            	$scope.tempUploadedFilePath = succResponse.response
            }).error(function(errResponse){
            	$rootScope.restMessages = errResponse.message;
            });
        }
    };
	
});

Controllers.controller("jobCtrl", function($scope, $rootScope, restservice, $cookies) {
	$scope.isproceed = false;
	$scope.job = {};
	$scope.formSubmitted = false;
	$scope.responseMessage = "";
	
	//Shows Latest Jobs
	$scope.latestJob = function() {			
		restservice.get( '', "api/v1/job/latestjob").then(function(response) {
			if (response != null) {
				$scope.job = response;	
        	} else {
        		$scope.responseMessage = response.message;	
        	}
        });
	
    };
    $scope.latestJob();
    
    //Shows Best Paid Jobs 
    $scope.bestPaidJob = function() {	
		
		restservice.get( '', "api/v1/job/bestpaidjob").then(function(response) {
			if (response != null) {
				$scope.job = response;	
				console.log($scope.job);
        	} else {
        		$scope.responseMessage = response.message;	
        	}
        });
	
    };
    
    //Shows Shortest Time Jobs 
    $scope.shortestTimeJob = function() {		
		restservice.get( '', "api/v1/job/shortesttimejob").then(function(response) {
			if (response != null) {
				$scope.job = response;	
        	} else {
        		$scope.responseMessage = response.message;	
        	}
        });
	
    };
    
    //Shows Earliest deadline Jobs 
    $scope.earliestDeadlineJob = function() {		
		restservice.get( '', "api/v1/job/earliestdeadlinejob").then(function(response) {
			if (response != null) {
				$scope.job = response;	
        	} else {
        		$scope.responseMessage = response.message;	
        	}
        });
	
    };
    
    //Shows Nearest You Jobs 
    $scope.nearestJob = function() {		
		restservice.get( '', "api/v1/job/nearestjob").then(function(response) {
			if (response != null) {
				$scope.job = response;	
        	} else {
        		$scope.responseMessage = response.message;	
        	}
        });
	
    };     
});