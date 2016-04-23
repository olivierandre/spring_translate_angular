(function() {

  'use strict';

  var module = angular.module('translateApp');

  module.controller('HeaderController', ['$scope', '$aside', '$uibModal', 'SecurityService', '$location', '$rootScope', function($scope, $aside, $uibModal, SecurityService, $location, $rootScope) {

    $scope.securityFacade = SecurityService;
    $scope.showScore = false;

    $rootScope.$on('$routeChangeSuccess', function() {
      if ($location.path() === '/') {
        $scope.showScore = true;
      } else {
        $scope.showScore = false;
      }
    });

    $scope.openAside = function() {
      $aside.open({
        templateUrl: 'views/templates/aside.html',
        controller: 'AsideController',
        placement: 'right',
        size: 'sm',
        backdrop: true,
      }).result.then(function() {
        $rootScope.asideOpen = false;
      });
    };

  }]);
}());
