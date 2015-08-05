package com.wsjonly.cache.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

public class LocalHashMap<K,V> {
    @XmlElement(name = "property")
    private List<LocalHashMapEntry> properties = new ArrayList<LocalHashMapEntry>();

    @XmlTransient
    public List<LocalHashMapEntry> getProperties(){
        return this.properties;
    }
}
