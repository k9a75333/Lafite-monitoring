package com.eooker.lafite.common.timer; 

import org.apache.tools.ant.types.resources.selectors.Date;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

	/**
     * Created by LinZihao on 2018-08-12.
     */
    @Component
    public class TaskTest {
     
    	Date data = new Date();
        int a=0;
//        @Scheduled(cron="10/5 * * * * ? ")//5秒一次
        public void task(){
            System.out.println("第"+a+++"个5秒");
        }
    	
    	
    }