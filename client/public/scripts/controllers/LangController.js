(function() {

	'use strict';

	var module = angular.module('translateApp');

	module.controller('LangController', function($scope, LangService, dialogs) {
		$scope.langFacade = LangService;
		$scope.langs = LangService.langs;
		$scope.newLang = {};
		$scope.show = 'all';
		$scope.pageClass = 'page-lang';
        $scope.minimum = 2;

		$scope.showDiv = function(value) {
			$scope.show = value;
		};

		$scope.valid = function(form) {
			if (form.$valid) {
				LangService.saveLang($scope.newLang).then(function(lang) {
					$scope.langs.push(lang);
					$scope.newLang = {};
					form.$setPristine();
				});
			}
		};

		$scope.delete = function(lang) {
			var dialog = dialogs.confirm();

			dialog.result.then(function() {
				LangService.deleteLang(lang);
			});
		};

		$scope.errorInput = function(input) {
			var error = input.$error;

			if (error.minlength || error.maxlength) {
				if (input.$name === 'code') {
					return 'Minimum 2 caractères. Maximum 3 caractères.';
				}

			} else if (error.required) {
				return 'Champ obligatoire';
			}

		};

        $scope.getTranslate = function(lang) {
            if(lang) {
                return {
                    lang: lang.toLowerCase()
                };
            }
			return {
				minimum: $scope.minimum
			};
		};

	});

}());
