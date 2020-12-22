package com.asep1001.finalprojectudacoding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class jpaAduditConfig {
    @Bean
    public AuditorAware<String> auditorProvider(){
        return new AuditorAwareImpl();
    }
}
