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
                self.hotImage = null;
                self.hotTemp = -273.15;
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
                                        xhrObj.setRequestHeader("Content-Type", "application/xml");
                                        xhrObj.setRequestHeader("Authorization", "Basic OjVKcTMzOFVJQU8wdlM0eHB6clpwVXNlZ3ZuSkRtNlRzR0ovSHNMTG1OWkE=");
                                    },
                                    type: "GET",
                                    url: "https://api.datamarket.azure.com/Bing/Search/Image?Query=%27" + item.city + "%27&$format=json",
                                    success: function (response) {
                                        self.hotImage = response.d.results[0].MediaUrl;
                                    }
                                });
                                $.ajax({
                                    type: "GET",
                                    url: "http://api.openweathermap.org/data/2.5/weather?q=" + item.city + "&APPID=e541fe2f77e0ef1c60a19d68fc5750b7",
                                    dataType: "JSONP",
                                    jsonpCallback: 'callback',
                                    success: function (response) {
                                        self.hotTemp += response.main.temp;
                                    }
                                });
                                self.hotDest.push(item);
                            }
                        }
                        );
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
                    $('body').css("cursor","wait");
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
                            $('body').css("cursor","auto");
                        }
                    } else {
                        if (self.selectedItemFrom === null) {
                            self.errorMsg = "Please choose destinations from dropdown";
                            $('body').css("cursor","auto");
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
                                $('body').css("cursor","auto");
                                window.location.href = "#/view3";
                            }
                        }, function errorCallback(response) {
                            self.flightlist = "No matches found";
                            $rootScope.trips = [];
                            self.errorMsg = "Sorry, something went wrong";
                            $('body').css("cursor","auto");
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
                                $('body').css("cursor","auto");
                                window.location.href = "#/view3";
                            }
                        }, function errorCallback(response) {
                            self.flightlist = "No matches found";
                            $rootScope.trips = [];
                            self.errorMsg = "Sorry, no results found";
                            $('body').css("cursor","auto");
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


