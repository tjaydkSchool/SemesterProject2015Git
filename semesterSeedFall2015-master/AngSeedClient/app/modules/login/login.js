angular.module('myLoginModule', [])
        .controller('myLoginController', [function(){
                var self = this;
        }])
            .directive('loginModule', function(){
                return {
                    templateUrl: "modules/login/login.html",
                    restrict: "EA"
                };
            });


