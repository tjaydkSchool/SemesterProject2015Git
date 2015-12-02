'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', [
    'ngRoute',
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
    'myFrontPageModule',
    'myLoginModule',
    'mySignUpModule'
]).
        config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view1', {
                    templateUrl: 'view1/view1.html'
                });
                $routeProvider.otherwise({redirectTo: '/view1'});
            }]).
        config(function ($httpProvider) {
            $httpProvider.interceptors.push('authInterceptor');
        });


