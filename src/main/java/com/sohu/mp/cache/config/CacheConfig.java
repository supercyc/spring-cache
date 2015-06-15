package com.sohu.mp.cache.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/**
 * User: shunlongli
 * Date: 2010-3-19
 * Time: 10:30:07
 */
public class CacheConfig {
    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "cacheTimeSeconds")
    private String cacheTimeSeconds;

    @XmlAttribute(name = "keyGeneratorClass")
    private String keyGeneratorClass;

    @XmlTransient
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public String getCacheTimeSeconds() {
        return cacheTimeSeconds;
    }

    public void setCacheTimeSeconds(String cacheTimeSeconds) {
        this.cacheTimeSeconds = cacheTimeSeconds;
    }

    @XmlTransient
    public String getKeyGeneratorClass() {
        return keyGeneratorClass;
    }

    public void setKeyGeneratorClass(String keyGeneratorClass) {
        this.keyGeneratorClass = keyGeneratorClass;
    }
}
