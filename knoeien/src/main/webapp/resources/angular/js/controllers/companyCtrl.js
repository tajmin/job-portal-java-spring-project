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
			$("#profile-response-modal").foundation('toggle');
		});

	};
	
});

Controllers.controller("ClientCtrl", function($scope, $rootScope, restservice, $cookies, $http, $window, utilservice) {
	$scope.isproceed = false;
	$scope.user = {};
	$scope.userList = {};
	$scope.formSubmitted = false;
	$scope.responseMessage = "";
	$scope.user.deactivate = false;
	$scope.id = utilservice.getParameterByName("id");
	
	$scope.showUser = function() {
		console.log("shows user list");
		restservice.get('', "api/v1/user/showalluser").then(function(response) {
			if (response != null) {
				$scope.userList = response; 
			} else {
				$scope.responseMessage = response.message;
			}
		});

	};
	$scope.showUser();
	
	$scope.deleteUser = function(id) {
		console.log("deletes user");
		restservice.get('', "api/v1/user/deleteuser?userId=" + id).then(function(response) {
			if (response == null) {
				$scope.responseMessage = response.message;
			} 
		});

	};
	
	$scope.updateUser = function(id) {
		//if() return;
	
		if($scope.user.deactivate) {
			$scope.user.active = false;
		} else {
			$scope.user.active = true;
		}

		restservice.post( $scope.user, "api/v1/user/updateuser?id=" + id).then(function(response) {
			if (response != null) {
				$scope.isproceed = true;
				$scope.responseMessage = response.message;
        	} 
			$("#profile-response-modal").foundation('toggle');
        });
		
    };
    
    $scope.select = function(id) {
    	window.open($rootScope.getBaseUrl() + "/client-details.xhtml?id=" + id,	"_self");
    }
    
    $scope.clientDetailsById = function(clientId) {		
		restservice.get( '', "api/v1/user/showuserbyid?id=" + clientId).then(function(response) {
			if (response != null) {
				$scope.user = response;
				if($scope.user.active) {
					$scope.user.deactivate = false;
				} else {
					$scope.user.deactivate = true;
				}
        	}
        });
	
    };
    $scope.clientDetailsById($scope.id);
});

Controllers.controller("HelperCtrl", function($scope, $rootScope, restservice, $cookies, $http) {
	//$scope.isproceed = false;
	$scope.company = {};
	//$scope.formSubmitted = false;
	$scope.responseMessage = "";
	
	$scope.showCompany = function() {
		console.log("shows company info");
		restservice.get('', "api/v1/company/showcompanyinfo").then(function(response) {
			if (response != null) {
				$scope.company = response; 
				$scope.responseMessage = response.message;
			} else {
				$scope.responseMessage = response.message;
			}
		});

	};
	$scope.showCompany();

	
	$scope.updateCompany = function() {
		//if() return;

		restservice.post( $scope.company, "api/v1/company/editcompany").then(function(response) {
			if (response != null) {
				//$scope.isproceed = true;
				$scope.responseMessage = response.message;
        	} 
			$("#profile-response-modal").foundation('toggle');
        });
		
    };
});