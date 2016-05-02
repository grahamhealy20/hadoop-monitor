angular.module('admin').controller('SettingsCtrl', function (MonitorService, $scope) {
	$scope.message = "This is a test message from angular backend";
	$scope.reverse = true;	

	$scope.metrics = [];
	
	MonitorService.getMetrics(function(data) {
		$scope.metrics = data.data;
	}, handleError);
	
	$scope.addMetric = function (metric) {
		MonitorService.addMetric(metric, function(data) {
			$scope.metrics.push(data.data);
			handleSuccess("Metric successfully added");
		}, handleError);
	}           

	// Code to remove a cluster
	$scope.deleteMetric = function (metric) {
		MonitorService.deleteMetric(metric, function (data) {
			console.log("Deleting index: " + index);
			if(data.status == 200) {
				var index = $scope.metrics.indexOf(metric);
				$scope.metrics.splice(index, 1);
				handleSuccess("Metric successfully deleted");
			}
		}, handleError);
	}
});