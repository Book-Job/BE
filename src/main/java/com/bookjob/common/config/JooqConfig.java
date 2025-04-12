package com.bookjob.common.config;

import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JooqConfig {
    @Bean
    public org.jooq.Configuration configuration(DataSource dataSource) {
        DefaultConfiguration configuration = new DefaultConfiguration();
        configuration.set(dataSource);
        configuration.set(SQLDialect.MYSQL);

        configuration.settings()
                .withMapRecordComponentParameterNames(true);

        return configuration;
    }
}