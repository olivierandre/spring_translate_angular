(function() {

	'use strict';

	var module = angular.module('translateApp');

	module.controller('AdminController', ['$scope', 'toastr', 'SecurityService', 'SessionService', function($scope, toastr, SecurityService, SessionService) {
        if(!SecurityService.isFirstLog()) {
            toastr.success('Bienvenue sur la page d\'administration', 'Bonjour ' + SessionService.user.firstName);
            SecurityService.setFirstLog();
        }
	}]);

}());
