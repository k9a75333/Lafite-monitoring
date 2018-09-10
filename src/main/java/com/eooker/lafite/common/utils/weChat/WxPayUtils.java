package com.eooker.lafite.common.utils.weChat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;




/**
 * 类说明
 * 
 * @author chenyuhao
 * @date 2017年11月7日 新建
 */
public class WxPayUtils {
	public WeChatPrepay create(ShopSettings shopSettings, String desc,
			String detail, String attach, String orderNum, Double price,
			String realIp, String time_start, String time_expire) {
		WeChatPrepay prepay = new WeChatPrepay();
		prepay.setAppid(shopSettings.getAppId());
		prepay.setMch_id(shopSettings.getMch_id());
		prepay.setBody(desc);
		prepay.setDetail(detail);
		prepay.setAttach(attach);
		prepay.setOut_trade_no(orderNum);
		prepay.setTotal_fee((int) (price * 100));
		prepay.setSpbill_create_ip("123.12.12.123");
		prepay.setTime_start(time_start);
		prepay.setTime_expire(time_expire);

		String[] str = { "appid=" + prepay.getAppid(),
				"mch_id=" + prepay.getMch_id(),
				"nonce_str=" + prepay.getNonce_str(),
				"body=" + prepay.getBody(), "detail=" + prepay.getDetail(),
				"attach=" + prepay.getAttach(),
				"out_trade_no=" + prepay.getOut_trade_no(),
				"total_fee=" + prepay.getTotal_fee(),
				"spbill_create_ip=" + prepay.getSpbill_create_ip(),
				"time_start=" + prepay.getTime_start(),
				"time_expire=" + prepay.getTime_expire(),
				"notify_url=" + prepay.getNotify_url(),
				"trade_type=" + prepay.getTrade_type(), };
		Arrays.sort(str);
		String sortStr = "";
		int i = 0;
		for (String temp : str) {
			sortStr += temp + "&";

		}
		sortStr = sortStr + "key=" + shopSettings.getWxKey();

		prepay.setSign(Cryptography.md5Encode(sortStr).toUpperCase());
		System.out.println(prepay.toString());
		return prepay;
	}

	public JSONObject pay(WeChatPrepay weChatPrepay) {
		String xml = "<xml>\n"
				+ StaxonUtils.jsontoxml((JSONObject) JSONObject
						.toJSON(weChatPrepay)) + "</xml>";
		System.out.println("jsontoxml-->"+xml);
		String data = HttpsUtils.httpsRequestString(WeChatPrepay.PAY_URL,
				"POST", xml);
		System.out.println("xmltojson-->"+StaxonUtils.xmltoJson(data).get("xml"));
		return (JSONObject) StaxonUtils.xmltoJson(data).get("xml");
	}

	public static void main(String args[]) {
		WeChatPrepay weChatPrepay = new WeChatPrepay();
		ShopSettings set = new ShopSettings();
		String desc = "test"; // 商品描述
		String detail = "test"; // 商品详情
		String attach = "123"; // 附加信息
		String orderNum = "2017111321114183484"; // 订单号
		Double price = 1.0; // 价格 单位元，可以是1.02元
		String realIp = "127.0.0.1"; // 真是ip，额可以去获取，也可不用
//		String time_start = "201711081010"; // 开始时间
//		String time_expire = "201711091010"; // 结束时间
		Calendar nowTime = Calendar.getInstance();   
	    Date nowDate = (Date) nowTime.getTime(); //得到当前时间  
	    Calendar afterTime = Calendar.getInstance();   
	    afterTime.add(Calendar.MINUTE, 30); //当前分钟+30
	    Date afterDate = (Date) afterTime.getTime();  
	    String time_start = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(nowDate);
	    String time_expire = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(afterDate);
		WxPayUtils utils = new WxPayUtils();
		weChatPrepay = utils.create(set, desc, detail, attach, orderNum, price,
				realIp, time_start, time_expire);
		JSONObject json = utils.pay(weChatPrepay);
		System.out.println(json.toString());
	}
}
