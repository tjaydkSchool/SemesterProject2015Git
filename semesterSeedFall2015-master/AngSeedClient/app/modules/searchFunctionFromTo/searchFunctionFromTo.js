angular.module('mySearchFunctionFromToModule', [])
        .controller('mySearchFunctionFromToController', ['$scope', '$rootScope', '$http', '$location', '$log', function ($scope, $rootScope, $http, $location, $log) {
//                $("#datepicker").datepicker();
                var self = this;
                self.myDate = new Date();
                self.flightlist = [];

                $scope.selectedTabIndex = 0;


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

                    if ($scope.selectedTabIndex === 0) {
                        $http({
                            type: "GET",
                            url: "/AngSeedServer/api/flightinfo/" + self.selectedItemFrom.IATA + "/" + self.selectedItemTo.IATA + "/" + self.dateP.toISOString() + "/" + self.numberOfTickets
                        }).then(function succesCallback(response) {

                            $rootScope.trips = [];
                            self.flightlist = response.data;
                            for (var i = 0, max = self.flightlist.length; i < max; i++) {
                                if (self.flightlist[i] !== null) {
                                    $rootScope.trips.push(self.flightlist[i]);
                                }
                            }
                            if ($rootScope.trips.length === 0) {
                                alert("NO RESULTS");
                            }
                            else {
                                window.location.href = "#/view3";
                            }
                        }, function errorCallback(response) {
                            self.flightlist = "No matches found";
                            $rootScope.trips = [];
                            alert("NO RESULTS");
                        });
                    } else if ($scope.selectedTabIndex === 1) {
                        $http({
                            type: "GET",
                            url: "/AngSeedServer/api/flightinfo/" + self.selectedItemFrom.IATA + "/" + self.dateP.toISOString() + "/" + self.numberOfTickets
                        }).then(function succesCallback(response) {

                            $rootScope.trips = [];
                            self.flightlist = response.data;
                            for (var i = 0, max = self.flightlist.length; i < max; i++) {
                                if (self.flightlist[i] !== null) {
                                    $rootScope.trips.push(self.flightlist[i]);
                                }
                            }
                            if ($rootScope.trips.length === 0) {
                                alert("NO RESULTS");
                            }
                            else {
                                window.location.href = "#/view3";
                            }
                        }, function errorCallback(response) {
                            self.flightlist = "No matches found";
                            $rootScope.trips = [];
                            alert("NO RESULTS");
                        });
                    }
                };
            }])
        .directive('searchFunctionFromToModule', function () {
            return {
                templateUrl: "modules/searchFunctionFromTo/searchFunctionFromTo.html",
                restrict: "EA"
            }
            ;
        });


