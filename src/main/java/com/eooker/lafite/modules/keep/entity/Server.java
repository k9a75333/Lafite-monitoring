/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.eooker.lafite.modules.keep.entity;


import com.eooker.lafite.common.persistence.DataEntity;

/**
 * 服务器
 * @author xiyatu
 * @date 2018/6/18 9:05
 *
 */
public class Server extends DataEntity<Server> {
	
	private static final long serialVersionUID = 1L;

	private String name;
	private String host;
	private String username;
	private String password;
	private int port;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}



	@Override
	public String toString() {
		return "Server{" +
				"name='" + name + '\'' +
				", host='" + host + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", port=" + port +
				", remarks='" + remarks + '\'' +
				", createBy=" + createBy +
				", createDate=" + createDate +
				", updateBy=" + updateBy +
				", updateDate=" + updateDate +
				", delFlag='" + delFlag + '\'' +
				", id='" + id + '\'' +
				", currentUser=" + currentUser +
				", page=" + page +
				", sqlMap=" + sqlMap +
				", isNewRecord=" + isNewRecord +
				'}';
	}
}


