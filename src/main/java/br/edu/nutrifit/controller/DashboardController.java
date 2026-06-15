package br.edu.nutrifit.controller;

import br.edu.nutrifit.dto.DashboardResumoDTO;
import br.edu.nutrifit.model.Usuario;
import br.edu.nutrifit.service.DashboardService;
import br.edu.nutrifit.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;
    private final UsuarioService usuarioService;

    public DashboardController(DashboardService dashboardService, UsuarioService usuarioService) {
        this.dashboardService = dashboardService;
        this.usuarioService = usuarioService;
    }

    @GetMapping({"/", "/dashboard"})
    public String mostrarDashboard(HttpSession session, Model model, Authentication authentication) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioLogado == null
                && authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {

            usuarioLogado = usuarioService.buscarPorEmail(authentication.getName());

            if (usuarioLogado != null) {
                session.setAttribute("usuarioLogado", usuarioLogado);
            }
        }

        model.addAttribute("usuarioLogado", usuarioLogado);

        if (usuarioLogado != null) {
            DashboardResumoDTO resumo = dashboardService.montarResumo(usuarioLogado);
            model.addAttribute("resumo", resumo);
        } else {
            model.addAttribute("mensagem", "Bem-vindo ao NutriFit. Faça login para acessar suas funcionalidades.");
        }

        return "dashboard";
    }

}
