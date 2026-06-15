package br.edu.nutrifit.service;

import br.edu.nutrifit.model.Usuario;
import br.edu.nutrifit.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("usuarioAppService")
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    public Usuario cadastrar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado");
        }

        usuario.setAtivo(true);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Usuario usuario) {
        if (usuarioRepository.existsByEmailAndIdNot(usuario.getEmail(), usuario.getId())) {
            throw new RuntimeException("E-mail já cadastrado por outro usuário");
        }

        if (usuario.getSenha() != null && !usuario.getSenha().startsWith("$2")) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }

        return usuarioRepository.save(usuario);
    }

    public void desativar(Long id) {
        Usuario u = buscarPorId(id);
        u.setAtivo(false);
        usuarioRepository.save(u);
    }

}
