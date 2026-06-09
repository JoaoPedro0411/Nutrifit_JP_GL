package br.edu.nutrifit.controller;

import br.edu.nutrifit.model.Agendamento;
import br.edu.nutrifit.model.Usuario;
import br.edu.nutrifit.service.AgendamentoService;
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
public class AgendamentoController {

    private final AgendamentoService service;

    public AgendamentoController(AgendamentoService service) {
        this.service = service;
    }

    @GetMapping("/agendamentos")
    public String listar(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        List<Agendamento> lista = service.listarPorUsuario(usuario);
        model.addAttribute("agendamentos", lista);
        return "agendamentos/listar";
    }

    @GetMapping("/agendamentos/novo")
    public String novo(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        model.addAttribute("agendamento", new Agendamento());
        return "agendamentos/formulario";
    }

    @PostMapping("/agendamentos/salvar")
    public String salvar(@Valid Agendamento agendamento, BindingResult result, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        if (result.hasErrors()) return "agendamentos/formulario";
        service.salvar(agendamento, usuario);
        return "redirect:/agendamentos";
    }

    @GetMapping("/agendamentos/editar/{id}")
    public String editar(@PathVariable Long id, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        Agendamento a = service.buscarPorIdEUsuario(id, usuario);
        model.addAttribute("agendamento", a);
        return "agendamentos/formulario";
    }

    @GetMapping("/agendamentos/detalhes/{id}")
    public String detalhes(@PathVariable Long id, HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        Agendamento a = service.buscarPorIdEUsuario(id, usuario);
        model.addAttribute("agendamento", a);
        return "agendamentos/detalhes";
    }

    @PostMapping("/agendamentos/cancelar/{id}")
    public String cancelar(@PathVariable Long id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        service.cancelar(id, usuario);
        return "redirect:/agendamentos";
    }

    @PostMapping("/agendamentos/concluir/{id}")
    public String concluir(@PathVariable Long id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        service.concluir(id, usuario);
        return "redirect:/agendamentos";
    }

    @PostMapping("/agendamentos/excluir/{id}")
    public String excluir(@PathVariable Long id, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) return "redirect:/login";
        service.excluir(id, usuario);
        return "redirect:/agendamentos";
    }

}
