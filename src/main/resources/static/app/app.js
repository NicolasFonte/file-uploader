(function () {
  'use strict';
  angular.module('fileUploader.controllers', []);
  angular.module('fileUploader.services', []);
  angular.module('fileUploader', ['ngAnimate', 'toastr', 'datatables', 'datatables.bootstrap', 'fileUploader.controllers', 'fileUploader.services']);
}());