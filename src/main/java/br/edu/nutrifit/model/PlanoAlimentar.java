package br.edu.nutrifit.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PlanoAlimentar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do plano alimentar é obrigatório")
    @Size(max = 80, message = "O nome do plano alimentar deve ter no máximo 80 caracteres")
    @Column(length = 80, nullable = false)
    private String nome;

    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    @Column(length = 500)
    private String descricao;

    @NotNull(message = "A meta de calorias diárias é obrigatória")
    @Positive(message = "A meta de calorias diárias deve ser maior que zero")
    @Column(nullable = false)
    private Integer metaCaloriasDiarias;

    @NotNull(message = "A data de início é obrigatória")
    @Column(nullable = false)
    private LocalDate dataInicio;

    private LocalDate dataFim;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario usuario;

    @ManyToMany
    @JoinTable(
            name = "plano_alimentar_refeicao",
            joinColumns = @JoinColumn(name = "plano_alimentar_id"),
            inverseJoinColumns = @JoinColumn(name = "refeicao_id")
    )
    private List<Refeicao> refeicoes = new ArrayList<>();
}