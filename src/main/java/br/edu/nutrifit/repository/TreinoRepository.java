package br.edu.nutrifit.repository;

import br.edu.nutrifit.model.Treino;
import br.edu.nutrifit.model.Usuario;
import br.edu.nutrifit.model.enums.ENivelTreino;
import br.edu.nutrifit.model.enums.ETipoTreino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TreinoRepository extends JpaRepository<Treino, Long> {

    List<Treino> findByUsuarioOrderByNomeAsc(Usuario usuario);

    Optional<Treino> findByIdAndUsuario(Long id, Usuario usuario);

    long countByUsuario(Usuario usuario);

    List<Treino> findByUsuarioId(Long usuarioId);

    List<Treino> findByUsuarioIdOrderByNomeAsc(Long usuarioId);

    List<Treino> findByUsuarioIdAndNomeLike(Long usuarioId, String nome);

    List<Treino> findByUsuarioIdAndTipoTreino(Long usuarioId, ETipoTreino tipoTreino);

    List<Treino> findByUsuarioIdAndNivel(Long usuarioId, ENivelTreino nivel);

    long countByUsuarioId(Long usuarioId);
}
