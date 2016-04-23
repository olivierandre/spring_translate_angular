(function(angular, undefined) {
'use strict';

angular.module('Constants', [])

.constant('Constant', {env:'prod',api:'http://www.translatemoi.fr/api',apiSecure:'http://www.translatemoi.fr/api/secure',log:false,xlsFile:'application/vnd.ms-excel',xlsxFile:'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'})

;
})(angular);