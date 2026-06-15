package br.edu.nutrifit.service;

import br.edu.nutrifit.model.Usuario;
import br.edu.nutrifit.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario autenticar(String email, String senha) {
        return usuarioRepository.findByEmail(email)
                .filter(u -> Boolean.TRUE.equals(u.getAtivo()))
                .filter(u -> validarSenha(senha, u.getSenha()))
                .orElse(null);
    }

    public boolean usuarioPodeLogar(Usuario usuario) {
        return usuario != null && Boolean.TRUE.equals(usuario.getAtivo());
    }

    public boolean validarSenha(String senhaDigitada, String senhaSalva) {
        if (senhaDigitada == null || senhaSalva == null) return false;
        return passwordEncoder.matches(senhaDigitada, senhaSalva);
    }
}
