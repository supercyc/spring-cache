package com.sohu.mp.cache;

import java.util.Properties;

/**
 * 缓存管理接口类，负责从cache.xml中读取缓存配置信息并初始化缓存
 * 同时提供缓存管理的接口
 * User: shunlongli
 * Date: 2010-3-11
 * Time: 17:41:20
 */
public interface CacheManager {

    /**
     * 获取指定缓存
     * @param cacheName
     * @return
     */
    public Cache getCache(String cacheName);

    /**
     * 关闭指定缓存
     * @param cacheName
     * @return
     */
    public void closeCache(String cacheName);

    /**
     * 重新初始化缓存
     */
    public void reload();

    Cache getObjectCache();

    Cache getListCache();
    
    Cache getKVCache();

    Cache getSystemCache();

    Cache getAssisCache();
}
