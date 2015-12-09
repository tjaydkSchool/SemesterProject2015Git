angular.module('mySearchFunctionFromToModule', [])
        .controller('mySearchFunctionFromToController', ['$scope', '$http', function ($scope, $http) {
                $("#datepicker").datepicker();
                var self = this;
                self.flightlist = [];
                self.trips = [];
                self.datePickedFromPicker = new Date();
                
                self.searchFunctionFromTo = function () {
                    alert(self.datePickedFromPicker);

//                    FORMAT THE DATE
                    self.date = new Date(self.datePickedFromPicker);
                    var year = self.date.getFullYear();
                    var month = self.date.getMonth();
                    var day = self.date.getDate();
                    self.dateP = new Date(year, month, day, 2);
                    
                    
//                    end of the date

                    $http({
                        type: "GET",
                        url: "/AngSeedServer/api/flightinfo/" + self.destinationFrom + "/" + self.destinationTo + "/" + self.dateP.toISOString() + "/" + self.numberOfTickets
                    }).then(function succesCallback(response) {
                        self.trips = [];
                        self.flightlist = response.data;
                        for (var i = 0, max = self.flightlist.length; i < max; i++) {
                            for (var j = 0, max = self.flightlist[i].flights.length; j < max; j++) {
                                self.trips.push(self.flightlist[i].flights[j]);
                            }
                        }
                        if (self.trips.length === 0) {
                            alert("NO RESULTS");
                        }
                    }, function errorCallback(response) {
                        self.flightlist = "No matches found";
                        self.trips = [];
                        alert("NO RESULTS");
                    });
                };

            }])
        .directive('searchFunctionFromToModule', function () {
            return {
                templateUrl: "modules/searchFunctionFromTo/searchFunctionFromTo.html",
                restrict: "EA"
            };
        });


