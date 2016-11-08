(function () {

  angular
      .module('fileUploader.controllers')
      .controller('FileUploaderController', FileUploaderController);

  function FileUploaderController($scope, FileUploaderFactory, DTOptionsBuilder, toastr) {

    var vm = this;
    $scope.files = [];
    $scope.inputFiles;
    $scope.dtOptions = DTOptionsBuilder.newOptions().withBootstrap();

    $scope.uploadFile = function (element) {
      $scope.$apply(function ($scope) {
        $scope.inputFiles = element.files;
      });
    };

    $scope.addFile = function () {
      FileUploaderFactory.uploadToServer($scope.inputFiles, $scope.description, function () {
        // TODO: consider use directive
        angular.forEach(angular.element("input[type='file']"), function (inputElem) {
          angular.element(inputElem).val(null);
        });
        angular.forEach(angular.element("input[type='text']"), function (inputElem) {
          angular.element(inputElem).val(null);
        });
        toastr.success("File Uploaded Successfully");
      }, function () {
        toastr.error("Error uploading file");
      });
    };

    $scope.getAllFiles = function () {
      FileUploaderFactory.loadAllFiles(function (data, status, headers, config) {
        if (data) {
          $scope.files = data;
        }
      }, function (data, status, headers, config) {
      });
    };
  };
}());