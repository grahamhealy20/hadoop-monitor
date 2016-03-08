angular.module('admin').controller('DfsioCtrl', function (MonitorService, $scope, $routeParams) {
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