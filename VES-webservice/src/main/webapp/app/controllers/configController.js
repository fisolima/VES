/* global angular */

(function(){
    
var configCtrl = function($scope,configService) {
    
    $scope.direct = {
        storage:'',
        database:''
    };
        
    $scope.etcd = {
        storageKey:'',
        databaseKey:'',
        etcdEndPoint:''
    };
    
    $scope.setupType = 'manual';
    
    $scope.errorMessage = '';
    
    var onSuccess = function() {
        $scope.errorMessage = '';
        $scope.$parent.checkServerStatus();  
    };
    var onError = function(error) {
        $scope.errorMessage = error;
    };
    
    $scope.applyDirect = function() {        
        configService.applyDirect($scope.direct, onSuccess, onError);
    };
    
    $scope.applyEtcd = function() {        
        configService.applyEtcd($scope.etcd, onSuccess, onError);
    };
};

angular.module('mainApp').controller('configController',['$scope','configService', configCtrl]);

}());

