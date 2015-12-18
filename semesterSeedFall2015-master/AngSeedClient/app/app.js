'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
    'ngRoute',
    'ngMaterial',
    'ngAnimate',
    'ui.router',
    'ui.bootstrap',
    'myApp.security',
    'myApp.view1',
    'myApp.view2',
    'myApp.view3',
    'myApp.view4',
    'myApp.view5',
    'myApp.view6',
    'myApp.filters',
    'myApp.directives',
    'myApp.factories',
    'myApp.services',
    'mySearchFunctionFromModule',
    'mySearchFunctionFromToModule',
    'myLoginModule',
    'mySignUpModule'
]).
        config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view1', {
                    templateUrl: 'view1/view1.html'
                })
                        .when('/view2', {
                            templateUrl: 'view2/view2.html'
                        })
                        .when('/view3', {
                            templateUrl: 'view3/view3.html'
                        })
                        .when('/view4', {
                            templateUrl: 'view4/view4.html'
                        })
                        .when('/view5', {
                            templateUrl: 'view5/view5.html'
                        })
                        .when('/view6', {
                            templateUrl: 'view6/view6.html'
                        })

                $routeProvider.otherwise({redirectTo: '/view1'});
            }]).
        config(function ($httpProvider) {
            $httpProvider.interceptors.push('authInterceptor');
            $httpProvider.defaults.timeout = 20000;
        });


