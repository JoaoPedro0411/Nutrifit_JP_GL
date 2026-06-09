package br.edu.nutrifit.model;

import br.edu.nutrifit.model.enums.EStatusAgendamento;
import br.edu.nutrifit.model.enums.ETipoAgendamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título do agendamento é obrigatório")
    @Size(max = 80, message = "O título deve ter no máximo 80 caracteres")
    @Column(length = 80, nullable = false)
    private String titulo;

    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    @Column(length = 500)
    private String descricao;

    @NotNull(message = "A data e hora são obrigatórias")
    @Column(nullable = false)
    private LocalDateTime dataHora;

    @NotNull(message = "O tipo de agendamento é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private ETipoAgendamento tipoAgendamento;

    @NotNull(message = "O status do agendamento é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private EStatusAgendamento status;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario usuario;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = EStatusAgendamento.AGENDADO;
        }
    }
}