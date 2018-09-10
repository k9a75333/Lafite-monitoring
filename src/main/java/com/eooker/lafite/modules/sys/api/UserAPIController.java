/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.eooker.lafite.modules.sys.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eooker.lafite.common.exception.LafiteException;
import com.eooker.lafite.common.utils.CacheUtils;
import com.eooker.lafite.common.utils.PwdEncryptionUtils;
import com.eooker.lafite.common.utils.Result;
import com.eooker.lafite.common.utils.StringUtils;
import com.eooker.lafite.common.utils.SysException;
import com.eooker.lafite.common.web.BaseController;
import com.eooker.lafite.modules.sys.entity.User;
import com.eooker.lafite.modules.sys.service.SystemService;
import com.eooker.lafite.modules.sys.utils.CryptoUtils;

/**
 * 用户接口的Controller
 * (用户的所有接口都包含在这个Controller，包括对用户基本信息的增删改查的接口)
 * @author HongCHUYU
 * @version 2018-06-01
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/userAPI/")
public class UserAPIController extends BaseController {
	
	@Autowired
	private SystemService systemService;
	/**
	 * App登陆接口（用户根据登陆账号，密码和相应的设备信息等进行登陆接口的请求）
	 * @param number   登陆账号
	 * @param password 密码
	 * @param system   设备（iOS/Android）
	 * @param token
	 * @param registrationId  设备的id
	 * @return Result  
	 * @throws Exception 
	 */
	//登陆测试在和sys同一路径下的test/web/userControllerTest (JUnit测试)
	@ResponseBody
	@RequestMapping(value = "interface/login",method=RequestMethod.POST)
	public Result interfaceLogin(
			@RequestParam(value = "number", required = false) String number,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "system", required = false) String system,
			@RequestParam(value = "token", required = false) String token,
			@RequestParam(value = "registrationId", required = false) String registrationId) throws Exception{
		if(systemService.IdIsExist(number) == null) {             //账号不存在
			return Result.makeFailResult("帐号不存在");
		}else {
			PwdEncryptionUtils utils=new PwdEncryptionUtils();
			String tokenCredentials=utils.encryp(password);
			User user = systemService.getUserByNumber(number);
			String accountCredentials=user.getPassword();
			//当密码正确时
			if (tokenCredentials.equals(accountCredentials)) {
				user.setPushId(registrationId);
//				systemService.savePushId(user);
				Map<String,Object> map=new HashMap<String,Object>();
				user=systemService.getUserByNumber(user.getNumber());
				// CacheKit操作cache把user加入到缓存中
				String userKey = CryptoUtils.hexMD5(number + password + System.currentTimeMillis());
				String userId = user.getId();
				//查看是否已在其他同种设备登录
//					String listenId = systemService.checkLoginedAndSendLogoutMsg(userId,token);
				String listenId="";
				//将userKey和userId放入缓存
				String loginedKey = userId+"_logined_user_key";
				CacheUtils.put(SystemService.USER_LOGINED_CACHING, loginedKey, userKey);
				CacheUtils.put(SystemService.USER_CACHING, userKey, userId);
				//插入用户登录设备信息
//					systemService.checkUserDevice(userId, token, system);
				//将userkey和listenId放入map
				map.put("userkey", userKey);
				map.put("listenId", listenId);
				map.put("infoList", user);
				return Result.makeSuccessResult(map);
			}else
				return Result.makeFailResult();
		}
	}
	
	/**
	 * App修改用户个人资料接口（用户通过用户的id，对相应自己的用户信息进行修改）
	 * @param remarks    用户需要修改的信息参数
	 * @param userId     用户的唯一id
	 * @return Result
	 * @throws LafiteException 
	 */
	@ResponseBody
	@RequestMapping(value = "interface/updateUserMess/{userId}&{remarks}",method=RequestMethod.PUT)
	public Result interfaceUpdateUserMess(
			@PathVariable final String userId,
			@PathVariable final String remarks) throws LafiteException{
		User user = new User();
		user = systemService.getUser(userId);
		if(user!=null){
			user.setRemarks(remarks);
			systemService.updateUserInfo(user);
			return Result.makeSuccessResult("Update user information success");
		}else
			return Result.makeFailResult();
	}
	
	/**
	 * App修改密码接口（用户通过id对自己的密码进行修改）
	 * @param newPassword   用户新的密码
	 * @param oldPassword   用户旧的密码
	 * @param userkey       userkey
	 * @param userId        用户唯一id
	 * @return Result
	 * @throws LafiteException 
	 */
	@ResponseBody
	@RequestMapping(value = "interface/modifyPwd/{userId}&{oldPassword}&{newPassword}",method=RequestMethod.PUT)
	public Result interfaceModifyPwd(
			@PathVariable final String oldPassword,
			@PathVariable final String newPassword,
			@PathVariable final String userId) throws LafiteException{
		User user = new User();
		user = systemService.getUser(userId);
		logger.info(user.getId());
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
			if (SystemService.validatePassword(oldPassword, user.getPassword())){
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
				return Result.makeSuccessResult("Change user password success");
			}
			return Result.makeFailResult();
		}else
			return Result.makeFailResult();
	}
	
	/**
	 * App退出登陆接口（退出登陆，清除掉用户在tomcat下的缓存信息）
	 * @param userId    用户唯一id
	 * @return Result
	 */
	 @ResponseBody
	 @RequestMapping(value="interface/exitLogin/{userId}",method=RequestMethod.PUT)
	 public Result exitLogin(@PathVariable final String userId){
		 Map<String,Object> map = new HashMap<String,Object>();
		 String pushId = "";
		 if(StringUtils.isEmpty(userId)){
			 return Result.makeFailResult();
		 }else {
			 //退出登录，清除userkey
			 String loginedKey = userId+"_logined_user_key";                              //拼凑key
			 String userKey = (String)CacheUtils.get(SystemService.USER_LOGINED_CACHING,loginedKey);               //获取userId对应的userKey
			 CacheUtils.remove(SystemService.USER_CACHING,userKey);        //移除userKey，使userKey失效
//			 logger.error(ExceptionUtils.getStackTrace(""));
			 return Result.makeSuccessResult();
		 }
	 }
	 
}
