package com.android001.common.os.others;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by xixionghui on 2017/1/6.
 */

public class RomBuildProperties {

    private volatile static RomBuildProperties instance;


    static {
        try {
            instance = new RomBuildProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RomBuildProperties getInstance() {
        try {
            if (null == instance) instance = new RomBuildProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instance;
    }


    //---

    private Properties properties;

    public RomBuildProperties() throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
    }

    public boolean containsKey(final Object key) {
        return properties.containsKey(key);
    }

    public boolean containsValue(final Object value) {
        return properties.containsValue(value);
    }

    public String getProperty(final String name) {
        return properties.getProperty(name);
    }

    public String getProperty(final String name, final String defaultValue) {
        return properties.getProperty(name, defaultValue);
    }

    public Set<Map.Entry<Object, Object>> entrySet() {
        return properties.entrySet();
    }

    public boolean isEmpty() {
        return properties.isEmpty();
    }

    public Enumeration keys() {
        return properties.keys();
    }

    public Set keySet() {
        return properties.keySet();
    }

    public int size() {
        return properties.size();
    }

    public Collection values() {
        return properties.values();
    }

}
