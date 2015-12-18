'use strict';
angular.module('myApp.view6', ['ngRoute', 'ngAnimate', 'ui.bootstrap'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view6', {
                    templateUrl: 'view6/view6.html',
                    controller: 'View6Ctrl'
                });
            }])
        .controller('View6Ctrl', ['$scope', '$rootScope', '$http', function ($scope, $rootScope, $http) {
                var self = this;

                self.reserveeName = "";
                self.reserveePhone = "";
                self.flightD = "";
                self.error = "";



                self.showIndexes = function () {
                    self.flightD = $rootScope.flightrootS;
                    self.airline = $rootScope.airlineNameRoot;
                    self.fromDestS = $rootScope.fromDest;
                    self.toDestS = $rootScope.toDest;

                    self.reserveTryVariable = ({
                        "numberOfSeats": $rootScope.passengersCounter, //tages fra søgningen
                        "ReserveeName": self.reserveeName, //Skrives ved reservering
                        "ReservePhone": self.reserveePhone, //skrives ved reservering
                        "ReserveeEmail": $rootScope.username, //tages fra user login
                        "flightID": self.flightD, // tages fra ui-accordion information
                        "Passengers": $rootScope.passengersCount});
                    self.reserveFunctionAsJSON();
                };

                self.reserveFunctionAsJSON = function () {
                    self.error = "";
                    if ($rootScope.username == "" || $rootScope.username == null) {
                        self.error = "You have to login to make a reservation";
                    } else {

                        var req = {
                            method: 'POST',
                            url: 'api/flightreservation/' + self.airline + "/" + self.fromDestS + "/" + self.toDestS,
                            headers: {
                                'Content-Type': "application/json"
                            },
                            data: self.reserveTryVariable
                        };

                        $http(req).then(function () {

                        }, function () {
                            self.error = "Reservation complete";
                            $http.get('api/user/' + $scope.username).success(function (data) {
                                $rootScope.userReservations = data;
                            });
                            window.location.href = "#/view1";
                        });

                    };
                };
            }]);



