(function() {

	'use strict';

	var module = angular.module('translateApp');

	module.controller('CategoryController', function($scope, LangService, CategoryService, dialogs) {
		$scope.langFacade = LangService;
		$scope.categoryFacade = CategoryService;
		$scope.categories = CategoryService.categories;
		$scope.minCategory = 2;
		$scope.categoryMaster = {
			labels: {}
		};

		$scope.filteredCategories = [];
		$scope.currentPage = 1;
		$scope.numPerPage = 5;
		$scope.itemsPerPage = 5;

		$scope.$watchGroup(['currentPage + numPerPage', 'categories.length', 'categories'], function() {
			var begin = (($scope.currentPage - 1) * $scope.numPerPage),
				end = begin + $scope.numPerPage;

			$scope.filteredCategories = $scope.categories.slice(begin, end);
			$scope.numPages = $scope.categories.length;
		});

		$scope.getTranslate = function() {
			return {
				minimum: $scope.minCategory
			};
		};

		$scope.delete = function(category) {
			var dialog = dialogs.confirm();

			dialog.result.then(function() {
				CategoryService.deleteCategory(category);
			});

			$scope.resetForm();
		};

		$scope.submitForm = function() {
			var promise;

			if ($scope.category.id !== undefined) {
				promise = CategoryService.updateCategory($scope.category);
			} else {
				promise = CategoryService.saveCategory($scope.category);
			}
			promise.then(function() {
				$scope.resetForm();
				$scope.categoryForm.$setPristine();
			});
		};

		$scope.modify = function(category) {
			$scope.category = category;
		};

		$scope.resetForm = function(form) {
			$scope.category = angular.copy($scope.categoryMaster);

			if (form) {
				form.$setPristine();
				form.$setUntouched();
			}
		};
		$scope.resetForm();

		$scope.getIndex = function(index) {
			return (index + 1) + (($scope.currentPage - 1) * $scope.itemsPerPage);
		};

	});

}());