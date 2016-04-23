(function() {
    'use strict';
    var module = angular.module('translateServices');

    module.factory('MessageLoader', function(MessageRepository, TranslationService) {
        // return loaderFn
        return function(options) {
            return MessageRepository.getMessages(options.key).then(function(data) {
                TranslationService.setLoaded(options.key.split('_')[0]);
                return data;
            });
        };
    });

    module.factory('TranslationService', function($q, MessageRepository) {

        var languages;
        var defer = $q.defer();
        var isResolved = false;
        return {
            setLoaded: function(lang) {
                this.currentLang = lang;
                if (!isResolved) {
                    defer.resolve();
                }
                isResolved = true;

            },

            checkLoaded: function() {
                return defer.promise;
            },

            getLanguages: function() {
                if (languages) {
                    return $q.when(languages);
                }

                return MessageRepository.getMessages().then(function(data) {
                    languages = data;
                    return languages;

                });
            }



        };
    });
}());