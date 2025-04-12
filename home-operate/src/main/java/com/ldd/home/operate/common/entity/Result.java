package com.ldd.home.operate.common.entity;

import com.ldd.home.operate.common.constant.BaseConst;
import com.ldd.home.operate.common.utils.StrUtil;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
@ToString
public class Result {
    String code;
    String msg;
    Long total;
    List list;
    Object data;
    public static Result success(String msg){
        return new Result(BaseConst.SUCCESS,msg,null,null,null);
    }
    public static Result successList(String msg,List list){
        return new Result(BaseConst.SUCCESS,msg,null, list,null);
    }

    public static Result successData(String msg,Object data){
        return new Result(BaseConst.SUCCESS,msg,null,null,data);
    }

    public static Result successPage(String msg,Long total,List list ){
        return new Result(BaseConst.SUCCESS,msg,total,list,null);
    }
    public static Result successPage(String msg,Long total,List list ,Object Data){
        return new Result(BaseConst.SUCCESS,msg,total,list,Data);
    }
    public static Result fail(String msg){
        return new Result(BaseConst.ERROR,msg,null,null,null);
    }
    public static Result fail(String code,String msg){
        return new Result(code,msg,null,null,null);
    }
    public static Result fail404(String msg){
        return fail(StrUtil.toStr(HttpStatus.NOT_FOUND.value()),msg);
    }
    public static Result fail401(String msg){
        return fail(StrUtil.toStr(HttpStatus.UNAUTHORIZED.value()),msg);
    }


}
