package br.edu.nutrifit.controller;

import br.edu.nutrifit.model.Hidratacao;
import br.edu.nutrifit.model.Usuario;
import br.edu.nutrifit.service.HidratacaoService;
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
public class HidratacaoController {

    private final HidratacaoService service;

    public HidratacaoController(HidratacaoService service) {
        this.service = service;
    }

    @GetMapping("/hidratacoes")
    public String listar(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        List<Hidratacao> lista = service.listarPorUsuario(usuario);
        model.addAttribute("hidratacoes", lista);
        return "hidratacoes/listar";
    }

    @GetMapping("/hidratacoes/novo")
    public String novo(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        model.addAttribute("hidratacao", new Hidratacao());
        return "hidratacoes/formulario";
    }

    @PostMapping("/hidratacoes/salvar")
    public String salvar(@Valid Hidratacao hidratacao, BindingResult result, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        if (result.hasErrors()) return "hidratacoes/formulario";
        service.salvar(hidratacao, usuario);
        return "redirect:/hidratacoes";
    }

    @GetMapping("/hidratacoes/editar/{id}")
    public String editar(@PathVariable Long id, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        Hidratacao h = service.buscarPorIdEUsuario(id, usuario);
        model.addAttribute("hidratacao", h);
        return "hidratacoes/formulario";
    }

    @GetMapping("/hidratacoes/detalhes/{id}")
    public String detalhes(@PathVariable Long id, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        Hidratacao h = service.buscarPorIdEUsuario(id, usuario);
        model.addAttribute("hidratacao", h);
        return "hidratacoes/detalhes";
    }

    @PostMapping("/hidratacoes/registrar-hoje")
    public String registrarHoje(HttpSession session, @RequestParam Integer consumidoMl, @RequestParam(required = false) Integer metaMl) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        service.registrarConsumoHoje(usuario, consumidoMl, metaMl);
        return "redirect:/hidratacoes";
    }

    @PostMapping("/hidratacoes/excluir/{id}")
    public String excluir(@PathVariable Long id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        service.excluir(id, usuario);
        return "redirect:/hidratacoes";
    }

}
