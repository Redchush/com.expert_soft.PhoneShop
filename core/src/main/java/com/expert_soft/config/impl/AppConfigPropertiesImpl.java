package com.expert_soft.config.impl;


import com.expert_soft.config.AppConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppConfigPropertiesImpl implements AppConfigProperties{

    @Autowired
    private Environment env;

    @Override
    public String getProperty(String key) {
        return env.getProperty(key);
    }

    @Override
    public int getIntProperty(String key) {
        String property = env.getProperty(key);
        return Integer.parseInt(property);
    }

}
