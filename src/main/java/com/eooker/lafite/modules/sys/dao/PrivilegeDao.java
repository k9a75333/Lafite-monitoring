/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.eooker.lafite.modules.sys.dao;

import java.util.List;

import com.eooker.lafite.common.persistence.TreeDao;
import com.eooker.lafite.common.persistence.annotation.MyBatisDao;
import com.eooker.lafite.modules.sys.entity.Privilege;

/**
 * 模块权限DAO接口
 * @author HongCHUYU
 * @version 2018/01/01
 */
@MyBatisDao
public interface PrivilegeDao extends TreeDao<Privilege> {
	
	public List<Privilege> findByUserId(Privilege privilege);
	
}