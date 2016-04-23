(function() {
    'use strict';
    var module = angular.module('translateServices');

    module.service('ScoreService', function(ScoreRepository) {

        var self = this;

        self.loadScores = function() {
          var promise = ScoreRepository.load().$promise;

          promise.then(function(result) {
            self.scores = result;
          });
          return self.scores;
        };

        self.getSizeScores = function() {
            return self.scores.length;
        };

        self.saveScore = function(player, questions, goodAnswer, jump, duration, idCategory) {
          var score = {
            playerName: player,
            wordsToFind: questions,
            goodAnswer: goodAnswer,
            badAnswer: questions - (goodAnswer + jump),
            jumpAnswer: jump,
            gameDuration: duration,
            categoryId: idCategory
          };

          ScoreRepository.save({}, score).$promise.then(function() {
            self.scores.push(score);
          });
        };

        self.getRatio = function(questions, points) {
            var pourcent = 100 * (points / questions);
            return isNaN(pourcent) ? 0 : pourcent;
        };

    });
}());
