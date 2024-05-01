package com.eva.evolution.config;

import io.r2dbc.spi.ConnectionFactory;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;

@Configuration
@EnableConfigurationProperties({ R2dbcProperties.class, FlywayProperties.class })
public class DbConfig {

  @Bean
  public ConnectionFactoryInitializer dbInitializer(ConnectionFactory connectionFactory) {
    ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
    initializer.setConnectionFactory(connectionFactory);
    return initializer;
  }

  @Bean(initMethod = "migrate")
  public Flyway flyway(FlywayProperties flywayProperties, R2dbcProperties r2dbcProperties) {
    return Flyway.configure()
        .dataSource(
            flywayProperties.getUrl(),
            r2dbcProperties.getUsername(),
            r2dbcProperties.getPassword()
        )
        .locations(flywayProperties.getLocations()
            .stream()
            .toArray(String[]::new))
        .baselineOnMigrate(true)
        .load();
  }
}
