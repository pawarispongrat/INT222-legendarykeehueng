import {revokeToken} from "@/assets/data/dataHandler";
import router from "@/router";
import {Auth} from "@/assets/data/msalAuthenticate";

const ACCESS_TOKEN = "accessToken"
const REFRESH_TOKEN = "refreshToken"

const ROLES = ["admin","announcer","visitor"]

export function getJwt() {
    const token = getAccessToken()
    // console.log(decoded)
    return token && JSON.parse(atob(token?.split('.')[1]))
}
export function getJwtName() {
    return getJwt()?.name
}

export function getJwtEmail() {
    return getJwt()?.email
}
export function getJwtRoles() {
    return getJwt()?.aut ?? "visitor"
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

export function clearToken() {
    localStorage.removeItem(ACCESS_TOKEN)
    localStorage.removeItem(REFRESH_TOKEN)
}
export function setAccessToken(token, status) {
    localStorage.setItem(ACCESS_TOKEN, token)
    return status
}
export function setRefreshToken(token, status) {
    localStorage.setItem(REFRESH_TOKEN, token)
    return status
}