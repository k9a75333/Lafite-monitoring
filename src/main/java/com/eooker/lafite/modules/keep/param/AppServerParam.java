package com.eooker.lafite.modules.keep.param;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author xiyatu
 * @date 2018/6/18 19:15
 * Description
 */
public class AppServerParam {

    @NotBlank
    private String serverId;
    @NotBlank
    private String name;
    @NotBlank
    private String serverDir;
    @NotBlank
    private String logDir;
    @NotBlank
    private String binDir;
    @NotBlank
    private String sbinDir;
    @NotNull
    private Integer port;
    @NotNull
    private Integer type;

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

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
