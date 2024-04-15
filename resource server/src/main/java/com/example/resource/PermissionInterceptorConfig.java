package com.example.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class PermissionInterceptorConfig implements WebMvcConfigurer {
	@Bean
	public  PermissionAutorizationInterceptor permissionautorizationInterceptor() {
	    return new PermissionAutorizationInterceptor();
	}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionautorizationInterceptor()) // Intercepta todas las peticiones
        .excludePathPatterns("documentation/swagger-ui.html", "/api/health-check"); // Excluye las excepciones
}
    
}