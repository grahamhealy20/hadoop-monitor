angular.module('admin').controller('OverviewCtrl', function (MonitorService, $scope, $routeParams, $timeout, $filter ) {

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
			
			//console.log(data);
			$scope.prevMetrics = $scope.metrics;
			$scope.metrics = data.beans;
			
			// Loop through array and check each key value 
			for(var i = 0; i < $scope.metrics.length; i++) {
				angular.forEach($scope.metrics[i], function(value, key) {
					
				});
			}

			$scope.freeHeap = parseFloat($scope.metrics[0].MemHeapMaxM) - parseFloat($scope.metrics[0].MemHeapUsedM) ;
			$scope.usedHeap = parseFloat($scope.metrics[0].MemHeapUsedM);

			var blocksReplicated = parseFloat($scope.metrics[6].BlocksTotal) - parseFloat($scope.metrics[6].UnderReplicatedBlocks);
			var blocksUnderReplicated = parseFloat($scope.metrics[6].UnderReplicatedBlocks);

			var capacityFree = parseFloat($scope.metrics[6].CapacityRemainingGB);
			var capacityUsed = parseFloat($scope.metrics[6].CapacityUsedGB);

			//Set chart data
			$scope.dataStorage = [capacityFree, capacityUsed];
			$scope.blockData = [blocksReplicated, blocksUnderReplicated];

		}, function(data) {
			var metrics = data;
			for(var i = 0; i < metrics.length; i++) {
				
			}
			// Match up data to the layout
			
			
			console.log(data);
		}, handleError);
		
		function findPosition(metric) {
			
		}
		
		
		//  Get metric via key
		function getMetric(key) {
			console.log("finding");
			for(var i = 0; i < $scope.cluster.layout.rows; i++) {
				for(var j = 0; j < $scope.cluster.layout.rows[i].cols.length; j++) {
					if($scope.cluster.layout.rows[i].cols[j].metric.key == key) {
						console.log("Found");
						return $scope.cluster.layout.rows[i].cols[j];
					}
				}
			}
		}
	
	},
	handleError);


	MonitorService.getNamenodeLogTail($routeParams.id, function(data) {
		$scope.namenodelogTail = data.data;		
	}, handleError);
	
	MonitorService.getDatanodeLogTail($routeParams.id, function(data) {
		$scope.datanodelogTail = data.data;	
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