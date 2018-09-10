/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.eooker.lafite.modules.operation.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eooker.lafite.common.persistence.Page;
import com.eooker.lafite.common.web.BaseController;
import com.eooker.lafite.common.utils.StringUtils;
import com.eooker.lafite.modules.maintainance.entity.Maintainance;
import com.eooker.lafite.modules.maintainance.service.MaintainanceService;
import com.eooker.lafite.modules.sys.entity.User;
import com.eooker.lafite.modules.sys.utils.UserUtils;

/**
 * 运营Controller
 * @author HongCHUYU
 * @version 2016-06-04
 */
@Controller
@RequestMapping(value = "${adminPath}/operate/operate")
public class OperationController extends BaseController {


}
