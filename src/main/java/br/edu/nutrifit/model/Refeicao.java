package br.edu.nutrifit.model;

import br.edu.nutrifit.model.enums.ETipoRefeicao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Refeicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da refeição é obrigatório")
    @Size(max = 80, message = "O nome da refeição deve ter no máximo 80 caracteres")
    @Column(length = 80, nullable = false)
    private String nome;

    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    @Column(length = 500)
    private String descricao;

    @NotNull(message = "O tipo da refeição é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private ETipoRefeicao tipoRefeicao;

    @NotNull(message = "As calorias são obrigatórias")
    @PositiveOrZero(message = "As calorias devem ser maior ou igual a zero")
    @Column(nullable = false)
    private Integer calorias;

    @NotNull(message = "As proteínas são obrigatórias")
    @PositiveOrZero(message = "As proteínas devem ser maior ou igual a zero")
    @Column(nullable = false)
    private Double proteinas;

    @NotNull(message = "Os carboidratos são obrigatórios")
    @PositiveOrZero(message = "Os carboidratos devem ser maior ou igual a zero")
    @Column(nullable = false)
    private Double carboidratos;

    @NotNull(message = "As gorduras são obrigatórias")
    @PositiveOrZero(message = "As gorduras devem ser maior ou igual a zero")
    @Column(nullable = false)
    private Double gorduras;

    private LocalTime horarioSugerido;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario usuario;
}