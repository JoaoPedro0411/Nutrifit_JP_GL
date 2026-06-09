package br.edu.nutrifit.repository;

import br.edu.nutrifit.model.Hidratacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HidratacaoRepository
        extends JpaRepository<Hidratacao, Long> {

    public Hidratacao findByUsuarioIdAndData(Long usuarioId, LocalDate data);

    public List<Hidratacao> findByUsuarioIdOrderByDataDesc(Long usuarioId);

    public List<Hidratacao> findByUsuarioIdAndDataBetweenOrderByDataDesc(
            Long usuarioId, LocalDate dataInicial, LocalDate dataFinal);

    public boolean existsByUsuarioIdAndData(Long usuarioId, LocalDate data);

    public long countByUsuarioId(Long usuarioId);
}