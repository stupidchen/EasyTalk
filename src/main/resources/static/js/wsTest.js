var ws = new WebSocket("ws://localhost:8080/websocketTest");

ws.onopen = function () {
}
ws.onmessage = function (event) {
    data.rmsg = event.data;
    alert(event.data);
}

ws.onclose = function (event) {
}

ws.onerror = function (event) {
}

function sendTest() {
    var sendMsg = eval(document.getElementById('testText')).value;
    ws.send(sendMsg);
    return false;
}
