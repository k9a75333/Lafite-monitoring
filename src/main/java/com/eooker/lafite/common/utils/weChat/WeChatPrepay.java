package com.eooker.lafite.common.utils.weChat;


/**
 * 类说明
 * 
 * @author chenyuhao
 * @date 2017年11月7日 新建
 */
public class WeChatPrepay {
    //发送请求的URL地址
    public final static String PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public final static String NOTIFICATION_URL = "http://120.25.97.127:8080/baoxin/a/pay/weChat/interface/weChatNotify";//通知接口  写线上的接口地址
   // public final static String NOTIFICATION_URL = "http://localhost";

    //小程序ID
    private String appid;
    //商户号
    private String mch_id;
    //随机字符串
    private String nonce_str;
    //签名(字典序 + MD5)
    private String sign;
    //商品描述
    private String body;
    //商品详情
    private String detail;
    //附加数据
    private String attach;
    //商户订单号(订单号)
    private String out_trade_no;
    //总金额
    private int total_fee;
    //终端IP
    private String spbill_create_ip;
    //交易起始时间
    private String time_start;
    //交易结束时间(超时:15分钟)
    private String time_expire;
    //回调地址
    private String notify_url;
    //交易类型(JSAPI)
    private String trade_type;
    //

    public WeChatPrepay() {
        this.trade_type = "APP";
        this.notify_url = NOTIFICATION_URL;
        this.nonce_str = UUIDTool.generate();
    }


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    @Override
    public String toString() {
        return "WeChatPrepay{" +
                ", appid='" + appid + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", nonce_str='" + nonce_str + '\'' +
                ", sign='" + sign + '\'' +
                ", body='" + body + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", total_fee=" + total_fee +
                ", spbill_create_ip='" + spbill_create_ip + '\'' +
                ", time_start='" + time_start + '\'' +
                ", time_expire='" + time_expire + '\'' +
                ", notify_url='" + notify_url + '\'' +
                ", trade_type='" + trade_type + '\'' +
                '}';
    }
}
