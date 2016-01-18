Services.factory('authService', function(Restangular, $rootScope, $cookies) {
	var service = {};
	service.access_token = localStorage.auth || '';
	service.serviceBaseUrl = '';
	$rootScope.access_token = service.access_token;
	$rootScope.isGoodResponse = true;
	$rootScope.restMessages = {};
	service.enableAuth = function(isPost) {
		return Restangular.withConfig(function(RestangularConfigurer) {
			RestangularConfigurer.addRequestInterceptor(function(element) {
				$rootScope.isGoodResponse = false;
				$rootScope.restMessages = {};
				return element
			});
			
			RestangularConfigurer.addResponseInterceptor(function(response, restangularObject, url) {
				if(!response.success) {
					$rootScope.restMessages = response.messages;
					console.log($rootScope.restMessages);
					return false;
				}
				
				$rootScope.isGoodResponse = true;
				if (response.response) {
					return response.response;
				}
				return response;
			});
			
			RestangularConfigurer.setErrorInterceptor(function(response, restangularObject, url) {
				if (response.status === 503) {
					window.location.href = window.location.origin + service.serviceBaseUrl + '/noService.xhtml'
							
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