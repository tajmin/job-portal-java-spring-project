package com.selvesperer.knoeien.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.DeserializationFeature;
/*
 * @author Mithun
 * configuration for MVCConfiguration
 */
@ComponentScan(basePackages="com.selvesperer.knoeien.web.controllers.rest")
@Configuration
@EnableWebMvc
public class ApplicationMVCConfiguration extends WebMvcConfigurerAdapter {   
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	Map<String, String> resources = new HashMap<String, String>();    	
    	resources.put("/*.html", "/");
        resources.put("/*.xhtml", "/");
        resources.put("/favicon.ico", "/");
        resources.put("/js/**", "/js/");
        resources.put("/css/**", "/css/");
        resources.put("/resources/**", "/resources/");
        resources.put("/robots.txt", "/");
        resources.put("/sitemap.xml", "/");
        for (String resourceMatcher : resources.keySet()) {
            registry.addResourceHandler(resourceMatcher).addResourceLocations(resources.get(resourceMatcher));
        }
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customJackson2Converter());
    }

    @Bean
    MappingJackson2HttpMessageConverter customJackson2Converter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.getObjectMapper().configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        converter.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        converter.getObjectMapper().configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        return converter;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(268435456);
        return multipartResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
          //Placeholder for incase we need this.
//        registry.addInterceptor(new SomeInterceptor());
    }

}
