package com.expert_soft.config;

import com.expert_soft.model.order.Cart;
import com.expert_soft.test_util.TestConstants;
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
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
        {TestConstants.ROOT_CONTEXT, TestConstants.SERVLET_CONTEXT})
@WebAppConfiguration
@ActiveProfiles("test")
public class ApplicationConfigTest {

    @Autowired private WebApplicationContext ctx;
    @Autowired private Cart cart;

    @Test
    public void testSource(){
        ReloadableResourceBundleMessageSource messageSource =
                ctx.getBean("messageSource",
                ReloadableResourceBundleMessageSource.class);
        Set<String> basenameSet = messageSource.getBasenameSet();
        String message = ctx.getMessage("test.test", null, Locale.ROOT);
        assertEquals("Test", message);
    }

    @Test
    public void testCart(){
        Cart bean = ctx.getBean(Cart.class);
        assertNotNull(bean);
        assertNotNull(cart);
    }

}