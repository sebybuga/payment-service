package com.payments.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.sql.DataSource;

@Configuration
@ConditionalOnBean(DataSource.class)
@EnableJpaAuditing
public class JpaConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}