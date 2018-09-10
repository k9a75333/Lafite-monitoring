package com.eooker.lafite.modules.sys.web;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Date;

import org.junit.runners.Parameterized.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.scheduling.annotation.Scheduled;

import com.eooker.lafite.common.utils.IdGen;
import com.eooker.lafite.common.utils.JedisUtils;
import com.eooker.lafite.common.utils.Result;
import com.eooker.lafite.modules.sys.entity.TestData;
import com.eooker.lafite.modules.sys.service.TestDataService;

@Controller
@RequestMapping("TD")
public class TestDataController {

	@Autowired
	private TestDataService testDataService;
	


	/**
	 * 测试insert
	 * 
	 * @return Result
	 */
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public Result add(@ModelAttribute TestData testData) {
		testData.setId(UUID.randomUUID().toString());
		testData.setTime(new Date());
		testDataService.addData(testData);
		
		return Result.makeSuccessResult();
	}
	
	/**
	 * 测试delete
	 * 
	 * @return Result
	 */
	@ResponseBody
	@RequestMapping(value = "del/{id}", method = RequestMethod.DELETE)
	public Result del(@PathVariable String id) {
		
		testDataService.delData(id);
		
		return Result.makeSuccessResult();
	}
	
	/**
	 * 测试delete
	 * 
	 * @return Result
	 */
	@ResponseBody
	@RequestMapping(value = "updata/{id}", method = RequestMethod.PUT)
	public Result updata(@PathVariable String id,@ModelAttribute TestData testData) {
		
		testDataService.updataData(testData);
		
		return Result.makeSuccessResult();
	}
	
	/**
	 * 测试select
	 * 
	 * @return Result
	 */
	@ResponseBody
	@RequestMapping(value = "select/{id}", method = RequestMethod.GET)
	public Result select(@PathVariable String id,@ModelAttribute TestData testData) {
		
		testData = testDataService.selectData(id);
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("testData",testData);
		
		return Result.makeSuccessResult(result);
	}
	
	@ResponseBody
	@RequestMapping(value = "write/{key}", method = RequestMethod.GET)
	public Result redisWriteList(@PathVariable String key,@ModelAttribute TestData testData) {
		
		List<Object> list = new ArrayList<>();
		Map<String,Object> result = new HashMap<String, Object>();
		
			testData.setData("test1");
			list.add(0, "test1");
		
		JedisUtils.setObjectList(key, list, 0);
		
		List<Object> get = JedisUtils.getObjectList(key);
		
		result.put("list",get);
		
		return Result.makeSuccessResult(result);
	}
	
	@ResponseBody
	@RequestMapping(value = "read/{key}", method = RequestMethod.GET)
	public Result redisReadList(@PathVariable String key) {
		
		List<String> list = (List<String>)JedisUtils.getList(key);
		
		
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("list",list);
		
		return Result.makeSuccessResult(result);
	}
	
	/**
	 * 测试定时insert
	 * 
	 * @return Result
	 * @throws ParseException 
	 */
	@Scheduled(cron="5/10 * * * * ? ")
	@ResponseBody
	@RequestMapping(value = "timerAdd", method = RequestMethod.POST)
	public Result timerAdd() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.US);
		TestData testData = new TestData();
		
//		testData.setTime(new Date());  //写入sql时间
		
		System.out.println("getMap");
		Map <String,Object> water = JedisUtils.getObjectMap("water");
		JedisUtils.delObject("water");
		for(String key:water.keySet()) {
			System.err.println("key值："+key+" value值："+water.get(key));
			String splide[] =key.split(",");
			testData.setId(UUID.randomUUID().toString());
			testData.setData((String)water.get(key));
			testData.setTime(sdf.parse(splide[1]));
			testDataService.addData(testData);
		}
		
		
		
		System.out.println("insert"+new Date());
		return Result.makeSuccessResult();
	}
	
}
