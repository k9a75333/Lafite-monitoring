package com.eooker.lafite.modules.operation.dao;

import com.eooker.lafite.common.persistence.CrudDao;
import com.eooker.lafite.common.persistence.annotation.MyBatisDao;
import com.eooker.lafite.modules.maintainance.entity.Maintainance;
import com.eooker.lafite.modules.operation.entity.Operation;

/**
 * 运营DAO接口
 * @author HongCHUYU
 * @version 2016-06-04
 */
@MyBatisDao
public interface OperationDao extends CrudDao<Operation> {
	
}
