var authWebSocket;
var authStatus; //0:login 1:register

function checkAuthWebSocket() {
    if (authWebSocket == null) {
        authWebSocket = new WebSocket('ws://localhost:8080/auth');
        authWebSocket.onopen = function (event) {
            if (authStatus == 0) 
                sendAccess();
            else
                sendInfo();
        }
        authWebSocket.onmessage = function (event) {
            var receiveMsg = event.data;
            if (receiveMsg.indexOf('L:S') != -1) {
                if (token == null) {
                    token = receiveMsg.substr(4);
                    setCookie(tokenCookieName, token);
                }
                window.location.href = 'index2.html';
            }
            if (receiveMsg.indexOf('L:F') != -1) {
                alert('Email or password incorrect!');
            }
            if (receiveMsg.indexOf('R:S') != -1) {
                alert('Registration finished. Please login!');
                window.location.href = 'login.html';
            }
            if (receiveMsg.indexOf('R:F') != -1) {
                alert(receiveMsg.substr(4, receiveMsg.length));
            }
        }
        authWebSocket.onerror = function (event) {
        }
        authWebSocket.onclose = function () {
            authWebSocket = null;
        }
    }
    else {
        if (authStatus == 0)
            sendAccess();
        else
            sendInfo();       
    }
}

function sendAccess () {
    if (token == null) {
        var username = eval(document.getElementById("username")).value;
        var password = eval(document.getElementById("password")).value;
        var sendMsg = 'L:' + username + '&' + password;
        authWebSocket.send(sendMsg);
    }
}

function sendInfo () {
    var username = eval(document.getElementById("username_reg")).value;
    var email = eval(document.getElementById("email_reg")).value;
    var password = eval(document.getElementById("password_reg")).value;
    var passwordConfirm = eval(document.getElementById("password_reg_confirm")).value;
    var sendMsg = 'R:' + username + '&' + email + '&' + password + '&' + passwordConfirm;
    authWebSocket.send(sendMsg);
}

function checkLogin () {
    authStatus = 0;
    checkAuthWebSocket();
    return false;
}

function checkRegister () {
    authStatus = 1;
    checkAuthWebSocket();
    return false;
}