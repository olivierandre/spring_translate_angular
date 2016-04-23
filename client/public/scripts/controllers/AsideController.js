(function () {

	'use strict';

	var module = angular.module('translateApp');

	module.controller('AsideController', ['$scope', '$uibModalInstance', 'SessionService', 'SecurityService', '$rootScope', '$timeout', '$uibModal', function ($scope, $uibModalInstance, SessionService, SecurityService, $rootScope, $timeout, $uibModal) {
		$scope.user = SessionService.getUser();
		$scope.securityFacade = SecurityService;
        $rootScope.asideOpen = true;

		$timeout(function () {
			document.querySelector('.ng-aside').addEventListener('click', function (e) {
                if(e.target.parentElement.nodeName === 'BODY') {
                    $rootScope.asideOpen = false;
                }
			});
		});

		$scope.open = function (e) {
			$uibModalInstance.close();
			e.stopPropagation();
		};

		$scope.cancel = function (e) {
			$rootScope.asideOpen = false;
			$uibModalInstance.dismiss();
			e.stopPropagation();
		};

        $scope.openHighScores = function() {
			$uibModal.open({
				templateUrl: 'views/templates/score.html',
				controller: ['$scope', 'ScoreService', '$uibModalInstance', function($scope, ScoreService, $uibModalInstance) {
                    ScoreService.loadScores();
					$scope.scoreFacade = ScoreService;

					$scope.predicate = 'goodAnswer';
					$scope.reverse = true;
					$scope.close = function() {
						$uibModalInstance.close();
					};

					$scope.order = function(predicate) {
						$scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
						$scope.predicate = predicate;
					};
				}],
				size: 'lg'
			});
		};

	}]);

}());
