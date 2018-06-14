'use strict';

goodApp.controller('CollegeController', function ($scope, resolvedCollege, College) {

        $scope.colleges = resolvedCollege;

        $scope.create = function () {
            College.save($scope.college,
                function () {
                    $scope.colleges = College.query();
                    $('#saveCollegeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.college = College.get({id: id});
            $('#saveCollegeModal').modal('show');
        };

        $scope.delete = function (id) {
            College.delete({id: id},
                function () {
                    $scope.colleges = College.query();
                });
        };

        $scope.clear = function () {
            $scope.college = {name: null, fees: null, id: null};
        };
    });
