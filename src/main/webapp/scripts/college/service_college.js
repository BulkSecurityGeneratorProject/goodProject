'use strict';

goodApp.factory('College', function ($resource) {
        return $resource('app/rest/colleges/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
