package br.edu.nutrifit.repository;

import br.edu.nutrifit.model.PlanoAlimentar;
import br.edu.nutrifit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlanoAlimentarRepository extends JpaRepository<PlanoAlimentar, Long> {

    List<PlanoAlimentar> findByUsuarioOrderByDataInicioDesc(Usuario usuario);

    Optional<PlanoAlimentar> findByIdAndUsuario(Long id, Usuario usuario);

    long countByUsuario(Usuario usuario);

    List<PlanoAlimentar> findByUsuarioId(Long usuarioId);

    List<PlanoAlimentar> findByUsuarioIdOrderByDataInicioDesc(Long usuarioId);

    List<PlanoAlimentar> findByUsuarioIdAndNomeLike(Long usuarioId, String nome);

    List<PlanoAlimentar> findByUsuarioIdAndDataInicioLessThanEqual(Long usuarioId, LocalDate data);

    long countByUsuarioId(Long usuarioId);
}
