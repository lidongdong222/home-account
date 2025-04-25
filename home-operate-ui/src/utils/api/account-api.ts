import request from "@/utils/request";

export const ApiExportUrl="/account/exportAccountList";
export interface Account{
    accId?:number;
    accPeriod?:string;
    subType?:string;
    subCode?:string ;
    subId?:string ;
    subName?:string;
    amount?:number;
    accDate?:string ;
    paymentType?:string;
    digest?:string ;
}
export function  ApiGetAccountList(queryForm:any):Promise<any>{return request.post("/account/getAccountList",queryForm);}
export function  ApiAddAccountConfirm(account:Account):Promise<any>{return request.post("/account/addAccountConfirm",account);}
export function  ApiAddAccount(account:Account):Promise<any>{return request.post("/account/addAccount",account);}
export function  ApiUpdAccount(account:Account):Promise<any>{return request.post("/account/updAccount",account);}
export function  ApiDelAccount(id:string):Promise<any>{return request.post("/account/delAccount",{id:id});}
export function  ApiImportWxBill(file:any,repParam:any):Promise<any>{
    const formData = new FormData();
    formData.append("file",file);
    for(const key in repParam) {
      formData.append(key,repParam[key]);
    }
    return request({
      url: "/account/importWxBill",
      method: "post",
      data: formData,
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  }
export function  ApiImportWxBillData(params:any):Promise<any>{return request.post("/account/importWxBillData",params);}
export function  ApiAddWxMatchRule(params:any):Promise<any>{return request.post("/account/addWxMatchRule",params);}
export function  ApiGetWxMatchRule():Promise<any>{return request.post("/account/getWxMatchRuleList");}
export function  ApiMatchSubjectByRule(params:any):Promise<any>{return request.post("/account/matchSubjectByRule",params);}


export interface Subject{
    subId?:string;
    subType?:string;
    subCode?:string;
    subName?:string;
    subDesc?:string;
    sort?:number;
    createDate?:string;
}
export const apiExporSubjecttUrl="/account/subject/exporSubjectList";
export function  ApiGetSubjectList(query:any):Promise<any>{return request.post("/account/subject/getSubList",query);}
export function  ApiAddSubject(subject:Subject):Promise<any>{return request.post("/account/subject/addSubInfo",subject);}
export function  ApiUpdSubject(subject:Subject):Promise<any>{return request.post("/account/subject/updSubinfo",subject);}
export function  ApiDelSubject(id:string):Promise<any>{return request.post("/account/subject/delSubinfo",{id:id});}

