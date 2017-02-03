package com.expert_soft.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import java.util.Locale;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class ApplicationConfigTest {

    @Autowired
    WebApplicationContext ctx;

    @Test
    public void testSource(){
        ReloadableResourceBundleMessageSource messageSource =
                ctx.getBean("messageSource",
                ReloadableResourceBundleMessageSource.class);
        Set<String> basenameSet = messageSource.getBasenameSet();
        String message = ctx.getMessage("test.test", null, Locale.ROOT);
        assertEquals("Test", message);
    }

}