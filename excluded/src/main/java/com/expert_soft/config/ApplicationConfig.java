package com.expert_soft.config;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.ServletContext;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Locale;


@Configuration
@ComponentScan("com.expert_soft")
@EnableWebMvc
@ActiveProfiles("production")
@PropertySource(
        value={"classpath:config/application.properties"})
public class ApplicationConfig extends WebMvcConfigurerAdapter {

    private static final Logger LOGGER = LogManager.getLogger(ApplicationConfig.class);

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("WEB-INF/i18n/messages", "classpath:ValidationMessages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Bean
    public LocaleResolver localeResolver(){
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(new Locale("en"));
        resolver.setCookieName("myLocaleCookie");
        resolver.setCookieMaxAge(4800);
        return resolver;
    }

    private static final String DB_URL_PREFIX = "jdbc:hsqldb:file:";

    @Bean
    @Profile("production")
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer()
            throws FileNotFoundException {

        URL url = ResourceUtils.getURL("classpath:db_root/phonify_db");
        System.setProperty("db.url", DB_URL_PREFIX + url.getPath());
        LOGGER.debug("New property loaded: " + System.getProperty("db.url"));
        PropertySourcesPlaceholderConfigurer configurer =
                new PropertySourcesPlaceholderConfigurer();
        configurer.setLocalOverride(true);
        return configurer;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("locale");
        registry.addInterceptor(interceptor);
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }




}
