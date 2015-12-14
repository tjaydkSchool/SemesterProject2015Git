angular.module('mySignUpModule', [])
        .controller('mySignUpController', ['$rootScope', '$http', '$location', function ($rootScope, $http, $location) {
                var self = this;
                $rootScope.usersubmit = function () {
//                    alert("signup here");
                    var submitData = {
                        url: 'api/user',
                        method: 'POST',
                        dataType: 'json',
                        data: {username: self.username, password: self.password}
                    };
                    $http(submitData).then(function success(response) {
                        $location.path("#/view1");
                        $("#loginScreen").css("opacity", ".9");
                        $("#loginScreen").css("z-index", "1000");
                        $(".searchField").css("opacity", "0");
                        $("#loginBtn").css("opacity", "0");
                    }, function error(data, status) {
                        alert(data.error(error));
                    });
                };
            }])
        .directive('signUpModule', function () {
            return{
                templateUrl: "modules/signUp/signUp.html",
                restrict: "EA"
            };
        });


