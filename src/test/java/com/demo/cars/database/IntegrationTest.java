package com.demo.cars.database;

import org.flywaydb.core.Flyway;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class IntegrationTest {
    @Container
    public static final MySQLContainer<?> MySQL;

    static {
        MySQL = new MySQLContainer<>("mysql")
                .withDatabaseName("cars")
                .withUsername("user")
                .withPassword("1234");
        MySQL.start();

        runMigrations(MySQL);
    }

    private static void runMigrations(JdbcDatabaseContainer<?> c) {
        Flyway flyway = Flyway.configure()
                .dataSource(c.getJdbcUrl(), c.getUsername(), c.getPassword())
                .locations("filesystem:./flyway/sql")
                .load();

        flyway.migrate();
    }


    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MySQL::getJdbcUrl);
        registry.add("spring.datasource.username", MySQL::getUsername);
        registry.add("spring.datasource.password", MySQL::getPassword);
    }
}
