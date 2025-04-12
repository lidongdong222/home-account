export function lpad(str:string,len:number,fill:string){
    return new Array(len - str.length + 1).join(fill) + str;
}
export function rpad(str:string,len:number,fill:string){
    return str+new Array(len - str.length + 1).join(fill);
}