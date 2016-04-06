angular.module('admin').controller('MRBenchCtrl', function (MonitorService, $scope, $routeParams) {
	$scope.message = "This is a test message from angular backend";
	$scope.reverse = true;

	MonitorService.getCluster($routeParams.id, function(data) {
		$scope.cluster = data.data;            	
	}, handleError);

	// Get Benchmarks
	MonitorService.getMRBenchBenchmarks($routeParams.id, function(data) {				
		$scope.results = data.data;
	}, handleError);


	$scope.deleteBenchmark = function (result) {
		MonitorService.deleteMRBenchBenchmark(result, function(data) {
			var index = $scope.results.indexOf(result);
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