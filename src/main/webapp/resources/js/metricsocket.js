//WebSocket code
var stompClient = null;

//Setup Websocket
function connect(id, callback) {
	var socket = new SockJS('/HadoopMon/realtime');
	stompClient = Stomp.over(socket);
	stompClient.connect('', '', function(frame) {		        	 
		//console.log('Connected: ' + frame);
		stompClient.subscribe("/data/" + id, function(message){
			
			var msg = JSON.parse(message.body);
			console.log(msg);
			
			callback(msg);
			
			//console.log(msgparsed);

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
