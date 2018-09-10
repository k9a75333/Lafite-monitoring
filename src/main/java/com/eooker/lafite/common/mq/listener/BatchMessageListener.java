package com.eooker.lafite.common.mq.listener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.order.ConsumeOrderContext;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.aliyun.openservices.ons.api.order.OrderAction;
import com.eooker.lafite.common.utils.JedisUtils;

public class BatchMessageListener implements MessageOrderListener {

	Map<String, Object> water = new HashMap<String, Object>();
	Map<String, Object> electric = new HashMap<String, Object>();
	static final String WATER = "water";
	static final String ELECTRIC = "electric";
	@Override
	public OrderAction consume(Message message, ConsumeOrderContext context) {
		System.err.println("BATCH process!!!! Message [topic=" + message.getTopic() + ",tag=" + message.getTag()
				+ ",body=" + new String(message.getBody()) + "]");
		try {
			// do something..
			Date date = new Date();
			if(message.getTag().equals(WATER)) {
				water.put((WATER+","+date),new String(message.getBody()));
				JedisUtils.setObjectMap(WATER, water, 0);
				water.clear();
			}
			if(message.getTag().equals(ELECTRIC)) {
				
				electric.put((ELECTRIC+","+date),new String(message.getBody()));
				JedisUtils.setObjectMap(ELECTRIC, electric, 0);
				electric.clear();
			}
			
			return OrderAction.Success;
		} catch (Exception e) {
			// 消费失败
			return OrderAction.Suspend;
		}
	}

}
