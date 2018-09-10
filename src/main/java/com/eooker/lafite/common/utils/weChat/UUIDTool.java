package com.eooker.lafite.common.utils.weChat;

import java.util.UUID; 

/**
 * UUID生成类
 * @author xiyatu
 *
 */
public class UUIDTool {   
 
    public static String generate() {   
        UUID uuid = UUID.randomUUID();   
        String str = uuid.toString();   
        // 去掉"-"符号   
        String temp = str.substring(0, 8) 
        		+ str.substring(9, 13) 
        		+ str.substring(14, 18) 
        		+ str.substring(19, 23) 
        		+ str.substring(24);   
        return temp;   
    }   
    
} 
