(function () {
	'use strict';
	var module = angular.module('translateServices');

	module.service('SecurityService', ['$q', '$localStorage', 'SecurityRepository', 'SessionService', '$cookies', function ($q, $localStorage, SecurityRepository, SessionService, $cookies) {
		var self = this;
		self.alert = {};
		var showErrorAuthenticate = false;

        self.homeHref = '#/';

		self.getAlert = function () {
			return self.alert;
		};

		self.resetAlert = function () {
			self.alert = {};
		};

		self.getShowErrorAuthenticate = function () {
			return showErrorAuthenticate;
		};

		self.setShowErrorAuthenticate = function (bool) {
			showErrorAuthenticate = bool;
		};

		self.getUser = function () {
			self.promise = SecurityRepository.getUser();
		};

		self.isAuthenticate = function (needAuth) {
			var deferred = $q.defer();

			if (!SessionService.getUser() && self.promise) {
				self.promise.then(function (user) {

					SessionService.setUser(user.data);
					if (needAuth !== undefined && !needAuth) {
						deferred.resolve();
						self.authenticated = true;
					} else if (user.data.firstConnection) {
						deferred.reject({
							firstConnection: true
						});
						self.authenticated = true;
					} else if (!user.data.enabled) {
						deferred.reject({
							enabled: true
						});
					} else {
						deferred.resolve();
						self.authenticated = true;
					}

				}, function () {
					if (needAuth !== undefined && !needAuth) {
						deferred.resolve({
							needAuthentication: false
						});
					} else {
						deferred.reject({
							needAuthentication: true
						});
					}

				});
				delete self.promise;
			} else {
				var user = SessionService.getUser();
				if (!user) {
					deferred.reject({
						needAuthentication: true
					});
				} else if (user.firstConnection) {
					deferred.reject({
						firstConnection: true
					});
				} else if (!user.enabled) {
					deferred.reject({
						enabled: true
					});
				} else {
					deferred.resolve();
					self.authenticated = true;
				}

			}

			return deferred.promise;
		};

        var authenticateUser = function (user) {
			self.authenticated = true;
            self.homeHref = '#/admin';
			delete $localStorage.token;
			$localStorage.$default({
				token: user.data.token,
			});
			SessionService.setUser(user.data);
			self.setShowErrorAuthenticate(false);
			return user.data;
		};

        self.setFirstLog = function() {
            $localStorage.$default({
				firstLog: true
			});
        };

        self.isFirstLog = function() {
            return $localStorage.firstLog;
        };

		self.authenticate = function (credentials) {
			return SecurityRepository.authenticate({
				username: credentials.username,
				password: credentials.password
			}).then(function (user) {
				return authenticateUser(user);
			});
		};

		self.changePassword = function (user, password) {
			return SecurityRepository.changePassword(user.id, user.username, password).then(function (user) {
				return authenticateUser(user);
			});
		};

		self.logout = function () {
			self.resetSecurity();
		};

		self.resetSecurity = function () {
            $cookies.put('JSESSIONID');
			delete $localStorage.token;
            delete $localStorage.firstLog;
			self.authenticated = false;
			SessionService.resetSession();
            self.homeHref = '#/';
		};

		self.sendAlert = function (message, typeAlert) {
			var type = 'danger';
			if (angular.isDefined(typeAlert)) {
				type = typeAlert;
			}

			self.alert = {
				type: type,
				msg: message,
				time: 3000
			};
			self.setShowErrorAuthenticate(true);

			return self.alert;
		};

	}]);

}());
