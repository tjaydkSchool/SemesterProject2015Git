angular.module('mySearchFunctionFromToModule', [])
        .controller('mySearchFunctionFromToController',['$scope', '$http', function($scope, $http){
                var self = this;
        
                self.flights = [
                    {"airline":"Hejsa"}
                ];   
                self.searchFunctionFromTo = function(){
                    $http({
                        type: "GET",
                        url: "/AngSeedServer/api/flightinfo/" + self.destinationFrom + "/" + self.destinationTo + "/" + self.travelDate + "/" + self.numberOfTickets
                    }).then(function succesCallback(response){
                       self.flights = response.data; 
                    }, function errorCallback(response){
                        self.flights = "No matches found";
                    });
                };
        
        }])
            .directive('searchFunctionFromToModule', function(){
                return {
                    templateUrl: "modules/searchFunctionFromTo/searchFunctionFromTo.html",
                    restrict: "EA"
                };
            });


