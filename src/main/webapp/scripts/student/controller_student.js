'use strict';

goodApp.controller('StudentController', function ($scope, resolvedStudent, Student) {

        $scope.students = resolvedStudent;

        $scope.create = function () {
            Student.save($scope.student,
                function () {
                    $scope.students = Student.query();
                    $('#saveStudentModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.student = Student.get({id: id});
            $('#saveStudentModal').modal('show');
        };

        $scope.delete = function (id) {
            Student.delete({id: id},
                function () {
                    $scope.students = Student.query();
                });
        };

        $scope.clear = function () {
            $scope.student = {name: null, usn: null, id: null};
        };
    });
