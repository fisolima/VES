/* global angular */

(function(){
    
angular.module('mainApp')
    .config(['$routeProvider',function($routeProvider){
        $routeProvider.
                when('/config',{
                        templateUrl: 'app/views/config.html',
                        controller: 'configController',
                        activetab: 'config'
                }).
                when('/session', {
                        templateUrl: 'app/views/session.html',
                        controller: 'sessionController',
                        activetab: 'session'
                }).
                otherwise( {
                        templateUrl: 'app/views/session.html',
                        controller: 'sessionController',
                        activetab: 'session'
                });
        }]);
    
}());