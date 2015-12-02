angular.module('myFrontPageModule', [])
        .controller('myFrontPageController',[function(){
                var self = this;
        }])
            .directive('frontPageModule', function(){
                return {
                    templateUrl: "modules/frontpage/frontpage.html",
                    restrict: "EA"
                };
            });


