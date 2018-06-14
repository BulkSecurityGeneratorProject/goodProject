'use strict';

goodApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/college', {
                    templateUrl: 'views/colleges.html',
                    controller: 'CollegeController',
                    resolve:{
                        resolvedCollege: ['College', function (College) {
                            return College.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
