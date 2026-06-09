package br.edu.nutrifit.service;

import br.edu.nutrifit.dto.DashboardResumoDTO;
import br.edu.nutrifit.model.Usuario;
import br.edu.nutrifit.model.enums.EStatusAgendamento;
import br.edu.nutrifit.repository.AgendamentoRepository;
import br.edu.nutrifit.repository.HidratacaoRepository;
import br.edu.nutrifit.repository.PlanoAlimentarRepository;
import br.edu.nutrifit.repository.RefeicaoRepository;
import br.edu.nutrifit.repository.TreinoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DashboardService {

    private final RefeicaoRepository refeicaoRepository;
    private final PlanoAlimentarRepository planoRepository;
    private final TreinoRepository treinoRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final HidratacaoRepository hidratacaoRepository;

    public DashboardService(RefeicaoRepository refeicaoRepository, PlanoAlimentarRepository planoRepository, TreinoRepository treinoRepository, AgendamentoRepository agendamentoRepository, HidratacaoRepository hidratacaoRepository) {
        this.refeicaoRepository = refeicaoRepository;
        this.planoRepository = planoRepository;
        this.treinoRepository = treinoRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.hidratacaoRepository = hidratacaoRepository;
    }

    public DashboardResumoDTO montarResumo(Usuario usuario) {
        DashboardResumoDTO dto = new DashboardResumoDTO();
        dto.nomeUsuario = usuario.getNome();
        dto.peso = usuario.getPeso();
        dto.altura = usuario.getAltura();
        dto.objetivoUsuario = usuario.getObjetivo();
        dto.totalRefeicoes = refeicaoRepository.countByUsuario(usuario);
        dto.totalPlanosAlimentares = planoRepository.countByUsuario(usuario);
        dto.totalTreinos = treinoRepository.countByUsuario(usuario);
        dto.proximoAgendamento = agendamentoRepository.findTopByUsuarioAndStatusAndDataHoraAfterOrderByDataHoraAsc(usuario, EStatusAgendamento.AGENDADO, LocalDateTime.now()).orElse(null);
        dto.hidratacaoHoje = hidratacaoRepository.findByUsuarioAndData(usuario, LocalDate.now()).orElse(null);
        if (dto.hidratacaoHoje != null && dto.hidratacaoHoje.getMetaMl() != null && dto.hidratacaoHoje.getConsumidoMl() != null) {
            dto.percentualHidratacao = (double) dto.hidratacaoHoje.getConsumidoMl() / dto.hidratacaoHoje.getMetaMl() * 100.0;
            dto.restanteHidratacaoMl = Math.max(0, dto.hidratacaoHoje.getMetaMl() - dto.hidratacaoHoje.getConsumidoMl());
        }
        return dto;
    }
}
