import { formatNumber, isNumeric } from "./numberUtils";

const TIMEZONE = Intl.DateTimeFormat().resolvedOptions().timeZone

function isIsoDate(str) {
    if (typeof str === "number" || isNumeric(str)) return false
    const date = Date.parse(str);
    if (isNaN(date)) return false
    return true // valid date 
}
function humanizeDate(str) {
    if (!str) return "-"
    const localeDate = new Date(str).toLocaleString('en-US', { timeZone: TIMEZONE })
    const timeZoneDate = new Date(localeDate)
    const day = timeZoneDate.toLocaleString('default', { day: 'numeric' })
    const month = timeZoneDate.toLocaleString('default', { month: 'short' })
    const year = timeZoneDate.toLocaleString('default', { year: 'numeric' })
    const hour = formatNumber(timeZoneDate.getHours(), 2)
    const minute = formatNumber(timeZoneDate.getMinutes(), 2)
  
    return `${day} ${month} ${year}, ${hour}:${minute}`
  
}
export { isIsoDate,humanizeDate }