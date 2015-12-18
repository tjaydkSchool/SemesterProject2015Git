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



                self.showIndexes = function () {
                    self.flightD = $rootScope.flightrootS;
                    self.airline = $rootScope.airlineNameRoot;
                    self.fromDestS = $rootScope.fromDest;
                    self.toDestS = $rootScope.toDest;
                    
                    self.reserveTryVariable = ({
                        "numberOfSeats": $rootScope.passengersCounter, //tages fra søgningen
                        "ReserveeName": self.reserveeName, //Skrives ved reservering
                        "ReservePhone": self.reserveePhone, //skrives ved reservering
                        "ReserveeEmail": "test@test.dk", //tages fra user login
                        "flightID": self.flightD, // tages fra ui-accordion information
                        "Passengers": $rootScope.passengersCount});
                    self.reserveFunctionAsJSON()
                };

                self.reserveFunctionAsJSON = function () {
                    if($scope.isAdmin === true || $scope.isUser === true){
                    var res = $http.post('api/flightreservation/' + self.airline + "/"+ self.fromDestS +"/"+ self.toDestS, self.reserveTryVariable); //arline bliver taget fra uib-accorsdion information
                    res.success(function (data, status, headers, config) {
                        self.message = data;
                        alert("Reservation completed");
                    });
                    res.error(function (data, status, headers, config) {
                    });
                }else{
                    alert("You have to login");
                };
            };
            }]);



