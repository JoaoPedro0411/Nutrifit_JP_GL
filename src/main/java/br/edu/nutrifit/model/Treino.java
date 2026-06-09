package br.edu.nutrifit.model;

import br.edu.nutrifit.model.enums.ENivelTreino;
import br.edu.nutrifit.model.enums.ETipoTreino;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do treino é obrigatório")
    @Size(max = 80, message = "O nome do treino deve ter no máximo 80 caracteres")
    @Column(length = 80, nullable = false)
    private String nome;

    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    @Column(length = 500)
    private String descricao;

    @NotNull(message = "O tipo de treino é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private ETipoTreino tipoTreino;

    @NotNull(message = "O nível do treino é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private ENivelTreino nivel;

    @NotNull(message = "A duração do treino é obrigatória")
    @Positive(message = "A duração do treino deve ser maior que zero")
    @Column(nullable = false)
    private Integer duracaoMinutos;

    @NotNull(message = "A frequência semanal é obrigatória")
    @Min(value = 1, message = "A frequência semanal deve ser no mínimo 1")
    @Max(value = 7, message = "A frequência semanal deve ser no máximo 7")
    @Column(nullable = false)
    private Integer frequenciaSemanal;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario usuario;
}