package br.edu.nutrifit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ConfigSecurity {

    @Bean
    public PasswordEncoder getCriptografia(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain webSecurity(HttpSecurity http) throws Exception {
        // For MVP simplicity we allow all requests and use manual session control.
        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        // Disable CSRF for the MVP so the login form can POST without a token
        http.csrf(csrf -> csrf.disable());
        // Disable Spring Security's default form login so our AuthController handles /login
        http.formLogin(form -> form.disable());
        return http.build();

    }

}