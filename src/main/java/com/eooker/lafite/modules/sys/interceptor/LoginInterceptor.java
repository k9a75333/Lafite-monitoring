package com.eooker.lafite.modules.sys.interceptor;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.plugin.ehcache.CacheKit;
import com.eooker.lafite.common.service.BaseService;
import com.eooker.lafite.common.utils.CacheUtils;
import com.eooker.lafite.modules.sys.utils.StringUtils;

import net.sf.json.JSONObject;

/**
 * 用户登陆拦截器
 * 
 * @author kevin.qiu
 * 
 */
public class LoginInterceptor extends BaseService implements HandlerInterceptor {

	public final static String TESTING_USERKEY = "123456";
	public final static String TESTING_USERKEY_STUDENT = "234567";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String userkey = request.getParameter("userkey");
		System.out.println(userkey);
		if(userkey == null){
			/*Result result = Result.makeResult(Result.ACCESS_DENIED_CODE, "Login Invaild");
			ai.getController().renderJson(result);*/
            response.sendError(400, "请求接口失败");
			return false;
		}
		
		String userId = "";
		userId = (String)CacheUtils.get("UserCaching", userkey);
		logger.info("=========>userId:"+userId+"userkey:=====>"+userkey);
		if (StringUtils.isEmpty(userId)) {
			// 如果用户为空则跳转登陆
			/*Result result = Result.makeResult(Result.ACCESS_DENIED_CODE, "Login Invaild");
			ai.getController().renderJson(result);*/
			return false;
		} else {
			/*// 登陆后设置全局变量UserId，用于权限判断
			ai.getController().setAttr("userId", userId);
			ai.invoke();*/
			return true;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
