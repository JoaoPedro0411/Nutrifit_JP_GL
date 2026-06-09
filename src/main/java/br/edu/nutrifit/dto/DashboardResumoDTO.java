package br.edu.nutrifit.dto;

import br.edu.nutrifit.model.Agendamento;
import br.edu.nutrifit.model.Hidratacao;
import br.edu.nutrifit.model.enums.EObjetivoUsuario;

public class DashboardResumoDTO {
    public String nomeUsuario;
    public Double peso;
    public Double altura;
    public EObjetivoUsuario objetivoUsuario;
    public long totalRefeicoes;
    public long totalPlanosAlimentares;
    public long totalTreinos;
    public Agendamento proximoAgendamento;
    public Hidratacao hidratacaoHoje;
    public double percentualHidratacao;
    public int restanteHidratacaoMl;
}
