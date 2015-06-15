package com.sohu.mp.cache.impl;

import com.sohu.mp.cache.KeyGenerator;

/**
 * User: Administrator
 * Date: 2010-3-20
 * Time: 19:15:02
 */
public class DefaultKeyGenerator extends AbstractKeyGenerator{
    @Override
    protected String transformKeyObject(Object key) {
        return key.toString();
    }
}
