function checkToken() {
    getTokenCookie();
    if (token == null) {
        window.location.href = 'login.html';
    }
}

function logout() {
    deleteCookie(tokenCookieName);
    window.location.href = 'index2.html';
}
