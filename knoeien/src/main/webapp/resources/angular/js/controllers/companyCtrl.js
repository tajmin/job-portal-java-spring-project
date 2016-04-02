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

Controllers.controller("ClientCtrl", function($scope, $rootScope, restservice, $cookies, $http, authService) {
	$scope.isproceed = false;
	$scope.user = {};
	$scope.userList = {};
	$scope.formSubmitted = false;
	$scope.responseMessage = "";
	
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
});