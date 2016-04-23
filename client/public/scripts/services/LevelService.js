(function() {
    'use strict';
    var module = angular.module('translateServices');

    module.service('LevelService', function(LevelRepository) {

        var self = this;

        self.loadLevels = function() {
            var promise = LevelRepository.load();

            promise.then(function(levels) {
                self.levels = levels;
            });


            return promise;
        };

        self.saveLevel = function(level) {
            level.order = getOrder();

            return LevelRepository.save(level).then(function(newLevel) {
                self.levels.push(newLevel);
                return self.levels;
            });
        };

        self.updateLevel = function(level) {
            return LevelRepository.updateLevel(level).then(function(updateLevel) {
                var idx = self.levels.indexOf(level);
                self.levels[idx] = updateLevel;

                return self.levels;
            });
        };

        self.deleteLevel = function(level) {
            return LevelRepository.deleteLevel(level).then(function() {
                var idx = self.levels.indexOf(level);
                self.levels.splice(idx, 1);
                return self.levels;
            });
        };

        var getOrder = function() {
            if(self.levels.length === 0) {
                return 1;
            } else {
                return Math.max.apply(Math, self.levels.map(function(order) {
                    return order.order + 1;
                }));
            }

        };



    });
}());
