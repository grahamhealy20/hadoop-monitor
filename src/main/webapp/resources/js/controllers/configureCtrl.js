angular.module('admin').controller('ConfigureCtrl', function (MonitorService, $scope, $routeParams) {
	$scope.message = "This is a test message from angular backend";

	MonitorService.getCluster($routeParams.id, function(data) {
		$scope.cluster = data.data;                
	}, handleError);
	
	$scope.balanceCluster = function() {
		MonitorService.balanceCluster($routeParams.id, function(response) {
			console.log(response);
			handleSuccess("Cluster Successfully Balanced");
		}, handleError);
	};


	$scope.updateCluster = function (cluster) {

		MonitorService.updateCluster(cluster, function(response) {
			console.log(response);
			handleSuccess("Cluster successfully updated");
		}, handleError);
		console.log(cluster);
	};
});