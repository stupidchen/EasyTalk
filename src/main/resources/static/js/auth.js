var authWebSocket;
var authStatus = -2; //0:login 1:register 2:token -1:success -2:init

function sendAuth () {
    if (authStatus == 0) sendAccess();
    if (authStatus == 1) sendInfo();
    if (authStatus == 2) sendToken();
}

function checkAuthWebSocket() {
    if (authWebSocket == null) {
        authWebSocket = new WebSocket('ws://localhost:8080/auth');
        authWebSocket.onopen = function (event) {
            sendAuth();
        }
        authWebSocket.onmessage = function (event) {
            var receiveMsg = event.data;
            if (receiveMsg.indexOf('L:S') == 0) {
                if (token == null) {
                    token = receiveMsg.substr(4);
                    setCookie(tokenCookieName, token);
                }
                authStatus = -1;
                window.location.href = 'index.html';
            }
            if (receiveMsg.indexOf('L:F') == 0) {
                alert('Email or password incorrect!');
            }
            if (receiveMsg.indexOf('R:S') == 0) {
                alert('Registration finished. Please login!');
                window.location.href = 'login.html';
            }
            if (receiveMsg.indexOf('R:F') == 0) {
                alert(receiveMsg.substr(4, receiveMsg.length));
            }
            if (receiveMsg.indexOf('T:P') == 0) {
                var loginUserId = receiveMsg.substr(4);
                authStatus = -1;
                authWebSocket.close();
            }
            if (receiveMsg.indexOf('T:F') == 0) {
                alert(receiveMsg.substr(4, receiveMsg.length));
                logout();
            }
        }
        authWebSocket.onerror = function (event) {
        }
        authWebSocket.onclose = function () {
            authWebSocket = null;
        }
    }
    else {
        sendAuth();
    }
}

function sendAccess () {
    if (token == null) {
        var username = eval(document.getElementById('username')).value;
        var password = eval(document.getElementById('password')).value;
        var sendMsg = 'L:' + username + '&' + password;
        authWebSocket.send(sendMsg);
    }
}

function sendInfo () {
    var username = eval(document.getElementById('username_reg')).value;
    var email = eval(document.getElementById('email_reg')).value;
    var password = eval(document.getElementById('password_reg')).value;
    var passwordConfirm = eval(document.getElementById('password_reg_confirm')).value;
    if (password === passwordConfirm) {
        var sendMsg = 'R:' + username + '&' + email + '&' + password + '&' + passwordConfirm;
        authWebSocket.send(sendMsg);
    }
    else {
        alert('Two password is different!');
    }
}

function sendToken() {
    authWebSocket.send('T:' + token);
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