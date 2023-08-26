function isNumeric(str) {
    if (typeof str !== "string") return false // we only process strings!  
    return !isNaN(parseFloat(str)) && isFinite(str);
}

function formatNumber(time, places) {
    return time.toString().padStart(places, "0")
}

export { isNumeric, formatNumber }