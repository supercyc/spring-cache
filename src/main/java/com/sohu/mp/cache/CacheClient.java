package com.sohu.mp.cache;

import java.util.Map;

/**
 * User: shunlongli
 * Date: 2010-3-18
 * Time: 16:36:45
 */
public interface CacheClient {

    /**
     * 从Memcached中获取缓存对象
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 一次从Memcached中获取多个缓存对象
     * @param keys
     * @return
     */
    Map<String, Object> getMulti(String... keys);

    /**
     * 向Memcached中存入对象
     * @param key
     * @param cacheTimeSeconds 缓存时间
     * @param o
     */
    void set(String key, int cacheTimeSeconds, Object o);

    /**
     * 从Memcached中删除缓存对象
     * @param key
     */
    boolean delete(String key);

    /**
     * 原子递增
     * @param key
     * @param factor 递增值
     * @param startingValue 初始值
     */
    void incr(String key, int factor, int startingValue);

    /**
     * 关闭Memcached客户端
     */
    void shutdown();

}
