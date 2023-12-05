import {getAccessToken, getRefreshToken, isTokenExpired} from "@/assets/data/tokenStorage";
import {revokeToken} from "@/assets/data/dataHandler";
import router from "@/router";

const HttpMethod = {
    GET: "GET",
    POST: "POST",
    PUT: "PUT",
    DELETE: "DELETE",
}
export default class FetchHandler {
    constructor(url) {
        this.url = url
        this.method = HttpMethod.GET
        this.headers = {}
        this.revoke = false
    }
    header(head,data) {
        this.headers[head] = data
        return this
    }
    bearer(token) {
        if (!token) return this
        this.headers["Content-Type"] = "application/json"
        this.headers["Authorization"] = `Bearer ${token}`
        return this
    }
    authorize() {
        const token = getAccessToken()
        if (!token) return this
        this.headers["Content-Type"] = "application/json"
        this.headers["Authorization"] = `Bearer ${token}`
        this.revoke = true
        return this
    }
    post(body) {
        this.method = HttpMethod.POST
        this.headers["Content-Type"] = "application/json"
        this.body = body
        return this
    }
    content(type){
        if (type === undefined) {
            delete this.headers["Content-Type"]
        } else
        this.headers["Content-Type"] = type
        return this
    }
    put(body) {
        this.method = HttpMethod.PUT
        this.headers["Content-Type"] = "application/json"
        this.body = body
        return this
    }
    delete() {
        this.method = HttpMethod.DELETE
        return this
    }


    async response(isJson = true) {
        try {
            const options = { method: this.method }
            if (this.body) options["body"] = isJson === true ? JSON.stringify(this.body): this.body
            if (Object.keys(this.headers).length !== 0) options["headers"] = this.headers
            const response =  await fetch(this.url, options)
          
            if (this.revoke && response.status === 401) {
                const refreshToken = getRefreshToken()
                const status = await revokeToken(refreshToken)
                if (status === 404) await router.push("/announcement")
                if (!status || status === 401 || status === 403) await router.push("/login")
                else return this.authorize().response()
            }
            return response
        } catch (e) {
            return undefined
        }
    }
    json(status, callback) {
        return this.response().then((res) => {
                if (res.status === status) {
                    callback?.()
                    return res.json()
                }
                else if (res.ok) return res.json()
                else return undefined
        }).then((response) => response ?? null).catch((reason) => {
            callback?.()
            return undefined
        })
    }
}