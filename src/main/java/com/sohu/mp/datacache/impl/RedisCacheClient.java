package com.sohu.mp.datacache.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.Tuple;

import com.sohu.mp.cache.CacheClient;
import com.sohu.mp.datacache.util.SerializeUtil;

/**
 * Redis访问的封装
 * create time: 2014-4-9 下午03:41:04
 * @author dexingyang
 */
public class RedisCacheClient implements CacheClient {

    private static final Logger log = LoggerFactory.getLogger(RedisCacheClient.class);
    
    private final JedisPool pool;
        
    public RedisCacheClient(JedisPool pool){
        this.pool = pool;
    }
    
    @Override
    public Object get(String key) {
        log.debug("Jedis.get({})",key);
        Object value = null;
        Jedis jedis = pool.getResource();
        boolean borrowOrOprSuccess = true;
        try{
            value = SerializeUtil.deserialize(jedis.get(key.getBytes(Protocol.CHARSET)));
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.get("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return value;
    }
    
    @Override
    public void set(String key, int cacheTimeSeconds, Object o) {
        log.debug("Jedis.set({})", key);
        Jedis jedis = pool.getResource();
        boolean borrowOrOprSuccess = true;
        try{
            jedis.set(key.getBytes(Protocol.CHARSET), SerializeUtil.serialize(o));
            if(cacheTimeSeconds > 0)
                jedis.expire(key.getBytes(Protocol.CHARSET), cacheTimeSeconds);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.set("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
    }
    
    public String getString(String key) {
        log.debug("Jedis.get({})",key);
        String value = null;
        Jedis jedis = pool.getResource();
        boolean borrowOrOprSuccess = true;
        try{
            value = jedis.get(key);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.get("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return value;
    }

    @Override
    public Map<String, Object> getMulti(String... keys) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public void setString(String key, int cacheTimeSeconds, String o) {
        log.debug("Jedis.set({})", key);
        Jedis jedis = pool.getResource();
        boolean borrowOrOprSuccess = true;
        try{
            jedis.set(key, o);
            if(cacheTimeSeconds > 0)
                jedis.expire(key, cacheTimeSeconds);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.set("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
    }
    
    /**
     * 获取所有匹配的key值
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern){
        Set<String> keys = null;
        Jedis jedis = pool.getResource();
        boolean borrowOrOprSuccess = true;
        try{
            keys = jedis.keys(pattern);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.keys("+pattern+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return keys;
    }

    @Override
    public boolean delete(String key) {
        log.debug("Jedis.delete({})",key);
        Jedis jedis = pool.getResource();
        long ret = 0;
        boolean borrowOrOprSuccess = true;
        try{
            ret = jedis.del(key);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.delete("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        
        return (ret==0)?false:true;
    }

    @Override
    public void incr(String key, int factor, int startingValue) {
        // TODO Auto-generated method stub
    }

    @Override
    public void shutdown() {
        log.debug("Destroy JedisPool...");
        pool.destroy();
    }
    
    public boolean exists(String key){
        Jedis jedis = pool.getResource();
        boolean isExist = false;
        boolean borrowOrOprSuccess = true;
        try{
            isExist = jedis.exists(key);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.exists("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return isExist;
    }
    
    public void expire(String key, int seconds){
        Jedis jedis = pool.getResource();
        boolean borrowOrOprSuccess = true;
        try{
            jedis.expire(key, seconds);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.expire("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
    }
    
    /**
     * 对key进行重命名
     * @param key
     * @param newKey
     */
    public void rename(String key,String newKey){
        Jedis jedis = pool.getResource();
        boolean borrowOrOprSuccess = true;
        try{
            jedis.rename(key, newKey);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.expire("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
    }
    
    /**
     * 当key不存在时，设置值
     * @param key
     * @param value
     * @return true表示key不存在，设置成功;false,表示key已存在
     */
    public boolean setnx(String key,int cacheTimeSeconds,String value){
        Jedis jedis = pool.getResource();
        boolean borrowOrOprSuccess = true;
        boolean isSuc = false;
        try{
            Long ret = jedis.setnx(key, value);
            isSuc = ret.intValue()==1?true:false;
            if(isSuc && cacheTimeSeconds > 0)
                jedis.expire(key, cacheTimeSeconds);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.expire("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return isSuc;
    }
    
    /**
     * redis  getset()
     * @param key
     * @param value
     * @return
     */
    public String getSet(String key, String value){
    	Jedis jedis = pool.getResource();
    	boolean borrowOrOprSuccess = true;
    	String oldKey = null;
       	try {
			oldKey = jedis.getSet(key, value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			borrowOrOprSuccess = false;
			if (jedis != null)
				pool.returnBrokenResource(jedis);
			log.error("Jedis.getSet("+key+") error.",e);
		}finally{
			if (borrowOrOprSuccess)
				pool.returnResource(jedis);
		}
       	
       	return oldKey;
    }
    /////////////////////////////////////////////////////
    ////////// Redis Sorted Set数据类型实现List 缓存//////////
    /////////////////////////////////////////////////////
    /**
     * 向Sorted Set添加一个成员
     * @param key
     * @param score
     * @param value
     * @return
     */
    public boolean zAdd(String key,long score,String value){
        Jedis jedis = pool.getResource();
        boolean ret = true;
        boolean borrowOrOprSuccess = true;
        try{
            jedis.zadd(key, score, value);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.sadd("+key+") error.",e);
            ret = false;
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return ret;
    }
    
    /**
     * 获取Sorted Set中指定范围内的数据,按score从小到大的顺序
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zRange(String key,long start,long end){
        Jedis jedis = pool.getResource();
        Set<String> set = null;
        boolean borrowOrOprSuccess = true;
        try{
            set = jedis.zrange(key, start, end);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.sadd("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return set;
    }
    
    /**
     * 获取Sorted Set中指定范围内的数据,按score从大到小的顺序
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zRevRange(String key,long start,long end){
        Jedis jedis = pool.getResource();
        Set<String> set = null;
        boolean borrowOrOprSuccess = true;
        try{
            set = jedis.zrevrange(key, start, end);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.sadd("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return set;
    }
    
    /**
     * 获取Sorted Set中指定范围内的数据(带score数据),按score从大到小的顺序
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<Tuple> zRevRangeWithScore(String key,long start,long end){
        Jedis jedis = pool.getResource();
        Set<Tuple> set = null;
        boolean borrowOrOprSuccess = true;
        try{
            set = jedis.zrevrangeWithScores(key, start, end);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.sadd("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return set;
    }
    
    /**
     * 删除Soreted Set中的指定成员
     * @param key
     * @param value
     * @return
     */
    public boolean zRem(String key,String value){
        Jedis jedis = pool.getResource();
        boolean ret = true;
        boolean borrowOrOprSuccess = true;
        try{
            jedis.zrem(key, value);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.sadd("+key+") error.",e);
            ret = false;
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return ret;
    }
    
    /**
     * 获取Sorted Set中的成员个数
     * @param key
     * @return
     */
    public long zCount(String key){
        Jedis jedis = pool.getResource();
        long count = 0;
        boolean borrowOrOprSuccess = true;
        try{
            count = jedis.zcard(key);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.sadd("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return count;
    }
    
    /**
     * 判断成员是否存在于Sorted Set中
     * @param key
     * @param member
     * @return
     */
    public boolean zExist(String key,String member){
        Jedis jedis = pool.getResource();
        boolean borrowOrOprSuccess = true;
        Double score = null;
        try{
            score = jedis.zscore(key, member);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.sadd("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return score == null ? false : true;
    }
    
    /**
     * 获取Sorted Set中成员的score值
     * @param key
     * @param member
     * @return
     */
    public long zScore(String key,String member){
        Jedis jedis = pool.getResource();
        boolean borrowOrOprSuccess = true;
        Double score = null;
        try{
            score = jedis.zscore(key, member);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.sadd("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return score != null ? score.longValue() : -1;
    }
    
    /**
     * 获取指定score区间的元素,'-inf'和'+inf'表示最低和最高值
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<String> zRangeByScore(String key,String min,String max){
        Jedis jedis = pool.getResource();
        boolean borrowOrOprSuccess = true;
        Set<String> result = null;
        try{
            result = jedis.zrangeByScore(key, min, max);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.sadd("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return result;
    }
    
    /**
     * 获取指定score区间的元素,'-inf'和'+inf'表示最低和最高值,按score从大到小的顺序
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Set<String> zRevRangeByScore(String key,String min,String max){
        Jedis jedis = pool.getResource();
        boolean borrowOrOprSuccess = true;
        Set<String> result = null;
        try{
            result = jedis.zrevrangeByScore(key, max, min);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.sadd("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return result;
    }
    
    /**
     * 删除指定score区间的元素,'-inf'和'+inf'表示最低和最高值
     * @param key
     * @param min
     * @param max
     * @return
     */
    public long zRemRangeByScore(String key,String min,String max){
        Jedis jedis = pool.getResource();
        boolean borrowOrOprSuccess = true;
        Long count = null;
        try{
            count = jedis.zremrangeByScore(key, min, max);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.sadd("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return count != null ? count.longValue() : 0;
    }
    
    /////////////////////////////////////////////////////
    ////////// Redis Hash数据类型实现HashMap 缓存/////////////
    /////////////////////////////////////////////////////
    /**
     * 向Hash添加一个成员
     * @param key
     * @param field
     * @param value
     * @return
     */
    public boolean hSet(String key,String field,String value){
        Jedis jedis = pool.getResource();
        boolean ret = true;
        boolean borrowOrOprSuccess = true;
        try{
            jedis.hset(key, field, value);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.hset("+key+") error.",e);
            ret = false;
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return ret;
    }
    
    /**
     * 向Hash中添加一组成员
     * @param key
     * @param values
     * @return
     */
    public boolean hSet(String key,Map<String,String> values){
        Jedis jedis = pool.getResource();
        boolean ret = true;
        boolean borrowOrOprSuccess = true;
        try{
            Transaction t = jedis.multi();
            Iterator iter = values.entrySet().iterator();
            while(iter.hasNext()){
                Map.Entry<String, String> entry = (Map.Entry<String, String>)iter.next();
                String field = entry.getKey();
                String value = entry.getValue();
                t.hset(key, field, value);
            }
            t.exec();
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.hset("+key+") error.",e);
            ret = false;
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return ret;
    }
    
    /**
     * 从Hash中根据field获取关联的value值
     * @param key
     * @param field
     * @return
     */
    public String hGet(String key,String field){
        Jedis jedis = pool.getResource();
        String value = null;
        boolean borrowOrOprSuccess = true;
        try{
            value = jedis.hget(key, field);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.hget("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return value;
    }
    
    /**
     * 从Hash中删除指定的field
     * @param key
     * @param field
     * @return
     */
    public boolean hDelete(String key,String field){
        Jedis jedis = pool.getResource();
        boolean ret = true;
        boolean borrowOrOprSuccess = true;
        try{
            jedis.hdel(key, field);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.hdel("+key+") error.",e);
            ret = false;
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return ret;
    }
    
    /**
     * 从Hash中删除指定的fields
     * @param key
     * @param fields
     * @return
     */
    public boolean hDelete(String key,List<String> fields){
        if(fields.size() <= 0)
            return true;
        Jedis jedis = pool.getResource();
        boolean ret = true;
        boolean borrowOrOprSuccess = true;
        try{
            Transaction t = jedis.multi();
            for(String field:fields){
                t.hdel(key, field);
            }
            t.exec();
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.hdel("+key+") error.",e);
            ret = false;
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return ret;
    }
    
    /**
     * 获取Hash中成员的个数
     * @param key
     * @return
     */
    public long hLength(String key){
        Jedis jedis = pool.getResource();
        long length = 0l;
        boolean borrowOrOprSuccess = true;
        try{
            length = jedis.hlen(key);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.hlen("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return length;
    }
    
    /**
     * 判断Hash中指定的field是否存在
     * @param key
     * @param field
     * @return
     */
    public boolean hExists(String key,String field){
        Jedis jedis = pool.getResource();
        boolean isExist = false;
        boolean borrowOrOprSuccess = true;
        try{
            isExist = jedis.hexists(key, field);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.hexists("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return isExist;
    }
    
    /**
     * 返回hash表中所有的key和value值
     * @param key
     * @return
     */
    public Map<String,String> hGetAll(String key){
        Jedis jedis = pool.getResource();
        Map<String,String> map = null;
        boolean borrowOrOprSuccess = true;
        try{
            map = jedis.hgetAll(key);
            if(map.size() == 0)
                map = null;
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.hvals("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return map;
    }
    
    /**
     * 获取Hash中的所有key
     * @param key
     * @return
     */
    public Set<String> hKeys(String key){
        Jedis jedis = pool.getResource();
        Set<String> set = null;
        boolean borrowOrOprSuccess = true;
        try{
            set = jedis.hkeys(key);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.hvals("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return set;
    }
    
    /**
     * 获取Hash中的所有value
     * @param key
     * @return
     */
    public List<String> hValues(String key){
        Jedis jedis = pool.getResource();
        List<String> list = null;
        boolean borrowOrOprSuccess = true;
        try{
            list = jedis.hvals(key);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.hvals("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return list;
    }
    
    
    public boolean setBytes(byte[] key, byte[] value){
    	Jedis jedis = pool.getResource();
        String result = null;
        boolean borrowOrOprSuccess = true;
        try{
            result = jedis.set(key, value);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.get("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        if (result.equalsIgnoreCase("ok"))
        	return true;
        else return false;
    }
    
    public byte[] getBytes(byte[] key){
        Jedis jedis = pool.getResource();
        byte[] obj = null;
        boolean borrowOrOprSuccess = true;
        try{
            obj = jedis.get(key);
        }catch(Exception e){
            borrowOrOprSuccess = false;
            if(jedis != null)
                pool.returnBrokenResource(jedis);
            log.error("Jedis.get("+key+") error.",e);
        }finally{
            if(borrowOrOprSuccess)
                pool.returnResource(jedis);
        }
        return obj;
    }
}
