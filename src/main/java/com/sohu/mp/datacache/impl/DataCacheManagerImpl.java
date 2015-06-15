package com.sohu.mp.datacache.impl;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sohu.mp.cache.CacheClientFactory;
import com.sohu.mp.cache.config.CacheConfig;
import com.sohu.mp.cache.config.CacheConfigs;
import com.sohu.mp.cache.config.ConfigHandler;
import com.sohu.mp.datacache.DataCache;
import com.sohu.mp.datacache.DataCacheManager;

/**
 * create time: 2013-6-21 下午02:05:28
 * @author dexingyang
 */
public class DataCacheManagerImpl implements DataCacheManager {

	private static final Logger log = LoggerFactory.getLogger(DataCacheManagerImpl.class);
	
	private String configFile = "datacache.xml";
	
	private CacheConfigs cacheConfigs;
	
	private RedisCacheClient cacheClient;
	
	private Map<String, DataCache> caches = new ConcurrentHashMap<String, DataCache>();
	
	public void init(){
		this.configure(this.configFile);
	}
	
	public void destroy(){
		cacheClient.shutdown();
	}
	
	/**
     * 根据配置文件初始化缓存
     * @param configFile
     */
    public void configure(String configFile){
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(configFile);
        try {
            cacheConfigs = ConfigHandler.fromXML(CacheConfigs.class, is);

            CacheClientFactory cacheClientFactory = (RedisCacheClientFactory) Class.forName(cacheConfigs.getCacheClientConfig().getFactory()).newInstance();
            cacheClient = (RedisCacheClient)cacheClientFactory.createCacheClient(cacheConfigs.getCacheClientConfig().getProperties());

            for(CacheConfig cacheConfig: cacheConfigs.getCacheConfigs()){
                DataCacheImpl cache = new DataCacheImpl(cacheConfig.getName(), cacheClient);
                cache.setCacheTimeSeconds(Integer.parseInt(cacheConfig.getCacheTimeSeconds()));
                this.caches.put(cache.getCacheName(), cache);
            }
        } catch (Exception e) {
            log.error("cache configure error: ",e);
            if(cacheClient != null){
                cacheClient.shutdown();
            }
        }
    }
	
    /**
     * 获取指定名称的缓存
     */
	@Override
	public DataCache getDataCache(String cacheName) {
		return this.caches.get(cacheName);
	}

	/**
	 * 关闭指定名称的缓存
	 */
	@Override
	public void closeDataCache(String cacheName) {
		this.caches.remove(cacheName);
	}

	/**
	 * 重新配置缓存
	 */
	@Override
	public void reload() {
		this.cacheClient.shutdown();
		this.configure(configFile);
	}
	
	
//	@Override
//	public DataCache getMpAdCache(){
//		return this.getDataCache(DataCache.CACHE_NAME_MP_AD);
//	}
	
	
	
	public String getConfigFile() {
		return configFile;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	@Override
	public DataCache getMpAccountsCache() {
		// TODO Auto-generated method stub
		System.out.println("getMpAccpintsCache");
		return this.getDataCache(DataCache.CACHE_MP_ACCOUNTS);
	}

}
