angular.module('mySearchFunctionFromToModule', [])
        .controller('mySearchFunctionFromToController', ['$scope', '$rootScope', '$http', '$location', '$log', function ($scope, $rootScope, $http, $location, $log) {
//                $("#datepicker").datepicker();
                var self = this;
                self.myDate = new Date();
                self.hotDest = [];
                self.hotDestVid = [];
                self.flightlist = [];
                self.errorMsg = null;

                $scope.selectedTabIndex = 0;


                self.selectedItemFrom = null;
                self.selectedItemTo = null;
                self.searchTextFrom = null;
                self.querySearch = querySearch;
                self.cities = [];
                $http.get('json/cities.json').success(function (response) {
                    self.cities = response;
                });

                $http.get('api/flightinfo/hotdestinations').success(function (response) {
                    angular.forEach(response, function (item) {
                        var dest = item;
                        angular.forEach(self.cities, function (item) {
                            if (item.IATA.indexOf(dest) === 0) {
                                $.ajax({
                                    beforeSend: function (xhrObj) {
                                        xhrObj.setRequestHeader("Content-Type", "application/json");
                                        xhrObj.setRequestHeader("Api-Key", "gfzeggrdzegkpqahe9xcwvvf");
                                        xhrObj.setRequestHeader("Postman-Token", "a791af09-d9f7-6249-6d55-539065f49dd5");
                                    },
                                    type: "GET",
                                    url: "https://api.gettyimages.com:443/v3/search/videos?phrase=" + item.city,
                                    success: function (response) {
                                        alert(response.videos[0].display_sizes[0].uri);
                                    }
                                });
                                $.ajax({
                                    type: "GET",
                                    url: "http://api.openweathermap.org/data/2.5/weather?q=" + item.city + "&APPID=e541fe2f77e0ef1c60a19d68fc5750b7",
                                    dataType: "JSONP",
                                    jsonpCallback: 'callback',
                                    success: function (response) {
                                        var temp = -273.15;
                                        temp += response.main.temp;
                                    }
                                });

                                self.hotDest.push(item);
                            }
                        });
                    });
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
                    self.errorMsg = null;
                    //  FORMAT THE DATE
                    var year = self.myDate.getFullYear();
                    var month = self.myDate.getMonth();
                    var day = self.myDate.getDate();
                    self.dateP = new Date(year, month, day, 2);
                    $rootScope.passengersCounter = self.numberOfTickets;
//                    end of the date

                    // CHECK IF DESTINATIONS ARE PICKED FROM DROPDOWNS
                    if ($scope.selectedTabIndex === 0) {
                        if (self.selectedItemFrom === null || self.selectedItemTo === null) {
                            self.errorMsg = "Please choose destinations from dropdown";
                        }
                    } else {
                        if (self.selectedItemFrom === null) {
                            self.errorMsg = "Please choose destinations from dropdown";
                        }
                    }

                    if ($scope.selectedTabIndex === 0) {
                        $http({
                            type: "GET",
                            url: "/AngSeedServer/api/flightinfo/" + self.selectedItemFrom.IATA + "/" + self.selectedItemTo.IATA + "/" + self.dateP.toISOString() + "/" + $rootScope.passengersCounter
                        }).then(function succesCallback(response) {

                            $rootScope.trips = [];
                            self.flightlist = response.data;
                            for (var i = 0, max = self.flightlist.length; i < max; i++) {
                                if (self.flightlist[i] !== null) {
                                    $rootScope.trips.push(self.flightlist[i]);
                                }
                            }
                            if ($rootScope.trips.length === 0) {
                                self.errorMsg = "Sorry, no results found";
                            }
                            else {
                                window.location.href = "#/view3";
                            }
                        }, function errorCallback(response) {
                            self.flightlist = "No matches found";
                            $rootScope.trips = [];
                            self.errorMsg = "Sorry, something went wrong";
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
                                self.errorMsg = "Sorry, no results found";
                            }
                            else {
                                window.location.href = "#/view3";
                            }
                        }, function errorCallback(response) {
                            self.flightlist = "No matches found";
                            $rootScope.trips = [];
                            self.errorMsg = "Sorry, no results found";
                        });
                    }
                }
                ;
            }])
        .directive('searchFunctionFromToModule', function () {
            return {
                templateUrl: "modules/searchFunctionFromTo/searchFunctionFromTo.html",
                restrict: "EA"
            }
            ;
        });


