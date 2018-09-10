package com.eooker.lafite.modules.keep.utils;

import com.eooker.lafite.common.exception.LafiteException;
import com.eooker.lafite.modules.keep.entity.SSHConfig;
import com.eooker.lafite.modules.keep.exception.KeepExceptionCode;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author xiyatu
 * @date 2018/6/18 8:57
 * Description
 */

public class SSHUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SSHUtil.class);

    private Session session;
    private ChannelExec channelExec;

    /**
     * @param sshConfig
     * @return
     * @throws JSchException
     * @throws IOException
     */
    public String exeCommand(SSHConfig sshConfig, String command) throws LafiteException {

        //jcraft 连接
        JSch jsch = new JSch();
        try {

            session = jsch.getSession(sshConfig.getUsername(), sshConfig.getHost(), sshConfig.getPort());
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(sshConfig.getPassword());
            session.connect(5000);
        } catch (JSchException e) {
            LOGGER.error("-----SSH Connect Error:{}", e.getMessage());
            throw new LafiteException(KeepExceptionCode.KEEP_EXCEPTION_CODE,"SSH连接失败");
        }

        //执行命令
        try {
            channelExec = (ChannelExec) session.openChannel("exec");
            InputStream in = null;
            in = channelExec.getInputStream();
            channelExec.setCommand(command);
            channelExec.connect();
            //将结果输出为String
            return IOUtils.toString(in, "UTF-8");
        } catch (Exception e) {
            LOGGER.error("-----SSH Exec Command Error:{}", e.getMessage());
            throw new LafiteException(KeepExceptionCode.KEEP_EXCEPTION_CODE,"SSH连接失败");
        }
    }

    /**
     * 断开连接
     */
    public void disconnect() {
        channelExec.disconnect();
        session.disconnect();
    }
}
