import request from "@/utils/request";

export interface Schedule{
    scheId?:number;
    jobId?:string;
    groupId?:string;
    cron?:string;
    className?:string;
    highFrequency?:string,
    status?:string;
    remark?:string;
}
export function  ApiGetScheduleList(query:any):Promise<any>{return request.post("/sys/schedule/getScheduleList",query);}
export function  ApiAddSchedule(Schedule:Schedule):Promise<any>{return request.post("/sys/schedule/addSchedule",Schedule);}
export function  ApiUpdSchedule(Schedule:Schedule):Promise<any>{return request.post("/sys/schedule/updSchedule",Schedule);}
export function  ApiDelSchedule(id:String):Promise<any>{return request.post("/sys/schedule/delSchedule",{id:id});}
export function  ApiExecImmediateSchedule(id:String):Promise<any>{return request.post("/sys/schedule/execImmediateSchedule",{id:id});}

