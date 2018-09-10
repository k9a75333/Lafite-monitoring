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
import com.eooker.lafite.modules.sys.entity.Menu;
import com.eooker.lafite.modules.sys.entity.Office;
import com.eooker.lafite.modules.sys.entity.Privilege;
import com.eooker.lafite.modules.sys.entity.Role;
import com.eooker.lafite.modules.sys.entity.User;
import com.eooker.lafite.modules.sys.security.SystemAuthorizingRealm;
import com.eooker.lafite.modules.sys.utils.UUIDTool;
import com.eooker.lafite.modules.sys.utils.UserUtils;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * @author HongCHUYU
 * @version 2018/01/01
 */
@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService implements InitializingBean {
	
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	public static final String LOGIN_FAIL_IP_CACHING= "LoginFailIpCaching";
	public static final String USER_CACHING= "UserCaching";
	public static final String USER_LOGINED_CACHING= "UserLoginedCaching";
	  
	public static final String IOS_SYSTEM = "ios";
	public static final String ANDRIOD_SYSTEM = "android";
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private SessionDAO sessionDao;
	@Autowired
	private SystemAuthorizingRealm systemRealm;
	
	public SessionDAO getSessionDao() {
		return sessionDao;
	}

//	@Autowired
//	private IdentityService identityService;

	//-- User Service --//
	
	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	public User getUser(String id) {
		return UserUtils.get(id);
	}

	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return
	 */
	public User getUserByLoginName(String loginName) {
		return UserUtils.getByLoginName(loginName);
	}
	public User getUserByNumber(String number) {
		return userDao.getByNumber(number);
	}
	public User getUserByPhone(String phone) {
		return userDao.getByPhone(phone);
	}
	
	public Page<User> findUser(Page<User> page, User user) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		// 设置分页参数
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findList(user));
		return page;
	}
	
	/**
	 * 无分页查询人员列表
	 * @param user
	 * @return
	 */
	public List<User> findUser(User user){
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		List<User> list = userDao.findList(user);
		return list;
	}

	/**
	 * 通过部门ID获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> findUserByOfficeId(String officeId) {
		List<User> list = (List<User>)CacheUtils.get(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId);
		if (list == null){
			User user = new User();
			user.setOffice(new Office(officeId));
			list = userDao.findUserByOfficeId(user);
			CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId, list);
		}
		return list;
	}
	
	@Transactional(readOnly = false)
	public void saveUser(User user) throws LafiteException {
		try {
			if (StringUtils.isBlank(user.getId())){
				user.preInsert();
				userDao.insert(user);
			}else{
				// 清除原用户机构用户缓存
				User oldUser = userDao.get(user.getId());
				if (oldUser.getOffice() != null && oldUser.getOffice().getId() != null){
					CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + oldUser.getOffice().getId());
				}
				// 更新用户数据
				user.preUpdate();
				userDao.update(user);
			}
			if (StringUtils.isNotBlank(user.getId())){
				// 更新用户与角色关联
				userDao.deleteUserRole(user);
				if (user.getRoleList() != null && user.getRoleList().size() > 0){
					userDao.insertUserRole(user);
				}else{
					throw new ServiceException(user.getLoginName() + "没有设置角色！");
				}
				// 将当前用户同步到Activiti
				//saveActivitiUser(user);
				// 清除用户缓存
				UserUtils.clearCache(user);
//				// 清除权限缓存
//				systemRealm.clearAllCachedAuthorizationInfo();
			}
		} catch (Exception e) {
//			logger.error(ExceptionUtils.getStackTrace(e));
//			throw SysException.makeServiceException(e.getMessage());
			throw new LafiteException(CommonCode.PARAM_INVALID,e.getMessage());
		}
	}
	
	@Transactional(readOnly = false)
	public void updateUserInfo(User user) throws LafiteException {
		try {
			user.preUpdate();
			//userDao.updateUserInfo(user);
			// 清除用户缓存
			UserUtils.clearCache(user);
//			// 清除权限缓存
//			systemRealm.clearAllCachedAuthorizationInfo();
		} catch (Exception e) {
			throw new LafiteException(CommonCode.PARAM_INVALID,e.getMessage());
		}
	}
	
	@Transactional(readOnly = false)
	public void deleteUser(User user) throws LafiteException {
		try {
			userDao.delete(user);
			// 同步到Activiti
			//deleteActivitiUser(user);
			// 清除用户缓存
			UserUtils.clearCache(user);
//			// 清除权限缓存
//			systemRealm.clearAllCachedAuthorizationInfo();
		} catch (Exception e) {
			throw new LafiteException(CommonCode.PARAM_INVALID,e.getMessage());
		}
	}
	
	@Transactional(readOnly = false)
	public void updatePasswordById(String id, String loginName, String newPassword) throws LafiteException {
		try {
			User user = new User(id);
			user.setPassword(entryptPassword(newPassword));
			userDao.updatePasswordById(user);
			// 清除用户缓存
			user.setLoginName(loginName);
			UserUtils.clearCache(user);
//			// 清除权限缓存
//			systemRealm.clearAllCachedAuthorizationInfo();
		} catch (Exception e) {
			throw new LafiteException(CommonCode.PARAM_INVALID,e.getMessage());
		}
	}
	
	@Transactional(readOnly = false)
	public void updateUserLoginInfo(User user) throws LafiteException {
		try {
			user.setLoginIp(StringUtils.getRemoteAddr(Servlets.getRequest()));
			userDao.updateLoginInfo(user);
		} catch (Exception e) {
			throw new LafiteException(CommonCode.PARAM_INVALID,e.getMessage());
		}
	}
	
	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
	}
	
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * 获得活动会话
	 * @return
	 */
	public Collection<Session> getActiveSessions(){
		return sessionDao.getActiveSessions(false);
	}
	
	//-- Role Service --//
	
	public Role getRole(String id) {
		return roleDao.get(id);
	}

	public Role getRoleByName(String name) {
		Role r = new Role();
		r.setName(name);
		return roleDao.getByName(r);
	}
	
	public Role getRoleByEnname(String enname) {
		Role r = new Role();
		r.setEnname(enname);
		return roleDao.getByEnname(r);
	}
	
	public List<Role> findRole(Role role){
		return roleDao.findList(role);
	}
	
	public List<Role> findAllRole(){
		return UserUtils.getRoleList();
	}
	
	@Transactional(readOnly = false)
	public void saveRole(Role role) throws LafiteException {
		try {
			if (StringUtils.isBlank(role.getId())){
				role.preInsert();
				roleDao.insert(role);
				// 同步到Activiti
				//saveActivitiGroup(role);
			}else{
				role.preUpdate();
				roleDao.update(role);
			}
			// 更新角色与菜单关联
			roleDao.deleteRoleMenu(role);
			if (role.getMenuList().size() > 0){
				roleDao.insertRoleMenu(role);
			}
			// 更新角色与部门关联
			roleDao.deleteRoleOffice(role);
			if (role.getOfficeList().size() > 0){
				roleDao.insertRoleOffice(role);
			}
			// 同步到Activiti
			//saveActivitiGroup(role);
			// 清除用户角色缓存
			UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
//			// 清除权限缓存
//			systemRealm.clearAllCachedAuthorizationInfo();
		} catch (Exception e) {
			throw new LafiteException(CommonCode.PARAM_INVALID,e.getMessage());
		}
	}

	@Transactional(readOnly = false)
	public void deleteRole(Role role) throws LafiteException {
		try {
			roleDao.delete(role);
			// 同步到Activiti
			//deleteActivitiGroup(role);
			// 清除用户角色缓存
			UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
//			// 清除权限缓存
//			systemRealm.clearAllCachedAuthorizationInfo();
		} catch (Exception e) {
			throw new LafiteException(CommonCode.PARAM_INVALID,e.getMessage());
		}
	}
	
	@Transactional(readOnly = false)
	public Boolean outUserInRole(Role role, User user) throws LafiteException {
		List<Role> roles = user.getRoleList();
		for (Role e : roles){
			if (e.getId().equals(role.getId())){
				roles.remove(e);
				saveUser(user);
				return true;
			}
		}
		return false;
	}
	
	@Transactional(readOnly = false)
	public User assignUserToRole(Role role, User user) throws LafiteException {
		if (user == null){
			return null;
		}
		List<String> roleIds = user.getRoleIdList();
		if (roleIds.contains(role.getId())) {
			return null;
		}
		user.getRoleList().add(role);
		saveUser(user);
		return user;
	}

	//-- Menu Service --//
	
	public Menu getMenu(String id) {
		return menuDao.get(id);
	}

	public List<Menu> findAllMenu(){
		return UserUtils.getMenuList();
	}
	
	@Transactional(readOnly = false)
	public void saveMenu(Menu menu) throws LafiteException {
		try {
			// 获取父节点实体
			menu.setParent(this.getMenu(menu.getParent().getId()));
			
			// 获取修改前的parentIds，用于更新子节点的parentIds
			String oldParentIds = menu.getParentIds(); 
			
			// 设置新的父节点串
			menu.setParentIds(menu.getParent().getParentIds()+menu.getParent().getId()+",");

			// 保存或更新实体
			if (StringUtils.isBlank(menu.getId())){
				menu.preInsert();
				menuDao.insert(menu);
			}else{
				menu.preUpdate();
				menuDao.update(menu);
			}
			
			// 更新子节点 parentIds
			Menu m = new Menu();
			m.setParentIds("%,"+menu.getId()+",%");
			List<Menu> list = menuDao.findByParentIdsLike(m);
			for (Menu e : list){
				e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
				menuDao.updateParentIds(e);
			}
			// 清除用户菜单缓存
			UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//			// 清除权限缓存
//			systemRealm.clearAllCachedAuthorizationInfo();
			// 清除日志相关缓存
			CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
		} catch (Exception e) {
			throw new LafiteException(CommonCode.PARAM_INVALID,e.getMessage());
		}
	}

	@Transactional(readOnly = false)
	public void updateMenuSort(Menu menu) throws LafiteException {
		try {
			menuDao.updateSort(menu);
			// 清除用户菜单缓存
			UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//			// 清除权限缓存
//			systemRealm.clearAllCachedAuthorizationInfo();
			// 清除日志相关缓存
			CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
		} catch (Exception e) {
			throw new LafiteException(CommonCode.PARAM_INVALID,e.getMessage());
		}
	}

	@Transactional(readOnly = false)
	public void deleteMenu(Menu menu) throws LafiteException {
		try {
			menuDao.delete(menu);
			// 清除用户菜单缓存
			UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//			// 清除权限缓存
//			systemRealm.clearAllCachedAuthorizationInfo();
			// 清除日志相关缓存
			CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
		} catch (Exception e) {
			throw new LafiteException(CommonCode.PARAM_INVALID,e.getMessage());
		}
	}
	
	/**
	 * 查看是否该账号存在
	 * @return
	 */
	public String IdIsExist(String number) {
		return userDao.IdIsExist(number);
	}
	
	/**
	 * 获取Key加载信息
	 */
	public static boolean printKeyLoadMessage(){
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n======================================================================\r\n");
		sb.append("\r\n    欢迎使用 "+Global.getConfig("productName")+"  - Powered By Eooker\r\n");
		sb.append("\r\n======================================================================\r\n");
		System.out.println(sb.toString());
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
	}
	
}
