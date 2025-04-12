import request from "@/utils/request";

export interface RepCloumn{
    colId?:number;
    headerIndex?:number ;
    colIndex?:number ;
    colName?:string ;
    colDataType?:string;
    colProp?:string;
    row_span?:number;
    col_span?:number;
    width?:number;
    isNumberFormat?:string;
}
export function  ApigetRepColumns(queryForm:any):Promise<any>{return request.post('/report/getRepColumns',queryForm);}
export function  ApiExport(queryForm:any):Promise<any>{return request.post('/report/export',queryForm);}
