package com.eooker.lafite.modules.keep.entity;


import com.eooker.lafite.common.persistence.DataEntity;

/**
 * 应用服务
 */
public class AppServer extends DataEntity<AppServer> {

  private String serverId;
  private String name;
  private String serverDir;
  private String logDir;
  private String binDir;
  private String sbinDir;
  private int port;
  private int type;



  public String getServerId() {
    return serverId;
  }

  public void setServerId(String serverId) {
    this.serverId = serverId;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getServerDir() {
    return serverDir;
  }

  public void setServerDir(String serverDir) {
    this.serverDir = serverDir;
  }


  public String getLogDir() {
    return logDir;
  }

  public void setLogDir(String logDir) {
    this.logDir = logDir;
  }


  public String getBinDir() {
    return binDir;
  }

  public void setBinDir(String binDir) {
    this.binDir = binDir;
  }


  public String getSbinDir() {
    return sbinDir;
  }

  public void setSbinDir(String sbinDir) {
    this.sbinDir = sbinDir;
  }


  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }
}
