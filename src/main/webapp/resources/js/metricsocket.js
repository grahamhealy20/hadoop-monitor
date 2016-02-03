//WebSocket code
var stompClient = null;

//Setup Websocket
function connect() {
	var socket = new SockJS('/HadoopMon/metrics');
	stompClient = Stomp.over(socket);
	stompClient.connect('', '', function(frame) {		        	 
		console.log('Connected: ' + frame);
		stompClient.subscribe("/data/1", function(message){

			var msg = JSON.parse(message.body);
			console.log(msg);

			$("#time").text(msg.name).fadeIn();
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

function showGreeting(message) {
	var response = document.getElementById('response');
	var p = document.createElement('p');
	p.style.wordWrap = 'break-word';
	p.appendChild(document.createTextNode(message));
	response.appendChild(p);
}

connect();