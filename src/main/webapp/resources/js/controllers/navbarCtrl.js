////////// Controller for the navigation bar //////////
angular.module('admin').controller('NavbarCtrl', function ($scope, $interval) {
	$scope.format = 'M/d/yy h:mm:ss a';
	$interval(function () {
		$scope.time = Date.now();
	}, 10);
	
	//Setup alerts websocket
	connectAlerts(function(data) {
		console.log("Alert received");
		handleAlert(data);
	});
});