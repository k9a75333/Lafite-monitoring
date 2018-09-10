package com.eooker.lafite.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @author xiyatu
 * @date 2018/7/1 18:44
 * Description
 */
public class CommonResponse<T> {

    private int code;
    private String message;
    private T result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public CommonResponse(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public CommonResponse(T data) {
        this.code = CommonCode.SUCCESS.getCode();
        this.result = data;
    }
}
