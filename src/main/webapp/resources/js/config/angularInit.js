// Base URL
var BASE_URL = "/HadoopMon";

// App declaration
var app = angular.module('admin', ['ngRoute', 'angular-loading-bar', 'ngAnimate', 'chart.js', 'ngDragDrop']);

        // App configuration
app.config(function ($routeProvider) {

	$routeProvider.
    	when('/',
        {
    		templateUrl: 'resources/pages/clusters.html',
            controller: 'ClustersCtrl'
        }).
        when('/cluster/:id',
        {
        	templateUrl: 'resources/pages/overview.html',
            controller: 'OverviewCtrl'
        }).
        when('/jobs/:id',
        {
        	templateUrl: 'resources/pages/jobs.html',
            controller: 'JobsCtrl'
        }).
        when('/dfsio/:id',
        {
        	templateUrl: 'resources/pages/dfsio.html',
            controller: 'DfsioCtrl'
        }).
        when('/mrbench/:id',
        {
        	templateUrl: 'resources/pages/mrbench.html',
        	controller: 'MRBenchCtrl'
        }).
        when('/rules/:id',
        {
        	templateUrl: 'resources/pages/rules.html',
            controller: 'RulesCtrl'
        }).
        when('/alerts/:id',
        {
            templateUrl: 'resources/pages/alerts.html',
            controller: 'AlertsCtrl'
        }).
        when('/configure/:id',
        {
        	templateUrl: 'resources/pages/configure.html',
            controller: 'ConfigureCtrl'
        }).
        otherwise({
        	redirectTo: '/'
        });
});