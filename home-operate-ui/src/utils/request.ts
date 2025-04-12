//对于axios二次封装
//二次封装的目的  1.利用axios的请求和响应拦截器  2.简化服务器返回的数据，简化服务器返回的错误 4.配置超时时间
import axios, { AxiosHeaders } from "axios";
import router from "@/router";

const loginPath = "/login";

function getHeaders(){
    const authorization = localStorage.getItem("authorization");
    const headers:AxiosHeaders = new AxiosHeaders();
    // headers.set("Content-Type","application/json;charset=utf-8");
    if(authorization){
        headers.set("Authorization",authorization);
    }
    return headers;
}
// 利用axios.create 方法创建axios实例
const request = axios.create({
    baseURL:import.meta.env.VITE_APP_BASE_API,
    timeout: 50000, //超时时间
})

//请求拦截器
request.interceptors.request.use(config=>{
    // config请求拦截器注入的对象
    config.headers=getHeaders();
    return config;
})
request.interceptors.response.use((response:any)=>{
    
    if(response.data instanceof ArrayBuffer){
        return response;
    }
    // 成功回调
    const { code, msg } = response.data;
    if(code=="401"){
        sessionStorage.clear();
        if(router.currentRoute.value.fullPath!=loginPath ){
            router.push("/login")
        }
    }else if(code != "200") {
        if(!(router.currentRoute.value.fullPath==loginPath)){
            ElMessage.error(msg);
        }
    }
    const authorization = response.headers.get("Authorization");
    if(authorization){
        localStorage.setItem("authorization",authorization);
    }
    return response.data;
},(error)=>{
    // 异常回调
    return Promise.reject(new Error(error.message));
})

export default request;