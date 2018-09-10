package com.eooker.lafite.common.exception;

/**
 * @author xiyatu
 * @date 2018/6/18 9:30
 * Description
 */
public class LafiteException extends Exception {
    private BaseCode baseCode;
    private String msg;


    public BaseCode getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(BaseCode baseCode) {
        this.baseCode = baseCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LafiteException(BaseCode baseCode) {
        this.baseCode = baseCode;
        this.msg = baseCode.getDesc();
    }

    public LafiteException(BaseCode baseCode, String msg) {
        this.baseCode = baseCode;
        this.msg = msg;
    }

    public LafiteException(String message, BaseCode baseCode, String msg) {
        super(message);
        this.baseCode = baseCode;
        this.msg = msg;
    }

    public LafiteException(String message, Throwable cause, BaseCode baseCode, String msg) {
        super(message, cause);
        this.baseCode = baseCode;
        this.msg = msg;
    }

    public LafiteException(Throwable cause, BaseCode baseCode, String msg) {
        super(cause);
        this.baseCode = baseCode;
        this.msg = msg;
    }

    public LafiteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, BaseCode baseCode, String msg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.baseCode = baseCode;
        this.msg = msg;
    }
}
