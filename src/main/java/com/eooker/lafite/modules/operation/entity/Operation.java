/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.eooker.lafite.modules.operation.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.eooker.lafite.common.persistence.DataEntity;
import com.eooker.lafite.common.supcan.annotation.treelist.SupTreeList;
import com.eooker.lafite.common.supcan.annotation.treelist.cols.SupCol;
import com.eooker.lafite.common.supcan.annotation.treelist.cols.SupGroup;
import com.eooker.lafite.modules.sys.entity.Office;

/**
 * 运营Entity
 * @author HongCHUYU
 * @version 2016-06-04
 */
public class Operation extends DataEntity<Operation> {
	
	private static final long serialVersionUID = 1L;
	private String loginName;// 登录名
	private String name; 	// 名称

}


