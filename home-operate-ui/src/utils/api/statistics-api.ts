import request from "@/utils/request";

export interface StatisticsData{
    title?:string;
    sheets?:Array<Sheet>
}
export interface Sheet{
    title?:string;
    headers?:Array<any>;
    leftHeaders?:Array<any>;
    data?:Array<any>;
}
export function  ApiGetStatistics(query:any):Promise<any>{return request.post("/report/getReportInfo",query);}

