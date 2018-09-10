package com.eooker.lafite.common.utils.weChat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.eooker.lafite.common.utils.StringUtils;


/**
 * 微信签名验证
 * 微信:支付结果通知的内容一定要做签名验证 用于微信签名验证
 * 
 * @author yezijie
 *
 */
public class CheckSign {


	/**
	 * 支付成功后，微信回调参数签名验证
	 * 
	 * @param 微信返回到JSON串
	 * @return true代表验证正确 false代表验证失败
	 */
	public static boolean wechatSuccessPaySignCheck(JSONObject wechatJSON) {
		if (wechatJSON == null || StringUtils.isBlank(wechatJSON.toString())) {
			return false;
		}

		String sign = wechatJSON.get("sign").toString(); // 微信返回的签名，用于后续比较
		String name[] = { "return_code", "return_msg", "appid", "mch_id", "device_info", "nonce_str", "sign_type",
				"result_code", "err_code", "err_code_des", "openid", "is_subscribe", "trade_type", "bank_type",
				"total_fee", "settlement_total_fee", "fee_type", "cash_fee", "cash_fee_type", "coupon_fee",
				"coupon_count", "coupon_type_$n", "coupon_id_$n", "coupon_fee_$n", "transaction_id", "out_trade_no",
				"attach", "time_end","prepay_id"};
		List<String> wechatList = new ArrayList<String>();
		String eacheValue;
		for (int i = 0; i < name.length; i++) {
			try {
				eacheValue = wechatJSON.get(name[i].trim()).toString();

				if (eacheValue != null && !eacheValue.equals("")) {
					wechatList.add(name[i] + "=" + eacheValue);
				}
			} catch (Exception e) {
				continue;
			}
		}
		String str[] = new String[wechatList.size()];
		for (int i = 0; i < wechatList.size(); i++) {
			str[i] = wechatList.get(i);
		}
		Arrays.sort(str);
		String sortStr = "";
		for (String temp : str) {
			sortStr += temp + "&";

		}
		
		ShopSettings shopSettings = new ShopSettings();
		sortStr = sortStr + "key=" + shopSettings.getWxKey();
		String MD5SortStr = Cryptography.md5Encode(sortStr).toUpperCase();
		if (MD5SortStr.equals(sign)) {
			return true;
		} else {
			return false;
		}
	}
}
