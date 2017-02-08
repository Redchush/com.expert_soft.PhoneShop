package com.expert_soft.aspect;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.net.URL;

public class AppPropertyOverrider implements InitializingBean {

    private static final Logger LOGGER = Logger.getLogger(AppPropertyOverrider.class);
    private String dbUrlPrefix = "jdbc:hsqldb:file:";
    private String pathToSource;
    private String varToReplace;

    public void setDbUrlPrefix(String dbUrlPrefix) {
        this.dbUrlPrefix = dbUrlPrefix;
    }

    public void setPathToSource(String pathToSource) {
        this.pathToSource = pathToSource;
    }

    public void setVarToReplace(String varToReplace) {
        this.varToReplace = varToReplace;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        URL url = null;
        try {
            url = ResourceUtils.getURL(pathToSource);
            System.setProperty(varToReplace, dbUrlPrefix + url.getPath());
            LOGGER.debug("new url set: " + System.getProperty(varToReplace));
        } catch (FileNotFoundException e) {
            LOGGER.error("The file-marker of db place isn't exist. " +
                    "The place of database unpredictable");
        }
    }
}
