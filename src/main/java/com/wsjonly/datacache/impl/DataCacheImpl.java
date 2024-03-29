package com.wsjonly.datacache.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Tuple;

import com.wsjonly.cache.CacheClient;
import com.wsjonly.cache.KeyGenerator;
import com.wsjonly.cache.impl.DefaultKeyGenerator;
import com.wsjonly.datacache.DataCache;


public class DataCacheImpl implements DataCache {
	private static final Logger log = LoggerFactory.getLogger(DataCacheImpl.class);
	public static final String DEFAULT_CHARSET = "UTF-8";
	
	private final String cacheName;
	private final RedisCacheClient cacheClient;
	private int cacheTimeSeconds = 600;
	private KeyGenerator keyGenerator = new DefaultKeyGenerator();
	
	public DataCacheImpl(String cacheName, RedisCacheClient cacheClient){
		this.cacheName = (cacheName != null)?cacheName:"DEFAULT";
		this.cacheClient = cacheClient;
	}
	
	public String createKey(Object key){
//		return this.keyGenerator.createKey(this.cacheName, key);
		return key.toString();
	}
	
	public String removeKeyPrefix(Object key){
	    return key.toString().replace(this.cacheName+"_", "");
	}
	
    public Object get(Object key) {
        return this.cacheClient.get(this.createKey(key));
    }

    public void put(Object key, Object value) {
        this.cacheClient.set(this.createKey(key), cacheTimeSeconds, value);
    }	
    
    public void put(Object key, int cacheTimeSeconds, Object value){
        this.cacheClient.set(this.createKey(key), cacheTimeSeconds, value);
    }
	
	public String getString(String key) {
		return this.cacheClient.getString(this.createKey(key));
	}
	
    public void putString(String key, String value) {
        this.cacheClient.setString(this.createKey(key), cacheTimeSeconds, value);
    }	
    
    public void putString(String key, int cacheTimeSeconds, String value) {
        this.cacheClient.setString(this.createKey(key), cacheTimeSeconds, value);
    }

	public boolean remove(String key) {
		return this.cacheClient.delete(this.createKey(key));
	}

	public boolean exist(String key) {
		return this.cacheClient.exists(this.createKey(key));
	}
	
	public void expire(String key, int seconds){
		this.cacheClient.expire(this.createKey(key), seconds);
	}
	
    public void rename(String key,String newKey){
        this.cacheClient.rename(this.createKey(key), this.createKey(newKey));
    }
    
    public boolean setnx(String key,String value){
        return this.cacheClient.setnx(this.createKey(key), cacheTimeSeconds, value);
    }
	
	public String getSet(String key, String value) {
		// TODO Auto-generated method stub
		return this.cacheClient.getSet(this.createKey(key), value);
	}
 
    
	public Set<String> keys(String pattern){
		return this.cacheClient.keys(this.createKey(pattern));
	}

	public int getCacheTimeSeconds() {
		return cacheTimeSeconds;
	}

	public void setCacheTimeSeconds(int cacheTimeSeconds) {
		this.cacheTimeSeconds = cacheTimeSeconds;
	}

	public KeyGenerator getKeyGenerator() {
		return keyGenerator;
	}

	public void setKeyGenerator(KeyGenerator keyGenerator) {
		this.keyGenerator = keyGenerator;
	}

	public String getCacheName() {
		return cacheName;
	}

	public CacheClient getCacheClient() {
		return cacheClient;
	}
	
	///////////////////////////////////////////
    ///////// 缓存Sorted Set类型数据操作  ////////////
    ///////////////////////////////////////////
	public boolean zAdd(String key,long score,String value){
	    return this.cacheClient.zAdd(this.createKey(key), score, value);
	}
	
	public Set<String> zRange(String key,long start,long end){
	    return this.cacheClient.zRange(this.createKey(key), start, end);
	}
	
    public Set<String> zRevRange(String key,long start,long end){
        return this.cacheClient.zRevRange(this.createKey(key), start, end);
    }
	
	public Set<Tuple> zRevRangeWithScore(String key,long start,long end){
	    return this.cacheClient.zRevRangeWithScore(this.createKey(key), start, end);
	}
	
	public boolean zRem(String key,String value){
	    return this.cacheClient.zRem(this.createKey(key), value);
	}
	
	public long zCount(String key){
	    return this.cacheClient.zCount(this.createKey(key));
	}
	
	public boolean zExist(String key,String member){
	    return this.cacheClient.zExist(this.createKey(key), member);
	}
	
	public long zScore(String key,String member){
	    return this.cacheClient.zScore(this.createKey(key), member);
	}
	
	public Set<String> zRangeByScore(String key,String min,String max){
	    return this.cacheClient.zRangeByScore(this.createKey(key), min, max);
	}
	
    public Set<String> zRevRangeByScore(String key,String min,String max){
        return this.cacheClient.zRevRangeByScore(this.createKey(key), min, max);
    }	
	
	public long zRemRangeByScore(String key,String min,String max){
	    return this.cacheClient.zRemRangeByScore(this.createKey(key), min, max);
	}
	
	///////////////////////////////////////////
	///////// 缓存中的HashMap操作 /////////////////
	///////////////////////////////////////////
	public boolean hSet(String key,String field,String value){
		return this.cacheClient.hSet(this.createKey(key), field, value);
	}
	
	public boolean hSet(String key,Map<String,String> values){
		return this.cacheClient.hSet(this.createKey(key), values);
	}
	
	public Object hGet(String key,String field){
		return this.cacheClient.hGet(this.createKey(key), field);
	}
	
	public boolean hDelete(String key,String field){
		return this.cacheClient.hDelete(this.createKey(key), field);
	}
	
	public boolean hDelete(String key,List<String> fields){
		return this.cacheClient.hDelete(this.createKey(key), fields);
	}
	
	public long hLength(String key){
		return this.cacheClient.hLength(this.createKey(key));
	}
	
	public boolean hExists(String key,String field){
		return this.cacheClient.hExists(this.createKey(key), field);
	}
	
	public Map<String,String> hGetAll(String key){
	    return this.cacheClient.hGetAll(this.createKey(key));
	}
	
	public Set<String> hKeys(String key){
	    return this.cacheClient.hKeys(this.createKey(key));
	}
	
	public List<String> hValues(String key){
		return this.cacheClient.hValues(this.createKey(key));
	}

	//get object
	public byte[] getBytes(String key) {
		// TODO Auto-generated method stub
		return this.cacheClient.getBytes(encodeString(key));
	}
	
	//set object
	public boolean setBytes(String key, byte[] value) {
		// TODO Auto-generated method stub
		return this.cacheClient.setBytes(encodeString(key), value);
	}

    /**
     * Encode a string into the current character set.
     */
    private byte[] encodeString(String in) {
        byte[] rv = null;
        try {
            rv = in.getBytes(DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return rv;
    }

}
