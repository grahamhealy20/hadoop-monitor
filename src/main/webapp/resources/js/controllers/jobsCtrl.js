angular.module('admin').controller('JobsCtrl', function (MonitorService, $scope, $routeParams) {
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