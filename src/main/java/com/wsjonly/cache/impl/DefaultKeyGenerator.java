package com.wsjonly.cache.impl;

import com.wsjonly.cache.KeyGenerator;


public class DefaultKeyGenerator extends AbstractKeyGenerator{
    @Override
    protected String transformKeyObject(Object key) {
        return key.toString();
    }
}
