(function() {

	'use strict';

	var module = angular.module('translateApp');

	module.controller('SettingController', ['$scope', 'SettingService', 'dialogs', '$translate', '$filter', function($scope, SettingService, dialogs, $translate, $filter) {

		$scope.settingFacade = SettingService;

        var reset = function() {
			$scope.settings = angular.copy(SettingService.settings);
			$scope.settingFacade.settingChange = false;
			delete $scope.inserted;
		};

		$scope.manageEnableDuration = function(duration) {
			var idx = $scope.settings.gameDuration.indexOf(duration);
			$scope.settings.gameDuration[idx].enable = !$scope.settings.gameDuration[idx].enable;
		};

		$scope.reset = function() {

			if (angular.isDefined($scope.settings) && SettingService.settingChange) {
				dialogs.confirm(undefined, 'Etes-vous sur ?').result.then(function() {
					reset();
				});
			} else {
				reset();
			}

		};

		$scope.reset();

		$scope.addDuration = function() {
			var id = SettingService.getMaxId($scope.settings.gameDuration);

			$scope.inserted = {
				id: id,
				time: 10,
				enable: false
			};

			$scope.settings.gameDuration.push($scope.inserted);
		};

		$scope.saveDuration = function(duration) {
			if (duration === $scope.inserted) {
				SettingService.createDuration(duration);
			} else {
				SettingService.updateDuration(duration);
			}

			delete $scope.inserted;
		};

		$scope.deleteDuration = function(duration) {
			SettingService.deleteDuration(duration);
			var idx = $scope.settings.gameDuration.indexOf(duration);
			$scope.settings.gameDuration.splice(idx, 1);
		};

		$scope.saveSettings = function() {
			SettingService.saveDuration($scope.settings).then(function() {
				reset();
			});
		};

        $scope.changeLaunchStatus = function() {
            SettingService.settingChange = true;
        };

		$scope.cancel = function(rowform, duration) {
			if (angular.equals(duration, $scope.inserted)) {
				var idx = $scope.settings.gameDuration.indexOf($scope.inserted);
				$scope.settings.gameDuration.splice(idx, 1);
			}
			delete $scope.inserted;

			rowform.$cancel();
		};

		$scope.checkTime = function(data, id) {

			var time = $filter('filter')(SettingService.settings.gameDuration, {
				time: data
			});

			if (!data) {
				return 'Valeur obligatoire';
			} else if (time.length > 0 && time[0].id !== id) {
				return $translate.instant('SETTING.DURATION_EXIST');
			} else if (data < 5 || data > 59) {
				return $translate.instant('SETTING.DURATION_INTERVAL');
			}
		};

	}]);

}());
