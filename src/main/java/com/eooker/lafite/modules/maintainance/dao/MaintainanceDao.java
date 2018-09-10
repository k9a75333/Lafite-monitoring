package com.eooker.lafite.modules.maintainance.dao;

import com.eooker.lafite.common.persistence.CrudDao;
import com.eooker.lafite.common.persistence.annotation.MyBatisDao;
import com.eooker.lafite.modules.maintainance.entity.Maintainance;

/**
 * 维护DAO接口
 * @author HongCHUYU
 * @version 2018-06-04
 */
@MyBatisDao
public interface MaintainanceDao extends CrudDao<Maintainance> {
	
}
