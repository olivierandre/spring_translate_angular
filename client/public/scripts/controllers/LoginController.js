(function () {

    'use strict';

    var module = angular.module('translateApp');

    module.controller('LoginController', ['$scope', '$rootScope', 'SecurityService', '$location', '$translate', 'toastr', function ($scope, $rootScope, SecurityService, $location, $translate, toastr) {
        $scope.securityFacade = SecurityService;

        if (SecurityService.getAlert().msg) {
            var alert = SecurityService.getAlert();
            //SecurityService.resetAlert();
            //console.log($scope.alert);
            toastr.error(alert.msg, 'Error');
        }

        // $scope.closeAlert = function () {
        //     $scope.alert = {};
        //     SecurityService.setShowErrorAuthenticate(false);
        // };

        $scope.login = function () {
            SecurityService.authenticate($scope.credentials).then(function (user) {
                if (user !== null) {
                    $rootScope.authenticated = true;
                    $location.path('/admin');
                    $scope.error = false;
                }

            }, function () {
                var message = $translate.instant('AUTH.BAD_CREDENTIALS');
                //console.log(message)
                //$scope.alert = SecurityService.sendAlert(message);
                //SecurityService.resetAlert();
                toastr.error(message, 'Error');
            });
            $scope.resetForm();
        };

        $scope.resetForm = function (form) {
            $scope.credentials = {
                username: '',
                password: ''
            };

            if (form) {
                form.$setPristine();
                form.$setUntouched();
            }
        };

        $scope.resetForm();

    }]);

}());
