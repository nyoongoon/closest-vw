function saveAuthToCookie(value) {
    document.cookie = `auth=${value}`;
}

function getAuthFromCookie() {
    return document.cookie.replace(/(?:(?:^|.*;\s*)auth\s*=\s*([^;]*).*$)|^.*$/, '$1');
}

export {
    saveAuthToCookie,
    getAuthFromCookie,
}