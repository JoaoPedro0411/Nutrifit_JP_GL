package br.edu.nutrifit.controller;

import br.edu.nutrifit.model.Refeicao;
import br.edu.nutrifit.model.Usuario;
import br.edu.nutrifit.service.RefeicaoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RefeicaoController {

    private final RefeicaoService service;

    public RefeicaoController(RefeicaoService service) {
        this.service = service;
    }

    @GetMapping("/refeicoes")
    public String listar(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        List<Refeicao> lista = service.listarPorUsuario(usuario);
        model.addAttribute("refeicoes", lista);
        return "refeicoes/listar";
    }

    @GetMapping("/refeicoes/novo")
    public String novo(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        model.addAttribute("refeicao", new Refeicao());
        return "refeicoes/formulario";
    }

    @PostMapping("/refeicoes/salvar")
    public String salvar(@Valid Refeicao refeicao, BindingResult result, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        if (result.hasErrors()) return "refeicoes/formulario";
        service.salvar(refeicao, usuario);
        return "redirect:/refeicoes";
    }

    @GetMapping("/refeicoes/editar/{id}")
    public String editar(@PathVariable Long id, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        Refeicao r = service.buscarPorIdEUsuario(id, usuario);
        model.addAttribute("refeicao", r);
        return "refeicoes/formulario";
    }

    @GetMapping("/refeicoes/detalhes/{id}")
    public String detalhes(@PathVariable Long id, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        Refeicao r = service.buscarPorIdEUsuario(id, usuario);
        model.addAttribute("refeicao", r);
        return "refeicoes/detalhes";
    }

    @PostMapping("/refeicoes/excluir/{id}")
    public String excluir(@PathVariable Long id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        service.excluir(id, usuario);
        return "redirect:/refeicoes";
    }

}
