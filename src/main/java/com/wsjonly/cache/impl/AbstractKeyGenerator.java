package com.wsjonly.cache.impl;

import com.wsjonly.cache.KeyGenerator;
import com.wsjonly.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public abstract class AbstractKeyGenerator implements KeyGenerator {
    
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public static final int DEFAULT_MAX_KEY_LENGTH = 250;

    private static final Pattern CLEAN_PATTERN = Pattern.compile("\\s");

    private int maxKeyLength = DEFAULT_MAX_KEY_LENGTH;

    public String createKey(String cacheName, Object key) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }

        String keyString = concatenateKey(cacheName, transformKeyObject(key));

        if (keyString.length() > maxKeyLength) {
            return truncateKey(keyString);
        }

        String finalKey = CLEAN_PATTERN.matcher(keyString).replaceAll("");
        return finalKey;
    }

    protected abstract String transformKeyObject(Object key);

    protected String truncateKey(String key) {

        String keyHashCode = StringUtils.md5Hex(key);

        log.warn("Encoded key [{}] to md5 hash [{}]. " + ".", key, keyHashCode);

        return keyHashCode;
    }

    public int getMaxKeyLength() {
        return maxKeyLength;
    }

    public void setMaxKeyLength(int maxKeyLength) {
        this.maxKeyLength = maxKeyLength;
    }

    protected String concatenateKey(String regionName, Object key) {
        return new StringBuilder()
                .append(regionName)
                .append("_")
                .append(String.valueOf(key)).toString();
    }
}
