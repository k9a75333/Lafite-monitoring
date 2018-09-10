package com.eooker.lafite.modules.keep.dao;

import com.eooker.lafite.common.persistence.CrudDao;
import com.eooker.lafite.common.persistence.annotation.MyBatisDao;
import com.eooker.lafite.modules.keep.entity.AppServer;
import com.eooker.lafite.modules.keep.entity.po.AppServerPO;

import java.util.List;

/**
 * @author xiyatu
 * @date 2018/6/18 18:59
 * Description
 */
@MyBatisDao
public interface AppServerDao extends CrudDao<AppServer> {

    List<AppServerPO> getAppServerList(Integer type);
}
