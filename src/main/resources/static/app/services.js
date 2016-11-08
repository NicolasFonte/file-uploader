(function () {
  'use strict';

  angular
      .module('fileUploader.services')
      .factory('FileUploaderFactory', FileUploaderFactory);

  function FileUploaderFactory($http) {

    var urlUpload = 'http://localhost:8080/api/files/upload';
    var urlLoadAll = 'http://localhost:8080/api/files/list';

    return {
      uploadToServer: uploadToServer, loadAllFiles: loadAllFiles
    };

    function uploadToServer(files, description, success, error) {
      var fd = new FormData();
      fd.append('file', files[0]);
      fd.append('description', description);

      $http.post(urlUpload, fd, {
        withCredentials: false, headers: {
          'Content-Type': undefined
        }, transformRequest: angular.identity
      })
          .success(success)
          .error(error);
    }
    function loadAllFiles(success, error) {
      $http.get(urlLoadAll)
          .success(success)
          .error(error);
    }
  }
})();