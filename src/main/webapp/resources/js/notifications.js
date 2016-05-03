//WebSocket code
var stompClient = null;

//Setup Websocket
function connect() {
	var socket = new SockJS('/HadoopMon/realtime');
	stompClient = Stomp.over(socket);
	stompClient.connect('', '', function(frame) {		        	 
		console.log('Connected: ' + frame);
		stompClient.subscribe("/notification/notifications/userid", function(message){

			var msg = JSON.parse(message.body);
			//console.log(msg);

			
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

//Connect to websocket
connect();