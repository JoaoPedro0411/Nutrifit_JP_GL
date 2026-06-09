package br.edu.nutrifit.repository;

import br.edu.nutrifit.model.PlanoAlimentar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PlanoAlimentarRepository
        extends JpaRepository<PlanoAlimentar, Long> {

    public List<PlanoAlimentar> findByUsuarioId(Long usuarioId);

    public List<PlanoAlimentar> findByUsuarioIdOrderByDataInicioDesc(Long usuarioId);

    public List<PlanoAlimentar> findByUsuarioIdAndNomeLike(Long usuarioId, String nome);

    public List<PlanoAlimentar> findByUsuarioIdAndDataInicioLessThanEqual(Long usuarioId, LocalDate data);

    public long countByUsuarioId(Long usuarioId);
}