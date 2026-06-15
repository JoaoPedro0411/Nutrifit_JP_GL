package br.edu.nutrifit.controller;

import br.edu.nutrifit.model.Refeicao;
import br.edu.nutrifit.model.Usuario;
import br.edu.nutrifit.repository.UsuarioRepository;
import br.edu.nutrifit.service.RefeicaoService;
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
@RequestMapping("/refeicoes")
public class RefeicaoController {

    private final RefeicaoService refeicaoService;
    private final UsuarioRepository usuarioRepository;

    public RefeicaoController(RefeicaoService refeicaoService, UsuarioRepository usuarioRepository) {
        this.refeicaoService = refeicaoService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listar(Model model, Authentication authentication) {
        Usuario usuario = buscarUsuarioLogado(authentication);
        List<Refeicao> refeicoes = refeicaoService.listarPorUsuario(usuario);

        model.addAttribute("refeicoes", refeicoes);
        model.addAttribute("totalCalorias", refeicaoService.calcularTotalCalorias(refeicoes));

        return "refeicoes/listar";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("refeicao", new Refeicao());
        return "refeicoes/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Refeicao refeicao,
                         BindingResult resultado,
                         Authentication authentication) {

        if (resultado.hasErrors()) {
            return "refeicoes/formulario";
        }

        Usuario usuario = buscarUsuarioLogado(authentication);
        refeicaoService.salvar(refeicao, usuario);

        return "redirect:/refeicoes";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhes(@PathVariable Long id, Model model, Authentication authentication) {
        Usuario usuario = buscarUsuarioLogado(authentication);
        Refeicao refeicao = refeicaoService.buscarPorIdEUsuario(id, usuario);

        model.addAttribute("refeicao", refeicao);

        return "refeicoes/detalhes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, Authentication authentication) {
        Usuario usuario = buscarUsuarioLogado(authentication);
        Refeicao refeicao = refeicaoService.buscarPorIdEUsuario(id, usuario);

        model.addAttribute("refeicao", refeicao);

        return "refeicoes/formulario";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, Authentication authentication) {
        Usuario usuario = buscarUsuarioLogado(authentication);
        refeicaoService.excluir(id, usuario);

        return "redirect:/refeicoes";
    }

    private Usuario buscarUsuarioLogado(Authentication authentication) {
        String email = authentication.getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário logado não encontrado."));
    }
}
