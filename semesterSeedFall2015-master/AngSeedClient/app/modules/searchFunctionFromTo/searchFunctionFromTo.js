angular.module('mySearchFunctionFromToModule', [])
        .controller('mySearchFunctionFromToController', ['$scope', '$http', function ($scope, $http) {
                var self = this;

                self.flightlist = [];

//                alert(self.flightlist[0].flights.length);
//                alert(self.flightlist.flights[0].flightID);

                self.trips = [];
                self.searchFunctionFromTo = function () {
                    $http({
                        type: "GET",
                        url: "/AngSeedServer/api/flightinfo/" + self.destinationFrom + "/" + self.destinationTo + "/" + self.travelDate + "/" + self.numberOfTickets
                    }).then(function succesCallback(response) {
                        self.trips = [];
                        self.flightlist = response.data;
                        for (var i = 0, max = self.flightlist.length; i < max; i++) {
                            for (var j = 0, max = self.flightlist[i].flights.length; j < max; j++) {
                                self.trips.push(self.flightlist[i].flights[j]);
                            }
                        }
                        if(self.trips.length === 0) {
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


