package com.sohu.mp.cache;

/**
 * User: shunlongli
 * Date: 2010-3-18
 * Time: 16:46:48
 */
public interface KeyGenerator {
    String createKey(String cacheName, Object key);
}
