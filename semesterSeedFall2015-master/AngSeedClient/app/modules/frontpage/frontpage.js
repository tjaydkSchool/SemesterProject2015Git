angular.module('myFrontPageModule', [])
        .controller('myFrontPageController',['$scope', '$http', function($scope, $http){
                var self = this;
        
                self.flights = [
                    {"airline":"Hejsa"}
                ];
        
                self.searchFunctionFrom = function(){
                    $http({
                        type: "GET",
                        url: "http://localhost:8080/AngSeedServer/api/flightinfo/" + self.destinationFrom + "/" + self.travelDate + "/" + self.numberOfTickets,
                        dataType: "JSON" 
                    }).then(function succesCallback(response){
                        self.flights = response.data;
                    }, function errorCallback(response){
                       self.flights = "No matches found"; 
                    });
                };
        
        
                self.searchFunctionFromTo = function(){
                    $http({
                        type: "GET",
                        url: "/api/flightinfo/" + self.destinationFrom + "/" + self.destinationTo + "/" + self.travelDate + "/" + self.numberOfTickets
                    }).then(function succesCallback(response){
                       self.flights = response.data; 
                    }, function errorCallback(response){
                        self.flights = "No matches found";
                    });
                };
        
        }])
            .directive('frontPageModule', function(){
                return {
                    templateUrl: "modules/frontpage/frontpage.html",
                    restrict: "EA"
                };
            });


