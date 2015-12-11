'use strict';
angular.module('myApp.view3', ['ngRoute','ngAnimate', 'ui.bootstrap'])
        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view3', {
                    templateUrl: 'view3/view3.html',
                    controller: 'View3Ctrl'
                });
            }])
        .controller('View3Ctrl', ['$scope', '$rootScope', '$http', function ($scope, $rootScope, $http) {

                var self = this;
                self.results = [];
                self.results = $rootScope.trips;
                
                self.reserveTryVariable = ({        
                    
                    "numberOfSeats": 3, //Skrives ved reservering
                    "ReserveeName": "Bubber", //Skrives ved reservering
                    "ReservePhone": "12345678", //skrives ved reservering
                    "ReserveeEmail": "test@test.dk", //tages fra user login
                    "flightID": "100004", // tages fra ui-accordion information
                    "Passengers": [ //kan tilføjes ved reservation
                        {
                            "firstName": "Peter", 
                            "lastName": "Peterson"
                        },
                        {
                            "firstName": "Jane",
                            "lastName": "Peterson"
                        }
                    ]});


                self.reserveFunctionAsJSON = function () {

                    var res = $http.post('http://localhost:8080/AngSeedServer/api/flightreservation/' + self.results.airline , self.reserveTryVariable); //arline bliver taget fra uib-accorsdion information
                    res.success(function (data, status, headers, config) {
                        self.message = data;
                    });
                    res.error(function (data, status, headers, config) {
                        alert("failure message: " + JSON.stringify({data: data}))
                    });
                }




            }]);