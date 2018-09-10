/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.eooker.lafite.modules.sys.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.jfinal.plugin.activerecord.Record;
import com.eooker.lafite.common.config.Global;
import com.eooker.lafite.common.exception.CommonCode;
import com.eooker.lafite.common.exception.LafiteException;
import com.eooker.lafite.common.persistence.Page;
import com.eooker.lafite.common.security.Digests;
import com.eooker.lafite.common.security.shiro.session.SessionDAO;
import com.eooker.lafite.common.service.BaseService;
import com.eooker.lafite.common.service.ServiceException;
import com.eooker.lafite.common.utils.CacheUtils;
import com.eooker.lafite.common.utils.Encodes;
import com.eooker.lafite.common.utils.LogUtils;
import com.eooker.lafite.common.utils.PwdEncryptionUtils;
import com.eooker.lafite.common.utils.StringUtils;
import com.eooker.lafite.common.utils.SysException;
import com.eooker.lafite.common.web.Servlets;
import com.eooker.lafite.modules.keep.exception.KeepExceptionCode;
import com.eooker.lafite.modules.sys.dao.MenuDao;
import com.eooker.lafite.modules.sys.dao.RoleDao;
import com.eooker.lafite.modules.sys.dao.UserDao;
import com.eooker.lafite.modules.sys.dao.TestDataDao;
import com.eooker.lafite.modules.sys.entity.Menu;
import com.eooker.lafite.modules.sys.entity.Office;
import com.eooker.lafite.modules.sys.entity.Privilege;
import com.eooker.lafite.modules.sys.entity.Role;
import com.eooker.lafite.modules.sys.entity.User;
import com.eooker.lafite.modules.sys.entity.TestData;
import com.eooker.lafite.modules.sys.security.SystemAuthorizingRealm;
import com.eooker.lafite.modules.sys.utils.UUIDTool;
import com.eooker.lafite.modules.sys.utils.UserUtils;

/**
 * TestData测试service
 * @author LinZIHAO
 * @version 2018/08/09
 */
@Service
@Transactional(readOnly = true)
public class TestDataService /*extends BaseService*/{
	
	
	@Autowired
	private TestDataDao testDataDao;
	
	
	public User getUser(String id) {
		return UserUtils.get(id);
	}

	@Transactional
	public void addData(TestData testData) {
		
		testDataDao.insertTestData(testData);
		
	}
	
	@Transactional
	public void delData(String id) {
		
		testDataDao.deleteTestData(id);
		
	}
	
	@Transactional
	public void updataData(TestData testData) {
		
		testDataDao.updataTestData(testData);
		
	}
	
	@Transactional
	public TestData selectData(String id) {
		
		return testDataDao.selectTestData(id);
		
		
	}
	
}
