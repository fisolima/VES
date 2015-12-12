/* global angular */

(function(){
    
    angular.module('mainApp')
        .directive('vesSession',['sessionService',function(sessionService) {
            return {
                restrict:'E',
                scope: {
                    session: '='
                },
                templateUrl: 'app/directives/session.html',
                link: function(scope, element, attrs) {
                    scope.status = '';
                    scope.remove = function () {
                        sessionService.del(scope.session.id,
                            function () {                                
                                scope.$parent.deleteSession(scope.session.id);
                            },
                            function (error) {
                                scope.$parent.showError(error);
                            });                        
                    };
                    
                    scope.updateStatus = function() {
                        switch (scope.session.status) {
                            case 1: scope.status = 'WAITING'; break;
                            case 2: scope.status = 'READY'; break;
                            case 3: scope.status = 'PROCESSING: ' + scope.session.progress + '%'; break;
                            case 4: scope.status = 'INTERRUPTED'; break;
                            case 5: scope.status = 'COMPLETED'; break;
                        }
                    };
                }
            };
        }]);
    
}());

