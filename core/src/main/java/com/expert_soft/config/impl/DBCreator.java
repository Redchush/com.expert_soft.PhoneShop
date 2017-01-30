package com.expert_soft.config.impl;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class DBCreator implements InitializingBean {

    private static final Logger LOGGER = Logger.getLogger(DBCreator.class);

    @Autowired
    private ApplicationContext ctx;


    public DBCreator() throws IOException {}

    public void copy() throws IOException {
//        Resource resource = ctx.getResource("classpath:db/schema.sql");
//        LOGGER.debug(resource);
//        File file = resource.getFile();
//        Path dbFolder = file.toPath().getParent();
//        Files.copy(dbFolder, dbFolder.getParent());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        copy();
    }
}
