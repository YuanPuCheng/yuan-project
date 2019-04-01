package com.zihui.cwoa.system.shiro;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Properties;


@Configuration
public class ExceptionConf {

    @Bean
    public SimpleMappingExceptionResolver resolver() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();
        //捕获未授权的异常
        properties.setProperty("org.apache.shiro.authz.UnauthorizedException", "403");
        //捕获未登录访问权限的异常
        properties.setProperty("org.apache.shiro.authz.UnauthenticatedException", "400");
        properties.setProperty("404", "404");
        resolver.setExceptionMappings(properties);
        return resolver;
    }

}
