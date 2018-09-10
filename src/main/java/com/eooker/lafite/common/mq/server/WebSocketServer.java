package com.eooker.lafite.common.mq.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;
import org.springframework.web.socket.server.standard.SpringConfigurator;

/**
 *  必须加configurator = SpringConfigurator.class，否则在这个服务器端点里的@Autowried都将空指针
 */
@ServerEndpoint(value="/webSocket/{tag}", configurator = SpringConfigurator.class)
public class WebSocketServer {
	
	Logger logger = Logger.getLogger(WebSocketServer.class);
	
	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static volatile int onlineCount = 0;

	// 线程安全Set，用来存放每个客户端对应的WebSocket对象。
//	private static CopyOnWriteArraySet<WebSocketServer> sØocket = new CopyOnWriteArraySet<WebSocketServer>();
	
	//记录每个用户下多个终端的连接
    private static Map<String, Set<WebSocketServer>> socket = new HashMap<>();

	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	
	private String tag;
	
	/**
	 * 连接建立成功调用的方法
	 */
	@OnOpen
	public void onOpen(@PathParam("tag") String tag, Session session) {
		this.session = session;
		this.tag = tag;
		addOnlineCount();
		//根据该tag当前是否已经在别的终端登录进行添加操作
        if (socket.containsKey(this.tag)) {
            logger.info("当前tag:{"+this.tag+"}已有其他终端登录");
            socket.get(this.tag).add(this); //增加该用户set中的连接实例
        }else {
            logger.info("当前tag:{"+this.tag+"}第一个终端登录");
            Set<WebSocketServer> addTagSet = new HashSet<>();
            addTagSet.add(this);
            socket.put(this.tag, addTagSet);
        }
        logger.info("tag{"+tag+"}登录的终端个数是为{"+socket.get(this.tag).size()+"}");
        logger.info("当前所有tag数为：{"+socket.size()+"},所有终端个数为：{"+getOnlineCount()+"}");
//		socket.add(this); // 加入set中
//		addOnlineCount(); // 在线数加1
//		logger.debug("有新连接加入！当前连接数为" + getOnlineCount());
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		subOnlineCount();
		//移除当前用户终端登录的websocket信息,如果该用户的所有终端都下线了，则删除该用户的记录
        if (socket.get(this.tag).size() == 0) {
        	socket.remove(this.tag);
        }else{
        	socket.get(this.tag).remove(this);
        }
        logger.info("tag{"+this.tag+"}登录的终端个数是为{"+socket.get(this.tag).size()+"}");
        logger.info("当前所有tag数为：{"+socket.size()+"},所有终端个数为：{"+onlineCount+"}");
//		socket.remove(this); // 从set中删除
//		subOnlineCount(); // 在线数减1
//		logger.debug("有一连接关闭！当前连接数为" + getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法
	 * @param message 客户端发送过来的消息
	 * @param session 可选的参数
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		logger.info("收到来自tag为：{"+this.tag+"}的消息：{"+message+"}");
//		for (WebSocketServer item : socket) {
//			item.sendMessage(message);
//		}
	}

	/**
	 * 发生错误时调用
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		logger.error(this.tag + "的连接发送错误");
		error.printStackTrace();
	}

	
	/**
	   * @Title: sendMessageToUser
	   * @Description: 发送消息给用户下的所有终端
	   * @param @param userId 用户id
	   * @param @param message 发送的消息
	   * @param @return 发送成功返回true，反则返回false
	   */
	    public static Boolean sendMessageToTag(String tag,String message){
	        if (socket.containsKey(tag)) {
	            System.out.println(" 给tag为：{"+tag+"}的所有终端发送消息：{"+message+"}");
	            for (WebSocketServer WS : socket.get(tag)) {
//	                logger.info("sessionId为:{"+WS.session.getId()+"}");
	                try {
	                    WS.session.getBasicRemote().sendText(message);
	                } catch (IOException e) {
	                    e.printStackTrace();
	                    System.out.println(" 给tag为：{"+tag+"}发送消息失败");
	                    return false;
	                }
	            }
	            return true;
	        }
	        System.out.println("发送错误：当前连接不包含:" + tag);
	        return false;
	    }
//	public void sendMessage(String message, String tag) {
//		try {
//			Iterator<WebSocketServer> iterator = socket.iterator();
//			while (iterator.hasNext()) {
//				iterator.next().session.getBasicRemote().sendText(message);;
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		WebSocketServer.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		WebSocketServer.onlineCount--;
	}
}
