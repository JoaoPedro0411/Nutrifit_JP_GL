package br.edu.nutrifit.repository;

import br.edu.nutrifit.model.Refeicao;
import br.edu.nutrifit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefeicaoRepository extends JpaRepository<Refeicao, Long> {
    List<Refeicao> findByUsuarioOrderByNomeAsc(Usuario usuario);
    Optional<Refeicao> findByIdAndUsuario(Long id, Usuario usuario);
    long countByUsuario(Usuario usuario);
}
