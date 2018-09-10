package com.eooker.lafite.modules.keep.service;

import com.eooker.lafite.common.service.CrudService;
import com.eooker.lafite.modules.keep.dao.AppServerDao;
import com.eooker.lafite.modules.keep.entity.AppServer;
import com.eooker.lafite.modules.keep.entity.enums.AppServerType;
import com.eooker.lafite.modules.keep.entity.po.AppServerPO;
import com.eooker.lafite.modules.keep.entity.vo.AppServerVO;
import com.eooker.lafite.modules.keep.param.AppServerParam;
import com.eooker.lafite.modules.sys.utils.CollectionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiyatu
 * @date 2018/6/18 19:05
 * Description
 */
@Service
public class AppServerService extends CrudService<AppServerDao, AppServer> {


    public List<AppServerVO> getAppServerList(Integer type) {

        List<AppServerPO> list = dao.getAppServerList(type);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(this::buildToAppServerVO).collect(Collectors.toList());

    }

    private AppServerVO buildToAppServerVO(AppServerPO appServerPO){
        AppServerVO vo = new AppServerVO();
        vo.setServerId(appServerPO.getServerId());
        vo.setServerName(appServerPO.getServerName());
        vo.setId(appServerPO.getId());
        vo.setName(appServerPO.getName());
        vo.setServerDir(appServerPO.getServerDir());
        vo.setPort(appServerPO.getPort());
        vo.setTypeName(AppServerType.getDescByCode(appServerPO.getType()));
        vo.setCreateDate(DateFormatUtils.format(appServerPO.getCreateDate(),"yyyy-MM-dd HH:mm:ss"));
        return vo;
    }

    public void addAppServer(AppServerParam param) {
        AppServer appServer = new AppServer();
        BeanUtils.copyProperties(param, appServer);
        appServer.setCreateDate(new Date());
        appServer.setUpdateDate(new Date());
        dao.insert(appServer);
    }


}
