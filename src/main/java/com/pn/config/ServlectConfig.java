package com.pn.config;

import com.pn.filter.LoginCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServlectConfig {
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        LoginCheckFilter loginCheckFilter = new LoginCheckFilter();
        filterRegistrationBean.setFilter(loginCheckFilter);
        filterRegistrationBean.addUrlPatterns("/*");
        return  filterRegistrationBean;
    }
}
