package br.com.fiap.money_flow_api.config;

import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, DispatcherServletPath dispatcherServletPath) throws Exception { // filtros de segurança do spring!
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/transaction/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable()) // disabilita segurança csrf pois se trata de api e não seofre esse tipo de ataque
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    UserDetailsService userDetailsService(){ // meus usuarios estarao em memoria
        return new InMemoryUserDetailsManager(List.of(
                User.withUsername("carminati")
                        .password("$2a$12$TAS9azRJc7vjlJlwkWWuK.kf4v8YvYi.vH9NK.KWZQpw2fn6rjkzK")
                        .roles("ADMIN") // papel que ocupa no sitema
                        .build(), // raw -> hash
                User.withUsername("maria")
                        .password("$2a$12$6cAlhpy5nVZEmFOtIXxp6uI7sIwe3ipMXRNMQJxe51Gq2P5tBHe/y")
                        .roles("USER")
                        .build() // senha foi gerada 2937 do hash
        ));
    }

    @Bean
    PasswordEncoder passwordEncoder(){ // metodo para criptografar senha
        return new BCryptPasswordEncoder();
    }

}
