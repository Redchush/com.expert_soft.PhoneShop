package com.expert_soft.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ImportResource("classpath:web_test-bean.xml")
public class TestDataConfig {
}
