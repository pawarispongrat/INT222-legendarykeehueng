import {revokeToken} from "@/assets/data/dataHandler";
import router from "@/router";

const ACCESS_TOKEN = "accessToken"
const REFRESH_TOKEN = "refreshToken"

const ROLES = ["admin","announcer"]

export function getJwt() {
    const token = getAccessToken()
    return token && JSON.parse(atob(token.split('.')[1]))
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

export function isTokenExpired() {
    const token = getJwt()
    return token && (token.exp * 1000 < Date.now())
}

export function getAccessToken() {
    return localStorage.getItem(ACCESS_TOKEN)
}
export function getRefreshToken() { return localStorage.getItem(REFRESH_TOKEN) }

export function setAccessToken(token, status) {
    localStorage.setItem(ACCESS_TOKEN, token)
    return status
}
export function setRefreshToken(token, status) {
    localStorage.setItem(REFRESH_TOKEN, token)
    return status
}