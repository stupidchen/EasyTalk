var token;
var tokenCookieName = 'etLogin';

function getCookie (name) {
    if (document.cookie.length > 0) {
        var start = document.cookie.indexOf(name + '=');
        if (start != -1) {
            var end = document.cookie.indexOf(";", start);
            if (end == -1) end = document.cookie.length;
            return document.cookie.substring(start, end);
        }
    }
    return null;
}

function setCookie (name, value) {
    document.cookie = name + '=' + value + ';';
}

function deleteCookie (name) {
    var expireTime = new Date();
    expireTime.setTime(expireTime - 1);
    var cookieVal = getCookie(name);
    document.cookie = name + '=' + cookieVal + ';expires=' + expireTime.toUTCString();
}

function getTokenCookie() {
    token = getCookie(tokenCookieName);
}

function redirectIfLogin() {
    if (token == null) getTokenCookie();
    
    if (token != null) {
        alert('Please first logout before login again!');
        window.location.href = 'index2.html';
    }
}


