package com.eooker.lafite.common.utils;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.aliyuncs.exceptions.ClientException;
import com.eooker.lafite.common.service.Lafite3PTService;

public class Lafite3PTUtil extends Lafite3PTService {
	private static String  bucketName = "bucketName";
	
	/**
	 * 上传文件通过Byte流上传（阿里OSS服务）
	 * @param file          文件
	 * @param bucketName    存储空间（Bucket）
	 * @param object        文件/对象（Object）
	 **/
	public void upload(CommonsMultipartFile file,String object ){
		super.uploadBy3PLService(file,bucketName,object );
	}
	
	/**
	 * 获取临时的路径URL（阿里OSS服务）
	 * @param second        有效时间（秒为单位）
	 * @param bucketName    存储空间（Bucket）
	 * @param object        文件/对象（Object）
	 * @param type          存储空间的类型（0:私有读写，1:公共读私有写）
	 * @return String 临时路径
	 **/
	public void getFileURL(CommonsMultipartFile file,String object,int second,int type ){
		super.getSTS(object, object, second, type);
	}
	
	/**
	 * 短信服务（此用的是阿里短信服务）
	 * @param phone        手机号码
	 * @return void
	 * @throws ClientException 
	 **/
	public void sendShortMess(String phone) throws ClientException{
		super.SendSmsResponse(phone);
	}
	
	/**
	 * 推送（极光推送）
	 * @param title              推送标题
	 * @param content            推送内容
	 * @param oldRegistrationId  推送手机id
	 * @return void
	 **/
	public void pushMessage(String title,String content,String oldRegistrationId){
		super.push(title, content, oldRegistrationId);
	}
}
