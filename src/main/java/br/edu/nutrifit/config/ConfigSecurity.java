package br.edu.nutrifit.config;

import br.edu.nutrifit.repository.UsuarioRepository;
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

    private final UsuarioRepository usuarioRepository;

    public ConfigSecurity(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public PasswordEncoder getCriptografia() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain webSecurity(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((auth) -> {
            auth.requestMatchers(
                            "/",
                            "/dashboard",
                            "/css/**",
                            "/js/**",
                            "/images/**",
                            "/login",
                            "/cadastro",
                            "/h2-console",
                            "/h2-console/**"
                    ).permitAll()
                    .requestMatchers("/usuarios", "/usuarios/**").hasRole("ADMIN")
                    .requestMatchers("/refeicoes", "/refeicoes/**").hasRole("USUARIO")
                    .requestMatchers("/treinos", "/treinos/**").hasRole("USUARIO")
                    .requestMatchers("/planos", "/planos/**").hasRole("USUARIO")
                    .requestMatchers("/agendamentos", "/agendamentos/**").hasRole("USUARIO")
                    .requestMatchers("/hidratacoes", "/hidratacoes/**").hasRole("USUARIO")
                    .anyRequest().authenticated();
        }).formLogin((form) -> {
            form.loginPage("/login")
                    .loginProcessingUrl("/login")
                    .failureUrl("/login?fail")
                    .successHandler((request, response, authentication) -> {
                        usuarioRepository.findByEmail(authentication.getName())
                                .ifPresent(usuario -> request.getSession().setAttribute("usuarioLogado", usuario));

                        response.sendRedirect(request.getContextPath() + "/dashboard");
                    })
                    .permitAll();
        }).logout((logout) -> {
            logout.logoutUrl("/logout")
                    .logoutSuccessHandler((request, response, authentication) -> {
                        var session = request.getSession(false);

                        if (session != null) {
                            session.invalidate();
                        }

                        response.sendRedirect(request.getContextPath() + "/login?logout");
                    })
                    .permitAll();
        });

        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }
}
