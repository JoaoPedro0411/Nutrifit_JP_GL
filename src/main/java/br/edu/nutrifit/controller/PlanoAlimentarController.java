package br.edu.nutrifit.controller;

import br.edu.nutrifit.model.PlanoAlimentar;
import br.edu.nutrifit.model.Refeicao;
import br.edu.nutrifit.model.Usuario;
import br.edu.nutrifit.service.PlanoAlimentarService;
import br.edu.nutrifit.service.RefeicaoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PlanoAlimentarController {

    private final PlanoAlimentarService service;
    private final RefeicaoService refeicaoService;

    public PlanoAlimentarController(PlanoAlimentarService service, RefeicaoService refeicaoService) {
        this.service = service;
        this.refeicaoService = refeicaoService;
    }

    @GetMapping("/planos")
    public String listar(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        model.addAttribute("planos", service.listarPorUsuario(usuario));
        return "planos/listar";
    }

    @GetMapping("/planos/novo")
    public String novo(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        model.addAttribute("plano", new PlanoAlimentar());
        model.addAttribute("refeicoesDisponiveis", refeicaoService.listarPorUsuario(usuario));
        return "planos/formulario";
    }

    @PostMapping("/planos/salvar")
    public String salvar(@Valid PlanoAlimentar plano, BindingResult result, @RequestParam(required = false) List<Long> refeicoesIds, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        if (result.hasErrors()) {
            model.addAttribute("refeicoesDisponiveis", refeicaoService.listarPorUsuario(usuario));
            return "planos/formulario";
        }
        service.salvar(plano, usuario, refeicoesIds);
        return "redirect:/planos";
    }

    @GetMapping("/planos/editar/{id}")
    public String editar(@PathVariable Long id, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        PlanoAlimentar p = service.buscarPorIdEUsuario(id, usuario);
        model.addAttribute("plano", p);
        model.addAttribute("refeicoesDisponiveis", refeicaoService.listarPorUsuario(usuario));
        return "planos/formulario";
    }

    @GetMapping("/planos/detalhes/{id}")
    public String detalhes(@PathVariable Long id, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        PlanoAlimentar p = service.buscarPorIdEUsuario(id, usuario);
        model.addAttribute("plano", p);
        return "planos/detalhes";
    }

    @PostMapping("/planos/excluir/{id}")
    public String excluir(@PathVariable Long id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        service.excluir(id, usuario);
        return "redirect:/planos";
    }

}
