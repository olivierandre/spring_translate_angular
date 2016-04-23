(function() {
    'use strict';
    var module = angular.module('translateServices');

    module.service('SessionService', function() {

        var self = this;

        self.setUser = function(user) {
            self.user = user;
        };

        self.getUser = function() {
            return self.user;
        };

        self.resetSession = function() {
            delete self.user;
        };

    });
}());
