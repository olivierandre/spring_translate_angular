(function () {
	'use strict';
	var module = angular.module('translateServices');

	module.factory('RouteInterceptor', ['$location', '$rootScope', 'SecurityService', 'dialogs', function ($location, $rootScope, SecurityService, dialogs) {

		return {
			sessionExpired: function () {
				$rootScope.$on('session-expired', function () {
					SecurityService.logout();
					SecurityService.sendAlert('Fin de session');
					$location.path('/login');
				});
			},
			errorRoute: function () {
				$rootScope.$on('$routeChangeError', function (event, current, previous, rejection) {

					if (rejection.needAuthentication) {
						SecurityService.authenticated = false;
						SecurityService.sendAlert('Authentification obligatoire');
						SecurityService.setShowErrorAuthenticate(true);
						$location.path('/login');
					} else if (rejection.firstConnection) {
						SecurityService.authenticated = true;
						$location.path('/first-connection');
					} else if (rejection.enabled) {
						SecurityService.authenticated = false;
						SecurityService.sendAlert('Votre compte est désactivé.');
						SecurityService.setShowErrorAuthenticate(true);
						$location.path('/login');
					} else if (rejection.noWords) {
                        dialogs.notify(undefined, 'Le jeu n\'est pas encore prêt :-( \n Mais c\'est pour bientôt!!!');
					}

				});
			}
		};
	}]);
}());
