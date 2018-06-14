'use strict';

goodApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/student', {
                    templateUrl: 'views/students.html',
                    controller: 'StudentController',
                    resolve:{
                        resolvedStudent: ['Student', function (Student) {
                            return Student.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
