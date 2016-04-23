(function () {
	'use strict';
	var module = angular.module('translateServices');

	module.service('OptionService', ['$localStorage', function ($localStorage) {

		var self = this;

		var options = [{
				id: 1,
				value: 5
		}, {
				id: 2,
				value: 10
		}, {
				id: 3,
				value: 20
		},
			{
				id: 4,
				value: 50
			}
        ];

		self.setViewByUser = function (value) {
			$localStorage.viewBy = value;
		};

		self.getViewByUser = function () {
			return $localStorage.viewBy || options[0].value;
		};

		self.getAllViewBy = function () {
			return options;
		};

	}]);
}());
