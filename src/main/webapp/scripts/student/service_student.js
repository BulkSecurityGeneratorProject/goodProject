'use strict';

goodApp.factory('Student', function ($resource) {
        return $resource('app/rest/students/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
