package br.edu.nutrifit.controller;

import br.edu.nutrifit.model.Usuario;
import br.edu.nutrifit.service.AuthService;
import br.edu.nutrifit.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final AuthService authService;
    private final UsuarioService usuarioService;

    public AuthController(AuthService authService, UsuarioService usuarioService) {
        this.authService = authService;
        this.usuarioService = usuarioService;
    }

    @GetMapping({"/login"})
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping({"/login"})
    public String entrar(String username, String password, HttpSession session, RedirectAttributes attrs) {
        Usuario u = authService.autenticar(username, password);
        if (u == null) {
            return "redirect:/login?fail";
        }
        session.setAttribute("usuarioLogado", u);
        return "redirect:/dashboard";
    }

    @GetMapping({"/cadastro"})
    public String mostrarCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping({"/cadastro"})
    public String cadastrar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attrs) {
        if (result.hasErrors()) {
            return "cadastro";
        }
        try {
            usuarioService.cadastrar(usuario);
            attrs.addFlashAttribute("mensagemSucesso", "Cadastro realizado com sucesso. Faça login.");
            return "redirect:/login";
        } catch (RuntimeException ex) {
            attrs.addFlashAttribute("mensagemErro", ex.getMessage());
            return "redirect:/cadastro";
        }
    }

    @GetMapping({"/logout"})
    public String sair(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }

}
