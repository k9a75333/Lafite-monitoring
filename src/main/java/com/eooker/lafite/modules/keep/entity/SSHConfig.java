package com.eooker.lafite.modules.keep.entity;

/**
 * @author xiyatu
 * @date 2018/6/18 9:05
 * Description
 */
public class SSHConfig {

    private String host;
    private int port;
    private String username;
    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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

    public static SSHConfig setByServer(Server server){
        SSHConfig config = new SSHConfig();
        config.setHost(server.getHost());
        config.setPassword(server.getPassword());
        config.setPort(server.getPort());
        config.setUsername(server.getUsername());
        return config;
    }
}
