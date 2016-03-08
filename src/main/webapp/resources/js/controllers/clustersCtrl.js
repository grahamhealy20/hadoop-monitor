angular.module('admin').controller('ClustersCtrl', function (MonitorService, $scope, $routeParams) {
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