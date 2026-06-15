package br.edu.nutrifit.repository;

import br.edu.nutrifit.model.Hidratacao;
import br.edu.nutrifit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HidratacaoRepository extends JpaRepository<Hidratacao, Long> {

    List<Hidratacao> findByUsuarioOrderByDataDesc(Usuario usuario);

    Optional<Hidratacao> findByIdAndUsuario(Long id, Usuario usuario);

    Optional<Hidratacao> findByUsuarioAndData(Usuario usuario, LocalDate data);

    boolean existsByUsuarioAndData(Usuario usuario, LocalDate data);

    boolean existsByUsuarioAndDataAndIdNot(Usuario usuario, LocalDate data, Long id);

    Hidratacao findByUsuarioIdAndData(Long usuarioId, LocalDate data);

    List<Hidratacao> findByUsuarioIdOrderByDataDesc(Long usuarioId);

    List<Hidratacao> findByUsuarioIdAndDataBetweenOrderByDataDesc(Long usuarioId, LocalDate dataInicial, LocalDate dataFinal);

    boolean existsByUsuarioIdAndData(Long usuarioId, LocalDate data);

    long countByUsuarioId(Long usuarioId);
}
