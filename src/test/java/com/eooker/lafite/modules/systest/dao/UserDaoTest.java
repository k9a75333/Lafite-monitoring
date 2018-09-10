package com.eooker.lafite.modules.systest.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eooker.lafite.modules.sys.dao.UserDao;
import com.eooker.lafite.modules.sys.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-shiro.xml",
		"classpath*:spring-context.xml","classpath*:spring-context-jedis.xml",
		"classpath*:spring-context-activiti.xml","classpath*:mybatis-config.xml"})
public class UserDaoTest {
	
	@Autowired
	private UserDao UserDao;

	@Before
	public void setUp() throws Exception {
	}
	
	/*测试通过number来获取用户新的Dao*/
	@Test
	public void testGetByNumber() {
		String number = "admin";
		User user = UserDao.getByNumber(number);
		System.out.println(user.getName());
	}
//
//	@Test
//	public void testGetByPhone() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testIdIsExist() {
//		fail("Not yet implemented");
//	}

}
