/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.eooker.lafite.modules.maintainance.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eooker.lafite.common.service.CrudService;
import com.eooker.lafite.modules.maintainance.dao.MaintainanceDao;
import com.eooker.lafite.modules.maintainance.entity.Maintainance;

/**
 * 维护Service
 * @author HongCHUYU
 * @version 2018-06-04
 */
@Service
@Transactional(readOnly = true)
public class MaintainanceService extends CrudService<MaintainanceDao, Maintainance> {

}
