/* global angular */

(function(){
    
    var sessionSrv = function($http) {
        
        return {};
        
    };
    
    angular.module('mainApp').factory('sessionService',['$http',sessionSrv]);
    
}());

