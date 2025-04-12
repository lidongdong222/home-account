import request from "@/utils/request";

export interface User{
    userId?:number;
    usercode?:string;
    username?:string;
    phone?:string;
    password?:string;
    remark?:string;
    status?:string;
    roleIds?:Array<any>;
    qRoleIds?:string;
    qRoleNames?:string;
    viewRoleNames?:string;
    signature?:string;
}
export interface Role{
    roleId?:number;
    roleCode?:string;
    roleName?:string;
    roleDesc?:string;
    status?:string;
}
export interface Permission{
    permId?:number;
    permCode?:string;
    permType?:string;
    permUrl?:string;
    permDesc?:string;
}
export function  ApiGetUserList(query:any):Promise<any>{return request.post("/sys/User/getUserList",query);}
export function  ApiAddUser(User:User):Promise<any>{return request.post("/sys/User/addUser",User);}
export function  ApiUpdUser(User:User):Promise<any>{return request.post("/sys/User/updUser",User);}
export function  ApiDelUser(id:String):Promise<any>{return request.post("/sys/User/delUser",{id:id});}

export function  ApiGetRoleList(query:any):Promise<any>{return request.post("/sys/User/getRoleList",query);}
export function  ApiAddRole(role:Role):Promise<any>{return request.post("/sys/User/addRole",role);}
export function  ApiUpdRole(role:Role):Promise<any>{return request.post("/sys/User/updRole",role);}

export function  ApiGetPermissionList(query:any):Promise<any>{return request.post("/sys/User/getPermissionList",query);}
export function  ApiAddPermission(permission:Permission):Promise<any>{return request.post("/sys/User/addPermission",permission);}
export function  ApiUpdPermission(permission:Permission):Promise<any>{return request.post("/sys/User/updPermission",permission);}
export function  ApiDelPermission(id:String):Promise<any>{return request.post("/sys/User/delPermission",{id:id});}


export interface LoginForm{
    usercode?:string;
    password?:string;
    //是否30天免登陆
    isExempt?:boolean;
    authorization?:string|null;
}
export function  ApiGetLoginUser():Promise<any>{return request.post("/getLoginUser");}
export function  ApiLogin(param:LoginForm):Promise<any>{return request.post("/login",param);}
export function  ApiLogout():Promise<any>{return request.post("/logout",{});}

export interface PasswordForm{
    oldPass?:string;
    newPass?:string;
    newPassAgain?:string;
}
export function  ApiUpdPassword(param:PasswordForm):Promise<any>{return request.post("/updPassword",param);}


