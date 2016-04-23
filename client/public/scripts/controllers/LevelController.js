(function() {

	'use strict';

	var module = angular.module('translateApp');

	module.controller('LevelController', function($scope, LangService, LevelService, dialogs) {
		$scope.langFacade = LangService;
		$scope.levelFacade = LevelService;
		$scope.levels = LevelService.levels;


		$scope.minLevel = 2;
		$scope.levelMaster = {
			labels: {}
		};

		$scope.filteredLevels = [];
		$scope.currentPage = 1;
		$scope.numPerPage = 5;
		$scope.itemsPerPage = 5;

		$scope.$watchGroup(['currentPage + numPerPage', 'levels.length', 'levels'], function() {
			var begin = (($scope.currentPage - 1) * $scope.numPerPage),
				end = begin + $scope.numPerPage;

			$scope.filteredLevels = $scope.levels.slice(begin, end);
			$scope.numPages = $scope.levels.length;
		});

		$scope.getTranslate = function() {
			return {
				minimum: $scope.minLevel
			};
		};

		$scope.submitForm = function() {
			var promise;

			if ($scope.level.id !== undefined) {
				promise = LevelService.updateLevel($scope.level);
			} else {
				promise = LevelService.saveLevel($scope.level);
			}
			promise.then(function() {
				$scope.resetForm();
				$scope.levelForm.$setPristine();
			});
		};

		$scope.getIndex = function(index) {
			return (index + 1) + (($scope.currentPage - 1) * $scope.itemsPerPage);
		};

		$scope.modify = function(level) {
			$scope.level = level;
		};

		$scope.delete = function(level) {
			var dialog = dialogs.confirm();

			dialog.result.then(function() {
				LevelService.deleteLevel(level);
			});

			$scope.resetForm();
		};


		$scope.resetForm = function(form) {
			$scope.level = angular.copy($scope.levelMaster);

			if (form) {
				form.$setPristine();
				form.$setUntouched();
			}

		};
		$scope.resetForm();
	});

}());