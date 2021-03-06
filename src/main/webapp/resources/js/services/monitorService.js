// Service to interact with REST service
angular.module('admin').service('MonitorService', function($http) {

	///////// CLUSTERS /////////

	// Get all clusters
	this.getClusters = function(successCallback, errorCallback) {
		// Do a request then call specified callback funtions
		$http.get(BASE_URL + "/cluster/clusters").then(successCallback, errorCallback);
	};

	// get a cluster given an id
	this.getCluster = function(id, successCallback, errorCallback) {
		$http.get(BASE_URL + "/cluster/cluster/" + id).then(successCallback, errorCallback);
	};

	this.addCluster = function(cluster, successCallback, errorCallback) {
		$http.post(BASE_URL + "/cluster/add", cluster).then(successCallback, errorCallback);
	};

	this.updateCluster = function(cluster, successCallback, errorCallback) {
		$http.put(BASE_URL + "/cluster/edit", cluster).then(successCallback, errorCallback);
	};

	this.deleteCluster = function (cluster, successCallback, errorCallback) {
		console.log("Deleting cluster");
		$http.post(BASE_URL + "/cluster/delete", cluster).then(successCallback, errorCallback);
	};

	this.getNamenodeLogTail = function(id, successCallback, errorCallback) {
		$http.get(BASE_URL + "/cluster/log/namenode/" + id + "/tail").then(successCallback, errorCallback);
	};

	this.getDatanodeLogTail = function(id, successCallback, errorCallback) {
		$http.get(BASE_URL + "/cluster/log/datanode/" + id + "/tail").then(successCallback, errorCallback);
	};
	
	/////// GET METRIC ///////
	
	this.getMetricFromKey = function(id, key, successCallback, errorCallback) {
		$http.get(BASE_URL + "/metrics/" + id + "/" + key).then(successCallback, errorCallback);
	}
	
	/////// RULES /////////
	
	this.getRules = function(id, successCallback, errorCallback) {
		$http.get(BASE_URL + "/cluster/rules/" + id).then(successCallback, errorCallback);
	}
	
	this.addRule = function(id, rule, successCallback, errorCallback) {
		$http.post(BASE_URL + "/cluster/rules/" + id, rule).then(successCallback, errorCallback);
	}
	
	this.deleteRule = function(id, rule, successCallback, errorCallback) {		
		$http.post(BASE_URL + "/cluster/rules/" + id + "/delete", rule).then(successCallback, errorCallback);
	}
	
	this.balanceCluster = function(id, successCallback, errorCallback) {
		$http.get(BASE_URL + "/cluster/" + id + "/balance").then(successCallback, errorCallback);
	}
	
	/////// ALERTS /////////
	
	this.getAlerts = function(id, successCallback, errorCallback) {
		$http.get(BASE_URL + "/cluster/alerts/" + id).then(successCallback, errorCallback);
	}
	
	this.deleteAlert = function(id, alert, successCallback, errorCallback) {
		$http.post(BASE_URL + "/cluster/alerts/" + id + "/delete", alert).then(successCallback, errorCallback);
	}
	
	this.deleteAllAlerts = function(id, successCallback, errorCallback) {
		$http.post(BASE_URL + "/cluster/alerts/" + id + "/delete/all").then(successCallback, errorCallback);
	}
	
	

	///////// JOBS /////////

	this.getJobs = function(id, successCallback, errorCallback) {
		$http.get(BASE_URL + "/cluster/jobs/" + id).then(successCallback, errorCallback);
	};

	///////// DFSIO /////////

	this.getRecentDFSIOBenchmarks = function(id, successCallback, errorCallback) {
		$http.get(BASE_URL + "/dfsio/benchmarks/last/" + id).then(successCallback, errorCallback);
	};

	this.getDFSIOBenchmarks = function(id, successCallback, errorCallback) {
		$http.get(BASE_URL + "/dfsio/benchmarks/" + id).then(successCallback, errorCallback);
	};

	this.deleteDFSIOBenchmark = function(benchmark, successCallback, errorCallback) {
		$http.post(BASE_URL + "/dfsio/delete/" + benchmark.id, benchmark).then(successCallback, errorCallback);
	};

	this.runDFSIOBenchmark = function(data, successCallback, errorCallback) {
		$http.post(BASE_URL + "/dfsio/dfsio", data, {
			headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
		}).then(successCallback, errorCallback);
	};

	///////// MRBENCH /////////

	this.getRecentMRBenchBenchmarks = function(id, successCallback, errorCallback) {
		$http.get(BASE_URL + "/mrbench/benchmarks/last/" + id).then(successCallback, errorCallback);
	};

	this.getMRBenchBenchmarks = function(id, successCallback, errorCallback) {
		$http.get(BASE_URL + "/mrbench/benchmarks/" + id).then(successCallback, errorCallback);
	};

	this.deleteMRBenchBenchmark = function(benchmark, successCallback, errorCallback) {
		$http.post(BASE_URL + "/mrbench/delete/" + benchmark.id, benchmark).then(successCallback, errorCallback);
	};

	this.runMRBenchBenchmark = function(data, successCallback, errorCallback) {
		$http.post(BASE_URL + "/mrbench/mrbench", data, {
			headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
		}).then(successCallback, errorCallback);
	};
	
	////////// SETTINGS /////////
	
	this.addMetric = function(data, successCallback, errorCallback) {		
		$http.post(BASE_URL + "/settings/metric", data).then(successCallback, errorCallback);
	}
	
	this.getMetrics = function(successCallback, errorCallback) {
		$http.get(BASE_URL + "/settings/metrics").then(successCallback, errorCallback);
	}
	
	this.deleteMetric = function(data, successCallback, errorCallback) {
		console.log(data);
		$http.post(BASE_URL + "/settings/metric/delete", data).then(successCallback, errorCallback);
	}
});