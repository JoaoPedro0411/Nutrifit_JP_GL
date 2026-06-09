package br.edu.nutrifit.repository;

import br.edu.nutrifit.model.Usuario;
import br.edu.nutrifit.model.enums.EPerfilUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByEmailAndSenhaAndAtivoTrue(String email, String senha);

    List<Usuario> findByNomeLike(String nome);

    List<Usuario> findByAtivoTrueOrderByNomeAsc();

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    long countByAtivoTrue();

    List<Usuario> findByPerfil(EPerfilUsuario perfil);

    long countByPerfil(EPerfilUsuario perfil);

    List<Usuario> findByPerfilAndAtivoTrueOrderByNomeAsc(EPerfilUsuario perfil);

}