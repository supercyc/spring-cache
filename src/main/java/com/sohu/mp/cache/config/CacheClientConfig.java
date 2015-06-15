package com.sohu.mp.cache.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.HashMap;

/**
 * User: shunlongli
 * Date: 2010-3-19
 * Time: 10:36:35
 */

public class CacheClientConfig {
    @XmlElement
    private String factory;

    @XmlJavaTypeAdapter(HashMapAdapter.class)
    private HashMap<String,String> properties;

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public void setProperties(HashMap<String,String> properties) {
        this.properties = properties;
    }

    @XmlTransient
    public HashMap<String,String> getProperties() {
        return properties;
    }

    @XmlTransient
    public String getFactory() {
        return factory;
    }
}
