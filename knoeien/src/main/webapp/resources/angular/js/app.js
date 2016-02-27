'use strict';

/* App Module */
var Services = angular.module('selvesperer.services',[]);
var Controllers = angular.module('selvesperer.controllers', []);
var modules = [
               'ngCookies',
               'selvesperer.directives',
               'selvesperer.controllers',
               'selvesperer.services',
               'restangular',
               'ngMessages',	
               'ui.bootstrap'
           ];
if(window.location.href.indexOf("profile")!=-1)
	modules.push('angularFileUpload');
if(window.location.href.indexOf("insight")!=-1)
	modules.push('angularFileUpload');
if(window.location.href.indexOf("orgchart-setup")!=-1)
	modules.push('angularFileUpload');
var ppApp = angular.module('selvesperer', modules);

ppApp.config(
    function($httpProvider,RestangularProvider) {
        RestangularProvider.setBaseUrl(Settings.serviceBaseUrl);
    }
);

ppApp.run(function($rootScope, $location, authService, restservice) {
    authService.setServiceBaseUrl(Settings.serviceBaseUrl);
    
    console.log("this is just a test........");
   
    $rootScope.getParameterByName = function(sname) {    
    	var params = location.search.substr(location.search.indexOf("?")+1);
		  var sval = "";
		  params = params.split("&");
		    // split param and value into individual pieces
		    for (var i=0; i<params.length; i++)
		       {
		         var temp = params[i].split("=");
		         if ( [temp[0]] == sname ) { sval = temp[1]; }
		       }
		  return sval;
	}
});
