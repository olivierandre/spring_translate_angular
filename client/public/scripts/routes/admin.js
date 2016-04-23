(function () {

	'use strict';

	var module = angular.module('adminRoute', []);

	module.config(function ($routeProvider) {

		var getAllLangsAndAllCategories = function (SecurityService, LangService, CategoryService) {
			return SecurityService.isAuthenticate().
			then(LangService.loadLangs).
			then(CategoryService.loadCategories);
		};

		var getAllLangsAndAllLevels = function (SecurityService, LangService, LevelService) {
			return SecurityService.isAuthenticate().
			then(LangService.loadLangs).
			then(LevelService.loadLevels);
		};

		var getAllLangs = function (SecurityService, LangService) {
			return SecurityService.isAuthenticate().
			then(LangService.loadLangs);
		};

		var getAllLangsAndWordsAndCategories = function (SecurityService, LangService, WordService, CategoryService) {
			return SecurityService.isAuthenticate().
			then(LangService.loadLangs).
			then(WordService.loadWords).
			then(CategoryService.loadCategories);
		};

		var getAllLangsAndAllSettings = function (SecurityService, LangService) {
			return SecurityService.isAuthenticate().
			then(LangService.loadLangs);
		};

		$routeProvider.when('/login', {
			templateUrl: 'views/admin/login.html',
			controller: 'LoginController',
			title: 'AUTH.TITLE'
		}).
		when('/first-connection', {
			templateUrl: 'views/admin/changePassword.html',
			title: 'Nouveau mot de passe',
			resolve: {
				security: function (SecurityService) {
					SecurityService.isAuthenticate();
					SecurityService.sendAlert('AUTH.FIRST');
				},

				langs: function (LangService) {
					return LangService.loadLangs();
				}
			}
		}).
		when('/admin', {
			templateUrl: 'views/admin/admin.html',
			controller: 'AdminController',
			title: 'Accueil Administration',
			resolve: {
				langs: ['SecurityService', 'LangService', getAllLangs]
			}
		}).
		when('/admin/lang', {
			templateUrl: 'views/lang/lang.html',
			controller: 'LangController',
			title: 'BUTTON.MANAGE_LANG',
			resolve: {
				langs: ['SecurityService', 'LangService', getAllLangs]
			}
		}).
		when('/admin/word', {
			templateUrl: 'views/lang/word.html',
			controller: 'WordController',
			title: 'BUTTON.MANAGE_WORD',
			resolve: {
				words: ['SecurityService', 'LangService', 'WordService', 'CategoryService', getAllLangsAndWordsAndCategories]
			}
		}).
		when('/admin/category', {
			templateUrl: 'views/admin/category.html',
			controller: 'CategoryController',
			title: 'ADMIN.CATEGORY_TITLE',
			resolve: {
				langs: ['SecurityService', 'LangService', 'CategoryService', getAllLangsAndAllCategories]
			}
		}).when('/admin/level', {
			templateUrl: 'views/admin/level.html',
			controller: 'LevelController',
			title: 'ADMIN.LEVEL_TITLE',
			resolve: {
				langs: ['SecurityService', 'LangService', 'LevelService', getAllLangsAndAllLevels]
			}
		}).when('/admin/setting', {
			templateUrl: 'views/admin/setting.html',
			controller: 'SettingController',
			title: 'SETTING.TITLE',
			resolve: {
				langs: ['SecurityService', 'LangService', getAllLangsAndAllSettings]
			}
		}).when('/admin/user', {
			templateUrl: 'views/admin/user.html',
			controller: 'UserController',
			title: 'Vos données',
			resolve: {
				langs: ['SecurityService', 'LangService', getAllLangs]
			}
		}).when('/admin/import', {
			templateUrl: 'views/admin/import.html',
			controller: 'ImportController',
			title: 'Import de données',
			resolve: {
				langs: ['SecurityService', 'LangService', getAllLangs]
			}
		}).when('/logout', {
			redirectTo: '/login',
			resolve: {
				logout: function (SecurityService) {
					SecurityService.logout();
				}
			}
		}).
		otherwise({
			redirectTo: '/'
		});
	});

}());
