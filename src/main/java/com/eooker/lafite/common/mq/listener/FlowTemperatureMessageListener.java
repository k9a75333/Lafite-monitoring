package com.eooker.lafite.common.mq.listener;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.order.ConsumeOrderContext;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.aliyun.openservices.ons.api.order.OrderAction;

/**
 * 温度的流处理
 */
public class FlowTemperatureMessageListener implements MessageOrderListener {

	@Override
	public OrderAction consume(Message message, ConsumeOrderContext context) {
		System.out.println("temperature flow process!!!! Message [topic=" + message.getTopic() + ",tag="
				+ message.getTag() + ",body=" + new String(message.getBody()) + "]");
		try {
			// do something..
			return OrderAction.Success;
		} catch (Exception e) {
			// 消费失败
			return OrderAction.Suspend;
		}
	}

}
