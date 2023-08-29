package com.jiawa.mytrain.common.exception;

public class BusinessException extends RuntimeException{

    private BusinessExceptionEnum e;

    public BusinessException(BusinessExceptionEnum e) {
        this.e = e;
    }

    public BusinessExceptionEnum getE() {
        return e;
    }

    public void setE(BusinessExceptionEnum e) {
        this.e = e;
    }

    // 重写 RuntimeException 的返回堆栈信息函数
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
