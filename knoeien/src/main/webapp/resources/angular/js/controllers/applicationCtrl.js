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
	$scope.verifyMessageRequired = false;
	$scope.contactRequired = false;
	
	$("#verificationCode").removeClass("verificationCode");
	
	$scope.init = function() {
		console.log("dasd");
	};
	
	$scope.signup = function(isValid) {
		if (!isValid) return;		
		$scope.user.dateOfBirth = $scope.user.year + "-" + $scope.user.month + "-" + $scope.user.day;
		
		restservice.post($scope.user, "api/v1/user/signup").then(function(response) {
			if (response != null) {
				$scope.isproceed = true;
			}
		});
	};
	
	$scope.sendVerificationCode = function() {
		if($scope.user.contact && $scope.user.contact.trim().length > 0){
			$scope.verifyMessage = "processing...";
			$scope.verifyMessageRequired = true;
			$scope.contactRequired = false;
			restservice.post("", "api/v1/user/sendVerificationCode?mobileNumber=" + $scope.user.contact).then(function(response) {
				if (response != null) {
					$scope.verifyMessage = "Enter verification code and hit enter button";
					$scope.verifyMessageRequired = true;
				}
			});
		}else{
			$scope.contactRequired = true;
		}
	};
	

	$scope.verifyCode = function($event) {
		if ($scope.user.verificationCode && $scope.user.verificationCode.trim().length > 0) {
			if ($event.keyCode == 13 || $event.keyCode == 9) {
				$scope.verifyMessage = "Processing...";
				$scope.verifyMessageRequired = true;
				$scope.contactRequired = false;
				restservice.post("", "api/v1/user/verifyNumber?verificationCode=" + $scope.user.verificationCode).then(function(response) {
					if (response != null) {
						$scope.verifyMessage = "";
						$scope.verifyMessageRequired = false;
						$("#verificationCode").addClass("verificationCode");
					}
				});
			}
		}else{
			$scope.verifyMessage = "Enter verification code and hit enter button";
			$scope.verifyMessageRequired = true;
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

		restservice.post('', "api/v1/user/invite?email=" + $scope.user.inviteEmail).then(function(response) {
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
			if(response){
				$scope.isproceed = true;
				$rootScope.userinfos = response;
				window.open($rootScope.getBaseUrl() + "/index.xhtml",	"_self");
			}
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

Controllers.controller("editProfileCtrl", function($scope, $rootScope, restservice, $cookies, $http, authService) {
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
	$scope.verifyMessage="";
	$scope.mobileRequiredMessage="";

	$scope.profileInfo = function() {
		console.log("loading show profile");
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
		restservice.post( $scope.user, "api/v1/user/editProfile").then(function(response) {
			if (response != null) {
				$scope.isproceed = true;
				$scope.responseMessage = response.message;
        	} 
			$("#profile-response-modal").foundation('toggle');
        });
		
    };   
    
    $scope.loadUserSetting = function() {
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
		restservice.post($scope.user, "api/v1/user/saveUserSetting?name=" + settingName).then(function(response) {
			if (response != null && response.success) {
				$scope.isproceed = false;
				$scope.responseMessage = response.message;
			}
		});
	};
	
	$scope.invite = function(isValid) {
		if (!isValid) return;

		restservice.post('', "api/v1/user/invite?email=" + $scope.user.inviteEmail).then(function(response) {
			if (response != null && response.success) {
				$scope.isproceed = true;
				$scope.responseMessage = response.message;
			}
		});
	};
	
	$scope.transactionReceived = function() {
		console.log("loading payment received");
		
		restservice.get('', "api/v1/transaction/paymentReceived").then(function(response) {
			if (response != null) {
				console.log(response);
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
	
	$scope.sendVerificationCode = function() {
		if($scope.user.phone && $scope.user.phone.trim().length > 0){
			$scope.verifyMessage = "processing...";
			$scope.mobileRequiredMessage = "";
			restservice.post("", "api/v1/user/sendVerificationCode?mobileNumber=" + $scope.user.phone).then(function(response) {
				if (response != null) {
					$scope.verifyMessage = "enter verification code and hit enter button";
					$("#phone-verification-modal").foundation('toggle');
				}
			});
		}else{
			$scope.mobileRequiredMessage = "Please enter mobile number";
		}
	};
	
	$scope.verifyCode = function(isValid) {
		if(!isValid) return;
		
		if ($scope.user.verificationCode && $scope.user.verificationCode.trim().length > 0) {
				$scope.verifyMessage = "processing...";
				$scope.mobileRequiredMessage = "";
				restservice.post("", "api/v1/user/verifyNumber?verificationCode=" + $scope.user.verificationCode).then(function(response) {
					if (response != null) {
						$scope.verifyMessage = "";
					}
				});	
		}
	};

	$scope.openFileDialogue = function(){
		$("#userImageFileUpload").trigger('click');
	};
	
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

Controllers.controller("overviewCtrl", function($scope, $rootScope, restservice, $cookies, $window) {
	$scope.isproceed = false;
	$scope.assginedJob = [];
	$scope.postedJob = [];
	$scope.responseMessage = "";
	$scope.filter = {};
	$scope.assignedFilter = {};
	$scope.assignedFilter.page = 1;
	$scope.assignedFilter.moreLink = true;
	$scope.filter.page = 1;
	$scope.filter.moreLink = true;

	$scope.jobAssigned = function() {
		$scope.assignedFilter.moreLink = true;
		restservice.get('', "api/v1/job/getJobByAssignedUserId?page=" +$scope.assignedFilter.page).then(function(response) {
			if (response != null) {
				for (var i = 0; i < response.length; i++) {
					$scope.assginedJob.push(response[i]);
				}
				if(response.length < 4){
					$scope.assignedFilter.moreLink = false;				
				}else{
					$scope.assignedFilter.page += 1;
				}
	    	} else {
	    		$scope.assignedFilter.moreLink = false;
	    	}
	    });
	};	
	$scope.jobAssigned();
	
	$scope.jobPosted = function() {
		$scope.filter.moreLink = true;
		restservice.get('', "api/v1/job/getJobByCreatedUserId?page=" +$scope.filter.page).then(function(response) {
			if (response != null) {
				for (var i = 0; i < response.length; i++) {
					$scope.postedJob.push(response[i]);
				}
				if(response.length < 4){
					$scope.filter.moreLink = false;				
				}else{
					$scope.filter.page += 1;
				}
	    	} else {
        		$scope.filter.moreLink = false;
        	}
	    });
	};	
	$scope.jobPosted();
	
	/*$scope.postJob = function(jobId) {			
		restservice.post( '', "api/v1/job/postsavedjob?jobID=" + jobId).then(function(response) {
			if (response != null) {
				$scope.postedJob = response;
        	}
        });
	
    };*/
    
    $scope.postJob = function(jobid){
    	window.open($rootScope.getBaseUrl() + "/jobpost.xhtml?id=" + jobid,	"_self");
    }
	
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
	$scope.job.price = 0;
	$scope.job.deadlineMonth = 0;
	$scope.job.deadlineDay = 0;
	$scope.job.hours = 0;
	$scope.job.minutes = 0;
	$scope.filter = {};
	$scope.filter.isnext = true;
	$scope.page = 1;
	
	//@start this portion is responsible to edit draft job 
	$scope.jobId = utilservice.getParameterByName("id");
	if(!utilservice.isUndefinedOrNull($scope.jobId)){
		$scope.jobDetailsById = function(jobId) {			
			restservice.get( '', "api/v1/job/jobDetailsById?jobID=" + jobId).then(function(response) {
				if (response != null) {
					$scope.job = response;
					$scope.tempUploadedFilePath = $scope.job.imageUrl; 
	        	}
	        });
	    };
	    $scope.jobDetailsById($scope.jobId);
	}
	//@end
	
	
	//@start if title is provided then textbox will hide otherwise textbox will show
	var title = utilservice.getParameterByName("title");
	if(!utilservice.isUndefinedOrNull(title)){
		$scope.job.title = title;
		$scope.titleEdit = false;
	}else{
		$scope.titleEdit = true;
	}
	//@end
	
	
	//@start title edit button click listener
	$scope.makeTitleEditable = function() {
		$scope.titleEdit = true;		
    };
    //@end
    
    //@start next button click listener to move next page
    $scope.nextPage = function(isValid) {
    	$scope.filter.isnext = true;
    	if(!isValid) return;
    	
    	if($scope.page > 3){
    		$scope.filter.isnext = false;
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
			$scope.filter.isnext = false;
			$("#panel_job_approved-label").click();
		}
		
		//draft the job
		$scope.draftJob(isValid, false);
    };
    //@end
    
    
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
    
   $scope.setPageNo = function(i) {
    	$scope.page = i;
    };
	
	$scope.draftJob = function(isValid, showModal) {
		if(!isValid) return;
		
		$scope.job.draft = true;
		$scope.job.latitude = $("#latitude").val();
		$scope.job.longitude = $("#longitude").val();
		$scope.job.addressLine1 = $("#placesearch").val();
		$scope.job.imageUrl = $scope.tempUploadedFilePath;
		
		restservice.post($scope.job, "api/v1/job/addjob").then(function(response) {
			if (response != null) {
				$scope.job = response;
				if(showModal){
					$("#draft-confirmation-modal").foundation('toggle');
				}
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
				$("#post-confirmation-modal").foundation('toggle');
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
			if (response != null && response.cardNumber != null && response.cardNumber != "") {
				$scope.postJob();
			}else{
				$scope.payment = response;
				$("#card-info-modal").foundation('toggle');
			}
		});
	};	
	//$scope.getUserPaymentInfo();
	
	$scope.saveUserPaymentInfo = function(isValid) {
		if(!isValid) return;
		
		restservice.post( $scope.payment, "api/v1/user/saveUserPaymentInfo").then(function(response) {
			if (response != null) {
				$scope.postJob();
				$("#card-info-modal").foundation('close');				
        	}
        });
    };   
	
});

Controllers.controller("jobCtrl", function($scope, $rootScope, restservice, $cookies, $window) {
	$scope.isproceed = false;
	$scope.job = [];
	$scope.formSubmitted = false;
	$scope.responseMessage = "";
	$scope.filter = {};
	$scope.filter.page = 1;
	$scope.filter.moreLink = true;
	
	$scope.lgbPage = 1;
	$scope.nrjPage = 1;
	$scope.bpjPage = 1;
	$scope.edjPage = 1;
	
	$window.map = new google.maps.Map(document.getElementById('g-map'), {
        center: {
            lat: -34.397,
            lng: 150.644
        },
        zoom: 8
    });
	
	//Shows Latest Jobs
	$scope.loadJobs = function(type, page) {
		//$scope.job = [];
		$scope.filter = {};
		$scope.filter.page = page;
		$scope.filter.moreLink = true;
		restservice.get( '', "api/v1/job/findjobs?page="+$scope.filter.page+"&type="+type).then(function(response) {
			if (response != null) {
				for (var i = 0; i < response.length; i++) {
					$scope.job.push(response[i]);
				}
				
				if(response.length < 2){
					$scope.filter.moreLink = false;				
				}else{
					if(type == "LGB") $scope.lgbPage += 1;
					if(type == "NRJ") $scope.nrjPage += 1;
					if(type == "BPJ") $scope.bpjPage += 1;
					if(type == "EDJ") $scope.edjPage += 1;
				}
				$scope.showJobInMap();
        	} else {
        		//$scope.responseMessage = response.message;
        		$scope.filter.moreLink = false;
        	}
        });	
    };
    
    $scope.loadJobs('LGB', $scope.lgbPage);
    
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
	$scope.chat = false;
	$scope.employer = {};
	$scope.jobInterest = {};
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
				$scope.jobInterest.bidAmount = $scope.job.price; 
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
    
    $scope.increment = function(i) {
    	if(!i) i = 0;
    	i = parseFloat(i);
    	if(i < 0) return 0;
    	return i + 1;
    };
    
    $scope.decrement = function(i) {
    	if(!i) i = 0;
    	i = parseFloat(i);
    	if(i <= 0) return 0;
    	return i - 1;
    };
    
    $scope.saveJobInterest = function() {
    	$scope.jobInterest.bidAmount = parseFloat($scope.jobInterest.bidAmount);
    	if(!$scope.jobInterest.bidAmount){
    		return;
    	}
    	
    	$scope.jobInterest.jobId = $scope.id;
    	$scope.jobInterest.jobCreatedById = $scope.employer.id
    	restservice.post($scope.jobInterest, "api/v1/jobInterested/saveJobInterest").then(function(response) {
			if (response != null) {
				$scope.jobInterest = response;
				$scope.chat = true;
        	}
        });	
    };
    
    $scope.getJobInterestDetailsByInterestUserId = function(jobID) {			
		restservice.get( '', "api/v1/jobInterested/getJobInterestDetailsByInterestUserId?jobID=" + jobID).then(function(response) {
			if (response != null) {
				$scope.jobInterest = response;
				$scope.chat = true;
        	}
        });	
    };
    $scope.getJobInterestDetailsByInterestUserId($scope.id);
    
});

//Slider Image control
Controllers.controller("sliderImgCtrl", function($scope, $rootScope, restservice, $cookies, $http, authService) {
	$scope.isproceed = false;
	$scope.slider = {};
	$scope.sliderList = {};
	$scope.formSubmitted = false;
	$scope.responseMessage = "";
	$scope.thumb_image = "";
	$scope.imageFile;
	$scope.tempUploadedFilePath = "";
	$scope.verifyMessage="";
	
	
	$scope.openFileDialogue = function() {
		$("#sliderImageFileUpload").trigger('click');
	};
	
	$scope.imageUpload = function(input) {
    	if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('#slider_thumbnail_image').attr('src', e.target.result);
               // $('#user_cover_image').css('background-image', "url(" + e.target.result + ")");
            };
            $scope.imageFile = input.files[0];
            reader.readAsDataURL(input.files[0]);
            
            // save file as temporary
            var uploadUrl = "api/v1/sliderimage/uploadimage";
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
    
    $scope.saveImage = function(isValid) {
		if(!isValid) return;
		
		$scope.slider.imageUrl = $scope.tempUploadedFilePath;
		restservice.post( $scope.slider, "api/v1/sliderimage/addimage").then(function(response) {
			if (response != null) {
				$scope.isproceed = true;
				$scope.responseMessage = response.message;
        	} 
			$("#profile-response-modal").foundation('toggle');
        });
		
    };
    
    $scope.showImage = function() {
		console.log("shows image list");
		restservice.get('', "api/v1/sliderimage/showimage").then(function(response) {
			if (response != null) {
				$scope.sliderList = response;
				//$scope.tempUploadedFilePath = $scope.sliderList.backgroundImageUrl; 
			} else {
				$scope.responseMessage = response.message;
			}
		});

	};
	$scope.showImage();
	
	$scope.deleteImage = function(id) {
		console.log("deletes image");
		restservice.get('', "api/v1/sliderimage/deleteimage?sliderId=" + id).then(function(response) {
			if (response == null) {
				//$scope.sliderList = response;
				$scope.responseMessage = response.message;
				//$scope.tempUploadedFilePath = $scope.sliderList.backgroundImageUrl; 
			} 
		});

	};
	
});