(function() {
	'use strict';
	var module = angular.module('translateServices');

	module.service('WordService', function(WordRepository) {

		var self = this;

		self.loadWords = function() {
			var promise = WordRepository.load();

			promise.then(function(words) {
				self.words = words;
			});

			return promise;
		};

		self.loadWordsByCategoryId = function(id) {
			var promise = WordRepository.loadByCategoryId(id);

			promise.then(function(words) {
				self.words = words;
			});

			return promise;
		};

		self.saveWord = function(word) {
			return WordRepository.save(word).then(function(newWord) {
				self.words.push(newWord);
				return self.words;
			});
		};

		self.updateWord = function(word) {
			return WordRepository.updateWord(word).then(function(updateWord) {
				var idx = self.words.indexOf(word);
				self.words[idx] = updateWord;

				return self.words;
			});
		};

		self.deleteWord = function(word) {
			var id = word.id;

			return WordRepository.deleteWord(word).then(function() {
				var idx = self.words.indexOf(word);
				self.words.splice(idx, 1);
				return self.words;
			});
		};


	});
}());