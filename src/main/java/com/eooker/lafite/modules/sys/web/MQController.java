package com.eooker.lafite.modules.sys.web;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Date;

import org.junit.runners.Parameterized.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.order.OrderProducer;
import com.eooker.lafite.common.utils.IdGen;
import com.eooker.lafite.common.utils.Result;
import com.eooker.lafite.modules.sys.service.TestDataService;

@Controller
@RequestMapping("mq")
public class MQController {

	public static volatile boolean flag = false;

	@Autowired
	private OrderProducer orderProducer;
	
	// 推送模拟数据到mq上
	@RequestMapping("push")
	@ResponseBody

	public String push(@RequestParam(value = "num", required = true) int num) {

		// 发送水表数据
		Thread waterThread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < num; i++) {
					if (flag) {
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					Message msg = new Message("topic_for_demo", "water", genData("water").getBytes());
					msg.setKey(IdGen.uuid());
					String shardingKey = String.valueOf("water");
					try {
						SendResult sendResult = orderProducer.send(msg, shardingKey);
						if (sendResult != null) {
							System.out.println("Send mq message success." + " msg is: " + new String(msg.getBody()));
						}
					} catch (Exception e) {
						System.err.println(" Send mq message failed. Topic is:" + msg.getTopic());
						e.printStackTrace();
					}
					// 十秒钟发送一次数据
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		waterThread.start();
		// 发送电表数据
		Thread electricThread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < num; i++) {
					if (flag) {
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					Message msg = new Message("topic_for_demo", "electric", genData("electric").getBytes());
					msg.setKey(IdGen.uuid());
					String shardingKey = String.valueOf("electric");
					try {
						SendResult sendResult = orderProducer.send(msg, shardingKey);
						if (sendResult != null) {
							System.out.println("Send mq message success." + " msg is: " + new String(msg.getBody()));
						}
					} catch (Exception e) {
						System.err.println(" Send mq message failed. Topic is:" + msg.getTopic());
						e.printStackTrace();
					}
					// 十秒钟发送一次数据
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		electricThread.start();
		return "success";
	}

	@RequestMapping("toTrue")
	public void toTrue() {
		flag = true;
	}

	@RequestMapping("toFalse")
	public void toFalse() {
		flag = false;
	}

	@RequestMapping("electric")
	public String electric() {
		return "/modules/mq/electric";
	}

	@RequestMapping("water")
	public String water() {
		return "/modules/mq/water";
	}

	/**
	 * 返回一个json模拟测试数据
	 * 
	 * @return Result
	 */
	@ResponseBody
	@RequestMapping(value = "test", method = RequestMethod.GET)
	public Result TestData() {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> option = new HashMap<String, Object>();
		Map<String, Object> xAxis = new HashMap<String, Object>();
		Map<String, Object> yAxis = new HashMap<String, Object>();
		Map<String, Object> series = new HashMap<String, Object>();
		String xdata[] = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun" };
		String sdata[] = { "820", "932", "901", "934", "1290", "1330", "1320" };
		xAxis.put("data", xdata);
		xAxis.put("type", "category");
		yAxis.put("type", "value");
		series.put("type", "line");
		series.put("data", sdata);
		option.put("xAxis", xAxis);
		option.put("yAxis", yAxis);
		option.put("series", series);
		map.put("option", option);
		return Result.makeSuccessResult(map);
	}
	
	/**
	 * 返回json模拟测试数据————统计表
	 * 
	 * @return Result
	 */
	@ResponseBody
	@RequestMapping(value = "statisticData", method = RequestMethod.GET)
	public Result statisticData(@RequestParam(required = true) String type) {
		// 格式化器，保留两位小数
		DecimalFormat format = new DecimalFormat("##0.00");
		double add = Math.random() * 10000;
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.YEAR, (int) - (Math.random() * 30));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object>[] rows = new Map[6]; 
		for (int i = 0; i < 6; i++) {
			rows[i] = new HashMap<String, Object>();
			rightNow.add(Calendar.MONTH, (int) + (Math.random() * 20));
			rows[i].put("日期", sdf.format(rightNow.getTime()));
			add += Math.random() * 10000;
			rows[i].put(type, format.format(add));
		}
		map.put("rows", rows);
		return Result.makeSuccessResult(map);
	}
	
	/**
	 * 生成模拟数据
	 * 
	 * @param type 数据类型
	 * @return
	 */
	public static String genData(String type) {
		// 格式化器，保留两位小数
		DecimalFormat format = new DecimalFormat("##0.00");
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < 100; i++) {
			str.append(type + i + ":" + "" + format.format((i * 10 + 2 * Math.random()) / 1.2));
			if (i != 9)
				str.append(",");
		}
		return str.toString();
	}
	
}
