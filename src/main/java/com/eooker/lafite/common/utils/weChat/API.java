package com.eooker.lafite.common.utils.weChat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharEncoding;

import com.alibaba.fastjson.JSONObject;


/**
 * 类说明
 * 
 * @author HongCHUYU
 * @date 2017年11月7日 新建
 */
public class API {

	public void notify(HttpServletRequest request, HttpServletResponse response) {
		  try {
	            //读取Request Body读取到StringBuilder jsonString
	            String xml = IOUtils.toString(request.getInputStream(), CharEncoding.UTF_8); 
	            
	            JSONObject jsonObject = (JSONObject) StaxonUtils.xmltoJson(xml.toString()).get("xml");

	            String returnMsg = (String) jsonObject.get("return_msg");
	            if (returnMsg != null) {
	            	//這裡是失敗
	            	//返回xml给微信
	            	response.getWriter().write(wxFalseMsg());
	            }
	            String returnCode = jsonObject.getString("return_code");
	            String resultCode = jsonObject.getString("result_code");
	            if (returnCode != null && !returnCode.equals("SUCCESS")) {
	            	//這裡是失敗
	            	//返回xml给微信
	            	response.getWriter().write(wxFalseMsg());
	            } else if (resultCode != null && resultCode.equals("SUCCESS")) {
	            	String appid = jsonObject.get("appid").toString();
	            	String mch_id = jsonObject.get("mch_id").toString();
	            	if(appid.equals("wx7c3f53d08d679d01")&&mch_id.equals("mch_id")){
	            		  String orderNum = jsonObject.get("out_trade_no").toString();
	            		  //在这里写支付成功后的逻辑
	            		  
	            		  response.getWriter().write(wxSuccessMsg());  //这个不要删除
	            	}else{
	            		//失败的。
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
        return  "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }

}
