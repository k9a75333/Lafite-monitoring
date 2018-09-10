package com.eooker.lafite.modules.keep.dao;

import com.eooker.lafite.common.persistence.CrudDao;
import com.eooker.lafite.common.persistence.annotation.MyBatisDao;
import com.eooker.lafite.modules.keep.entity.Server;

import java.util.List;

/**
 * @author xiyatu
 * @date 2018/6/18 11:54
 * Description
 */
@MyBatisDao
public interface ServerDao extends CrudDao<Server> {

    List<Server> getServerList();

    Server getServerById(String id);

    int insertServer(Server server);

    int deleteServerById(String id);

    int updateServerById(Server server);
}
