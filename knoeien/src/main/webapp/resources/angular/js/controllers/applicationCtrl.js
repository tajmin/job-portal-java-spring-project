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
	$scope.verifyMessage = "";
	$scope.mobileRequiredMessage = "";
	
	$("#verificationCode").removeClass("verificationCode");
	
	$scope.signup = function(isValid) {
		if (!isValid) return;		
		$scope.user.dateOfBirth = $scope.user.year + "-" + $scope.user.month + "-" + $scope.user.day;
		
		restservice.post($scope.user, "api/v1/user/signup").then(function(response) {
			if (response != null && response.success) {
				$scope.responseMessage = response.message;
			}
		});
	};
	
	$scope.sendVerificationCode = function() {
		if($scope.user.contact && $scope.user.contact.trim().length > 0){
			$scope.verifyMessage = "processing...";
			$scope.mobileRequiredMessage = "";
			restservice.post("", "api/v1/user/sendVerificationCode?mobileNumber=" + $scope.user.contact).then(function(response) {
				if (response != null) {
					$scope.verifyMessage = "enter verification code and hit enter button";
				}
			});
		}else{
			$scope.mobileRequiredMessage = "Please enter mobile number";
		}
	};
	

	$scope.verifyCode = function($event) {
		if ($scope.user.verificationCode && $scope.user.verificationCode.trim().length > 0) {
			if ($event.keyCode == 13 || $event.keyCode == 9) {
				$scope.verifyMessage = "processing...";
				$scope.mobileRequiredMessage = "";
				restservice.post("", "api/v1/user/verifyNumber?verificationCode=" + $scope.user.verificationCode).then(function(response) {
					if (response != null) {
						$scope.verifyMessage = "";
						$("#verificationCode").addClass("verificationCode");
					}
				});
			}
		}else{
			$scope.verifyMessage = "enter verification code and hit enter button";
		}
	};
});

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

//Controllers.controller("saveUserSettingCtrl", function($scope, $rootScope, restservice, $cookies) {
//	$scope.isproceed = false;
//	$scope.user = {};
//	$scope.responseMessage = "";
//		
//	$scope.loadUserSetting = function() {
//		console.log("show user setting");
//		restservice.get('', "api/v1/user/loadUserSetting").then(function(response) {
//			if (response != null) {
//				$scope.user = response;
//			} else {
//				$scope.responseMessage = response.message;
//			}
//		});
//	};
//	$scope.loadUserSetting();
//	
//	$scope.settings = function(settingName) {
//	//console.log($scope.user.epost);
//		restservice.post($scope.user, "api/v1/user/saveUserSetting?name=" + settingName).then(function(response) {
//			if (response != null && response.success) {
//				$scope.isproceed = false;
//				$scope.responseMessage = response.message;
//			}
//		});
//	};
//});
//
//
//
//Controllers.controller("settingsCtrl", function($scope, $rootScope, restservice, $cookies) {
//	$scope.isproceed = false;
//	$scope.user = {};
//	$scope.responseMessage = "";
//	
//	$scope.settings = function() {
//		restservice.post( $scope.user, "api/v1/user/settings").then(function(response) {
//			if (response != null && response.success) {
//				$scope.isproceed = true;
//				$scope.responseMessage = response.message;				
//        	}
//        });
//    };
//});

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
			window.open($rootScope.getBaseUrl() + "/index.xhtml",	"_self");
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
			window.open($rootScope.getBaseUrl() + "/index.xhtml", "_self");
		});
	};
});

Controllers.controller("editProfileCtrl", function($scope, $rootScope, restservice, $cookies, utilservice, $http, authService) {
	$scope.isproceed = false;
	$scope.user = {};
	$scope.balance = {};
	$scope.paymentReceived = {};
	$scope.paymentMade = {};
	$scope.formSubmitted = false;
	$scope.responseMessage = "";
	$scope.cover_image = "";
	$scope.imageFile;
	$scope.tempUploadedFilePath = "";

	$scope.profileInfo = function() {
		console.log("loading show profile");
		console.log($scope.user);
		restservice.get('', "api/v1/user/profileInfo").then(function(response) {
			if (response != null) {
				$scope.user = response;
				$scope.tempUploadedFilePath = $scope.user.backgroundImageUrl; 
			} else {
				$scope.responseMessage = response.message;
			}
		});

	};
	$scope.profileInfo();
	
	$scope.editProfile = function(isValid) {
		if(!isValid) return;
		
		$scope.user.backgroundImageUrl = $scope.tempUploadedFilePath;
		console.log($scope.user.firstname);
		restservice.post( $scope.user, "api/v1/user/editProfile").then(function(response) {
			if (response != null) {
				$scope.isproceed = true;
				$scope.responseMessage = response.message;				
        	}
        });
		
    };   
    
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
	
	$scope.transactionReceived = function() {
		console.log("loading payment received");
		console.log($scope.paymentReceived);
		restservice.get('', "api/v1/transaction/paymentReceived").then(function(response) {
			if (response != null) {
				$scope.paymentReceived = response;
			} 
		});

	};
	$scope.transactionReceived();
	
	$scope.transactionPaid = function() {
		console.log("loading payment made");
		console.log($scope.paymentReceived);
		restservice.get('', "api/v1/transaction/paymentMade").then(function(response) {
			if (response != null) {
				$scope.paymentMade = response;
			} 
		});

	};
	$scope.transactionPaid();
	
	$scope.imageUpload = function(input){
    	if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('#user_thumbnail_image').attr('src', e.target.result);
                $('#user_cover_image').css('background-image', "url(" + e.target.result + ")");
            };
            $scope.imageFile = input.files[0];
            reader.readAsDataURL(input.files[0]);
            
            // save file as temporary
            var uploadUrl = "api/v1/user/uploadimage";
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

Controllers.controller("overviewCtrl", function($scope, $rootScope, restservice, $cookies) {
	$scope.isproceed = false;
	$scope.assginedJob = {};
	$scope.postedJob = {};
	$scope.responseMessage = "";

	$scope.jobAssigned = function() {
		restservice.get('', "api/v1/job/getJobByAssignedUserId").then(function(response) {
			if (response != null) {
				$scope.assginedJob = response;				
	    	}
	    });
	};	
	$scope.jobAssigned();
	
	$scope.jobPosted = function() {
		restservice.get('', "api/v1/job/getJobByCreatedUserId").then(function(response) {
			if (response != null) {
				$scope.postedJob = response;				
	    	}
	    });
	};	
	$scope.jobPosted();
	
});

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
	$scope.payment = {};
	$scope.formSubmitted = false;
	$scope.responseMessage = "";
	$scope.cover_image = "";
	$scope.imageFile;
	$scope.tempUploadedFilePath = "";
	$scope.titleEdit = false;
	$scope.page = 1;
	$scope.job.price = 0;
	$scope.job.deadlineMonth = 0;
	$scope.job.deadlineDay = 0;
	$scope.job.hours = 0;
	$scope.job.minutes = 0;
	
	var title = utilservice.getParameterByName("title");
	if(!utilservice.isUndefinedOrNull(title)){
		$scope.job.title = title;
	}
	
	$scope.makeTitleEditable = function() {
		$scope.titleEdit = true;		
    };
    
    $scope.nextPage = function(isValid) {
    	if(!isValid) return;
    	
    	if($scope.page > 3){
    		return;
    	}
    	
		$scope.page += 1;
		
		if($scope.page == 1){
			$("#panel_job_info-label").trigger('click');
		}
		
		if($scope.page == 2){
			$("#panel_job_details-label").trigger('click');
		}
		
		if($scope.page == 3){
			$("#panel_job_approved-label").click();
		}
		
    };
    
    $scope.increment = function(i) {
    	if(!i) i = 0;
    	i = parseInt(i);
    	if(i < 0) return 0;
    	return i + 1;
    };
    
    $scope.decrement = function(i) {
    	if(!i) i = 0;
    	i = parseInt(i);
    	if(i <= 0) return 0;
    	return i - 1;
    };
    
    $scope.isPost = function(isValid) {
    	if($scope.page >= 3){
    		return true;
    	}
    	return false;
    };
    
    $scope.setPageNo = function(i) {
    	$scope.page = i;
    };
	
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
				$("#draft-confirmation-modal").foundation('toggle');
        	}
        });
		
    };
    
    
    $scope.openPaymentWindow = function(isValid){
    	if(!isValid) return;
    	$scope.getUserPaymentInfo();
    };
    
    $scope.postJob = function() {
    	$scope.job.draft = false;
		$scope.job.latitude = $("#latitude").val();
		$scope.job.longitude = $("#longitude").val();
		$scope.job.addressLine1 = $("#placesearch").val();
		$scope.job.imageUrl = $scope.tempUploadedFilePath;
		
		restservice.post($scope.job, "api/v1/job/addjob").then(function(response) {
			if (response != null) {
				$scope.job = response;
				$("#draft-confirmation-modal").foundation('toggle');
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
            	$scope.tempUploadedFilePath = succResponse.response;
            	$scope.job.imageUrl = $scope.tempUploadedFilePath;
            }).error(function(errResponse){
            	$rootScope.restMessages = errResponse.message;
            });
        }
    };
    
    $scope.getUserPaymentInfo = function() {
		restservice.get('', "api/v1/user/getUserPaymentInfo").then(function(response) {
			if (response != null) {
				$scope.payment = response;
				console.log($scope.payment);
			}
			$("#card-info-modal").foundation('toggle');
		});
	};
	$scope.getUserPaymentInfo();    
	
	
	$scope.saveUserPaymentInfo = function(isValid) {
		if(!isValid) return;
		
		restservice.post( $scope.user, "api/v1/user/saveUserPaymentInfo").then(function(response) {
			if (response != null) {
				$scope.postJob();
        	}
			$("#evaluate-confirmation-modal").foundation('toggle');
        });
    };   
	
});

Controllers.controller("jobCtrl", function($scope, $rootScope, restservice, $cookies, $window) {
	$scope.isproceed = false;
	$scope.job = {};
	$scope.formSubmitted = false;
	$scope.responseMessage = "";
	
	$window.map = new google.maps.Map(document.getElementById('g-map'), {
        center: {
            lat: -34.397,
            lng: 150.644
        },
        zoom: 8
    });
	
	//Shows Latest Jobs
	$scope.latestJob = function() {			
		restservice.get( '', "api/v1/job/latestjob").then(function(response) {
			if (response != null) {
				$scope.job = response;
				$scope.showJobInMap();
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
    
    $scope.showJobInMap = function(){
    	//http://stackoverflow.com/questions/1544739/google-maps-api-v3-how-to-remove-all-markers
    	var bounds = new google.maps.LatLngBounds();
    	for(i in $scope.job) {
    		if($scope.job[i] && $scope.job[i].title){
    			var latLng = new google.maps.LatLng($scope.job[i].latitude, $scope.job[i].longitude); 
    	        var marker = new google.maps.Marker({
    	            position: latLng,
    	            map: $window.map,
    	            title: $scope.job[i].title
    	        });
    	        bounds.extend(marker.position);
    		}
    	}
    	$window.map.fitBounds(bounds);
    };
    
    $scope.selectJob = function(jobid){
    	window.open($rootScope.getBaseUrl() + "/jobdetail.xhtml?id=" + jobid,	"_self");
    }
    
});

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
    	//http://stackoverflow.com/questions/1544739/google-maps-api-v3-how-to-remove-all-markers
    	var bounds = new google.maps.LatLngBounds();
    	//for(i in $scope.job) {
    		if($scope.job && $scope.job.title){
    			var latLng = new google.maps.LatLng($scope.job.latitude, $scope.job.longitude); 
    	        var marker = new google.maps.Marker({
    	            position: latLng,
    	            map: $window.map,
    	            title: $scope.job.title
    	        });
    	        bounds.extend(marker.position);
    		}
    	//}
    	$window.map.fitBounds(bounds);
    };    
    
});