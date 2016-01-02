Services.factory('authService', function(Restangular, $rootScope, $cookies) {
	var service = {};
	service.access_token = localStorage.auth || '';
	service.serviceBaseUrl = '';
	$rootScope.access_token = service.access_token;
	var ajaxCount = 0;
	$rootScope.firstTime = true;
	$rootScope.isProcessing = false;
	$rootScope.isRequestCompleted = true;
	$rootScope.allowToSubmit = true;
	service.enableAuth = function(isPost) {
		return Restangular.withConfig(function(RestangularConfigurer) {
			RestangularConfigurer.addRequestInterceptor(function(element) {
				$rootScope.isProcessing = true;
				$rootScope.isRequestCompleted = true;
				if (!$rootScope.firstTime) {
					setTimeout(function() {
						$rootScope.isProcessing = false;
						$rootScope.firstTime = false
					}, 800)
				} else {
					setTimeout(function() {
						ajaxCount++;
						if (ajaxCount > 0) {
							$("#ajaxloader").css("top", "0px")
						}
					}, 800)
				}
				return element
			});
			RestangularConfigurer.addResponseInterceptor(function(response,restangularObject, url) {
				$rootScope.isProcessing = false;
				ajaxCount--;
				if (ajaxCount == 0) {
					$("#ajaxloader").css("top", "-35px")
				}
				
				if (response.response) {
					return response.response;
				}
				return response;
			});
			RestangularConfigurer.setErrorInterceptor(function(response, restangularObject, url) {
				$rootScope.isProcessing = false;
				ajaxCount--;
				if (ajaxCount == 0) {
					setTimeout(function() {
						$("#ajaxloader").css("top", "-35px")
					}, 800)
				}
			
				
				if (response.status === 503) {
					window.location.href = window.location.origin
							+ service.serviceBaseUrl + '/outOfService.xhtml'
							
				} else if(response.status == 401) {
					window.location.href = window.location.origin + service.serviceBaseUrl + '/login.xhtml'
				} else if(response.status == 400 || response.status == 500) {
					// TODO do some code here.
				}
			
				return response;
			})
		})
	};
	service.setAccessToken = function(accessToken) {
		service.access_token = accessToken
	};
	service.setServiceBaseUrl = function(serviceBaseUrl) {
		service.serviceBaseUrl = serviceBaseUrl
	};
	return service
});

Services.factory('restservice', function(authService) {
	var service;
	service = {};
	
	service.post = function(object, url) {
		var apiAuth;
		apiAuth = authService.enableAuth(true);
		if (apiAuth.all) {
			return apiAuth.all(url).post(object)
		} else {
			return false
		}
	};
	
	service.remove = function(object, url) {
		var apiAuth;
		apiAuth = authService.enableAuth(true);
		return apiAuth.all? apiAuth.all(url).remove(object) : false;
	};
	
	service.put = function(object, url) {
		var apiAuth;
		apiAuth = authService.enableAuth(true);
		return apiAuth.all ? apiAuth.one(url).customPUT(object) :  false;
	};
	
	service.get = function(object, url) {
		var apiAuth;
		apiAuth = authService.enableAuth(true);
		 return apiAuth.all? apiAuth.all(url).get(object) : false;
	};
	
	service.remove = function(object, url) {
		var apiAuth;
		apiAuth = authService.enableAuth(true);
		return apiAuth.all ? apiAuth.one(url).remove(object) : false;
	};
	
	return service
});