(function () {

    'use strict';

    var module = angular.module('translateDirectives');

    module.directive('tableDirect', function () {
        return {
            restrict: 'E',
            replace: true,
            transclude: true,
            templateUrl: '/views/templates/table.html',
            scope: {
                source: '=',
                numPerPage: '=',
                itemsPerPage: '=',
                class: '=',
                header: '=',
                orderByHeader: '='
            },
            link: function ($scope) {

                $scope.filteredResult = [];
                $scope.currentPage = 1;

                /*$scope.numPerPage = 5;
                $scope.itemsPerPage = 5;*/

                $scope.$watchGroup(['currentPage + numPerPage', 'source.length', 'source'], function () {
                    var begin = (($scope.currentPage - 1) * $scope.numPerPage),
                        end = begin + $scope.numPerPage;

                    $scope.filteredResult = $scope.source.slice(begin, end);

                    $scope.numPages = $scope.result.source.length;
                });

            }

        };
    });
}());
