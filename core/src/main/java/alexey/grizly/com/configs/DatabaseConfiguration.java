package alexey.grizly.com.configs;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


@Configuration
public class DatabaseConfiguration {
    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .username("postgres")
                .password("root")
                .url("jdbc:postgresql://localhost:5432/template")
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
