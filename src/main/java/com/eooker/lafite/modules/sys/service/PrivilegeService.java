/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.eooker.lafite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eooker.lafite.common.service.TreeService;
import com.eooker.lafite.common.utils.StringUtils;
import com.eooker.lafite.modules.sys.entity.Privilege;
import com.eooker.lafite.modules.sys.dao.PrivilegeDao;

/**
 * 模块权限Service
 * @author Hades
 * @version 2017-03-30
 */
@Service
@Transactional(readOnly = true)
public class PrivilegeService extends TreeService<PrivilegeDao, Privilege> {

	public Privilege get(String id) {
		return super.get(id);
	}
	
	public List<Privilege> findList(Privilege privilege) {
		if (StringUtils.isNotBlank(privilege.getParentIds())){
			privilege.setParentIds(","+privilege.getParentIds()+",");
		}
		return super.findList(privilege);
	}
	
	@Transactional(readOnly = false)
	public void save(Privilege privilege) {
		super.save(privilege);
	}
	
	@Transactional(readOnly = false)
	public void delete(Privilege privilege) {
		super.delete(privilege);
	}
	
}