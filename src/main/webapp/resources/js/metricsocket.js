//WebSocket code
var stompClient = null;

//Setup Websocket
function connect(clientId, metricsSuccessCallback, errorCallback) {
	console.log("Connecting");
	var socket = new SockJS('/HadoopMon/realtime');
	stompClient = Stomp.over(socket);

	//Disable debug output
	stompClient.debug = null;
	
	stompClient.connect('', '', function(frame) {				
		stompClient.subscribe("/data/" + clientId, function(message){
			var msg = JSON.parse(message.body);
			console.log("sub 1");
			metricsSuccessCallback(msg);
		}, errorCallback);
	
	});
}

function disconnect() {
	if (stompClient != null) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}
