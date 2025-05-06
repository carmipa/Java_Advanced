package br.com.fiap.money_flow_api.config;

<<<<<<< HEAD
import java.util.List;

=======
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
>>>>>>> 7e8ce2e5c1b08f86bede4718ee28864db5b63ae7
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

<<<<<<< HEAD
=======
import java.util.List;

>>>>>>> 7e8ce2e5c1b08f86bede4718ee28864db5b63ae7
@Configuration
public class SecurityConfig {

    @Bean
<<<<<<< HEAD
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                    .requestMatchers("/transactions/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
=======
    SecurityFilterChain securityFilterChain(HttpSecurity http, DispatcherServletPath dispatcherServletPath) throws Exception { // filtros de segurança do spring!
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/transaction/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable()) // disabilita segurança csrf pois se trata de api e não seofre esse tipo de ataque
>>>>>>> 7e8ce2e5c1b08f86bede4718ee28864db5b63ae7
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
<<<<<<< HEAD
    UserDetailsService userDetailsService(){
        return new InMemoryUserDetailsManager(List.of(
            User
                .withUsername("joao")
                .password("$2a$12$26f425/HkvO46.HvCTzGye7TCzN0Xgnd6staV/nA8gBsEkJUPFpse")
                .roles("ADMIN")
                .build(), 
            User
                .withUsername("maria")
                .password("$2a$12$eaRCNjPrNrUb319lJiUnVe0ZmHOiBOcsTReSMlLncft.Utm4y5YYe")
                .roles("USER")
                .build()
=======
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
>>>>>>> 7e8ce2e5c1b08f86bede4718ee28864db5b63ae7
        ));
    }

    @Bean
<<<<<<< HEAD
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
=======
    PasswordEncoder passwordEncoder(){ // metodo para criptografar senha
        return new BCryptPasswordEncoder();
    }

>>>>>>> 7e8ce2e5c1b08f86bede4718ee28864db5b63ae7
}
