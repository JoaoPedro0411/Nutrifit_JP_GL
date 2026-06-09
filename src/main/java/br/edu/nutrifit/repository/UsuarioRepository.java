package br.edu.nutrifit.repository;

import br.edu.nutrifit.model.Usuario;
import br.edu.nutrifit.model.enums.EPerfilUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository
        extends JpaRepository<Usuario, Long> {

    public Usuario findByEmail(String email);

    public Usuario findByEmailAndSenhaAndAtivoTrue(String email, String senha);

    public List<Usuario> findByNomeLike(String nome);

    public List<Usuario> findByAtivoTrueOrderByNomeAsc();

    public boolean existsByEmail(String email);

    public long countByAtivoTrue();

    public List<Usuario> findByPerfil(EPerfilUsuario perfil);

    public long countByPerfil(EPerfilUsuario perfil);

    public List<Usuario> findByPerfilAndAtivoTrueOrderByNomeAsc(EPerfilUsuario perfil);
}