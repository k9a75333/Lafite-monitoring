package com.eooker.lafite.modules.keep.exception;

import com.eooker.lafite.common.exception.BaseCode;

/**
 * @author xiyatu
 * @date 2018/6/26 11:18
 * Description
 *
 * 暂定这些异常，也可从新自己定义异常类型
 *
 */
public enum KeepExceptionCode implements BaseCode {
    KEEP_EXCEPTION_CODE(501);
    int code;

    KeepExceptionCode(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return "";
    }
    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public void setDesc(String desc) {

    }
}
