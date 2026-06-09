package br.edu.nutrifit.repository;

import br.edu.nutrifit.model.PlanoAlimentar;
import br.edu.nutrifit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanoAlimentarRepository extends JpaRepository<PlanoAlimentar, Long> {
    List<PlanoAlimentar> findByUsuarioOrderByDataInicioDesc(Usuario usuario);
    Optional<PlanoAlimentar> findByIdAndUsuario(Long id, Usuario usuario);
    long countByUsuario(Usuario usuario);
}
