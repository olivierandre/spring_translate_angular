(function () {

    'use strict';

    var module = angular.module('translateDirectives');

    module.directive('uploadButton', ['Upload', '$timeout', 'dialogs', '$rootScope', function (Upload, $timeout, dialogs, $rootScope) {
        return {
            restrict: 'E',
            replace: true,
            transclude: true,
            templateUrl: '/views/templates/upload.html',
            scope: {
                accept: '=',
                maxHeight: '=',
                maxSize: '=',
                pattern: '=',
                url: '=',
                result: '=',
                disabled: '='
            },
            link: function ($scope) {
                var progress = function (evt) {
                    return Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
                };

                $scope.uploadFiles = function (file) {
                    $scope.file = file;
                    $scope.result = {
                        success: [],
                        fail: []
                    };

                    if (file && !file.$error) {
                        var promise = Upload.upload({
                            url: $scope.url,
                            file: file
                        });

                        promise.progress(function (evt) {
                            dialogs.wait(undefined, 'Attente', progress(evt));
                        });

                        promise.then(function (result) {
                            $rootScope.$broadcast('dialogs.wait.complete');
                            dialogs.notify(undefined, 'Fichier téléchargé').result.then(function () {
                                $rootScope.$broadcast('dialogs.wait.complete');
                                $scope.result = {
                                    success: result.data.success,
                                    fail: result.data.fail,
                                    countSuccess: result.data.countSuccess,
                                    countFailed: result.data.countFailed
                                };

                                $scope.file = null;

                            });
                        }, function () {
                            $scope.file = null;

                        });


                    }
                };

            }

        };
    }]);

}());
