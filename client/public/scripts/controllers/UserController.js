(function() {

	'use strict';

	var module = angular.module('translateApp');

	module.controller('UserController', ['$scope', 'SessionService', '$rootScope', '$translate', 'UserService', 'dialogs', '$timeout', function($scope, SessionService, $rootScope, $translate, UserService, dialogs, $timeout) {

		var userMaster;
		$scope.EMAIL_REGEXP = /^[a-z0-9!#$%&'*+/=?^_`{|}~.-]+@[a-z0-9-]+(\.[a-z0-9-]+)*$/i;

		$scope.focusInput = function(input, bool) {
			$scope.focus[input] = bool;
		};

		$scope.translateEmail = function() {
			return $translate.instant('USER.EMAIL');
		};

		$scope.submitForm = function(form) {
			if (form.$valid) {
				UserService.updateUser($scope.user).then(function() {
					dialogs.notify(undefined, $translate.instant('DIALOGS.UPDATE_SUCCESS')).result.then(function() {
						$scope.resetForm(form);
					});
				});
			}
		};

		$scope.showButton = function(form) {
			return form.$dirty && !angular.equals($scope.user, userMaster);
		};

		$scope.accordion = {
			password: false
		};

		var closeAccordion = function() {
			$timeout(function() {
				$scope.accordion.password = false;
			}, 3000);
		};

		$rootScope.$on('close-accordion', function() {
			closeAccordion();
		});

		$scope.resetForm = function(form) {
			$scope.user = angular.copy(SessionService.getUser());
			userMaster = angular.copy(SessionService.getUser());
			$scope.focus = {
				email: false
			};

			if (form) {
				form.$setPristine();
				form.$setUntouched();
			}

		};

		$scope.resetForm();


	}]);

}());