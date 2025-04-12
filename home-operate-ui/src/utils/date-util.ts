import dayjs from "dayjs";

export function startDateRegion(startDate:Date,endDate:Date) {
    if (startDate > new Date()) return true;
    if (endDate && dayjs(startDate).format('YYYY-MM-DD') > dayjs(endDate).format('YYYY-MM-DD')) return true;
}
export function endDateRegion(startDate:Date,endDate:Date) {
    if (endDate > new Date()) return true;
    if (startDate && dayjs(endDate).format('YYYY-MM-DD') < dayjs(startDate).format('YYYY-MM-DD')) return true;
}