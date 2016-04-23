(function() {
    'use strict';
    var module = angular.module('translateServices', ['ngResource']);

    module.config(function($httpProvider) {
        $httpProvider.interceptors.push('HttpInterceptor');
    });

}());
