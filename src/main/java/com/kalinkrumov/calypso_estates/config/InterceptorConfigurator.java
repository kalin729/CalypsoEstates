package com.kalinkrumov.calypso_estates.config;

import com.kalinkrumov.calypso_estates.interceptors.IPBlacklistInterceptor;
import com.kalinkrumov.calypso_estates.interceptors.LoggingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class InterceptorConfigurator implements WebMvcConfigurer {

    private final LocaleChangeInterceptor localeChangeInterceptor;

    public InterceptorConfigurator(LocaleChangeInterceptor localeChangeInterceptor) {
        this.localeChangeInterceptor = localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoggingInterceptor());
        registry.addInterceptor(new IPBlacklistInterceptor());
        registry.addInterceptor(localeChangeInterceptor);
//        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
