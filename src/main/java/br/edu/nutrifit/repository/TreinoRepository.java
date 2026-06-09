package br.edu.nutrifit.repository;

import br.edu.nutrifit.model.Treino;
import br.edu.nutrifit.model.enums.ENivelTreino;
import br.edu.nutrifit.model.enums.ETipoTreino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreinoRepository
        extends JpaRepository<Treino, Long> {

    public List<Treino> findByUsuarioId(Long usuarioId);

    public List<Treino> findByUsuarioIdOrderByNomeAsc(Long usuarioId);

    public List<Treino> findByUsuarioIdAndNomeLike(Long usuarioId, String nome);

    public List<Treino> findByUsuarioIdAndTipoTreino(Long usuarioId, ETipoTreino tipoTreino);

    public List<Treino> findByUsuarioIdAndNivel(Long usuarioId, ENivelTreino nivel);

    public long countByUsuarioId(Long usuarioId);
}