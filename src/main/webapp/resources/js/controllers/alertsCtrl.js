angular.module('admin').controller('AlertsCtrl', function(MonitorService, $scope, $routeParams) {
	
	MonitorService.getCluster($routeParams.id, function(data) {
		$scope.cluster = data.data;		
	}, handleError);
	
	MonitorService.getAlerts($routeParams.id, function(data) {
		$scope.alerts = data.data;
	}, handleError);
	
	
});