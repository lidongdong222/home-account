import request from "@/utils/request";

export interface Menu{
    menuId?:number;
    parentId?:number;
    menuCode?:string;
    menuName?:string;
    router?:string;
    sort?:string;
    icon?:string ;
    status?:string;
    remark?:string;
    children?:Array<any>;
}
export interface SortReq{
    menuId?:number,
    sortType?:number
}
export function  ApiGetMenuList(query:any):Promise<any>{return request.post("/sysMenu/getMenuList",query);}
export function  ApiAddMenu(menu:Menu):Promise<any>{return request.post("/sysMenu/addMenu",menu);}
export function  ApiUpdMenu(menu:Menu):Promise<any>{return request.post("/sysMenu/updMenu",menu);}
export function  ApiDelMenu(id:string):Promise<any>{return request.post("/sysMenu/delMenu",{id:id});}
export function  ApiSortMenu(param:SortReq):Promise<any>{return request.post("/sysMenu/sortMenu",param);}

