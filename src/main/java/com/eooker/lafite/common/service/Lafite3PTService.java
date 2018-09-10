package com.eooker.lafite.common.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.aliyuncs.exceptions.ClientException;
import com.eooker.lafite.common.utils.DateUtils;
import com.eooker.lafite.common.utils.aliService.AliOSSUtil;
import com.eooker.lafite.common.utils.aliService.AliShortMessageUtil;
import com.eooker.lafite.common.utils.jiGuangPush.JPushUtils;

import net.sf.json.JSONObject;

public abstract class Lafite3PTService {
	
	/**
	 * 上传文件通过Byte流上传（阿里OSS服务）
	 * @param file          文件
	 * @param bucketName    存储空间（Bucket）
	 * @param object        文件/对象（Object）
	 **/
	public static boolean uploadBy3PLService(CommonsMultipartFile file,String bucketName,String object) {
		return AliOSSUtil.uploadByByte(file, bucketName, object);
	}
	
	/**
	 * 获取临时的路径URL（阿里OSS服务）
	 * @param second        有效时间（秒为单位）
	 * @param bucketName    存储空间（Bucket）
	 * @param object        文件/对象（Object）
	 * @param type          存储空间的类型（0:私有读写，1:公共读私有写）
	 * @return String 临时路径
	 **/
	public static String getSTS(String bucketName,String object,int second,int type){
		String URL = AliOSSUtil.getSTS(bucketName, object, second,type);
		return URL;
	}
	
	/**
	 * 短信服务（此用的是阿里短信服务）
	 * @param phone        手机号码
	 * @return void
	 * @throws ClientException 
	 **/
	public static void SendSmsResponse(String phone) throws ClientException{
		AliShortMessageUtil.sendSms(phone);
	}
	
	/**
	 * 直播（暂时还无）
	 * @param 无
	 * @return void
	 **/
	public static void live(){
		
	}
	
	/**
	 * 推送（极光推送）
	 * @param title        推送标题
	 * @param content      推送内容
	 * @param oldRegistrationId  推送手机id
	 * @return void
	 **/
	public static void push(String title,String content,String oldRegistrationId){
		JPushUtils.pushAlertByRegistrationId( title, oldRegistrationId);
		JPushUtils.pushMessageByRegistrationId(content,oldRegistrationId);
	}
}
