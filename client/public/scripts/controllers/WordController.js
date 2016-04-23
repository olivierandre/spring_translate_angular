(function () {

	'use strict';

	var module = angular.module('translateApp');

	module.controller('WordController', ['$scope', 'LangService', 'WordService', 'dialogs', 'CategoryService', 'OptionService', function ($scope, LangService, WordService, dialogs, CategoryService, OptionService) {
		$scope.langFacade = LangService;
		$scope.words = [];
		$scope.wordFacade = WordService;
		$scope.words = WordService.words;
		$scope.categoryFacade = CategoryService;
		$scope.itemsPerPage = 5;
		$scope.filteredWords = [];
		$scope.model = {
			currentPage: 1
		};

		$scope.options = OptionService.getAllViewBy();
		$scope.numPerPage = OptionService.getViewByUser();
		$scope.enablePagination = false;

		$scope.$watchGroup(['model.currentPage + numPerPage', 'words.length', 'words'], function () {
			var begin = (($scope.model.currentPage - 1) * $scope.numPerPage),
				end = begin + $scope.numPerPage;

			$scope.filteredWords = $scope.words.slice(begin, end);
			$scope.numPages = $scope.words.length;
			$scope.enablePagination = $scope.numPages > 1 ? true : false;
			OptionService.setViewByUser($scope.numPerPage);
		});

		$scope.wordMaster = {
			id: null,
			labels: {},
			category: null
		};
		$scope.minimum = 2;


		$scope.getTranslate = function (lang) {
			if (lang) {
				return {
					lang: lang.toLowerCase()
				};
			}
			return {
				minimum: $scope.minimum
			};
		};

		$scope.getLevel = function () {
			$scope.level = [];

			for (var i = 1; i < 11; i++) {
				$scope.level.push(i);
			}
		};

		$scope.delete = function (word) {
			var dialog = dialogs.confirm();

			dialog.result.then(function () {
				WordService.deleteWord(word);
			});
		};

		$scope.modify = function (word) {
			$scope.word = word;
		};

		$scope.submitForm = function () {
			var promise;

			if ($scope.word.id !== null) {
				promise = WordService.updateWord($scope.word);
			} else {
				promise = WordService.saveWord($scope.word);
			}

			promise.then(function () {
				$scope.resetForm();
				$scope.wordForm.$setPristine();
			});

		};

		$scope.resetForm = function (form) {
			$scope.word = angular.copy($scope.wordMaster);

			if (form) {
				form.$setPristine();
				form.$setUntouched();
			}
		};
		$scope.resetForm();

		$scope.getIndex = function (index) {
			return ((index + 1) + (($scope.model.currentPage - 1) * $scope.numPerPage));
		};

	}]);

}());
