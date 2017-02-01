package com.expert_soft.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@Import(ApplicationConfig.class)
@ImportResource("classpath:context/core_root-context.xml")
public class ServletConfig {
}
