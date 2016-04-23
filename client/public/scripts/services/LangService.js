(function () {
	'use strict';
	var module = angular.module('translateServices');

	module.service('LangService', function (LangRepository) {

		var self = this;

		self.loadLangs = function () {
			var promise = LangRepository.load();

			promise.then(function (langs) {
				self.langs = langs;
			});

			return promise;
		};

		self.saveLang = function (lang) {
			return LangRepository.save(lang).then(function (newLang) {
				self.langs = newLang;
				return newLang;
			});
		};

		self.deleteLang = function (lang) {
			var id = lang.id;

			return LangRepository.deleteLang({id: id}).then(function () {
				var idx = self.langs.indexOf(lang);
				self.langs.splice(idx, 1);
				return self.langs;
			});
		};

	});
}());
