package com.wsjonly.cache;


import java.util.Map;

public interface Cache {
    public static final String CACHE_NAME_OBJECT = "CMS_OBJ";
    public static final String CACHE_NAME_LIST = "CMS_LIST";
    public static final String CACHE_NAME_KV = "CMS_KV";
    public static final String CACHE_NAME_SYSTEM = "CMS_SYS";
    public static final String CACHE_NAME_ASSISIT = "CMS_ASSISIT";
    
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
     * 从缓存中删除对象
     * @param key
     */
    public void remove(Object key);

    /**
     * 查询缓存中是否存在对象
     * @param key
     */
    public boolean exist(Object key);
}
