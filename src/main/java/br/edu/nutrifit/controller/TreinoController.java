package br.edu.nutrifit.controller;

import br.edu.nutrifit.model.Treino;
import br.edu.nutrifit.model.Usuario;
import br.edu.nutrifit.service.TreinoService;
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
public class TreinoController {

    private final TreinoService service;

    public TreinoController(TreinoService service) {
        this.service = service;
    }

    @GetMapping("/treinos")
    public String listar(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        List<Treino> lista = service.listarPorUsuario(usuario);
        model.addAttribute("treinos", lista);
        return "treinos/listar";
    }

    @GetMapping("/treinos/novo")
    public String novo(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        model.addAttribute("treino", new Treino());
        return "treinos/formulario";
    }

    @PostMapping("/treinos/salvar")
    public String salvar(@Valid Treino treino, BindingResult result, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        if (result.hasErrors()) return "treinos/formulario";
        service.salvar(treino, usuario);
        return "redirect:/treinos";
    }

    @GetMapping("/treinos/editar/{id}")
    public String editar(@PathVariable Long id, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        Treino t = service.buscarPorIdEUsuario(id, usuario);
        model.addAttribute("treino", t);
        return "treinos/formulario";
    }

    @GetMapping("/treinos/detalhes/{id}")
    public String detalhes(@PathVariable Long id, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        Treino t = service.buscarPorIdEUsuario(id, usuario);
        model.addAttribute("treino", t);
        return "treinos/detalhes";
    }

    @PostMapping("/treinos/excluir/{id}")
    public String excluir(@PathVariable Long id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        service.excluir(id, usuario);
        return "redirect:/treinos";
    }

}
