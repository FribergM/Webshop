package se.iths.friberg.webshop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import se.iths.friberg.webshop.services.JpaUserDetailsService;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{
    
    private final JpaUserDetailsService jpaUserDetailsService;
    
    public SecurityConfig(JpaUserDetailsService jpaUserDetailsService){
        this.jpaUserDetailsService = jpaUserDetailsService;
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/","/index.html", "/css/**").permitAll();
                    auth.requestMatchers("/categories","/category/**").permitAll();
                    auth.requestMatchers("/products/**","/product/**").permitAll();
                    auth.requestMatchers("/register","/search").permitAll();
                    auth.requestMatchers("/cart/**").hasAnyRole("USER", "ADMIN");
                    auth.requestMatchers("/admin/**").hasRole("ADMIN");
                    
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .expiredUrl("/logout-handler"))
                .formLogin(login -> login
                        .successForwardUrl("/login-handler")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/logout-handler")
                        .permitAll()
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                )
                .userDetailsService(jpaUserDetailsService)
                .httpBasic(Customizer.withDefaults())
                .build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}
