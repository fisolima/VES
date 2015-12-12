/* global angular */

(function(){
    
    var sessionSrv = function($http) {
        
        var _get = function (onSuccess, onError) {
            $http({method:'GET',url:'api/sessions'}).then(
                function(response) {
                    onSuccess( response.data );
                },
                function(response) {
                    onError( response.data );
                });
        };
        
        var _create = function(onSuccess, onError) {
            $http({method:'POST',url:'api/sessions'}).then(
                function(response) {
                    onSuccess( response.data );
                },
                function(response) {
                    onError( response.data );
                });
        };
        
        var _getOne = function(sessionId, onSuccess, onError) {
            $http({method:'GET',url:'api/sessions/' + sessionId}).then(
                function(response) {
                    onSuccess( response.data );
                },
                function(response) {
                    onError( response.data );
                });
        };
        
        var _delete = function(sessionId, onSuccess, onError) {
            $http({method:'DELETE',url:'api/sessions/' + sessionId}).then(
                function(response) {
                    onSuccess();
                },
                function(response) {
                    onError( response.data );
                });
        };
        
        return {
            getAll: _get,
            add: _create,
            get: _getOne,
            del: _delete
        };
        
    };
    
    angular.module('mainApp').factory('sessionService',['$http',sessionSrv]);
    
}());

