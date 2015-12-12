/* global angular */

(function(){
    
var sessionCtrl = function($scope,sessionService) {
    
    $scope.sessions = [];    
    $scope.errorMessage = '';
    
    var _querySessions = function() {        
        sessionService.getAll( function(sessions){
            $scope.resetError();
            $scope.sessions = sessions;
        },
        $scope.showError);
    };
    
    $scope.showError = function (error) {
        $scope.errorMessage = error;
    };
    
    $scope.resetError = function () {
        $scope.errorMessage = '';
    };
    
    $scope.addSession = function() {
        sessionService.add( function(session){
            $scope.resetError();
            $scope.sessions.push(session);
        },
        $scope.showError);
    };

    $scope.deleteSession = function(sessionId) {
        $scope.resetError();
        for ( var i=0; i<$scope.sessions.length; i++ ) {
            if ($scope.sessions[i].id === sessionId) {
                $scope.sessions.splice(i,1);
                break;
            }
        }
    };
    
    angular.element(document).ready( _querySessions() );
};

angular.module('mainApp').controller('sessionController',['$scope','sessionService', sessionCtrl]);

}());


