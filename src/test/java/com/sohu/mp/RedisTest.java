package com.sohu.mp;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wsjonly.datacache.DataCache;
import com.wsjonly.datacache.DataCacheManager;

public class RedisTest {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
		
		DataCacheManager dataCacheManager = (DataCacheManager) ctx.getBean("dataCacheManager");
		DataCache dataCache = dataCacheManager.getMpAccountsCache();
		System.out.println(dataCache.exist("*"));
	}
}
