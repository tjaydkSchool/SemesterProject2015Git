angular.module('myTryHardDatePickerModule', [])
        .controller('myTryHardDatePickerController',['$scope', '$http', function($scope, $http){
                var self = this;
        
        
        self.datePickerFunction = function(){
            $( "#datepicker").datepicker();
        }
       
        
        }])
            .directive('tryHardDatePickerModule', function(){
                return {
                    templateUrl: "modules/TryHardDatePicker/tryHardDatePicker.html",
                    restrict: "EA"
                };
            });



