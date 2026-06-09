package br.edu.nutrifit.controller;

import br.edu.nutrifit.dto.DashboardResumoDTO;
import br.edu.nutrifit.model.Usuario;
import br.edu.nutrifit.service.DashboardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping({"/", "/dashboard"})
    public String mostrarDashboard(HttpSession session, Model model) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
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
