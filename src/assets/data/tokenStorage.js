
const ACCESS_TOKEN = "accessToken"
const REFRESH_TOKEN = "refreshToken"

const ROLES = ["admin","announcer"]

export function getJwt() {
    return JSON.parse(atob(getAccessToken().split('.')[1]))
}
export function getJwtSubject() {
    return getJwt()?.sub
}
function getJwtRoles() {
    return getJwt()?.aut
}
export function isEditor() {
    return getJwtRoles() && getJwtRoles().some((element) => ROLES.includes(element))
}
export function isAdmin() {
    return getJwtRoles() && getJwtRoles().includes(ROLES[0])
}
export function isAnnouncer() {
    return getJwtRoles() && getJwtRoles().includes(ROLES[1])
}


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
