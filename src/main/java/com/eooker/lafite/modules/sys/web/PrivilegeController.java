/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.eooker.lafite.modules.sys.web;

import java.util.List;
import java.util.Map;

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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.eooker.lafite.common.config.Global;
import com.eooker.lafite.common.web.BaseController;
import com.eooker.lafite.common.utils.StringUtils;
import com.eooker.lafite.common.utils.SysException;
import com.eooker.lafite.modules.sys.entity.Privilege;
import com.eooker.lafite.modules.sys.service.PrivilegeService;

/**
 * 模块权限Controller
 * @author Hades
 * @version 2017-03-30
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/privilege")
public class PrivilegeController extends BaseController {

	@Autowired
	private PrivilegeService privilegeService;
	
	@ModelAttribute
	public Privilege get(@RequestParam(required=false) String id) {
		Privilege entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = privilegeService.get(id);
		}
		if (entity == null){
			entity = new Privilege();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:privilege:view")
	@RequestMapping(value = {"list", ""})
	public String list(Privilege privilege, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Privilege> list = privilegeService.findList(privilege); 
		model.addAttribute("list", list);
		return "modules/sys/privilegeList";
	}

	@RequiresPermissions("sys:privilege:view")
	@RequestMapping(value = "form")
	public String form(Privilege privilege, Model model) {
		if (privilege.getParent()!=null && StringUtils.isNotBlank(privilege.getParent().getId())){
			privilege.setParent(privilegeService.get(privilege.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(privilege.getId())){
				Privilege privilegeChild = new Privilege();
				privilegeChild.setParent(new Privilege(privilege.getParent().getId()));
				List<Privilege> list = privilegeService.findList(privilege); 
				if (list.size() > 0){
					privilege.setSort(list.get(list.size()-1).getSort());
					if (privilege.getSort() != null){
						privilege.setSort(privilege.getSort() + 30);
					}
				}
			}
		}
		if (privilege.getSort() == null){
			privilege.setSort(30);
		}
		model.addAttribute("privilege", privilege);
		return "modules/sys/privilegeForm";
	}

	@RequiresPermissions("sys:privilege:edit")
	@RequestMapping(value = "save")
	public String save(Privilege privilege, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, privilege)){
			return form(privilege, model);
		}
		privilegeService.save(privilege);
		addMessage(redirectAttributes, "保存模块权限成功");
		return "redirect:"+Global.getAdminPath()+"/sys/privilege/?repage";
	}
	
	@RequiresPermissions("sys:privilege:edit")
	@RequestMapping(value = "delete")
	public String delete(Privilege privilege, RedirectAttributes redirectAttributes) {
		privilegeService.delete(privilege);
		addMessage(redirectAttributes, "删除模块权限成功");
		return "redirect:"+Global.getAdminPath()+"/sys/privilege/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Privilege> list = privilegeService.findList(new Privilege());
		for (int i=0; i<list.size(); i++){
			Privilege e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}