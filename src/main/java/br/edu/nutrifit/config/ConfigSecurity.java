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

        http.authorizeHttpRequests( (auth) ->{
            auth.requestMatchers("/", "/dashboard",
                            "/css/**", "/js/**", "/images/**", "/login",
                            "/h2-console", "/h2-console/**").permitAll()
                    .requestMatchers("/refeicoes", "/refeicoes/**").hasRole("USUARIO")
                    .requestMatchers("/treinos", "/treinos/**").hasRole("USUARIO")
                    .requestMatchers("/planos", "/planos/**").hasRole("USUARIO")
                    .requestMatchers("/agendamentos", "/agendamentos/**").hasRole("USUARIO")
                    .requestMatchers("/hidratacao", "/hidratacao/**").hasRole("USUARIO")
                    .requestMatchers("/usuarios", "/usuarios/**").hasRole("ADMIN")
                    .anyRequest().authenticated();
        } ).formLogin( f ->{
            f.loginPage("/login")
                    .failureUrl("/login?fail")
                    .defaultSuccessUrl("/dashboard").permitAll();
        }).logout( l ->{
            l.logoutUrl("/logout")
                    .logoutSuccessUrl("/dashboard").permitAll();
        });

        return http.build();

    }

}