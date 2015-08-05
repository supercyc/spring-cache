package com.wsjonly.cache.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;


@XmlRootElement(name = "CacheConfigs")
public class CacheConfigs {

    @XmlElement(name = "client")
    private CacheClientConfig cacheClientConfig;

    @XmlElement(name = "cache")
    private List<CacheConfig> cacheConfigs;

    @XmlTransient
    public List<CacheConfig> getCacheConfigs(){
        return this.cacheConfigs;
    }

    @XmlTransient
    public CacheClientConfig getCacheClientConfig(){
        return this.cacheClientConfig;
    }

    public void setCacheClientConfig(CacheClientConfig cacheClientConfig){
        this.cacheClientConfig = cacheClientConfig;
    }

    public void setCacheConfigs(List<CacheConfig> cacheConfigs) {
        this.cacheConfigs = cacheConfigs;
    }
}
