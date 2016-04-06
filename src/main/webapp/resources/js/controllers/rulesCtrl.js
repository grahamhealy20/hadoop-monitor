angular.module('admin').controller('RulesCtrl', function(MonitorService, $scope, $routeParams) {
	
	MonitorService.getCluster($routeParams.id, function(data) {
		$scope.cluster = data.data;		
	}, handleError);
	
	MonitorService.getRules($routeParams.id, function(data) {
		$scope.rules = data.data;
	}, handleError);
	
	MonitorService.getMetrics(function(data) {
		$scope.metrics = data.data;
	}, handleError);
	
	
	
	$scope.rule = {
			metric: ''
	};
	
	$scope.addRule = function(rule) {
		console.log(rule);
		MonitorService.addRule($scope.cluster.id, rule, function(response) {
			$scope.rules.push(response.data);
			console.log(response.data);
			handleSuccess("Rule successfully added");
		}, handleError);
	}
	
	$scope.deleteRule = function(rule) {
		MonitorService.deleteRule($scope.cluster.id, rule, function(data) {
			var index = $scope.rules.indexOf(rule);
			$scope.rules.splice(index, 1);
			handleSuccess("Rule successfully deleted");
		}, handleError);
	}
	
	$scope.onDrop = function() {
		console.log("dropped");
	}
	
});