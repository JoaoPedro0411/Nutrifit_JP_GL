package br.edu.nutrifit.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/","/dashboard"})
    public String index(Model model, Authentication auth) {

        if (auth == null) {
            model.addAttribute("mensagem", "Acompanhe refeições, treinos, agendamentos e hidratação no NutriFit.");
        } else if (auth.getAuthorities()
                .contains( new SimpleGrantedAuthority("ROLE_USUARIO"))){
            model.addAttribute("mensagem", "Bem-vindo ao seu painel de acompanhamento.");
        } else if (auth.getAuthorities()
                .contains( new SimpleGrantedAuthority("ROLE_ADMIN"))){
            model.addAttribute("mensagem", "Bem-vindo ao painel administrativo do NutriFit.");
        }

        return "dashboard";
    }

    @GetMapping("/refeicoes")
    public String refeicoes() {
        return "em-desenvolvimento";
    }

    @GetMapping("/treinos")
    public String treinos() {
        return "em-desenvolvimento";
    }

    @GetMapping("/planos")
    public String planos() {
        return "em-desenvolvimento";
    }

    @GetMapping("/agendamentos")
    public String agendamentos() {
        return "em-desenvolvimento";
    }

    @GetMapping("/hidratacao")
    public String hidratacao() {
        return "em-desenvolvimento";
    }

    @GetMapping("/usuarios")
    public String usuarios() {
        return "em-desenvolvimento";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

}