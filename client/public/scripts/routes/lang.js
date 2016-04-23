(function() {

    'use strict';

    var module = angular.module('langRoute', []);

    module.config(function($routeProvider) {

        // var getAllLangs = function(LangService) {
        //     return LangService.loadLangs();
        // };

        // var getAllLangsAndWordsAndCategories = function(LangService, WordService, CategoryService) {
        //     return LangService.loadLangs().
        //     then(WordService.loadWords).
        //     then(CategoryService.loadCategories);
        // };

        // $routeProvider.when('/lang', {
        //     templateUrl: 'views/lang/lang.html',
        //     controller: 'LangController',
        //     title: 'BUTTON.MANAGE_LANG',
        //     resolve: {
        //         langs: ['LangService', getAllLangs]
        //     }
        // }).
        // when('/word', {
        //     templateUrl: 'views/lang/word.html',
        //     controller: 'WordController',
        //     title: 'BUTTON.MANAGE_WORD',
        //     resolve: {
        //         words: ['LangService', 'WordService', 'CategoryService', getAllLangsAndWordsAndCategories]
        //     }
        // });
    });

}());