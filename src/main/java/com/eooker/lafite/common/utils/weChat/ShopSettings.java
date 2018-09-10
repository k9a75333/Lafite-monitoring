package com.eooker.lafite.common.utils.weChat;

/**
 * 类说明
 * 
 * @author chenyuhao
 * @date 2017年11月7日 新建
 */
public class ShopSettings{

    private String appId;
    private String appSecret;
    private String mch_id;
    private String wxKey;
    
    public ShopSettings(){
    	this.appId = "wx7c3f53d08d679d01";
    	this.mch_id = "1464441002";
    	this.wxKey = "671833845a7fc3b51c03bdf1df066aca";
    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getWxKey() {
        return wxKey;
    }

    public void setWxKey(String wxKey) {
        this.wxKey = wxKey;
    }

}
