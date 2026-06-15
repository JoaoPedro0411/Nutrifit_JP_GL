package br.edu.nutrifit.controller;

import br.edu.nutrifit.model.Treino;
import br.edu.nutrifit.model.Usuario;
import br.edu.nutrifit.repository.UsuarioRepository;
import br.edu.nutrifit.service.TreinoService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/treinos")
public class TreinoController {

    private final TreinoService treinoService;
    private final UsuarioRepository usuarioRepository;

    public TreinoController(TreinoService treinoService, UsuarioRepository usuarioRepository) {
        this.treinoService = treinoService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listar(Model model, Authentication authentication) {
        Usuario usuario = buscarUsuarioLogado(authentication);
        List<Treino> treinos = treinoService.listarPorUsuario(usuario);

        model.addAttribute("treinos", treinos);

        return "treinos/listar";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("treino", new Treino());
        return "treinos/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Treino treino,
                         BindingResult resultado,
                         Authentication authentication) {

        if (resultado.hasErrors()) {
            return "treinos/formulario";
        }

        Usuario usuario = buscarUsuarioLogado(authentication);
        treinoService.salvar(treino, usuario);

        return "redirect:/treinos";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhes(@PathVariable Long id, Model model, Authentication authentication) {
        Usuario usuario = buscarUsuarioLogado(authentication);
        Treino treino = treinoService.buscarPorIdEUsuario(id, usuario);

        model.addAttribute("treino", treino);

        return "treinos/detalhes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, Authentication authentication) {
        Usuario usuario = buscarUsuarioLogado(authentication);
        Treino treino = treinoService.buscarPorIdEUsuario(id, usuario);

        model.addAttribute("treino", treino);

        return "treinos/formulario";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, Authentication authentication) {
        Usuario usuario = buscarUsuarioLogado(authentication);
        treinoService.excluir(id, usuario);

        return "redirect:/treinos";
    }

    private Usuario buscarUsuarioLogado(Authentication authentication) {
        String email = authentication.getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário logado não encontrado."));
    }
}
