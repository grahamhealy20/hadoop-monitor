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
	
	// Get metrics
	MonitorService.getMetrics(function(data) {
		$scope.metrics = data.data;
		$scope.selectedMetric = $scope.metrics[0];
	}, handleError);
	
	$scope.maxValue = 100;
	
	$scope.addRow = function() {
		$scope.cluster.layout.rows.push({
			cols: []
		});
	}
	
	$scope.removeRow = function(row) {
		var index = $scope.cluster.layout.rows.indexOf(row);
		$scope.cluster.layout.rows.splice(index, 1);
	}
	
	$scope.addCol = function(row) {
		if(row.cols.length > 12) {
			handleError("Max columns is 12");
			return;
		}
		row.cols.push({
			metric: $scope.selectedMetric,
			limit: $scope.limit
		});
	}
	
	$scope.removeCol = function(row, col) {
		var index = row.cols.indexOf(col);
		row.cols.splice(index, 1);
	}
	
	$scope.getCount = function(row) {
		if(row.cols.length < 12) {
			return true;
		} else {
			return false;
		}
	}
	
	// UI settings
	$scope.layout = {
			rows: [
			       
			       ]	
				}
});