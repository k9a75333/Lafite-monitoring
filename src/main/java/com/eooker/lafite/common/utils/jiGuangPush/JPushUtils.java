package com.eooker.lafite.common.utils.jiGuangPush;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 极光推送工具类 极光推送关键是创建一个PushPayload
 * @author yezijie with malor
 */
public class JPushUtils {
	private final static String APP_KEY1 = "c59f9036cc184c85884562e5";
	private final static String MASTER_SECRET1 = "7147eebef9c1940dab667bf7";
	private final static String APP_KEY2 = "42de803d2d5edbedd0ce5ab5";
	private final static String MASTER_SECRET2 = "22ee68f5b6a02861021a41e6";
	public final static int ANDROID_PLATFORM = 0;
	public final static int IOS_PLATFORM = 1;
	public final static int ANDROID_IOS_PLATFORM = 2;
	public final static int ALL_PLATFORM = -1;
	private static JPushClient jPushClient1;
	private static JPushClient jPushClient2;
	static {
		jPushClient1 = new JPushClient(MASTER_SECRET1, APP_KEY1, null, ClientConfig.getInstance());  //用maven运行啊老铁
		jPushClient2= new JPushClient(MASTER_SECRET2, APP_KEY2, null, ClientConfig.getInstance());
	}

	/**
	 * 构造能往设备发送通知(ALERT)的 PushPayload
	 * 
	 * @param content
	 *            发送内容
	 * @param platform
	 *            接受平台
	 * @param title
	 *            安卓端可设置通知标题，若不需要则传入null即可
	 * @return
	 */
	private static PushPayload buildPushObject_all_alert(String content, int platform, String title) {
		if (platform == ANDROID_PLATFORM) {
			if (title != null) {
				return PushPayload.newBuilder().setPlatform(Platform.android())
						.setNotification(Notification.android(content, title, null)).setAudience(Audience.all())
						.build();
			} else {
				return PushPayload.newBuilder().setPlatform(Platform.android())
						.setNotification(Notification.alert(content)).setAudience(Audience.all()).build();
			}
		} else if (platform == IOS_PLATFORM) {
			return PushPayload.newBuilder().setPlatform(Platform.ios()).setNotification(Notification.alert(content))
					.setAudience(Audience.all()).build();
		} else if (platform == ANDROID_IOS_PLATFORM) {
			return PushPayload.newBuilder().setPlatform(Platform.android_ios())
					.setNotification(Notification.alert(content)).setAudience(Audience.all()).build();
		} else {
			return PushPayload.newBuilder().setPlatform(Platform.all()).setNotification(Notification.alert(content))
					.setAudience(Audience.all()).build();
		}
	}

	/**
	 * 构造能往对应alias发送通知(ALERT)的PushPayload
	 * 
	 * @param content
	 *            内容
	 * @param alias
	 *            别名
	 * @param platform
	 *            平台参数
	 * @param title
	 *            安卓端可设置通知标题，若不需要则传入null即可
	 * @return
	 */
	private static PushPayload buildPushObjcet_alias_alert(String content, String alias, int platform, String title) {
		if (platform == ANDROID_PLATFORM) {
			if (title != null) {
				return PushPayload.newBuilder().setPlatform(Platform.android())
						.setNotification(Notification.android(content, title, null)).setAudience(Audience.alias(alias))
						.build();
			} else {
				return PushPayload.newBuilder().setPlatform(Platform.android())
						.setNotification(Notification.alert(content)).setAudience(Audience.alias(alias)).build();
			}
		} else if (platform == IOS_PLATFORM) {
			return PushPayload.newBuilder().setPlatform(Platform.ios()).setNotification(Notification.alert(content))
					.setAudience(Audience.alias(alias)).build();
		} else if (platform == ANDROID_IOS_PLATFORM) {
			return PushPayload.newBuilder().setPlatform(Platform.android_ios())
					.setNotification(Notification.alert(content)).setAudience(Audience.alias(alias)).build();
		} else {
			return PushPayload.newBuilder().setPlatform(Platform.all()).setNotification(Notification.alert(content))
					.setAudience(Audience.alias(alias)).build();
		}
	}
	


	/**
	 * 构造能往对应tag发送通知(ALERT)的PushPayload
	 * 
	 * @param content
	 *            内容
	 * @param alias
	 *            别名
	 * @param platform
	 *            平台参数
	 * @param title
	 *            安卓端可设置通知标题，若不需要则传入null即可
	 * @return
	 */
	private static PushPayload buildPushObjcet_tag_alert(String content, String tag, int platform, String title) {
		if (platform == ANDROID_PLATFORM) {
			if (title != null) {
				return PushPayload.newBuilder().setPlatform(Platform.android())
						.setNotification(Notification.android(content, title, null)).setAudience(Audience.tag(tag))
						.build();
			} else {
				return PushPayload.newBuilder().setPlatform(Platform.android())
						.setNotification(Notification.alert(content)).setAudience(Audience.tag(tag)).build();
			}
		} else if (platform == IOS_PLATFORM) {
			return PushPayload.newBuilder().setPlatform(Platform.ios()).setNotification(Notification.alert(content))
					.setAudience(Audience.tag(tag)).build();
		} else if (platform == ANDROID_IOS_PLATFORM) {
			return PushPayload.newBuilder().setPlatform(Platform.android_ios())
					.setNotification(Notification.alert(content)).setAudience(Audience.tag(tag)).build();
		} else {
			return PushPayload.newBuilder().setPlatform(Platform.all()).setNotification(Notification.alert(content))
					.setAudience(Audience.tag(tag)).build();
		}
	}

	/**
	 * 构造一个给registrationId 发送通知的PushPayload
	 * @param content  发送内容
	 * @param registrationId   接受用户的ID
	 * @return
	 */
	private static PushPayload buildPushObjcet_alertByRegistrationId(String content,String registrationId){
		return PushPayload.newBuilder().setPlatform(Platform.all()).setNotification(Notification.alert(content)).setAudience(Audience.registrationId(registrationId)).build();
	}
	
	private static PushPayload buildPushObjcet_alert_All(String content){
		return PushPayload.newBuilder().setPlatform(Platform.all()).setMessage(Message.content(content)).setAudience(Audience.all()).build();
	}
	
	/**
	 * 构造一个给registrationId 发送消息的PushPayload
	 * @param content  发送内容
	 * @param registrationId   接受用户的ID
	 * @return
	 */
	private static PushPayload buildPushObjcet_msg_contentByRegistrationId(String content,String registrationId){
		return PushPayload.newBuilder().setPlatform(Platform.all()).setMessage(Message.content(content)).setAudience(Audience.registrationId(registrationId)).build();
	}
	
	/**
	 * 构造广播发送消息的PushPayload
	 * @param content  发送内容
	 * @return
	 */
	private static PushPayload buildPushObjcet_msg_content(String content){
		return PushPayload.newBuilder().setPlatform(Platform.all()).setMessage(Message.content(content)).setAudience(Audience.all()).build();
	}
	
	
	/**
	 * 用于给对应设备的所有用户发送通知
	 * 
	 * @param content
	 *            需要发送的通知
	 * @param platform
	 *            需要发送的平台
	 * 
	 * @param title
	 *            安卓端可设置通知标题，若不需要则传入null即可
	 * @return 发送是否成功
	 */
	public static boolean pushAllAlert(String content, int platform, String title) {
		PushPayload pushPayload = buildPushObject_all_alert(content, platform, title);
		try {
			PushResult result = jPushClient1.sendPush(pushPayload);
			int statusCode = result.statusCode;
			jPushClient1.close();
			if (statusCode == PushResult.ERROR_CODE_OK) {
				return true;
			}
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;

		} catch (APIRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * 用于给对应alias发送通知
	 * 
	 * @param content
	 *            内容
	 * @param platform
	 *            平台
	 * @param title
	 *            安卓端可设置通知标题，若不需要则传入null即可
	 * @param alias
	 *            别名
	 * @return 发送是否成功
	 */
	public static boolean pushAlertByAlias(String content, int platform, String title, String alias) {
		PushPayload pushPayload = buildPushObjcet_alias_alert(content, alias, platform, title);
		try {
			PushResult result = jPushClient1.sendPush(pushPayload);
			jPushClient1.close();
			if (result.statusCode == PushResult.ERROR_CODE_OK) {
				return true;
			}
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (APIRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	
	/**
	 * 用于给对应tag发送通知
	 * 
	 * @param content
	 *            内容
	 * @param platform
	 *            平台
	 * @param title
	 *            安卓端可设置通知标题，若不需要则传入null即可
	 * @param alias
	 *            别名
	 * @return 发送是否成功
	 */
	public static boolean pushAlertByTag(String content, int platform, String title, String tag) {
		PushPayload pushPayload = buildPushObjcet_tag_alert(content, tag, platform, title);
		try {
			PushResult result = jPushClient1.sendPush(pushPayload);
			jPushClient1.close();
			if (result.statusCode == PushResult.ERROR_CODE_OK) {
				return true;
			}
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (APIRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	/**
	 * 用于给对应的registrationId 发送通知
	 * @param content     内容
	 * @param registrationId   对应用户的Id
	 * @return 发送是否成功
	 */
	
	public static boolean pushAlertByRegistrationId(String content,String registrationId){
		PushPayload pushPayload = buildPushObjcet_alertByRegistrationId(content, registrationId);
		try {
			PushResult result = jPushClient1.sendPush(pushPayload);
			if(result.statusCode == PushResult.ERROR_CODE_OK){
				System.out.println("Alert------------------"+content+"-----------------");
				return true;
			}
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (APIRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public static boolean pushMessageToAll(String content) {
		PushPayload pushPayload = buildPushObjcet_alert_All(content);
		try {
			PushResult result = jPushClient1.sendPush(pushPayload);
			if(result.statusCode == PushResult.ERROR_CODE_OK){
				System.out.println("Alert------------------"+content+"-----------------");
				return true;
			}
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (APIRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	
	/**
	 * 用于给对应的registrationId 发送消息
	 * @param content     内容
	 * @param registrationId   对应用户的Id
	 * @return 发送是否成功
	 */
	public static boolean pushMessageByRegistrationId(String content,String registrationId){
		PushPayload pushPayload = buildPushObjcet_msg_contentByRegistrationId(content, registrationId);
		try {
			PushResult result = jPushClient1.sendPush(pushPayload);
			if(result.statusCode == PushResult.ERROR_CODE_OK){
				System.out.println("Message------------------"+content+"-----------------");
				return true;
			}
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (APIRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * 广播发送消息到到大屏幕
	 * @param content     内容
	 * @return 发送是否成功
	 */
	public static boolean pushMessageToBigScreen(String content){
		PushPayload pushPayload = buildPushObjcet_msg_content(content);
		try {
			PushResult result = jPushClient2.sendPush(pushPayload);
			if(result.statusCode == PushResult.ERROR_CODE_OK){
				System.out.println("BigScreen------------------"+content+"-----------------");
				return true;
			}
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (APIRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
