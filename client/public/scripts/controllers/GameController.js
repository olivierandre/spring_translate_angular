(function() {

	'use strict';

	var module = angular.module('translateApp');

	module.controller('GameController', function($scope, WordService, CategoryService, $timeout, dialogs, ScoreService, $filter, SettingService, $translate, toastr) {
		$scope.wordFacade = WordService;
		$scope.categoryFacade = CategoryService;
		$scope.scoresFacade = ScoreService;
		$scope.gameDuration = SettingService.getGameDuration();

		$scope.gameMaster = {
			player: '',
			category: null,
			timeMax: 0
		};

		$scope.answer = {
			playerAnswer: null,
			answer: null
		};

		var startGameTime = function() {
			$scope.seconds += 1;
			var promise = $timeout(startGameTime, 1000);
			if ($scope.seconds === $scope.game.timeMax) {
				$timeout.cancel(promise);
				$scope.endGame();
			}
			var type;

			if ($scope.seconds < ($scope.game.timeMax / 2)) {
				type = 'success';
			} else if ($scope.seconds < ($scope.game.timeMax / 1.2)) {
				type = 'warning';
			} else if ($scope.seconds <= $scope.game.timeMax) {
				type = 'danger';
			}

			$scope.type = type;

		};

		$scope.getWords = function() {
			var id = $scope.game.category;

			var promise = WordService.loadWordsByCategoryId(id);
			promise.then(function(words) {
				$scope.words = words;
				$scope.getWordToFind();
				startGameTime();
                $timeout(function() {
                    document.getElementById('wordToFind').focus();
                });

			});

		};

        var pickRandomProperty = function(obj) {
			var result = {
				translate: null,
				answer: null
			};
			var count = 0;
			for (var prop in obj) {
				var rand = Math.random() < 1 / ++count;

				if (rand) {
					result.translate = prop;
				}
			}

			for (prop in obj) {
				if (prop !== result.translate) {
					result.answer = prop;
				}
			}

			return result;
		};

		$scope.getWordToFind = function() {
			var words = $scope.words[Math.floor(Math.random() * $scope.words.length)];
			var index = $scope.words.indexOf(words);
			$scope.words.splice(index, 1);
            if($scope.words.length === 0) {
                $scope.getWords();
                return;
            }

			var answer = words.labels;
			var property = pickRandomProperty(answer);
			$scope.translateFind = answer[property.translate];
			$scope.answer = {
				playerAnswer: '',
				answer: answer[property.answer]
			};

			$scope.lang = property;

		};

		var summary = function(type) {
			$scope.summary.push({
				playerAnswer: $scope.answer.playerAnswer,
				answer: $scope.answer.answer,
				toTranslate: $scope.translateFind,
				type: type
			});
		};

		$scope.otherQuestion = function(alert) {
            document.getElementById('wordToFind').focus();
			$scope.score.questions += 1;
			var type = alert === undefined ? 'info' : alert.type;

			summary(type);
			if (alert === undefined) {
				alert = {
					type: type,
                    title: $translate.instant('GAME.NOT_MATTER'),
					msg: $translate.instant('GAME.CONTINUE')
				};
			}
            toastr.clear();
            toastr[alert.type](alert.msg, alert.title, {
                timeOut: 1500
            });

			$scope.getWordToFind();

		};

		$scope.verify = function() {
			var alert = {};

			if ($scope.answer.playerAnswer.toLowerCase() === $scope.answer.answer.toLowerCase()) {
				$scope.score.goodAnswer += 1;
				alert = {
					type: 'success',
                    title: $translate.instant('GAME.CONGRATULATIONS'),
					msg: $translate.instant('GAME.GOOD_ANSWER')
				};

			} else {
				alert = {
					type: 'error',
                    title: $translate.instant('GAME.FAILED'),
					msg: $translate.instant('GAME.BAD_ANSWER')
				};
			}
			$scope.otherQuestion(alert);
		};

		$scope.getPourcent = function(questions, points) {
			var pourcent = 100 * (points / questions);
			return isNaN(pourcent) ? 0 : pourcent;
		};

		$scope.resetForm = function() {
			$scope.game = angular.copy($scope.gameMaster);
			$scope.words = [];
			$scope.score = {
				goodAnswer: 0,
				questions: 0,
			};
			$scope.seconds = -1;
			$scope.start = false;
			$scope.minimum = 2;

		};
		$scope.resetForm();

		$scope.startGame = function() {
			$scope.summary = [];
			$scope.showSummary = false;
			$scope.start = true;
		};

		$scope.canPlay = function() {
			return $scope.game.player && $scope.game.category && $scope.game.timeMax > 0;
		};

		$scope.getTranslate = function() {
			return {
				minimum: $scope.minimum,
				playerName: $scope.game.player
			};
		};

		$scope.endGame = function() {
			$scope.score.ratio = $scope.getPourcent($scope.score.questions, $scope.score.goodAnswer);
			var toTranslate = 'GAME.END';
			if (!$scope.score.goodAnswer) {
				toTranslate = 'GAME.END_ZERO';
			} else if ($scope.score.goodAnswer === 1) {
				toTranslate = 'GAME.END_ONE';
			}

			$translate(toTranslate, {
				playerName: $scope.game.player,
				goodAnswer: $scope.score.goodAnswer
			}).then(function(value) {
				var jump = $filter('filter')($scope.summary, {
					type: 'info'
				}, true);
				var dialog = dialogs.notify('Fin du jeu', value);
				ScoreService.saveScore($scope.game.player, $scope.score.questions, $scope.score.goodAnswer, jump.length, $scope.game.timeMax, $scope.game.category);
				dialog.result.then(function() {
					$scope.showSummary = true;
					$scope.resetForm();
				});
			});

		};

	});


}());
