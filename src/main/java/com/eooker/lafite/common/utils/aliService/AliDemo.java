package com.eooker.lafite.common.utils.aliService;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.aliyuncs.exceptions.ClientException;
import com.eooker.lafite.common.utils.IdGen;
import com.eooker.lafite.common.utils.Lafite3PTUtil;

public class AliDemo {
	
	/**
	 * 阿里的OSS服务Demo
	 **/
	public void aliOSS(CommonsMultipartFile file){
		//命名一个为demo的公共存储空间（type：0私有，1公有）,该存储空间不用每次都新建一个。
		String bucketName = "Demo";
		int type = 1;
		String object = IdGen.uuid()+"_"+file.getOriginalFilename();
		//上传文件
		Lafite3PTUtil.uploadBy3PLService(file, bucketName, object);
		//获取文件的存储路径
		//（second：有效的时间，单位为秒;type:存储空间，0私有，1公有）
		int second = 3600;
		String URL = Lafite3PTUtil.getSTS(bucketName, object, second, type);
//		String URL = aliOSSUtil.getSTS(bucketName, object, second,type);
		System.out.println("***********文件的存储空间为*************");
		System.out.println("***********"+bucketName+"*************");
		System.out.println("***********文件的存储路径为*************");
		System.out.println("***********"+URL+"*************");
	}
	
	/**
	 * 阿里的短信服务Demo
	 * @throws ClientException 
	 **/
	public void aliMessage() throws ClientException{
		//需要发送短信的电话号码
		String phone = "13809795234";
		Lafite3PTUtil.SendSmsResponse(phone);
	}
}
