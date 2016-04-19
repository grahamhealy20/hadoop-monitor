angular.module('admin').controller('AlertsCtrl', function(MonitorService, $scope, $routeParams) {
	
	$scope.reverse = true;
	
	
	MonitorService.getCluster($routeParams.id, function(data) {
		$scope.cluster = data.data;		
	}, handleError);
	
	MonitorService.getAlerts($routeParams.id, function(data) {
		$scope.alerts = data.data;
	}, handleError);
	
	$scope.deleteAlert = function(alert) {
		MonitorService.deleteAlert($scope.cluster.id, alert, function(data) {
			var index = $scope.alerts.indexOf(alert);
			$scope.alerts.splice(index, 1);
			handleSuccess("Alert successfully deleted");
		}, handleError);
	}
	
	$scope.deleteAllAlerts = function() {
		MonitorService.deleteAllAlerts($scope.cluster.id, function(data) {
			$scope.alerts.length = 0;
			handleSuccess("All alerts successfully deleted");
		}, handleError)
	}
	
	
});