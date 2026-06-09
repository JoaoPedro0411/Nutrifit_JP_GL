package br.edu.nutrifit.repository;

import br.edu.nutrifit.model.Treino;
import br.edu.nutrifit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TreinoRepository extends JpaRepository<Treino, Long> {
    List<Treino> findByUsuarioOrderByNomeAsc(Usuario usuario);
    Optional<Treino> findByIdAndUsuario(Long id, Usuario usuario);
    long countByUsuario(Usuario usuario);
}
