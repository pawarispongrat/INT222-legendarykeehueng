function isUniqueUser(detail,errors) {
    if (!detail) return false
    const uniqueMessage = "does not unique"
    const unique = detail?.filter((error) => error.errorMessage === uniqueMessage).map((unique) => unique.field)
    if (unique.includes("username")) errors.value.username.push(uniqueMessage)
    if (unique.includes("name")) errors.value.name.push(uniqueMessage)
    if (unique.includes("email")) errors.value.email.push(uniqueMessage)
    if (unique.length === 0) return false
    return true
}
function isEmptyUser(user,errors) {
    const username = user.value?.username
    const email = user.value?.email
    const name = user.value?.name
    if (username.length === 0) errors.value.username.push("Please fill your username.")
    if (email.length === 0) errors.value.email.push("Please fill your email.")
    if (name.length === 0) errors.value.name.push("Please fill your name.")
    return Object.values(errors.value).every((error) => error?.length === 0) // check is all empty #no errors
}

export { isUniqueUser,isEmptyUser }