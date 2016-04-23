(function() {

    'use strict';

    var module = angular.module('gameRoute', []);

    module.config(function($routeProvider) {

        var getAllCategories = function(SettingService, LangService, CategoryService, ScoreService, SecurityService) {
            return SecurityService.isAuthenticate(false).
            then(SettingService.loadSetting).
			then(SettingService.isLaunch).
			then(LangService.loadLangs).
            then(CategoryService.loadCategoriesAffect).
            then(ScoreService.loadScores);
        };

        $routeProvider.when('/', {
            templateUrl: 'views/game.html',
            controller: 'GameController',
            title: 'GENERAL.WELCOME',
            resolve: {
                words:['SettingService', 'LangService', 'CategoryService', 'ScoreService', 'SecurityService', getAllCategories]
            }
        }).when('/500', {
			templateUrl: 'views/errors/500.html'
		});
    });

}());
