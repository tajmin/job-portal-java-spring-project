Controllers.controller("jobOutCtrl", function($scope, $rootScope, restservice, $cookies, $window, utilservice) {
	$scope.isproceed = false;
	$scope.interesteduserlist = [];
	$scope.message = {};
	$scope.formSubmitted = false;
	$scope.user={};
	$scope.responseMessage = "";
	$scope.jobdetails={};
	$scope.selectinteresteduser={};
	$scope.selecteduser="";
	$scope.selectedusersmessage={};
	$scope.userId="";
	$scope.jobId=utilservice.getParameterByName("jobId");
	$scope.messages = [];
	$scope.newMessage = {};
	$scope.msgpage = 1;
	$scope.msgMoreLink = true;
	$scope.employer = {};
	
	
	$scope.jobOutLists = function() {
		
		console.log("Loading Job out List");
		restservice.get('', "api/v1/jobOut/getJobOutListById?jobId=" +$scope.jobId).then(function(response) {
			if (response != null) {
				for (var i = 0; i < response.length; i++) {
					$scope.jobdetails=response[0];
					$scope.interesteduserlist.push(response[i]);
				}
				$scope.responseMessage = response.message;
			} 
		});

	};
	$scope.jobOutLists();
	
	$scope.selectinteresteduser= function(firstName,lastName,userMessage,imageUrl,Id,jobPosterId,jobSeekerId){
		$scope.isproceed = false;
		$scope.selecteduser=firstName+" "+lastName;
		$scope.selectedusersmessage=userMessage;
		$scope.selectedusersimage=imageUrl;
		$scope.jobPosterId=jobPosterId;
		$scope.jobSeekerId=Id;
		//$scope.jobSeekerId=jobSeekerId;
		$scope.msgpage = 1;
		
		$(".comments").removeClass("fa-commenting-o");
		$("#comments-" +Id).addClass("fa-commenting-o");
		console.log("Loading Job out Users Message List");
		$scope.getAllMessages($scope.jobId,jobSeekerId);
	}
	
	$scope.getUserByJobId = function(jobId) {			
		restservice.get( '', "api/v1/user/getUserByJobId?jobID=" + jobId).then(function(response) {
			if (response != null) {
				$scope.employer = response;
        	}
        });	
    };
    $scope.getUserByJobId($scope.jobId);
    
	
	// chat part
    $scope.sendMessageToEmployeer = function(jobSeekerId) {
    	$scope.newMessage.jobId = $scope.jobId;
    	$scope.newMessage.toUserId = jobSeekerId; //employer Id
    	
    	restservice.post($scope.newMessage, "api/v1/message/sendMessageToEmployeer").then(function(response) {
			if (response != null) {
				$scope.newMessage = {};
				$scope.msgMoreLink = true;
				$scope.msgpage = 1;
				$scope.getAllMessages($scope.jobId,jobSeekerId);
        	}
        });	
    }
    
    $scope.getAllMessages = function(jobID,jobSeekerId) {
    	$scope.messages=[];
    	restservice.get('', "api/v1/message/getMessageListByJobId?jobId=" + jobID +"&jobSeekerId="+ jobSeekerId + "&page="+ $scope.msgpage).then(function(response) {
			if (response != null) {
				for (var i = 0; i < response.length; i++) {
					$scope.messages.push(response[i]);
				}
				
				if(response.length < 2){
					$scope.msgMoreLink = false;				
				}else{
					$scope.msgpage += 1;
				}
        	}else {
        		$scope.msgMoreLink = false;
        	}
        });	
    }
	
	
	
});