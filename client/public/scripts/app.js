(function() {


	'use strict';

	/**
	 * @ngdoc overview
	 * @name translateApp
	 * @description
	 * # translateApp
	 *
	 * Main module of the application.
	 */
    var module;
    module = angular.module('translateApp', [
		'ngAnimate',
		'ngCookies',
		'ngSanitize',
		'ngTouch',
		'ngRoute',
		'ngMessages',
		'ui.bootstrap',
		'ngAside',
		'pascalprecht.translate',
		'gameRoute',
		'langRoute',
		'adminRoute',
		'translateServices',
		'translateDirectives',
		'dialogs.main',
		'dialogsDecorator',
		'Constants',
		'ngStorage',
		'angular-loading-bar',
		'xeditable',
		'ngFileUpload',
        'toggle-switch',
        'toastr'
	]);

	module.config(function($httpProvider, $translateProvider, dialogsProvider, $uibModalProvider, $logProvider, Constant, toastrConfig) {
		$translateProvider.preferredLanguage('fr');
		$translateProvider.useLoader('MessageLoader', {});
		$translateProvider.useSanitizeValueStrategy('escaped');
		$translateProvider.useLocalStorage();

		dialogsProvider.useBackdrop('static');
		dialogsProvider.useEscClose(false);
		dialogsProvider.useCopy(false);
		dialogsProvider.setSize('sm');

		$uibModalProvider.options.animation = true;

		$logProvider.debugEnabled(Constant.log);

		$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

        toastrConfig.allowHtml = true;
        toastrConfig.timeOut = 3000;
        toastrConfig.positionClass = 'toast-top-right';
        toastrConfig.preventDuplicates = true;
        toastrConfig.progressBar = true;

	});

	module.run(function($rootScope, $route, $http, $translate, TranslationService, SettingService, SecurityService, $location, RouteInterceptor, editableOptions, editableThemes) {
		$rootScope.$on('$routeChangeSuccess', function() {
			//Change page title, based on Route information
			$rootScope.pageTitle = $route.current.title;
		});

		// xeEditable
		editableThemes.bs3.inputClass = 'input-sm';
		editableThemes.bs3.buttonsClass = 'btn-sm';
		editableOptions.theme = 'bs3';

		RouteInterceptor.sessionExpired();
		RouteInterceptor.errorRoute();
		SecurityService.getUser();

		$rootScope.setLanguage = function(lang) {
			$http.defaults.headers.common.I18n = lang;
			$translate.use(lang);
			TranslationService.currentLang = lang;
            console.log($rootScope.asideOpen);
		};

		$rootScope.getCurrentLang = function() {
			return TranslationService.currentLang;
		};

		//  Chargement des settings
		SettingService.loadSetting();
	});

})();
