package se.iths.friberg.webshop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> {
//                auth.requestMatchers("/").permitAll();
//                auth.requestMatchers("/products/**").permitAll();
//                auth.requestMatchers("/cart/**").hasAnyRole("USER","ADMIN");
//                auth.requestMatchers("/admin/**").hasRole("ADMIN");
//                auth.anyRequest().authenticated();
                auth.anyRequest().permitAll();

            }).build();
    }

}
