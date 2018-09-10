package com.eooker.lafite.modules.systest.web;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eooker.lafite.common.utils.PwdEncryptionUtils;
import com.eooker.lafite.modules.sys.entity.Office;
import com.eooker.lafite.modules.sys.service.OfficeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-context-shiro.xml",
		"classpath*:spring-context.xml","classpath*:spring-context-jedis.xml",
		"classpath*:spring-context-activiti.xml","classpath*:mybatis-config.xml"})

public class OfficeControllerTest {
	@Autowired
	private OfficeService officeService;
	
	@Before
    public void setUp() throws Exception {
		
    }
	
	/*登陆测试的JUnit*/
	@Test
	public void testOfficeList(){
		String id = "1";
		Office office = new Office();
		office.setId(id);
		List<Office> list = officeService.findList(office);
		for(int i=0;i<list.size();i++){
			System.out.println(i+"   "+list.get(i).getId()+list.get(i).getName());
		}
	}

}
