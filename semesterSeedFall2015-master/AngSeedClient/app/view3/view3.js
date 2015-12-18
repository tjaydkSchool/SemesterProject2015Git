'use strict';
angular.module('myApp.view3', ['ngRoute', 'ngAnimate', 'ui.bootstrap'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view3', {
                    templateUrl: 'view3/view3.html',
                    controller: 'View3Ctrl'
                });
            }])
        .controller('View3Ctrl', ['$scope', '$rootScope', '$uibModal', '$http', '$location', function ($scope, $rootScope, $http, $location) {

                var self = this;
                self.results = [];
                self.results = $rootScope.trips;

                self.reserveTryVariable = ({
                    "numberOfSeats": $rootScope.passengersCounter, //tages fra søgningen
                    "ReserveeName": self.reserveeName, //Skrives ved reservering
                    "ReservePhone": self.reserveePhone, //skrives ved reservering
                    "ReserveeEmail": "test@test.dk", //tages fra user login
                    "flightID": self.flightIDD, // tages fra ui-accordion information
                    "Passengers": $rootScope.passengersCount
                });

                self.toReservationPage = function (flight, airlineName, from, to) {
                    $rootScope.flightrootS = flight;
                    $rootScope.airlineNameRoot = airlineName;
                    $rootScope.fromDest = from;
                    $rootScope.toDest = to;
                    $rootScope.passengersCount = [];
                    for (var i = 0; i < $rootScope.passengersCounter - 1; i++) {
                        $rootScope.passengersCount.push({
                            firstName: "",
                            lastName: ""
                        });
                    }
                    ;
                    
                    window.location.href = "#/view6";
                };
            }]);
