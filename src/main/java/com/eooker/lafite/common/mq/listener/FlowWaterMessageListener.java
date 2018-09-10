package com.eooker.lafite.common.mq.listener;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.order.ConsumeOrderContext;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.aliyun.openservices.ons.api.order.OrderAction;
import com.eooker.lafite.common.mq.server.WebSocketServer;

/**
 * 水表的流处理
 */
public class FlowWaterMessageListener implements MessageOrderListener {

	@Override
	public OrderAction consume(Message message, ConsumeOrderContext context) {
//		System.out.println("water flow process!!!! Message [topic=" + message.getTopic() + ",tag=" + message.getTag()
//		+ ",body=" + new String(message.getBody()) + "]");
		// 取第一个表的数据进行显示
		String str = new String(message.getBody()).split(",")[0].split(":")[1];
//		String str = new String(message.getBody());
		// 向tag为water的连接发送电表数据
		WebSocketServer.sendMessageToTag("water", str);
		try {
			// do something..
			return OrderAction.Success;
		} catch (Exception e) {
			// 消费失败
			return OrderAction.Suspend;
		}
	}

}
