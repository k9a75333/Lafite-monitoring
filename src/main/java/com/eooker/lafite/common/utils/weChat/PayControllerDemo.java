package com.eooker.lafite.common.utils.weChat;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharEncoding;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONObject;
import com.eooker.lafite.common.config.Global;
import com.eooker.lafite.common.utils.Result;
import com.eooker.lafite.common.utils.StringUtils;
import com.eooker.lafite.common.web.BaseController;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "${adminPath}/pay/weChat")
public class PayControllerDemo extends BaseController {

	@ResponseBody
	@RequestMapping(value = "interface/pay")
	public Result setPay(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("orderID") String orderID) {

		if (orderID == null || StringUtils.isBlank(orderID)) {
			return Result.makeFailResult();
		}
		
		WeChatPrepay weChatPrepay = new WeChatPrepay();
		ShopSettings set = new ShopSettings();
		String desc = ""; // 商品描述
		String detail = ""; // 商品详情

		String attach = ""; // 附加信息
		String orderNum = ""; // 订单号
		
		Double price = 0.01;      //!!!!测试使用!!!!!!

		String realIp = "127.0.0.1"; // 真是ip，额可以去获取，也可不用
		Calendar nowTime = Calendar.getInstance();
		Date nowDate = (Date) nowTime.getTime(); // 得到当前时间
		Calendar afterTime = Calendar.getInstance();
		afterTime.add(Calendar.MINUTE, 30); // 当前分钟+30
		Date afterDate = (Date) afterTime.getTime();
		String time_start = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(nowDate);
		String time_expire = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(afterDate);

		WxPayUtils utils = new WxPayUtils();
		weChatPrepay = utils.create(set, desc, detail, attach, orderNum, price, realIp, time_start, time_expire);
		JSONObject json = utils.pay(weChatPrepay);
		if (!CheckSign.wechatSuccessPaySignCheck(json)) {
			return Result.makeFailResult();
		}
		if(json.get("result_code").toString().equals("SUCCESS")){
				String prepay_id = json.get("prepay_id").toString();
//				bxOrder.setPaySerialNumber(prepay_id);
//				bxOrderService.save(bxOrder);
		}
		System.out.println(json);
		return Result.makeSuccessResult(json);
	}

	@ResponseBody
	@RequestMapping(value = "interface/weChatNotify")
	public void weChatNotify(HttpServletRequest request, HttpServletResponse response) {
		logger.info("<=======wechat notify======>");
		try {
			// 读取Request Body读取到StringBuilder jsonString
			String xml = IOUtils.toString(request.getInputStream(), CharEncoding.UTF_8); // 这个jar包，你们去找吧

			JSONObject jsonObject = (JSONObject) StaxonUtils.xmltoJson(xml.toString()).get("xml");
			logger.info(jsonObject.toString());
			if (!CheckSign.wechatSuccessPaySignCheck(jsonObject)) {
				response.getWriter().write(wxFalseMsg());
				return;
			}
			String returnMsg = (String) jsonObject.get("return_msg");
			if (returnMsg != null) {
				// 這裡是失敗
				// 返回xml给微信
				response.getWriter().write(wxFalseMsg());
			}
			String returnCode = jsonObject.getString("return_code");
			String resultCode = jsonObject.getString("result_code");
			if (returnCode != null && !returnCode.equals("SUCCESS")) {
				// 這裡是失敗
				// 返回xml给微信
				response.getWriter().write(wxFalseMsg());
			} else if (resultCode != null && resultCode.equals("SUCCESS")) {
				String appid = jsonObject.get("appid").toString();
				String mch_id = jsonObject.get("mch_id").toString();
				ShopSettings set = new ShopSettings();
				if (appid.equals(set.getAppId()) && mch_id.equals(set.getMch_id())) {
					String orderNum = jsonObject.get("out_trade_no").toString();
					String payMoney = jsonObject.get("total_fee").toString(); // 订单总金额，单位为分
					/**在这里写支付成功后的逻辑start**/
					
					/**在这里写支付成功后的逻辑end****/
				} else {
					// 失败的。
					response.getWriter().write(wxFalseMsg());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String wxSuccessMsg() {
		return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
	}

	private String wxFalseMsg() {
		return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
	}

}
