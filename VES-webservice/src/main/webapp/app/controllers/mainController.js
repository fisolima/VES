/* global angular */

(function(){
    
var mainCtrl = function($scope, $route, configService) {
    
    $scope.$route = $route;
    
    $scope.appTitle = "VES Polimi";
    
    $scope.config = {
        data: undefined,
        available: false
    };
    
    $scope.serverAvailable = false;
    
    var onServerConfigReady = function( configData ) {
        $scope.config.data = configData;
        $scope.config.available = true;
    };
    
    var onServerConfigUnavailable = function( result ) {
        $scope.config.data = result;
        $scope.config.available = false;
    };
    
    $scope.checkServerStatus = function() {
        configService.checkStatus( onServerConfigReady, onServerConfigUnavailable );
    };
    
    angular.element(document).ready( $scope.checkServerStatus() );
};

angular.module('mainApp').controller('mainController',['$scope','$route','configService', mainCtrl]);

}());

