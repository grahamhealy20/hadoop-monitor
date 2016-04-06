//WebSocket code
var stompClient = null;

//Setup Websocket
function connect(clientId, metricsSuccessCallback, alertSuccessCallback, errorCallback) {
	console.log("Connecting");
	var socket = new SockJS('/HadoopMon/realtime');
	stompClient = Stomp.over(socket);

	//Disable debug output
	stompClient.debug = null;
	
	stompClient.connect('', '', function(frame) {				
		stompClient.subscribe("/data/" + clientId, function(message){
			var msg = JSON.parse(message.body);
			//console.log("sub 1");
			metricsSuccessCallback(msg);
		}, errorCallback);
		
		stompClient.subscribe("/alerts/", function(message) {
			console.log("Calling Alert Callack");
			var msg = JSON.parse(message.body);
			alertSuccessCallback(msg);
		}, function(error) {
			console.log(error);
		});

	
	});
}

function disconnect() {
	if (stompClient != null) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}
