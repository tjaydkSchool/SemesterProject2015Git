angular.module('mySearchFunctionFromModule', [])
        .controller('mySearchFunctionFromController',['$scope', '$http', function($scope, $http){
                var self = this;
        
                self.flights = [
                    {"airline":"Hejsa"}
                ];
        
                self.searchFunctionFrom = function(){
                    $http({
                        type: "GET",
                        url: "/AngSeedServer/api/flightinfo/" + self.destinationFrom + "/" + self.travelDate + "/" + self.numberOfTickets,
                        dataType: "JSON" 
                    }).then(function succesCallback(response){
                        self.flights = response.data;
                    }, function errorCallback(response){
                       self.flights = "No matches found"; 
                    });
                };
        
        }])
            .directive('searchFunctionFromModule', function(){
                return {
                    templateUrl: "modules/searchFunctionFrom/searchFunctionFrom.html",
                    restrict: "EA"
                };
            });


