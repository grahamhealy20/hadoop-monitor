angular.module('admin').controller('RulesCtrl', function(MonitorService, $scope, $routeParams) {
	
	MonitorService.getCluster($routeParams.id, function(data) {
		$scope.cluster = data.data;		
	}, handleError);
	
	MonitorService.getRules($routeParams.id, function(data) {
		$scope.rules = data.data;
	}, handleError);
	
	$scope.metrics = [{
	                	  id: 1,
	                	  name: "CPU"
	                  },
	                  {
	                	  id: 2,
	                	  name: "Heap Used"
	                  },
	                  {
	                	  id: 3,
	                	  name: "Open Connections"
	                  },
	                  {
	                	id: 4,
	                	name: "MemMaxM"
	                  },
	                  {
	                	  id: 5,
	                	  name: "MemHeapUsedM"
	                  }];
	
	$scope.rule = {
			metric: ''
	};
	
	$scope.addRule = function(rule) {
			
		MonitorService.addRule($scope.cluster.id, rule, function(response) {
			$scope.rules.push(response.data);
			console.log(response.data);
			handleSuccess("Rule successfully added");
		}, handleError);
	}
	
	$scope.onDrop = function() {
		console.log("dropped");
	}
	
});