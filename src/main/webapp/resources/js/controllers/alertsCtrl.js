angular.module('admin').controller('AlertsCtrl', function(MonitorService, $scope, $routeParams) {
	
	MonitorService.getCluster($routeParams.id, function(data) {
		$scope.cluster = data.data;		
	}, handleError);
	
	MonitorService.getAlerts($routeParams.id, function(data) {
		$scope.alerts = data.data;
	}, handleError);
	
	$scope.deleteAlert = function(index, alert) {
		MonitorService.deleteAlert($scope.cluster.id, alert, function(data) {
			$scope.alerts.splice(index, 1);
			handleSuccess("Alert successfully deleted");
		}, handleError);
	}
	
	
});