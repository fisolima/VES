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
        
        var _uploadVideo = function(sessionId,file,onSuccess,onError) {
            var fd = new FormData();
            
            fd.append("file", file);

            $http.post('api/sessions/' + sessionId + '/video', fd, {
                withCredentials: true,
                headers: {'Content-Type': undefined },
                transformRequest: angular.identity
                }).then(onSuccess,onError);
        };
        
        var _uploadSubtitle = function(sessionId,file,onSuccess,onError) {
            var fd = new FormData();
            
            fd.append("file", file);

            $http.post('api/sessions/' + sessionId + '/subtitle', fd, {
                withCredentials: true,
                headers: {'Content-Type': undefined },
                transformRequest: angular.identity
                }).then(onSuccess,onError);
        };
        
        var _resize = function(sessionId,resizeData,onSuccess,onError) {
            $http({
                    method:'POST',
                    url:'api/sessions/' + sessionId + '/resize',
                    data: resizeData
                }).then(onSuccess,onError);
        };
        
        var _burn = function(sessionId,onSuccess,onError) {
            $http({
                    method:'POST',
                    url:'api/sessions/' + sessionId + '/burn'
                }).then(
                        onSuccess,
                        function (response) {                            
                            onError(response.data);
                        });
        };
        
        var _download = function(sessionId,onSuccess,onError) {
            var sessionUrl = 'api/sessions/' + sessionId + '/result';
            
            $http({
                    method:'GET',
                    headers: {'Accept':'application/octet-stream'},
                    cache: false,
                    url: sessionUrl
                }).then(
                        //onSuccess,
                        function (){
                            window.open(sessionUrl, '_self', '');  
                        },
                        function (response) {                            
                            onError(response.data);
                        });
        };
        
        return {
            getAll: _get,
            add: _create,
            get: _getOne,
            del: _delete,
            uploadVideo: _uploadVideo,
            uploadSubtitle: _uploadSubtitle,
            resize: _resize,
            burn: _burn,
            download: _download
        };
        
    };
    
    angular.module('mainApp').factory('sessionService',['$http',sessionSrv]);
    
}());

