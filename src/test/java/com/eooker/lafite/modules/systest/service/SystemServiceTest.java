package com.eooker.lafite.modules.systest.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eooker.lafite.common.utils.PwdEncryptionUtils;
import com.eooker.lafite.modules.sys.entity.User;
import com.eooker.lafite.modules.sys.service.SystemService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-shiro.xml",
		"classpath*:spring-context.xml","classpath*:spring-context-jedis.xml",
		"classpath*:spring-context-activiti.xml","classpath*:mybatis-config.xml"})
public class SystemServiceTest {
	
	@Autowired
	private SystemService systemService;
	
	@Before
	public void setUp() throws Exception {
		
	}
	
	/*登陆测试的JUnit*/
	@Test
	public void testLogin() {
		String password = "admin";
		String number = "admin";
		PwdEncryptionUtils utils=new PwdEncryptionUtils();
		
		String tokenCredentials=utils.encryp(password);
		User user = systemService.getUserByNumber(number);
		String accountCredentials=user.getPassword();
		//当密码正确时
		if (tokenCredentials.equals(accountCredentials)) {
			System.out.println("success");
		}else 
			System.out.println("fail");
	}

}
