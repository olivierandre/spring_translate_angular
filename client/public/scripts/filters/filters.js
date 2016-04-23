(function() {
    'use strict';

    var module = angular.module('translateApp');

    module.filter('objectToArray', function() {
        return function(input) {
            var out = [];
            for (var i in input) {
                out.push(input[i]);
            }
            return out;
        };
    });

    module.filter('i18n', function(TranslationService) {
        var i18nFilter = function(labels) {
            return (labels) ? labels[TranslationService.currentLang] : 'nolabel';
        };
        i18nFilter.$stateful = true;
        return i18nFilter;

    });

    module.filter('capitalize', function() {
        return function(input) {
            if (input !== null) {
                input = input.toLowerCase();
            }
            return input.substring(0, 1).toUpperCase() + input.substring(1);
        };
    });

    module.filter('langLabel', function(LangService, $filter) {
        return function(code) {
            var lang = $filter('filter')(LangService.langs, code);
            return lang[0].label;
        };
    });

    module.filter('categoryName', function(CategoryService, $filter) {
        return function(categoryId) {
            var categoryName = $filter('filter')(CategoryService.categories, categoryId);
            if(categoryName.length > 0) {
                return categoryName[0].labels;
            }
            return 'null';

        };
    });

}());
