/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.eooker.lafite.modules.sys.dao;

import com.eooker.lafite.common.persistence.CrudDao;
import com.eooker.lafite.common.persistence.annotation.MyBatisDao;
import com.eooker.lafite.modules.sys.entity.TestData;;

/**
 * 角色DAO接口
 * @author ThinkGem
 * @version 2013-12-05
 */
@MyBatisDao
public interface TestDataDao extends CrudDao<TestData> {

	
	/**
	 * 增删改查基本接口
	 * @param testdata
	 * @return
	 */
	public int deleteTestData(String id);

	public int insertTestData(TestData testData);
	
	public int updataTestData(TestData testData);
	
	public TestData selectTestData(String id);
	
	
}
