(function () {
	'use strict';
	var module = angular.module('translateServices');

	module.factory('HttpInterceptor', ['$q', '$localStorage', '$location', '$rootScope', 'SessionService', '$injector', function ($q, $localStorage, $location, $rootScope, SessionService, $injector) {

		return {
			request: function (config) {

				if ($localStorage.token !== null) {
					config.headers['X-Auth-Token'] = $localStorage.token;
				}

				return config;
			},
			requestError: function (rejection) {
				return $q.reject(rejection);
			},
			response: function (response) {
				return response;
			},
			responseError: function (rejection) {

				var error = rejection.data;
				var status = rejection.status;
				var statusText = rejection.statusText;
				var securityService = $injector.get('SecurityService');
				var dialogs = $injector.get('dialogs');

				var currentUrl = $location.url();
				if (status === 401) {
					if ((!SessionService.getUser() || statusText === 'Unauthorized') && (currentUrl.length > 6 && currentUrl.substring(0, 6) === '/admin')) {
						$rootScope.$emit('session-expired');
						securityService.sendAlert('AUTH.EXPIRED');
					}

				} else if (status === -1) {
                    $location.path('/500');
				} else {
					dialogs.error(undefined, error.message);
				}

				//	Permet de stopper le dialogs.wait en général si une exception a lieu
				$rootScope.$broadcast('dialogs.wait.complete');


				return $q.reject(rejection);
			}

		};


	}]);

}());
