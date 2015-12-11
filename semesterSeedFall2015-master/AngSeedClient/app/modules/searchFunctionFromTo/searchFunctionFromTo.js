angular.module('mySearchFunctionFromToModule', [])
        .controller('mySearchFunctionFromToController', ['$scope', '$http', function ($scope, $http) {
//                $("#datepicker").datepicker();
                var self = this;
                self.myDate = new Date();
                self.flightlist = [];
                self.trips = [];
                
                self.selectedItemFrom = null;
                self.selectedItemTo = null;
                self.searchTextFrom = null;
                self.querySearch = querySearch;
                self.cities = [];
                
                $http.get('json/cities.json').success(function (response) {
                    self.cities = response;
                });
                function querySearch(query) {
                    var q = query.toLowerCase();
                    var results = [];
                    var result = angular.forEach(self.cities, function (item) {
                        if (item.city.toLowerCase().indexOf(q) === 0) {
                            results.push(item);
                        }
                    });
                    return results;
                }
                ;

                self.searchFunctionFromTo = function () {
                    console.log(self.selectedItemFrom.IATA);

//                    FORMAT THE DATE
                    var year = self.myDate.getFullYear();
                    var month = self.myDate.getMonth();
                    var day = self.myDate.getDate();

                    self.dateP = new Date(year, month, day, 2);


//                    end of the date

                    $http({
                        type: "GET",
                        url: "/AngSeedServer/api/flightinfo/" + self.selectedItemFrom.IATA + "/" + self.selectedItemTo.IATA + "/" + self.dateP.toISOString() + "/" + self.numberOfTickets
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


