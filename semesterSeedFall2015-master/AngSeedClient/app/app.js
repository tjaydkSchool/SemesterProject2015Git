'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
    'ngRoute',
    'ui.router',
    'ngAnimate',
    'ui.bootstrap',
    'myApp.security',
    'myApp.view1',
    'myApp.view2',
    'myApp.view3',
    'myApp.view4',
    'myApp.filters',
    'myApp.directives',
    'myApp.factories',
    'myApp.services',
    'mySearchFunctionFromModule',
    'mySearchFunctionFromToModule',
    'myLoginModule',
    'mySignUpModule',
    'ngMaterial'
]).
        config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view1', {
                    templateUrl: 'view1/view1.html'
                })
                .when('/view2', {
                    templateUrl: 'view2/view2.html'
                });
                
                $routeProvider.otherwise({redirectTo: '/view1'});
            }]).
        config(function ($httpProvider) {
            $httpProvider.interceptors.push('authInterceptor');
            $httpProvider.defaults.timeout = 20000;
        });


