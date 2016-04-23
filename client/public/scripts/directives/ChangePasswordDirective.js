(function() {

	'use strict';

	var module = angular.module('translateDirectives');

	module.directive('changePassword', ['SecurityService', 'SecurityRepository', 'SessionService', '$location', '$rootScope', function(SecurityService, SecurityRepository, SessionService, $location, $rootScope) {
		return {
			restrict: 'E',
			replace: true,
			transclude: true,
			templateUrl: '/views/templates/changePassword.html',
			scope: {
				pathRedirect: '=',
				finish: '='
			},
			controller: function($scope) {
				$scope.securityFacade = SecurityService;

				if (SecurityService.getAlert()) {
					$scope.alerts = angular.copy(SecurityService.getAlert());
					SecurityService.resetAlert();
				}

			},
			link: function($scope) {
				var user = SessionService.getUser();
				$scope.alerts = [];

				$scope.check = function(form) {
					return form.$pristine || ($scope.new.password !== $scope.new.repeat) || form.$invalid;
				};

				$scope.resetForm = function(form) {
					$scope.new = {
						password: '',
						repeat: ''
					};

					if (form) {
						form.$setPristine();
						form.$setUntouched();
					}
				};

				$scope.resetForm();

				$scope.changePassword = function(form) {
					SecurityService.changePassword(user, $scope.new.password).then(function(user) {
						if (user !== null) {
							if (!angular.isDefined($scope.pathRedirect) || !$scope.pathRedirect) {
								$scope.alerts.push(SecurityService.sendAlert('Modification réussie', 'success'));
								SecurityService.resetAlert();
							} else if (angular.isDefined($scope.pathRedirect)) {
								$location.path($scope.pathRedirect);
							}

							$scope.error = false;

							if (angular.isDefined($scope.finish)) {
								$rootScope.$emit($scope.finish);
							}
						}
					});
					// ,
					// function(reason) {
					// 	var error = reason.data;
					// 	if (error.exception === 'fr.oan.translate.exception.TranslateException') {
					// 		$scope.alerts.push(SecurityService.sendAlert(error.message));
					// 		SecurityService.resetAlert();
					// 	} else if (error.exception === 'java.lang.Exception' && error.status === 500) {
					// 		$scope.alerts.push(SecurityService.sendAlert(reason.data.error));
					// 		SecurityService.resetAlert();
					// 	} else {
					// 		$scope.alerts.push(SecurityService.sendAlert('Mauvaise combinaison'));
					// 		SecurityService.resetAlert();
					// 	}
					//
					// });
					$scope.resetForm(form);
				};

				$scope.closeAlert = function() {
					$scope.alerts = [];
					SecurityService.setShowErrorAuthenticate(false);
				};

			}

		};
	}]);
}());