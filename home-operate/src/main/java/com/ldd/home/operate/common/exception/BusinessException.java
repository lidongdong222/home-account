package com.ldd.home.operate.common.exception;

/**
 * 业务异常
 */
public class BusinessException  extends Exception {

    public BusinessException(String errMsg) {
        super(errMsg);
    }

    public BusinessException() {
        super();
    }
}
