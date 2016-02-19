<!DOCTYPE html>
<html ng-app="admin">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Admin Dashboard</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

	<link rel="stylesheet" href="resources/css/loading-bar.css"/>
    <link rel="stylesheet" href="resources/css/sidebar.css" />
    <link rel="stylesheet" href="resources/css/main.css" />
    
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
</head>

<nav class="navbar navbar-default navbar-fixed-top navbar-inverse" ng-controller="NavbarCtrl">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" ng-href="#/">IBM</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a ng-href="#/">Clusters <span class="sr-only">(current)</span></a></li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li><a href="#/"><strong>{{time | date:format}}</strong></a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav><!-- /.navbar -->

<body>
    <!--Template area inside ng-view -->
    <div ng-view>

    </div>
    
    <div class="alert alert-danger alert-dismissible" role="alert">
	  <button type="button" class="close" <span aria-hidden="true">&times;</span></button>
	  <strong class="title">Error</strong> <p class="error-content"> Better check yourself, you're not looking too good</p>
	</div>

    <!-- JS -->
    <script src="https://code.jquery.com/jquery-2.2.0.min.js"></script>
    <script src="resources/js/Chart.min.js"></script>

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>

    <script src="resources/js/angular.min.js"></script>
    <script src="resources/js/angular-route.min.js"></script>
        <script src="resources/js/angular-animate.min.js"></script>
    
    <script src="resources/js/loading-bar.js"></script>
    
    <script>
        // Base URL
        var baseUrl = "http://localhost:8080/HadoopMon";

        // App declaration
        var app = angular.module('admin', ['ngRoute', 'angular-loading-bar', 'ngAnimate']);

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
                when('/configure/:id',
                {
                    templateUrl: 'resources/pages/configure.html',
                    controller: 'ConfigureCtrl'
                }).
                otherwise({
                    redirectTo: '/'
                });
        });

        // Controller
        app.controller('ClustersCtrl', function ($scope, $http, $routeParams) {
            $scope.message = "This is a test message from angular backend";
			
            $http.get(baseUrl + "/cluster/getclusters").then(
                function (data) { //Success handler
                    console.log(data);
                    $scope.clusters = data.data;
                },
                handleError
            );

            // Code to remove a cluster
            $scope.deleteCluster = function (index, cluster) {
                $http.post(baseUrl + "/cluster/deletecluster/", cluster).then(function (data) {
                    $scope.clusters.splice(index, 1);
                });
            }
        });

        app.controller('OverviewCtrl', function ($scope, $http, $routeParams) {
            $scope.message = "This is a test message from angular backend";

            $http.get(baseUrl + "/cluster/getcluster/" + $routeParams.id).then(
                function (data) { //Success handler
                    $scope.cluster = data.data;
                },
                handleError
            );
        });

        app.controller('JobsCtrl', function ($scope, $http, $routeParams) {
            $scope.message = "This is a test message from angular backend";

            $http.get(baseUrl + "/cluster/getcluster/" + $routeParams.id).then(
                function (data) { //Success handler
                    $scope.cluster = data.data;

                },
                handleError
            );
        });

        app.controller('DfsioCtrl', function ($scope, $http, $routeParams) {
            $scope.message = "This is a test message from angular backend";

            // Get cluster
            $http.get(baseUrl + "/cluster/getcluster/" + $routeParams.id).then(
                function (data) { //Success handler
                    $scope.cluster = data.data;
                },
                handleError
            );

            // Get benchmarks
            $http.get(baseUrl + "/dfsio/benchmarks/" + $routeParams.id).then(
                function (data) {
                    $scope.results = data.data;
                },
                handleError
            );

            $scope.deleteBenchmark = function (index, result) {
                $http.post(baseUrl + "/dfsio/delete/" + result.id, result).then(function (data) {
                    $scope.results.splice(index, 1);
                });
            }

            // to decide wether a result is bad
            $scope.isAlert = function (result) {
                if (result.throughputMb < 10) {
                    return true;
                } else {
                    return false;
                }
            }

            // Run benchmark on server
            $scope.runDFSIOBenchmark = function () {

                // Check if form is valid
                if ($scope.benchmarkForm.$valid) {
                    console.log("id:" + $scope.cluster.id);
                    console.log("num files:" + $scope.form.nrFiles);
                    console.log("file size:" + $scope.form.fileSize);

                    $http.post(baseUrl + "/dfsio/dfsio", $.param({
                        id: $scope.cluster.id,
                        numFiles: $scope.form.nrFiles,
                        fileSize: $scope.form.fileSize
                    }),
                    {
                       headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
                    }).then(function (data) {
                        console.log(data);
                    }, handleError);

                } else {
                    console.log("invalid");
                }
            }
        });

        app.controller('MRBenchCtrl', function ($scope, $http, $routeParams) {
            $scope.message = "This is a test message from angular backend";

            // Get cluster
            $http.get(baseUrl + "/cluster/getcluster/" + $routeParams.id).then(
                function (data) { //Success handler
                    $scope.cluster = data.data;
                },
                handleError
            );

            // Get benchmarks
            $http.get(baseUrl + "/mrbench/benchmarks/" + $routeParams.id).then(
                function (data) {
                    $scope.results = data.data;
                },
                handleError
            );

            $scope.deleteBenchmark = function (index, result) {
                $http.post(baseUrl + "/mrbench/delete/" + result.id, result).then(function (data) {
                    $scope.results.splice(index, 1);
                });
            }
        });

        app.controller('ConfigureCtrl', function ($scope, $http, $routeParams) {
            $scope.message = "This is a test message from angular backend";

            $http.get(baseUrl + "/cluster/getcluster/" + $routeParams.id).then(
                function (data) { //Success handler
                    $scope.cluster = data.data;
                },
                handleError
            );
        });

		////////// Controller for sidebar //////////
        app.controller('SidebarCtrl', function ($scope, $location) {
            //Check for current path for active element
            $scope.isCurrentPath = function (path) {
                return $location.path() == path;
            }
        });

		////////// Controller for the navigation bar //////////
        app.controller('NavbarCtrl', function ($scope, $interval) {
            $scope.format = 'M/d/yy h:mm:ss a';
            $interval(function () {
                $scope.time = Date.now();
            }, 10);
        });

		////////// Sidebar directive //////////
        app.directive('sidebar', function () {
            return {
                templateUrl: 'resources/utils/sidebar.html'
            }
        });
        
        ////////// Error Handler //////////
        function handleError(response) {
            $('.error-content').text(response.data.message);
        	$('.alert').slideDown(250);
        	
        	//Close alert box after 2 seconds
        	setTimeout(function() {
        		$('.alert').slideUp(250);
        	}, 5000);
        }
        
        // Close down alert
        $('.close').click(function(e) {
        	$('.alert').slideUp(250);
        })
    </script>
</body>
</html>
