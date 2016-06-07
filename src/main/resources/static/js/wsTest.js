var ws = new WebSocket("ws://localhost:8080/websocketTest");
var result = 'aa';
ws.onopen = function () {
    result += 'Connection success: ';
    ws.send("hello");
}
ws.onmessage = function (event) {
    result += 'Message received: ' + event.data;
    ws.send("got it");
    ws.close();
}

ws.onclose = function (event) {
    result += 'Connection closed.';
}

ws.onerror = function (event) {
    result += 'Error';
}
ws.send('hi');
ws.close();
