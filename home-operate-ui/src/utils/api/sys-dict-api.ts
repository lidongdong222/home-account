import request from "@/utils/request";
import BASE from "../base";

export class DictQuery{
    pageNum:number = BASE.defalutPageNum;
    pageSize:number = BASE.defalutPageSize;
    mhcx:string = BASE.mhcx;
}

export class Dict{
    dictType?:string = "";
    dictName?:string = "";
    sort?:string = "";
    status?:string = "";
    remark?:string = "";
}
export function  ApiGetDictList(query:Object):Promise<any>{return request.post("/sys/dict/getDictList",query);}
export function  ApiAddDict(dict:Dict):Promise<any>{return request.post("/sys/dict/addDict",dict);}
export function  ApiUpdDict(dict:Dict):Promise<any>{return request.post("/sys/dict/updDict",dict);}
export function  ApiDelDict(id:string):Promise<any>{return request.post("/sys/dict/delDict",{id:id});}


export class DictDtl{
    dictId?:number = 0;
    dictType?:string = "";
    dictCode?:string = "";
    dictValue?:string = "";
    sort?:string = "";
    status?:string = "";
    remark?:string = "";
}

export function  ApiGetDictByType(dictType:string):Promise<any>{return request.post("/sys/dict/getDictByType",{dictType:dictType});}
export function  ApiGetDictDtlList(id:string):Promise<any>{return request.post("/sys/dict/getDictDtlList",{id:id});}
export function  ApiAddDictDtl(dict:Dict):Promise<any>{return request.post("/sys/dict/addDictDtl",dict);}
export function  ApiUpdDictDtl(dict:Dict):Promise<any>{return request.post("/sys/dict/updDictDtl",dict);}
export function  ApiDelDictDtl(id:string):Promise<any>{return request.post("/sys/dict/delDictDtl",{id:id});}

