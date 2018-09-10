package com.eooker.lafite.common.exception;

/**
 * @author xiyatu
 * @date 2018/6/18 11:26
 * Description
 */
public class ExceptionMessage {

    private int code;
    private String msg;
    private Exception exception;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
