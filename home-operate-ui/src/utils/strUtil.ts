const strUtil = {
    lpad: function(str:string,len:number,fill:string){
        return new Array(len - str.length + 1).join(fill) + str;
    },
    rpad:function (str:string,len:number,fill:string){
        return str+new Array(len - str.length + 1).join(fill);
    },
    isEmpty:function(o:any){
        if(o==undefined || o==null || o===""){
            return true;
        }
        return false;
    }
  }
export default strUtil;