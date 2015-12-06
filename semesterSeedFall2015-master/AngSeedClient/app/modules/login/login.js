angular.module('myLoginModule', [])
        .controller('myLoginController',['$scope','$http', function($scope, $http){
                var self = this;
        
        }])
            .directive('loginModule', function(){
                return {
                    templateUrl: "modules/login/login.html",
                    restrict: "EA"
                };
            });


