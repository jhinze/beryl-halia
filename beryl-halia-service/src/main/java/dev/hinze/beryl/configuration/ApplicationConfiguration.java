package dev.hinze.beryl.configuration;

import dev.hinze.beryl.interceptor.AccessInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(
        ApplicationProperties.class
)
public class ApplicationConfiguration {

    @Bean
    public WebMvcConfigurer webMvcConfiguration(AccessInterceptor accessInterceptor) {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(accessInterceptor);
                WebMvcConfigurer.super.addInterceptors(registry);
            }
        };
    }

}
