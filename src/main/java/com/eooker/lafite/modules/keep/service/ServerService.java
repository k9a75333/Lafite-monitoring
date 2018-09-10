/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.eooker.lafite.modules.keep.service;

import com.eooker.lafite.modules.keep.exception.KeepExceptionCode;
import com.eooker.lafite.common.exception.LafiteException;
import com.eooker.lafite.common.service.CrudService;
import com.eooker.lafite.common.utils.DESUtils;
import com.eooker.lafite.modules.keep.dao.ServerDao;
import com.eooker.lafite.modules.keep.entity.SSHConfig;
import com.eooker.lafite.modules.keep.entity.Server;
import com.eooker.lafite.modules.keep.entity.vo.ServerVO;
import com.eooker.lafite.modules.keep.param.SSHConfigParam;
import com.eooker.lafite.modules.keep.utils.SSHUtil;
import com.eooker.lafite.modules.sys.utils.CollectionUtils;
import com.eooker.lafite.modules.sys.utils.StringUtils;
import com.eooker.lafite.modules.sys.utils.UUIDTool;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 维护Service
 *
 * @author HongCHUYU
 * @version 2018-06-04
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ServerService extends CrudService<ServerDao, Server> {

    public String ssh(String username) throws LafiteException {
        SSHConfig config = new SSHConfig();
        config.setHost("120.79.198.234");
        config.setPassword("CHENyuhao!@#");
        config.setPort(22);
        config.setUsername(username);
        SSHUtil util = new SSHUtil();
        return util.exeCommand(config, "netstat -anp|grep 80");
    }

    public List<ServerVO> getServerList() {
        List<Server> list = dao.getServerList();
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(this::buildToServerVO).collect(Collectors.toList());
    }

    private ServerVO buildToServerVO(Server server) {
        ServerVO vo = new ServerVO();
        vo.setId(server.getId());
        vo.setName(server.getName());
        vo.setHost(server.getHost());
        vo.setPort(server.getPort());
        vo.setUsername(server.getUsername());
        vo.setRemarks(server.getRemarks());
        vo.setCreateDate(DateFormatUtils.format(server.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
        return vo;
    }

    public ServerVO getServerById(String id) throws LafiteException {
        Server server = dao.getServerById(id);
        if (server == null) {
            throw new LafiteException(KeepExceptionCode.KEEP_EXCEPTION_CODE,"未找到服务器");
        }
        return buildToServerVO(server);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveServer(Server server) throws LafiteException {
        if (StringUtils.isEmpty(server.getId())) {
            if (server.getPort() == 0) {
                server.setPort(22);
            }
            server.setPassword(DESUtils.getEncryptString(server.getPassword()));
            server.setId(UUIDTool.generate());
            server.setCreateDate(new Date());
            server.setUpdateDate(new Date());
            int code = dao.insertServer(server);
            if (code < 0) {
                throw new LafiteException(KeepExceptionCode.KEEP_EXCEPTION_CODE,"添加服务器失败");
            }
        } else {
            if (server.getPort() == 0) {
                server.setPort(22);
            }
            server.setUpdateDate(new Date());
            int code = dao.updateServerById(server);
            if (code < 0) {
                throw new LafiteException(KeepExceptionCode.KEEP_EXCEPTION_CODE,"更新服务器失败");
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteServer(String id) throws LafiteException {
        int code = dao.deleteServerById(id);
        if (code < 0) {
            throw new LafiteException(KeepExceptionCode.KEEP_EXCEPTION_CODE,"添加服务器失败");
        }
    }

    /**
     * 新增服务器测试连接
     *
     * @param param
     * @return
     */
    public String testSSHConnect(SSHConfigParam param) throws LafiteException {
        SSHUtil util = new SSHUtil();
        SSHConfig config = new SSHConfig();
        config.setHost(param.getHost());
        config.setUsername(param.getUsername());
        if(param.getPort() == 0){
            config.setPort(22);
        }else{
            config.setPort(param.getPort());
        }
        if (StringUtils.isEmpty(param.getId())) {
            config.setPassword(param.getPassword());
        } else {
            Server server = dao.getServerById(param.getId());
            if (server == null) {
                throw new LafiteException(KeepExceptionCode.KEEP_EXCEPTION_CODE,"未找到服务器");
            }
            config.setPassword(DESUtils.getDecryptString(server.getPassword()));
        }

        try {
            util.exeCommand(config, "echo success");
            util.disconnect();
            return "连接成功";
        } catch (LafiteException e) {
            //catch异常，不让它跳转至错误界面
            return "连接失败";
        }
    }

}
