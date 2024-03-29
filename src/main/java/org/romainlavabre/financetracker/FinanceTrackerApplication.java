package org.romainlavabre.financetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class FinanceTrackerApplication {

    public static void main( final String[] args ) {
        SpringApplication.run( FinanceTrackerApplication.class, args );
    }


    @Bean
    public WebMvcConfigurer corsConfigurer(  ) {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings( final CorsRegistry registry ) {
                final String pattern = "/**";

                registry.addMapping( pattern )
                        .allowedMethods( "GET", "POST", "PUT", "DELETE" )
                        .allowedOrigins( "*" )
                        .exposedHeaders( "Location", "Authorization" );
            }
        };

    }
}
