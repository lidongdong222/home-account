import request from "@/utils/request";

export interface SysConfig{
    configId?:number;
    configKey?:string;
    configValue?:string;
    configDesc?:string;
}
export function  ApiGetSysConfigList(query:any):Promise<any>{return request.post("/sys/config/getSysConfigList",query);}
export function  ApiAddSysConfig(config:SysConfig):Promise<any>{return request.post("/sys/config/addSysConfig",config);}
export function  ApiUpdSysConfig(config:SysConfig):Promise<any>{return request.post("/sys/config/updSysConfig",config);}
export function  ApiDelSysConfig(id:String):Promise<any>{return request.post("/sys/config/delSysConfig",{id:id});}

