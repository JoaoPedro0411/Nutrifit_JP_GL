package br.edu.nutrifit.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
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


    

}