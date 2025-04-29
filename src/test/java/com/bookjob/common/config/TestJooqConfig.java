package com.bookjob.common.config;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class TestJooqConfig {
    @Bean
    public org.jooq.Configuration testJooqConfiguration(DataSource dataSource) {
        DefaultConfiguration configuration = new DefaultConfiguration();
        configuration.set(dataSource);
        configuration.set(SQLDialect.MYSQL);

        configuration.settings()
                .withMapRecordComponentParameterNames(true);

        return configuration;
    }

    @Bean
    public DSLContext dslContext(org.jooq.Configuration configuration) {
        return new DefaultDSLContext(configuration);
    }
}
