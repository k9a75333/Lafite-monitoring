package com.eooker.lafite.common.exception;

/**
 * @author xiyatu
 * @date 2018/6/18 9:41
 * Description
 */
public enum CommonCode implements BaseCode{
    SUCCESS(200,"成功"),
    PARAM_INVALID(400,"参数异常"),
    PARAM_LACK(401,"缺少参数"),
    HTTP_METHOD_UNSUPPORTED(402,"HTTP请求方式不支持"),
    SYSTEM_ERROR(500,"系统异常"),
    ;

    private int code;
    private String desc;

    CommonCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }
    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
