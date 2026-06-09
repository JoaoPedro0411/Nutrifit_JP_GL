package br.edu.nutrifit.config;

import br.edu.nutrifit.model.Usuario;
import br.edu.nutrifit.model.enums.EPerfilUsuario;
import br.edu.nutrifit.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    UsuarioRepository urepo;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Usuario u = urepo.findByEmail(username);
        if (u == null){
            throw new UsernameNotFoundException("E-mail não encontrado!!!");
        }

        if (u.getAtivo() == null || !u.getAtivo()){
            throw new UsernameNotFoundException("Usuário inativo!!!");
        }

        return User.builder().username(u.getEmail())
                .password(u.getSenha())
                .roles( (u.getPerfil() == EPerfilUsuario.ADMINISTRADOR )? "ADMIN" : "USUARIO" )
                .build();
    }

}