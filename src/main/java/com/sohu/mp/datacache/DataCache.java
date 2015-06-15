package com.sohu.mp.datacache;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Tuple;

/**
 * 提供给上层使用的缓存接口，缓存使用redis
 * create time: 2013-6-21 下午01:38:09
 * @author dexingyang
 */
public interface DataCache {
    public static final String CACHE_MP_ACCOUNTS= "MP_ACCOUNTS";
    
    public static final String CACHE_SYNC_ACCOUNTS = "ACCOUNTS_SYNC";
    
    /**
     * 创建缓存的key
     * @param key
     * @return
     */
    public String createKey(Object key);
    
    /**
     * 删除缓存key的前缀
     * @param key
     * @return
     */
    public String removeKeyPrefix(Object key);
    
    /**
     * 从缓存中获取对象
     * @param key
     * @return the cached object or <tt>null</tt>
     */
    public Object get(Object key);

    /**
     * 把一个对象加入缓存
     * @param key
     * @param value
     */
    public void put(Object key, Object value);
    
    /**
     * 把一个对象加入缓存，并设置过期时间
     * @param key
     * @param cacheTimeSeconds
     * @param value
     */
    public void put(Object key, int cacheTimeSeconds, Object value);
    
    /**
     * 从缓存中获取对象
     * @param key
     * @return the cached object or <tt>null</tt>
     */
    public String getString(String key);

    /**
     * 把一个字符串加入缓存
     * @param key
     * @param value
     */
    public void putString(String key, String value);
    
    /**
     * 把一个字符串加入缓存
     * @param key
     * @param value
     */
    public void putString(String key,int cacheTimeSeconds, String value);
   

    /**
     * 从缓存中删除对象
     * @param key
     */
    public boolean remove(String key);

    /**
     * 查询缓存中是否存在对象
     * @param key
     */
    public boolean exist(String key);
    
    /**
     * 设置key的过期时间
     * @param key
     */
    public void expire(String key, int secondes);
    
    /**
     * 获取所有匹配的key值
     * @param pattern
     */
    public Set<String> keys(String pattern);
    
    /**
     * 对key进行重命名
     * @param key
     * @param newKey
     */
    public void rename(String key,String newKey);
    
    /**
     * 当key不存在时，设置值
     * @param key
     * @param value
     * @return true表示key不存在，设置成功;false,表示key已存在
     */
    public boolean setnx(String key,String value);
    
    
    /**
     * 
     */
    public String getSet(String key, String value);
    ////////如下定义Sorted Set的操作//////////
    /**
     * 向Sorted Set添加一个成员
     * @param key
     * @param score
     * @param value
     * @return
     */
    public boolean zAdd(String key,long score,String value);
    
    /**
     * 获取Sorted Set中指定范围内的数据,按score从小到大
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zRange(String key,long start,long end);
    
    /**
     * 获取Sorted Set中指定范围内的数据,按score从大到小
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zRevRange(String key,long start,long end);
    
    /**
     * 获取Sorted Set中指定范围内的数据(包含score),按score从大到小
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Tuple> zRevRangeWithScore(String key,long start,long end);
    
    /**
     * 删除Soreted Set中的指定成员
     * @param key
     * @param value
     * @return
     */
    public boolean zRem(String key,String value);
    
    /**
     * 获取Sorted Set中的成员个数
     * @param key
     * @return
     */
    public long zCount(String key);
    
    /**
     * 判断成员是否存在于Sorted Set中
     * @param key
     * @param member
     * @return
     */
    public boolean zExist(String key,String member);
    
    /**
     * 获取Sorted Set中成员的score值
     * @param key
     * @param member
     * @return
     */
    public long zScore(String key,String member);
    
    /**
     * 获取指定score区间的元素,'-inf'和'+inf'表示最低和最高值
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<String> zRangeByScore(String key,String min,String max);
    
    /**
     * 获取指定score区间的元素,'-inf'和'+inf'表示最低和最高值,按score从大到小
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<String> zRevRangeByScore(String key,String min,String max);
    
    /**
     * 删除指定score区间的元素,'-inf'和'+inf'表示最低和最高值
     * @param key
     * @param min
     * @param max
     * @return
     */
    public long zRemRangeByScore(String key,String min,String max);
    
    /////////如下定义HashMap操作/////////////
    /**
     * 向缓存中的HashMap增加一个成员
     * @param key
     * @param filed HashMap中的key值
     * @param value
     */
    public boolean hSet(String key,String field,String value);
    
    /**
     * 向缓存中的HashMap添加一组成员
     * @param key
     * @param values
     * @return
     */
    public boolean hSet(String key,Map<String,String> values);
    
    /**
     * 从缓存中的HashMap获取给定filed关联的值
     * @param key
     * @param filed
     * @return
     */
    public Object hGet(String key,String field);
    
    /**
     * 从缓存中的HashMap删除给定的field
     * @param key
     * @param filed
     * @return
     */
    public boolean hDelete(String key,String field);
    
    /**
     * 从缓存的HashMap中删除指定的fields
     * @param key
     * @param fields
     * @return
     */
    public boolean hDelete(String key,List<String> fields);
    /**
     * 获取缓存中的HashMap的成员个数
     * @param key
     * @return
     */
    public long hLength(String key);
    
    /**
     * 判断缓存中的HashMap指定的filed是否存在
     * @param key
     * @param filed
     * @return
     */
    public boolean hExists(String key,String field);
    
    /**
     * 返回hash表中所有的key和value值
     * @param key
     * @return
     */
    public Map<String,String> hGetAll(String key);
    
    /**
     * 获取Hash中的所有key
     * @param key
     * @return
     */
    public Set<String> hKeys(String key);
    
    /**
     * 获取缓存中的HashMap的所有value
     * @param key
     * @return
     */
    public List<String> hValues(String key);
    
    
    
    public byte[] getBytes(String key);
    
    public boolean setBytes(String key, byte[] value);
}
