package com.ivr.config;

import java.io.File;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;

public class RestClientProperties {

    private static Configurations configs = new Configurations();
    private static Configuration config;

    /**
     * Constructor
     */
    private RestClientProperties() {
        try {
            config = configs.properties(new File("restclient.properties"));
        } catch (Exception e) {
        }
    }

    /**
     * get singleton instance
     * 
     * @return
     */
    public static RestClientProperties getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * get string property
     * 
     * @param key
     * @return
     */
    public String getString(String key) {
        return config.getString(key);
    }

    /**
     * get int property
     * 
     * @param key
     * @return
     */
    public int getInt(String key) {
        return config.getInt(key);
    }

    /**
     * get boolean property
     * 
     * @param key
     * @return
     */
    public boolean getBoolean(String key) {
        return config.getBoolean(key);
    }

    /**
     * Singleton holder class for lazy-load
     */
    private static class SingletonHolder {
        private static final RestClientProperties INSTANCE = new RestClientProperties();
    }
}