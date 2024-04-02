package com.film.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Sora
 * @date 2021/1/22 17:16
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("*")
                .maxAge(3600)
                .allowedHeaders("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/poster/**")
//                .addResourceLocations("file:///D:/Study/FilmSystem/film/poster/");
                .addResourceLocations("file:///D:/Company/Hammer/Old/film-system-master/film/poster/");
        registry.addResourceHandler("/img/**")
//                .addResourceLocations("file:///D:/Study/FilmSystem/film/img/");
                .addResourceLocations("file:///D:/Company/Hammer/Old/film-system-master/film/img/");
    }
}
