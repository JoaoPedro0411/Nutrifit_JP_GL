package br.edu.nutrifit.repository;

import br.edu.nutrifit.model.Agendamento;
import br.edu.nutrifit.model.Usuario;
import br.edu.nutrifit.model.enums.EStatusAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByUsuarioOrderByDataHoraAsc(Usuario usuario);
    Optional<Agendamento> findByIdAndUsuario(Long id, Usuario usuario);
    Optional<Agendamento> findTopByUsuarioAndStatusAndDataHoraAfterOrderByDataHoraAsc(Usuario usuario, EStatusAgendamento status, LocalDateTime dataHora);
    long countByUsuario(Usuario usuario);
}
