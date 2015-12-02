angular.module('mySignUpModule', [])
        .controller('mySignUpController', [function(){
                var self = this;
        }])
            .directive('signUpModule', function(){
                return{
                    templateUrl: "modules/signUp/signUp.html",
                    restrict: "EA"
                };
            });


