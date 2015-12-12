/* global angular */

(function(){
    
var configSrv = function($http) {

    var checkServerConfigStatus = function( onSuccess, onError ) {
        $http({method:'GET',url:'api/cfg'}).then(
                function(response) {
                    onSuccess( response.data );
                },
                function(response) {
                    onError( response.data );
                });
    };

    var applyServerConfigDirect = function(directData, onSuccess, onError) {            
        $http({method:'PUT',url:'api/cfg/direct',data:directData}).then(
                function() {
                    onSuccess();
                },
                function(response) {
                    onError( response.data );
                });
    };

    var applyServerConfigEtcd = function(etcdData, onSuccess, onError) {
        $http({method:'PUT',url:'api/cfg/etcd',data:etcdData}).then(
                function() {
                    onSuccess();
                },
                function(response) {
                    onError( response.data );
                });
    };

    return {
        checkStatus: checkServerConfigStatus,
        applyDirect: applyServerConfigDirect,
        applyEtcd: applyServerConfigEtcd
    };
};

angular.module('mainApp').factory('configService',['$http',configSrv]);
    
}());

