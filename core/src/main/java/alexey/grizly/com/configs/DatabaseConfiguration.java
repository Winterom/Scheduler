package alexey.grizly.com.configs;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


@Configuration
public class DatabaseConfiguration {
    @Bean("primaryDb")
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .username("postgres")
                .password("London8793")
                .url("jdbc:postgresql://localhost:5432/template")
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
