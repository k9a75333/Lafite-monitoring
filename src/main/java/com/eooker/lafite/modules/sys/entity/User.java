/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.eooker.lafite.modules.sys.entity;

import java.util.Date;
import java.util.List;


import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.eooker.lafite.common.config.Global;
import com.eooker.lafite.common.persistence.DataEntity;
import com.eooker.lafite.common.supcan.annotation.treelist.cols.SupCol;
import com.eooker.lafite.common.utils.Collections3;
import com.eooker.lafite.common.utils.excel.annotation.ExcelField;
import com.eooker.lafite.common.utils.excel.fieldtype.RoleListType;

/**
 * 用户Entity
 * @author HongCHUYU
 * @version 2018/01/01
 */
public class User extends DataEntity<User> {

	private static final long serialVersionUID = 1L;
	private Office office;	// 归属部门
	private Role role;	// 根据角色查询用户条件
	
	private String officeId;
	private String loginName;// 登录名
	private String oldLoginName;//旧登录名
	private String loginIp;	// 最后登陆IP
	private String loginFlag;	// 是否允许登陆
	private String number;		// 工号
	private String idNumber;		// 身份证号码
	private String phone;		// 电话号码
	private String email;		// EMAIL
	private String isusing;		// 0:&aring;
	private String password;		// 密码
	private String newPassword;  //新密码
	private String iconurl;		// 头像URL
	private String sex;		// 0为女，1为男
	private String usertypeid;		// usertypeid
	private String name;		// name
	private String loginip;		// loginip
	private String pushId;
	
	private List<Role> roleList = Lists.newArrayList(); // 拥有角色列表

	public User() {
		super();
		this.loginFlag = Global.YES;
	}
	
	public User(String id){
		super(id);
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public User(String id, String loginName){
		super(id);
		this.loginName = loginName;
	}
	
	public String getOldLoginName() {
		return oldLoginName;
	}

	public void setOldLoginName(String oldLoginName) {
		this.oldLoginName = oldLoginName;
	}

	public User(Role role){
		super();
		this.role = role;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	@SupCol(isUnique="true", isHide="true")
	@ExcelField(title="ID", type=1, align=2, sort=1)
	public String getId() {
		return id;
	}

	
	@Length(min=1, max=100, message="登录名长度必须介于 1 和 100 之间")
	@ExcelField(title="登录名", align=2, sort=30)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@ExcelField(title="角色role", align=2, sort=108,value="role.name",type=1)
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
		
	}

	@JsonIgnore
	@ExcelField(title="拥有角色", align=1, sort=800, fieldType=RoleListType.class,type=2)
	public List<Role> getRoleList() {
		return roleList;
	}
	
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	@JsonIgnore
	public List<String> getRoleIdList() {
		List<String> roleIdList = Lists.newArrayList();
		for (Role role : roleList) {
			roleIdList.add(role.getId());
		}
		return roleIdList;
	}

	public void setRoleIdList(List<String> roleIdList) {
		roleList = Lists.newArrayList();
		for (String roleId : roleIdList) {
			Role role = new Role();
			role.setId(roleId);
			roleList.add(role);
		}
	}
	
	/**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 */
	public String getRoleNames() {
		return Collections3.extractToString(roleList, "name", ",");
	}
	
	public boolean isAdmin(){
		return isAdmin(this.id);
	}
	
	public static boolean isAdmin(String id){
		return id != null && "1".equals(id);
	}
	@ExcelField(title="学号", align=2, sort=2)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@ExcelField(title="联系方式", align=2, sort=30)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/*@ExcelField(title="邮箱", align=2, sort=30)*/
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIsusing() {
		return isusing;
	}

	public void setIsusing(String isusing) {
		this.isusing = isusing;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIconurl() {
		return iconurl;
	}

	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}
	@ExcelField(title="性别", align=2, sort=8,dictType="sex")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getUsertypeid() {
		return usertypeid;
	}

	public void setUsertypeid(String usertypeid) {
		this.usertypeid = usertypeid;
	}

	@ExcelField(title="姓名", align=2, sort=8)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginip() {
		return loginip;
	}

	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}

//	public String getDeptpid() {
//		return deptpid;
//	}
//
//	public void setDeptpid(String deptpid) {
//		this.deptpid = deptpid;
//	}


	/*@ExcelField(title="年级", align=2, sort=9)
	public Dept getPdept() {
		return dept.getParent();
	}

	public void setPdept(Dept pdept) {
		this.pdept = pdept;
		dept.setParent(pdept);		
	}*/
	
	
	/*public Dept getPsdept() {
		return dept.getParent().getParent();
	}

	public void setPsdept(Dept psdept) {
		this.psdept = psdept;
		dept.getParent().setParent(psdept);
	}*/
	

	@Override
	public String toString() {
		return id;
	}

	@ExcelField(title="身份证号", align=2, sort=16)
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getPushId() {
		return pushId;
	}

	public void setPushId(String pushId) {
		this.pushId = pushId;
	}
	
}