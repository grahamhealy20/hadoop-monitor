//WebSocket code
var stompClient = null;
var wait = false;

//Setup Websocket

function connect(clientId, metricsSuccessCallback, layoutSuccessCallback, errorCallback) {
	console.log("Connecting cluster: " + clientId);
	var socket = new SockJS( BASE_URL + '/realtime');
	stompClient = Stomp.over(socket);
	//Disable debug output
	stompClient.debug = null;
	stompClient.connect('', '', function(frame) {
		//Subscribe to realtime metrics
		var subscription = stompClient.subscribe("/data/" + clientId, function(message){
			console.log(message);
			var msg = JSON.parse(message.body);
			metricsSuccessCallback(msg, subscription);
		}, errorCallback);
		
		stompClient.subscribe("/data/layout/" + clientId, function(message){
			var msg = JSON.parse(message.body);
			//console.log(message);
			layoutSuccessCallback(msg);			
		}, function(error) {
			console.log("error");
		});
	}
	);
}

function unsubscribe(sub) {
	console.log("unsubscribing");
	stompClient.unsubscribe(sub);
}

//Websocket for alerts
function connectAlerts(callback) {

	//Small delay, to prevent any resource allocation problems
	setTimeout(function() {
		var socket = new SockJS( BASE_URL + '/realtime');
		stompClient = Stomp.over(socket);
		//Disable debug output
		stompClient.debug = null;
		stompClient.connect('', '', function(frame) {
			//Subscribe to alerts
			stompClient.subscribe("/alerts/", function(data) {
				var alert = JSON.parse(data.body);
				callback(alert);
			}, function(error) {
				console.log(error);
			});
		}
		);
	}, 1000);
}

function disconnectMetricWebsocket() {
	stompClient.disconnect(function() {
		console.log("Disconnected Successfully");
	});
	//console.log("Disconnected");
	stompClient = null;
}
