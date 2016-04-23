(function () {
	'use strict';
	var module = angular.module('translateServices');

	module.service('SettingService', ['SettingRepository', '$filter', '$q', function (SettingRepository, $filter, $q) {

		var self = this;
		self.settingChange = false;
		self.toDelete = [];

		self.loadSetting = function () {
			var promise = SettingRepository.load().$promise;
			promise.then(function (settings) {
				self.settings = settings;
			});

			return promise;
		};

		self.isLaunch = function () {
			var deferred = $q.defer();

			if (self.settings.launch) {
				deferred.resolve();
			} else {
				deferred.reject({
					noWords: true
				});
			}

			return deferred.promise;
		};

		self.getGameDuration = function () {
			return self.settings.gameDuration;
		};

		self.getMaxId = function (gameDuration) {
			return Math.max.apply(Math, gameDuration.map(function (duration) {
				var id = parseInt(duration.id);
				return id + 1;
			}));
		};

		self.createDuration = function (duration) {
			duration.state = 'I';
			self.settingChange = true;
		};

		self.updateDuration = function (duration) {
			if (!angular.isDefined(duration.state)) {
				duration.state = 'U';
				self.settingChange = true;
			}
		};

		self.deleteDuration = function (duration) {
			if (!angular.isDefined(duration.state)) {
				duration.state = 'D';
				self.settingChange = true;
				self.toDelete.push(duration);
			}

		};

		self.saveDuration = function (settings) {
			var toInsert = $filter('filter')(settings.gameDuration, {
				state: 'I'
			});

			var toUpdate = $filter('filter')(settings.gameDuration, {
				state: 'U'
			});

			return SettingRepository.save({}, {
				durationToInsert: toInsert,
				durationToUpdate: toUpdate,
				durationToDelete: self.toDelete,
                launch: settings.launch
			}).$promise.then(function (settings) {
				self.settings = settings;
				self.settingChange = false;
				self.toDelete = [];
			});

		};


  }]);

}());
