
const ACCESS_TOKEN = "accessToken"
const REFRESH_TOKEN = "refreshToken"


export function authorizeToken(token) {
    return token ? {'Content-type': 'application/json', 'Authorization': `Bearer ${token}` } : {}
}

export function getAuthorizedToken() {
    return authorizeToken(getAccessToken())
}
export function getAccessToken() { return localStorage.getItem(ACCESS_TOKEN) }
export function getRefreshToken() { return localStorage.getItem(REFRESH_TOKEN) }

export function setAccessToken(accessToken, status) {
    localStorage.setItem(ACCESS_TOKEN, accessToken)
    return status
}
export function setRefreshToken(accessToken, status) {
    localStorage.setItem(REFRESH_TOKEN, accessToken)
    return status
}
