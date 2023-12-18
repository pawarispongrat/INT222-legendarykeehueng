function isUniqueUser(detail,errors) {
    handleServerError(detail)
    if (!detail) return false
    
    const uniqueMessage = "does not unique"
    const unique = detail?.filter((error) => error.errorMessage === uniqueMessage).map((unique) => unique.field)
    if (unique.includes("username")) errors.value.username.push(uniqueMessage)
    if (unique.includes("name")) errors.value.name.push(uniqueMessage)
    if (unique.includes("email")) errors.value.email.push(uniqueMessage)
    return unique.length !== 0;

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
function handleServerError(serverErrors) {
    if (serverErrors) {
        serverErrors.forEach((obj) => errors.value[ obj.field ].push( obj.errorMessage ))
    } else { 
        toast.error("Error! There is something wrong with the input or server.") 
    }
}

export { isUniqueUser,isEmptyUser }