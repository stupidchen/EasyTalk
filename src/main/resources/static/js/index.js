function checkToken() {
    getTokenCookie();
    if (token != null) {
        authStatus = 2;
        checkAuthWebSocket();
    }
    else {
        window.location.href = 'login.html';
    }
}

function logout() {
    deleteCookie(tokenCookieName);
    loginUserId = null;
    window.location.href = 'login.html';
}

