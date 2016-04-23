(function () {

    'use strict';

    var module = angular.module('translateApp');

    module.controller('ImportController', ['$scope', 'LangService', 'Constant', function ($scope, LangService, Constant) {

        $scope.accept = Constant.xlsFile + ',' + Constant.xlsxFile;
        $scope.result = {
            success: [],
            fail: []
        };
        $scope.langFacade = LangService;

        $scope.filteredSuccessResult = [];
        $scope.filteredFailResult = [];
        $scope.currentPage = 1;
        $scope.numPerPage = 5;
        $scope.itemsPerPage = 5;

        $scope.$watchGroup(['currentPage + numPerPage', 'result.success.length', 'result.success', 'result.fail.length', 'result.fail'], function () {
            var begin = (($scope.currentPage - 1) * $scope.numPerPage),
                end = begin + $scope.numPerPage;

            $scope.filteredSuccessResult = $scope.result.success.slice(begin, end);
            $scope.filteredFailResult = $scope.result.fail.slice(begin, end);

            $scope.numSuccessPages = $scope.result.success.length;
            $scope.numFailPages = $scope.result.fail.length;
        });

        $scope.getIndex = function (index) {
            return (index + 1) + (($scope.currentPage - 1) * $scope.itemsPerPage);
        };

        $scope.defineUrl = function () {
            if ($scope.isCategory) {
                $scope.url = Constant.apiSecure + '/upload/categories';
            } else {
                $scope.url = Constant.apiSecure + '/upload/words';
            }
        };


    }]);

}());