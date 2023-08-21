import { formatDateString,getDateTime } from "../utils"

class User {

    constructor(name,username,email,role,createdOnDate,createdOnTime,updateOnDate,updateOnTime,id) {
        this.id = id ?? 0
        this.name = name ?? ''
        this.username = username ?? ''
        this.email = email ?? ''
        this.role = role ?? ''
        this.createdOnDate = createdOnDate ?? ''
        this.createdOnTime = createdOnTime ?? ''
        this.updateOnDate = updateOnDate ?? ''
        this.updateOnTime = updateOnTime ?? ''
    }
    getCreatedOn() { return getDateTime(this.createdOnDate,this.createdOnTime) }
    getUpdateOn() { return getDateTime(this.updateOnDate,this.updateOnTime) }
    getUTCCreatedOn() { return this.getCreatedOn() ? getCreatedOn().toISOString() : null }
    getUTCUpdateOn() { return this.getUpdateOn() ? this.getUpdateOn().toISOString() : null}
    
    
    fromJSON(user) {
        const create = formatDateString(user.createdOn)?.split(' ')
        const update = formatDateString(user.updateOn)?.split(' ')
        this.id = user.id
        this.username = user.username
        this.name = user.name
        this.email = user.email
        this.createdOnDate = create?.[0] ?? null
        this.createdOnTime = create?.[1] ?? null
        this.updateOnDate = update?.[0] ?? null
        this.updateOnTime = update?.[1] ?? null
        this.role = user.role
        return this
    }
    toJSON() {
        return {
            id: this.id,
            username: this.username,
            name: this.name,
            email:this.email,
            createdOn: this.getUTCCreatedOn(),
            updateOn: this.getUTCUpdateOn(),
            role: this.role
        }
    }
}
export default User;