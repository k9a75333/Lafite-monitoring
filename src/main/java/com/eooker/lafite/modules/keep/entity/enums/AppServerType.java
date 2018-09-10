package com.eooker.lafite.modules.keep.entity.enums;

/**
 * @author xiyatu
 * @date 2018/6/18 18:47
 * Description
 */
public enum AppServerType {
    UNKNOW(-1,"未知"),
    MYSQL_SERVER(1,"Mysql"),
    TOMCAT_SERVER(2,"Tomcat"),
    NGINX_SERVER(3,"Nginx"),
    ;



    private int code;
    private String desc;


    AppServerType(int code,String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String getDescByCode(int code){
        AppServerType[] appServerTypes = AppServerType.values();
        for(AppServerType appServerType:appServerTypes){
            if(appServerType.getCode() == code){
                return appServerType.getDesc();
            }
        }
        return UNKNOW.getDesc();
    }
}
