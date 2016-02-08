//WebSocket code
var stompClient = null;

//Setup Websocket
function connect() {
	var socket = new SockJS('/HadoopMon/realtime');
	stompClient = Stomp.over(socket);
	stompClient.connect('', '', function(frame) {		        	 
		//console.log('Connected: ' + frame);
		stompClient.subscribe("/data/1", function(message){

			var msg = JSON.parse(message.body);
			var msgparsed = JSON.parse(msg.name);
			//console.log(msgparsed);
			
			// Go through each bean
			$.each(msgparsed, function(i, val) {
				var bean = val[0];
				var beanstr = JSON.stringify(bean);
				$("#metrics").text(beanstr).fadeIn();
				
				console.log(val[0].name);
				
			});
			
			
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