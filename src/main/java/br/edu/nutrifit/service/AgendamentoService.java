package br.edu.nutrifit.service;

import br.edu.nutrifit.model.Agendamento;
import br.edu.nutrifit.model.Usuario;
import br.edu.nutrifit.model.enums.EStatusAgendamento;
import br.edu.nutrifit.repository.AgendamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AgendamentoService {

    private final AgendamentoRepository repo;

    public AgendamentoService(AgendamentoRepository repo) {
        this.repo = repo;
    }

    public List<Agendamento> listarPorUsuario(Usuario usuario) {
        return repo.findByUsuarioOrderByDataHoraAsc(usuario);
    }

    public Agendamento buscarPorIdEUsuario(Long id, Usuario usuario) {
        return repo.findByIdAndUsuario(id, usuario).orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
    }

    public Agendamento salvar(Agendamento agendamento, Usuario usuario) {
        if (agendamento.getDataHora() != null && agendamento.getDataHora().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Data do agendamento não pode ser no passado");
        }
        agendamento.setUsuario(usuario);
        if (agendamento.getStatus() == null) agendamento.setStatus(EStatusAgendamento.AGENDADO);
        return repo.save(agendamento);
    }

    public void excluir(Long id, Usuario usuario) {
        Agendamento a = buscarPorIdEUsuario(id, usuario);
        repo.delete(a);
    }

    public void cancelar(Long id, Usuario usuario) {
        Agendamento a = buscarPorIdEUsuario(id, usuario);
        a.setStatus(EStatusAgendamento.CANCELADO);
        repo.save(a);
    }

    public void concluir(Long id, Usuario usuario) {
        Agendamento a = buscarPorIdEUsuario(id, usuario);
        a.setStatus(EStatusAgendamento.CONCLUIDO);
        repo.save(a);
    }
}
