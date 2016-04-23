(function() {
	'use strict';
	var module = angular.module('translateServices');

	module.service('UserService', ['UserRepository', 'SessionService', function(UserRepository, SessionService) {

		var self = this;

		self.updateUser = function(user) {
			return UserRepository.updateUser(user).$promise.then(function(user) {
				SessionService.setUser(user);
				return user;
			});
		};

	}]);
}());