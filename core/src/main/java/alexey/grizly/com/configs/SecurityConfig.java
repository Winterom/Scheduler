package alexey.grizly.com.configs;

import alexey.grizly.com.users.service.UserAccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
    private final JwtRequestFilter jwtRequestFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;

    @Autowired
    public SecurityConfig(JwtRequestFilter jwtRequestFilter, ExceptionHandlerFilter exceptionHandlerFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.exceptionHandlerFilter = exceptionHandlerFilter;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .cors().disable()
                .authorizeHttpRequests()
                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().disable()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(exceptionHandlerFilter, ExceptionTranslationFilter.class);
        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserAccountDetailsService userDetailService)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
