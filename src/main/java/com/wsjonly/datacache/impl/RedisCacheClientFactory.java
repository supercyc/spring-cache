package com.wsjonly.datacache.impl;

import java.util.Map;

import org.apache.commons.pool.impl.GenericObjectPool.Config;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.wsjonly.cache.CacheClient;
import com.wsjonly.cache.CacheClientFactory;

/**
 * create time: 2013-6-21 下午03:49:40
 * @author dexingyang
 */
public class RedisCacheClientFactory implements CacheClientFactory {

	/**
	 * redis节点列表，形如host1:port1;host2:port2
	 */
	public static final String PROP_SERVERS = "servers";
	/**
	 * 连接超时时间，单位ms
	 */
	public static final String PROP_TIMEOUT = "timeout";
	/**
	 * 控制JedisPool中可分配多少个jedis实例
	 */
	public static final String PROP_MAX_ACTIVE = "maxActive";
	/**
	 * 控制JedisPool中最少有多少个状态为idle的jedis实例
	 */
	public static final String PROP_MIN_IDLE = "minIdle";
	/**
	 * 控制JedisPool中最多有多少个状态为idle的jedis实例
	 */
	public static final String PROP_MAX_IDLE = "maxIdle";
	/**
	 * 在borrow一个jedis实例时，最大的等待时间，单位是ms
	 */
	public static final String PROP_MAX_WAIT = "maxWait";
	
	private static JedisPool pool = null;
	
	public CacheClient createCacheClient(Map<String, String> properties) {
		
		RedisCacheClient cacheClient = null;
		try{
			String servers = properties.get(PROP_SERVERS);
			String[] arr = servers.split(";");
			String[] arr2 = arr[0].split(":");
			String server = arr2[0];
			int port = Integer.parseInt(arr2[1]);
			
			JedisPoolConfig config = new JedisPoolConfig();
			config.setTestOnBorrow(true);
			if(properties.get(PROP_MAX_ACTIVE) != null){
				int maxActive = Integer.parseInt(properties.get(PROP_MAX_ACTIVE));
				config.setMaxActive(maxActive);
			}
			if(properties.get(PROP_MIN_IDLE) != null){
			    int minIdle = Integer.parseInt(properties.get(PROP_MIN_IDLE));
			    config.setMinIdle(minIdle);
			}
			if(properties.get(PROP_MAX_IDLE) != null){
				int maxIdle = Integer.parseInt(properties.get(PROP_MAX_IDLE));
				config.setMaxIdle(maxIdle);
			}
			if(properties.get(PROP_MAX_WAIT) != null){
				int maxWait = Integer.parseInt(properties.get(PROP_MAX_WAIT));
				config.setMaxWait(maxWait);
			}
			
			if(properties.get(PROP_TIMEOUT) != null){
				int timeout = Integer.parseInt(properties.get(PROP_TIMEOUT));
				pool = new JedisPool(config,server,port,timeout);
			}else
				pool = new JedisPool(config,server,port);
			
			cacheClient = new RedisCacheClient(pool);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return cacheClient;
	}
	
	public static JedisPool getJedisPool(){
		return pool;
	}
}
