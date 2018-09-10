package com.eooker.lafite.common.utils;

import java.util.Map;

public class SysException extends RuntimeException{
	private static final long serialVersionUID = 67474;
	
	/**
	 * 状态code，501就是Controller层出现异常，511就是Service层出现异常，521就是Dao层出现异常
	 */
	public final static String CONTERLLER_EXCEPTION_CODE = "501";
	public final static String SERVICE_EXCEPTION_CODE = "511";
	public final static String DAO_EXCEPTION_CODE = "521";
	
	private String stateCode;
	private String msgDes;
	public String getStateCode() {
		return stateCode;
	}

	public String getMsgDes() {
		return msgDes;
	}

	public SysException(Exception e){
		super();
	}
	
	public SysException(String message){
		super(message);
		msgDes = message;
	}
	
	public SysException(String stateCode,String msgDes){
		super();
		this.stateCode = stateCode;
		this.msgDes = msgDes;
	}
	
	public static SysException makeControllerException(String message) {
		return new SysException(CONTERLLER_EXCEPTION_CODE,message);
	}
	public static SysException makeServiceException(String message) {
		return new SysException(SERVICE_EXCEPTION_CODE,message);
	}
	public static SysException makeDaoException(String message) {
		return new SysException(DAO_EXCEPTION_CODE,message);
	}
	
	
}
