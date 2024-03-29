package com.wsjonly.cache;

import com.wsjonly.cache.CacheClient;

import java.util.Map;

/**
 * User: shunlongli
 * Date: 2010-3-19
 * Time: 13:41:11
 */
public interface CacheClientFactory {
    CacheClient createCacheClient(Map<String, String> properties);
}
