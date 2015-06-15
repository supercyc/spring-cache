package com.sohu.mp.datacache;

/**
 * 缓存数据存储管理接口类
 * 从datacache.xml中读取缓存配置信息并初始化
 * create time: 2013-6-21 下午02:00:41
 * @author dexingyang
 */
public interface DataCacheManager {
	
	/**
     * 获取指定缓存
     * @param cacheName
     * @return
     */
    public DataCache getDataCache(String cacheName);

    /**
     * 关闭指定缓存
     * @param cacheName
     * @return
     */
    public void closeDataCache(String cacheName);

    /**
     * 重新初始化缓存
     */
    public void reload();
   
    public DataCache getMpAccountsCache();
        
}
