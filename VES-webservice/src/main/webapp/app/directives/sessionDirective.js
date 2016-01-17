/* global angular */

(function(){
    
    angular.module('mainApp')
        .directive('vesSession',['$timeout', 'sessionService',function($timeout,sessionService) {
            return {
                restrict:'E',
                scope: {
                    session: '='
                },
                templateUrl: 'app/directives/session.html',
                controller: function($scope) {                    
                    $scope.STATUS_WAITING = 1;
                    $scope.STATUS_READY = 2;
                    $scope.STATUS_PROCESSING = 3;
                    $scope.STATUS_INTERRUPTED = 4;
                    $scope.STATUS_COMPLETED = 5;
                    $scope.RES_VIDEO = 1;                    
                    $scope.RES_RESIZE = 2;
                    $scope.RES_SUBTITLE = 3;
                    
                    $scope.remove = function () {
                        sessionService.del($scope.session.id,
                            function () {                                
                                $scope.$parent.deleteSession($scope.session.id);
                            },
                            function (error) {
                                $scope.$parent.showError(error);
                            });                        
                    };
                    
                    $scope.status = '';
                    $scope.videoFile = '';
                    $scope.subtitleFile = '';
                    $scope.resize = {widthPercentage: 100, heightPercentage: 100};
                    $scope.progressTimerId = null;
                    
                    $scope.updateStatus = function() {
                        // session status
                        switch ($scope.session.status) {
                            case $scope.STATUS_WAITING: {
                                    $scope.status = 'WAITING';
                            } break;
                            case $scope.STATUS_READY: {
                                    $scope.status = 'READY';
                            } break;
                            case $scope.STATUS_PROCESSING: {
                                    $scope.status = 'PROCESSING: ' + $scope.session.progress + '%';
                                    
                                    if ($scope.progressTimerId === null)
                                        $scope.progressTimerId = setInterval( progress, 2000);
                            } break;
                            case $scope.STATUS_INTERRUPTED: {
                                    $scope.status = 'INTERRUPTED';
                            } break;
                            case $scope.STATUS_COMPLETED: {
                                    $scope.status = 'COMPLETED';
                            } break;
                        }
                        
                        // session resources
                        if ($scope.session.resources !== null) {
                            for (var i=0; i<$scope.session.resources.length; i++) {
                                switch ($scope.session.resources[i].type) {
                                    case $scope.RES_VIDEO:
                                        $scope.videoFile = $scope.session.resources[i].file;
                                        break;
                                    case $scope.RES_RESIZE:
                                        $scope.resize = {
                                            widthPercentage: $scope.session.resources[i].w,
                                            heightPercentage: $scope.session.resources[i].h
                                        };
                                        break;
                                    case $scope.RES_SUBTITLE:
                                        $scope.subtitleFile = $scope.session.resources[i].file;
                                        break;
                                }
                            }
                        }
                    };
                    
                    var onSessionChanged = function() {
                        $scope.$parent.resetError();
                        
                        // update
                        sessionService.get(
                                $scope.session.id,
                                function (newSession) {
                                    $scope.session = newSession;
                                    
                                    $scope.updateStatus();
                                },
                                function (error) {
                                    $scope.$parent.showError(error);
                                });
                    };
                    
                    $scope.uploadVideo = function(files) {
                        sessionService.uploadVideo( 
                                $scope.session.id,
                                files[0],
                                onSessionChanged,
                                function() {
                                    $scope.$parent.showError('Upload video failed!');
                                });
                    };
                    
                    $scope.uploadSubtitle = function(files) {
                        sessionService.uploadSubtitle( 
                                $scope.session.id,
                                files[0],
                                onSessionChanged,
                                function() {
                                    $scope.$parent.showError('Upload subtitle failed!');
                                });
                    };
                    
                    $scope.resizeVideo = function () {
                        sessionService.resize(
                                $scope.session.id,
                                $scope.resize,
                                onSessionChanged,
                                function() {
                                    $scope.$parent.showError('Resize request failed!');
                                });
                    };
                    
                    var stopProgress = function () {
                        if ($scope.progressTimerId)
                            clearInterval($scope.progressTimerId);
                    };
                    
                    var progress = function () {
                        sessionService.get(
                                $scope.session.id,
                                function (newSession) {
                                    $scope.session = newSession;
                                    
                                    $scope.updateStatus();
                                    
                                    if ($scope.session.status !== $scope.STATUS_PROCESSING)
                                        stopProgress();
                                },
                                function (error) {
                                    $scope.$parent.showError(error);
                                    stopProgress();
                                });
                    };
                    
                    $scope.burn = function () {
                        sessionService.burn(
                                $scope.session.id,
                                function () {
                                    $scope.progressTimerId = setInterval( progress, 2000);
                                },
                                function(error) {
                                    $scope.$parent.showError('Burn failed: ' + error);
                                });
                    };
                    
                    $scope.download = function () {                        
                        sessionService.download(
                                $scope.session.id,
                                function () {
                                },
                                function(error) {
                                    $scope.$parent.showError('Download failed: ' + error);
                                });
                    };
                    
                    $scope.cancel = function () {
                        console.log("cancel!");
                    };
                },
                link: function(scope, element, attrs) {
                    $timeout(scope.updateStatus, 0);
                }
            };
        }]);
    
}());

