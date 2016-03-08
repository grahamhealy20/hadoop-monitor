<!DOCTYPE html>
<html ng-app="admin">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Admin Dashboard</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

	<link rel="stylesheet" href="resources/css/angular-chart.min.css" />
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
	  <button type="button" class="close"> <span aria-hidden="true">&times;</span></button>
	  <strong class="error-title">Error</strong> <p class="error-content"></p>
	</div>
	
	<div class="alert alert-success alert-dismissible" role="alert">
	  <button type="button" class="close"> <span aria-hidden="true">&times;</span></button>
	  <strong class="success-title">Success</strong> <p class="success-content"></p>
	</div>
	
	
	

    <!-- JS -->
    <script src="resources/js/jquery-2.2.0.min.js"></script>
    <script src="resources/js/Chart.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
     
    <!-- Websocket JS -->
    <script src="resources/js/sockjs-0.3.4.min.js"></script>
    <script src="resources/js/stomp.min.js"></script>
    <script src="resources/js/metricsocket.js"></script>

	<!-- Angular JS -->
    <script src="resources/js/angular.min.js"></script>
    <script src="resources/js/angular-route.min.js"></script>
    <script src="resources/js/angular-animate.min.js"></script>
    <script src="resources/js/angular-chart.min.js"></script>
    <script src="resources/js/loading-bar.js"></script>
    
    <script>
        // Base URL
        var BASE_URL = "/HadoopMon";

        // App declaration
        var app = angular.module('admin', ['ngRoute', 'angular-loading-bar', 'ngAnimate', 'chart.js']);

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
        
        // Service to interact with REST service
        app.service('MonitorService', function($http) {
        	
        	///////// CLUSTERS /////////
        	
        	// Get all clusters
        	this.getClusters = function(successCallback, errorCallback) {
        		// Do a request then call specified callback funtions
        		$http.get(BASE_URL + "/cluster/clusters").then(successCallback, errorCallback);
        	};
        	
        	// get a cluster given an id
        	this.getCluster = function(id, successCallback, errorCallback) {
        		 $http.get(BASE_URL + "/cluster/cluster/" + id).then(successCallback, errorCallback);
        	};
        	
        	this.addCluster = function(cluster, successCallback, errorCallback) {
        		$http.post(BASE_URL + "/cluster/add", cluster).then(successCallback, errorCallback);
        	};
        	
        	this.updateCluster = function(cluster, successCallback, errorCallback) {
        		$http.put(BASE_URL + "/cluster/edit", cluster).then(successCallback, errorCallback);
        	};
        	
        	this.deleteCluster = function (cluster, successCallback, errorCallback) {
        		$http.post(BASE_URL + "/cluster/delete", cluster).then(successCallback, errorCallback);
        	};
        	
        	this.getNamenodeLogTail = function(id, successCallback, errorCallback) {
        		$http.get(BASE_URL + "/cluster/log/namenode/" + id + "/tail").then(successCallback, errorCallback);
        	};
        	
        	this.getDatanodeLogTail = function(id, successCallback, errorCallback) {
        		$http.get(BASE_URL + "/cluster/log/datanode/" + id + "/tail").then(successCallback, errorCallback);
        	};
        	
        	///////// JOBS /////////
        	             	
        	this.getJobs = function(id, successCallback, errorCallback) {
        		$http.get(BASE_URL + "/cluster/jobs/" + id).then(successCallback, errorCallback);
        	};
        	
        	///////// DFSIO /////////
        	
        	this.getRecentDFSIOBenchmarks = function(id, successCallback, errorCallback) {
        		$http.get(BASE_URL + "/dfsio/benchmarks/last/" + id).then(successCallback, errorCallback);
        	};
        	
        	this.getDFSIOBenchmarks = function(id, successCallback, errorCallback) {
        		$http.get(BASE_URL + "/dfsio/benchmarks/" + id).then(successCallback, errorCallback);
        	};
        	
        	this.deleteDFSIOBenchmark = function(benchmark, successCallback, errorCallback) {
        		 $http.post(BASE_URL + "/dfsio/delete/" + benchmark.id, benchmark).then(successCallback, errorCallback);
        	};
        	
        	this.runDFSIOBenchmark = function(data, successCallback, errorCallback) {
        		$http.post(BASE_URL + "/dfsio/dfsio", data, {
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
                }).then(successCallback, errorCallback);
        	};
        	
        	///////// MRBENCH /////////
        	
        	this.getRecentMRBenchBenchmarks = function(id, successCallback, errorCallback) {
        		$http.get(BASE_URL + "/mrbench/benchmarks/last/" + id).then(successCallback, errorCallback);
        	};
        	
        	this.getMRBenchBenchmarks = function(id, successCallback, errorCallback) {
        		$http.get(BASE_URL + "/mrbench/benchmarks/" + id).then(successCallback, errorCallback);
        	};
        	
        	this.deleteMRBenchBenchmark = function(benchmark, successCallback, errorCallback) {
        		 $http.post(BASE_URL + "/mrbench/delete/" + benchmark.id, benchmark).then(successCallback, errorCallback);
        	};
        	
        	this.runMRBenchBenchmark = function(data, successCallback, errorCallback) {
        		$http.post(BASE_URL + "/mrbench/mrbench", data, {
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
                }).then(successCallback, errorCallback);
        	};
        });

        // Controller
        app.controller('ClustersCtrl', function (MonitorService, $scope, $routeParams) {
            $scope.message = "This is a test message from angular backend";
			$scope.reverse = true;
            
			MonitorService.getClusters(function (data) { //Success handler				
                $scope.clusters = data.data;
            },
            handleError);		
            
            $scope.addCluster = function (cluster) {
	            MonitorService.addCluster(cluster, function(data) {
	            	$scope.clusters.push(data.data);
	            	handleSuccess("Cluster successfully added");
	            }, handleError);
            }           

            // Code to remove a cluster
            $scope.deleteCluster = function (index, cluster) {
                MonitorService.deleteCluster(cluster, function (data) {
                	console.log("Deleting index: " + index);
                	if(data.status == 200) {
                    	$scope.clusters.splice(index, 1);
                    	handleSuccess("Cluster successfully deleted");
                	}
                }, handleError);
            }
        });

        app.controller('OverviewCtrl', function (MonitorService, $scope, $routeParams, $timeout, $filter ) {

            $scope.metrics = {};
            $scope.prevMetrics = {};
            $scope.cpuLoad = [[]];
            $scope.cpuLoadLabels = ['Time'];
            $scope.storageLabels = ["Free GB", "Used GB"];
            $scope.blockLabels = ["Replicated", "Unreplicated"];
            
            $scope.message = "This is a test message from angular backend";
            
            $scope.format = 'M/d/yy h:mm:ss a';

            MonitorService.getCluster($routeParams.id, function (data) { //Success handler
                
            	//Set cluster object
            	$scope.cluster = data.data;
            	             	
            	//Init websocket
            	connect($scope.cluster.id, function(data) {
                    console.log(data);
                    $scope.prevMetrics = $scope.metrics;
            		$scope.metrics = data.beans;
            		
            		$scope.freeHeap = parseFloat($scope.metrics[0].MemHeapMaxM) - parseFloat($scope.metrics[0].MemHeapUsedM) ;
            		$scope.usedHeap = parseFloat($scope.metrics[0].MemHeapUsedM);
            		
            		var blocksReplicated = parseFloat($scope.metrics[6].BlocksTotal) - parseFloat($scope.metrics[6].UnderReplicatedBlocks);
            		var blocksUnderReplicated = parseFloat($scope.metrics[6].UnderReplicatedBlocks);
            		
            		var capacityFree = parseFloat($scope.metrics[6].CapacityRemainingGB);
            		var capacityUsed = parseFloat($scope.metrics[6].CapacityUsedGB);
            		
            		//Set chart data
                    $scope.dataStorage = [capacityFree, capacityUsed];
                    $scope.blockData = [blocksReplicated, blocksUnderReplicated];
                    
            	}, handleError);
            	
            },
            handleError );
            
            
            MonitorService.getNamenodeLogTail($routeParams.id, function(data) {
            	$scope.namenodelogTail = data.data;
            }, handleError);
                        

            // Code to remove a cluster
            $scope.deleteCluster = function (cluster) {
            	MonitorService.deleteCluster(cluster, function (data) {
                	if(data.status == 200) {
                		//Redirect
                    	window.location="#/clusters";
                    	handleSuccess("Cluster successfully deleted");
                	}
                }, handleError);                        
            };
            
            // Convert a value to a %
            $scope.setWidth = function (width, max) {
            	var percent = (parseFloat(width) / parseFloat(max) * 100);
            	
            	if(percent > 0 && percent < 100) {
            		return percent + "%";
            	} else if( percent > 100) {
            		return "100%";
            	}
            }

            // Decides the color of metric bar 
            $scope.getMetricColour = function(metric) {
				
            	var value  = parseFloat(metric);
           		// Decide colour
            	if(value < 24) {
            		return "lightblue";
            	} else if (value < 49) {
            		return "aquamarine";
            	} else if (value < 74) {
            		return "orange";
            	} else if (value <= 100) {
            		return "lightcoral";
            	} else {
            		return "lightblue";
            	}
            }
            
            // Decides
           
			$scope.getCarat = function(metric, previousMetric) {
			  	var value = parseFloat(metric);
			  	var prevValue = parseFloat(previousMetric);
				if (value < prevValue ) {
			        return "fa fa-caret-down fa-3x";
			    }
			    else if (value > prevValue ) {
			    	return "fa fa-caret-up fa-3x";
				}
			    else {
			    	return "fa fa-minus fa-3x";
			    }
			}
            
            $scope.onClick = function (points, evt) {
              console.log(points, evt);
            };
            
            
            // Grab last 5 dfsio records
            // Get benchmarks            
            MonitorService.getRecentDFSIOBenchmarks($routeParams.id, function(data) {            				
				$scope.dfsioData = [[]];
				$scope.dfsioLabels = [];
				$scope.dfsioSeries = ['Total Time']
				
				angular.forEach(data.data, function(value, key) {					
					$scope.dfsioData[0].push(parseFloat(value.totalTime));
					$scope.dfsioLabels.push($filter('date')(value.date));
				});            	
            }, handleError);
            
         	// Grab last 5 mrbench records
            MonitorService.getRecentMRBenchBenchmarks($routeParams.id, function(data) {
            	$scope.mrbenchData = [[]];
				$scope.mrbenchLabels = [];
				$scope.mrBenchSeries = ['Total Time']
				
				angular.forEach(data.data, function(value, key) {						
					$scope.mrbenchData[0].push(parseFloat(value.totalTime / 1000));
					$scope.mrbenchLabels.push($filter('date')(value.date));
				});            	
            }, handleError);
                        
        });

        app.controller('JobsCtrl', function (MonitorService, $scope, $routeParams) {
            $scope.message = "This is a test message from angular backend";
            $scope.reverse = true;
			
            // Get cluster
            MonitorService.getCluster($routeParams.id, function(data) {
            	$scope.cluster = data.data;            	
            }, handleError);

            // Get jobs
            MonitorService.getJobs($routeParams.id, function(data) {
            	 $scope.jobs = data.data.apps.app;
                 console.log(data);            	
            }, handleError);

            
            ///////// JOB COMPARISON /////////
            $scope.jobComparison = [];
            
            // Compare a job
            $scope.addJobToComparison = function (index, job) {          	
            	// Check for match
            	for(var i = 0; i < $scope.jobComparison.length; i++) {	
            		if($scope.jobComparison[i] == job) {
            			// Remove if in list
            			$scope.jobComparison.splice(i, 1);
            			return true;
            		} 
            	}
            	// Else add to list
            	$scope.jobComparison.push(job);
            }
            
            $scope.hasFailed = function (job) {
            	if(job.finalStatus.trim() == "FAILED") {
            		return true;
            	} else {
            		return false;
            	}
            }
            
            $scope.isStarted = function (job) {
            	if(job.state.trim() == "ACCEPTED" || job.state.trim() == "RUNNING") {
            		return true;
            	} else {
            		return false;
            	}
            }
        });

        app.controller('DfsioCtrl', function (MonitorService, $scope, $routeParams) {
            $scope.message = "This is a test message from angular backend";
            $scope.reverse = true;
           
            // Get cluster
            MonitorService.getCluster($routeParams.id, function(data) {
            	$scope.cluster = data.data;
            	
                // to decide wether a result is bad
                $scope.isAlert = function (result) {
                    if (result.throughputMb < $scope.cluster.dfsioOptions.throughputLimit || result.avgIORate < $scope.cluster.dfsioOptions.ioLimit || result.stdDeviation > $scope.cluster.dfsioOptions.stdDeviationLimit ) {
                        return true;
                    } else {
                        return false;
                    }
                };
            }, handleError);
            

            // Get benchmarks
            MonitorService.getDFSIOBenchmarks($routeParams.id, function(data) {
            	$scope.results = data.data;
            }, handleError);
            

            $scope.deleteBenchmark = function (index, result) {
            	MonitorService.deleteDFSIOBenchmark(result, function(data) {
            		$scope.results.splice(index, 1);
            	}, handleError);
            }

            // Run benchmark on server
            $scope.runDFSIOBenchmark = function () {

                // Check if form is valid
                if ($scope.benchmarkForm.$valid) {
					MonitorService.runDFSIOBenchmark($.param(
						{
				        	id: $scope.cluster.id,
				            numFiles: $scope.form.nrFiles,
				            fileSize: $scope.form.fileSize
						}), 
						function(data) {
							// Success
	                    	$scope.results.push(data.data);	                        
	                        handleSuccess("DFSIO benchmark completed");							
					}, handleError);
						                    
                } else {
                    console.log("invalid");
                }
            };
            
            
            // Comparison
            $scope.dfsioComparison = [];
            
            // Compare a job
            $scope.addResultToComparison = function (index, result) {          	
            	// Check for match
            	for(var i = 0; i < $scope.dfsioComparison.length; i++) {	
            		if($scope.dfsioComparison[i] == result) {
            			// Remove if in list
            			$scope.dfsioComparison.splice(i, 1);
            			return true;
            		} 
            	}
            	// Else add to list
            	$scope.dfsioComparison.push(result);
            }
            
        });

        app.controller('MRBenchCtrl', function (MonitorService, $scope, $routeParams) {
            $scope.message = "This is a test message from angular backend";
            $scope.reverse = true;
            
            MonitorService.getCluster($routeParams.id, function(data) {
            	$scope.cluster = data.data;            	
            }, handleError);
            
            // Get Benchmarks
			MonitorService.getMRBenchBenchmarks($routeParams.id, function(data) {				
				$scope.results = data.data;
			}, handleError);
                     

            $scope.deleteBenchmark = function (index, result) {
            	MonitorService.deleteMRBenchBenchmark(result, function(data) {
            		$scope.results.splice(index, 1);
            	}, handleError);            	
            }
            
         	// Run benchmark on server
            $scope.runMRBench = function () {       	         	
                // Check if form is valid
                if ($scope.mrbenchForm.$valid) {

                    MonitorService.runMRBenchBenchmark($.param({
                            id: $scope.cluster.id,
                        	numRuns: $scope.form.numRuns
						}), function(data) {
	                    	$scope.results.push(data.data);	                        
	                        handleSuccess("MRBench benchmark completed");                    	
                    	}, handleError);                  
                } else {
                    console.log("invalid");
                }
            };
                     
            // Comparison
            $scope.mrbenchComparison = [];
            
            // Compare a job
            $scope.addResultToComparison = function (index, result) {          	
            	// Check for match
            	for(var i = 0; i < $scope.mrbenchComparison.length; i++) {	
            		if($scope.mrbenchComparison[i] == result) {
            			// Remove if in list
            			$scope.mrbenchComparison.splice(i, 1);
            			return true;
            		} 
            	}
            	// Else add to list
            	$scope.mrbenchComparison.push(result);
            }
            
        });

        app.controller('ConfigureCtrl', function (MonitorService, $scope, $routeParams) {
            $scope.message = "This is a test message from angular backend";

            MonitorService.getCluster($routeParams.id, function(data) {
            	$scope.cluster = data.data;                
            }, handleError);
                        
            
            $scope.updateCluster = function (cluster) {
            	
            	MonitorService.updateCluster(cluster, function(response) {
            		console.log(response);
            		handleSuccess("Cluster successfully updated");
            	}, handleError);
            	console.log(cluster);
            };
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
		
		////////// Metrics Socket //////////
		function metricsSocketCallback(data) {
			console.log(data);
		}
        
        ////////// Error Handler //////////
        function handleError(response) {
            $('.error-content').text(response.data.message);
        	$('.alert-danger').slideDown(250);
        	
        	//Close alert box after 2 seconds
        	setTimeout(function() {
        		$('.alert-danger').slideUp(250);
        	}, 5000);
        }
        
        ////////// Success Handler //////////
        function handleSuccess(message) {
            $('.success-content').text(message);
        	$('.alert-success').slideDown(250);
        	
        	//Close alert box after 2 seconds
        	setTimeout(function() {
        		$('.alert-success').slideUp(250);
        	}, 5000);
        }
        
        // Close down alert
        $('.close').click(function(e) {
        	$('.alert').slideUp(250);
        })
    </script>
</body>
</html>
