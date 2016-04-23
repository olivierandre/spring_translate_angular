(function() {
	'use strict';
	var module = angular.module('dialogsDecorator', ['dialogs.main', 'translateServices', 'pascalprecht.translate']);

	// module.controller('DetailErrorController', function DetailErrorController($scope, $uibModalInstance, data) {
	// 	$scope.data = data.detail;
	// 	$scope.header = data.header;
	// 	$scope.closeMsg = data.closeMsg;
	//
	// 	$scope.cancel = function() {
	// 		$uibModalInstance.dismiss('cancel');
	// 	};
	// });
	//
	// module.controller('ErrorController', function ErrorController($scope, message, message2) {
	// 	$scope.data = message;
	// 	$scope.data2 = message2;
	// });

    module.controller('confirmDialogCtrl', ['$scope', '$uibModalInstance', '$translate', 'data', function($scope, $uibModalInstance, $translate, data) {
        $scope.header = (angular.isDefined(data.header)) ? data.header : $translate.instant('DIALOGS_CONFIRMATION');
    	$scope.msg = (angular.isDefined(data.msg)) ? data.msg : $translate.instant('DIALOGS_CONFIRMATION_MSG');
    	$scope.icon = (angular.isDefined(data.fa) && angular.equals(data.fa,true)) ? 'fa fa-check' : 'glyphicon glyphicon-check';

    	$scope.no = function(){
    		$uibModalInstance.dismiss('no');
    	};

    	$scope.yes = function(){
    		$uibModalInstance.close('yes');
    	};
	}]);

	module.controller('notifyDialogCtrl', function($scope, $uibModalInstance, $translate, data) {
		$scope.header = (angular.isDefined(data.header)) ? data.header : $translate.instant('DIALOGS_NOTIFICATION');
		$scope.msg = (angular.isDefined(data.msg)) ? data.msg : $translate.instant('DIALOGS_NOTIFICATION_MSG');

		$scope.close = function() {
			$uibModalInstance.close();
		};
	});

	module.controller('errorDialogCtrl', function($scope, $uibModalInstance, $translate, data) {
		$scope.header = (angular.isDefined(data.header)) ? data.header : $translate.instant('DIALOGS_ERROR');
		$scope.msg = (angular.isDefined(data.msg)) ? $translate.instant(data.msg) : $translate.instant('DIALOGS_ERROR_MSG');

		$scope.close = function() {
			$uibModalInstance.close();
		};
	});


	module.controller('specificDialogCtrl', function($scope, $uibModalInstance, header, msg, yes, no) {
		$scope.header = (angular.isDefined(header)) ? header : 'Confirmation';
		$scope.msg = (angular.isDefined(msg)) ? msg : 'Confirmation required.';
		$scope.yesMsg = (angular.isDefined(yes)) ? yes : 'Yes';
		$scope.noMsg = (angular.isDefined(no)) ? no : 'Non';

		$scope.no = function() {
			$uibModalInstance.dismiss('no');
		};

		$scope.yes = function() {
			$uibModalInstance.close('yes');
		};
	});
	module.service('ExceptionService', function ExceptionService(dialogs, $translate) {
		var self = this;

		self.displayError = function(response) {
			dialogs.error(undefined, response.data);
		};

		self.displayUnauthorized = function() {
			dialogs.error($translate.instant('AUTH.UNAUTHORIZED'), $translate.instant('AUTH.UNAUTHORIZED_MSG'));
		};

	});



}());
