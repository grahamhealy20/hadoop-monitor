//WebSocket code
var stompClient = null;
var connected = false;

//Setup Websocket

function connect(clientId, metricsSuccessCallback, errorCallback) {
		console.log("Connecting");
		var socket = new SockJS( BASE_URL + '/realtime');
		stompClient = Stomp.over(socket);

		//Disable debug output
		stompClient.debug = null;

		stompClient.connect('', '', function(frame) {

			//Subscribe to realtime metrics
			stompClient.subscribe("/data/" + clientId, function(message){
				var msg = JSON.parse(message.body);
				console.log("sub 1");
				metricsSuccessCallback(msg);
			}, errorCallback);
		}
	);
}

// Websocket for alerts
function connectAlerts(callback) {
	console.log("Connecting");
	var socket = new SockJS( BASE_URL + '/realtime');
	stompClient = Stomp.over(socket);

	//Disable debug output
	stompClient.debug = null;

	stompClient.connect('', '', function(frame) {

		//Subscribe to alerts
		stompClient.subscribe("/alerts/", function(data) {
			console.log("Calling Alert Callack");
			var alert = JSON.parse(data.body);
			callback(alert);
		}, function(error) {
			console.log(error);
		});
	}
);
	
}

function disconnect() {
	if (stompClient != null) {
		stompClient.disconnect();
		connected = false;
	}
	setConnected(false);
	console.log("Disconnected");
}
