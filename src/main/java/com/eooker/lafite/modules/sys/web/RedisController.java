/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.eooker.lafite.modules.sys.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.header.writers.frameoptions.StaticAllowFromStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.eooker.lafite.common.exception.LafiteException;
import com.eooker.lafite.common.utils.CacheUtils;
import com.eooker.lafite.common.utils.PwdEncryptionUtils;
import com.eooker.lafite.common.utils.Result;
import com.eooker.lafite.common.utils.StringUtils;
import com.eooker.lafite.common.utils.SysException;
import com.eooker.lafite.common.utils.JedisUtils;
import com.eooker.lafite.common.web.BaseController;
import com.eooker.lafite.modules.sys.entity.TestData;


/**
 * 用于测试redis的Controller
 * @author Linzihao
 * @version 2018-07-26
 */
@Controller
@RequestMapping(value = "Redis/")
public class RedisController extends BaseController {
	
	
	 @ResponseBody
	 @RequestMapping(value="write/{key}/{value}",method=RequestMethod.POST)
	 public Result Write(@PathVariable String key,@PathVariable String value){
		
		 JedisUtils.setObject(key, value, 0);
		 
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put(key, value);
		 
		 
		 return Result.makeSuccessResult(map);
		 
	 }
	 
	 
	 @ResponseBody
	 @RequestMapping(value="read/{key}",method=RequestMethod.GET)
	 public Result Read(@PathVariable String key){
		
		 
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put(key, JedisUtils.getObject(key));
		 System.out.println("执行了1次查询");
		 
		 return Result.makeSuccessResult(map);
		 
	 }
	 
	 	@ResponseBody
		@RequestMapping(value = "writeList", method = RequestMethod.GET)
		public Result redisWriteList(/*@PathVariable String key,@ModelAttribute TestData testData*/) {
			
			List<Object> list = new ArrayList<Object>();
			
			Map<String,Object> result = new HashMap<String, Object>();
			
			/*	testData.setData("test1");
				list.add(0, "test1");*/
			
			//list.add(0, "t"); //设置
			
			JedisUtils.setObjectList("test1", list, 0);  //写入redis
			
			List<String> get = JedisUtils.getList("test1"); //从redis读出
			
			result.put("list",get);  //存入map中返回数据
			
			return Result.makeSuccessResult(result);
		}
	 
	 	@ResponseBody
		@RequestMapping(value = "writeMap/{key}", method = RequestMethod.GET)
		public Result redisWriteMap(@PathVariable String key/*,@ModelAttribute TestData testData*/) {
			
			Map<String,Object> redis = new HashMap<String, Object>();
			Map<String,Object> result = new HashMap<String, Object>();
			
			/*	testData.setData("test1");
				list.add(0, "test1");*/
			
			//list.add(0, "t"); //设置
			redis.put("maptest1", "maptest1");
			redis.put("maptest2", "maptest2");
			
			JedisUtils.setObjectMap(key, redis, 0);  //写入redis
			
			Map<String,Object> get = JedisUtils.getObjectMap(key); //从redis读出
			
			result.put("list",get);  //存入map中返回数据
			
			return Result.makeSuccessResult(result);
		}
	 	
	 	@ResponseBody
		@RequestMapping(value = "readMap/{key}", method = RequestMethod.GET)
		public Result redisReadMap(@PathVariable String key/*,@ModelAttribute TestData testData*/) {
			
			Map<String,Object> result = new HashMap<String, Object>();
			
			Map<String,Object> get = JedisUtils.getObjectMap(key); //从redis读出
			
			result.put("list",get);  //存入map中返回数据
			
			return Result.makeSuccessResult(result);
		}
}
