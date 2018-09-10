package com.eooker.lafite.common.utils;

import com.jfinal.plugin.ehcache.CacheKit;

public class CacheUtil extends CacheKit{
	
	public static <T> T get(String cacheName,Object key){
		T value = null;
		try{
			value = CacheKit.get(cacheName, key);
		}catch(NullPointerException e){
//			e.printStackTrace();
		}
		return value;
	}
	
	public static int getToInt(String cacheName,Object key){
		int value = 0;
		try{
			value = CacheKit.get(cacheName, key);
		}catch(NullPointerException e){
			value = 0;
//			e.printStackTrace();
		}
		return value;
	}

}
