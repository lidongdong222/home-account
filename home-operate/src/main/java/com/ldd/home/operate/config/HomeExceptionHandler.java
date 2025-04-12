package com.ldd.home.operate.config;

import com.ldd.home.operate.common.constant.ErrorMsgConst;
import com.ldd.home.operate.common.entity.Result;
import com.ldd.home.operate.common.exception.BusinessException;
import com.ldd.home.operate.common.utils.ExceptionUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
@Slf4j
public class HomeExceptionHandler {


    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result handlerMethodArgumentNotValidException(HttpServletRequest request, Throwable ex){
        log.error(ex instanceof BusinessException?ex.getMessage():ExceptionUtil.getExceptionCause(ex));
        if(ex instanceof MethodArgumentNotValidException){
            return Result.fail(ErrorMsgConst.PARAMS_DELETION_ERROR);
        }else if(ex instanceof BusinessException){
            return Result.fail(ex.getMessage());
        }else if(ex instanceof NoResourceFoundException){
            return Result.fail404("网络资源不存在！");
        }else{
            return Result.fail(ex.getMessage());
        }
    }



}
