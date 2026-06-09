package br.edu.nutrifit.model;

import br.edu.nutrifit.model.enums.EStatusHidratacao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id", "data"})
})
public class Hidratacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A data é obrigatória")
    @Column(nullable = false)
    private LocalDate data;

    @NotNull(message = "A meta de água é obrigatória")
    @Min(value = 500, message = "A meta deve ser de no mínimo 500 ml")
    @Max(value = 10000, message = "A meta deve ser de no máximo 10000 ml")
    @Column(nullable = false)
    private Integer metaMl;

    @NotNull(message = "O consumo de água é obrigatório")
    @Min(value = 0, message = "O consumo deve ser maior ou igual a zero")
    @Max(value = 15000, message = "O consumo deve ser de no máximo 15000 ml")
    @Column(nullable = false)
    private Integer consumidoMl;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario usuario;

    public double calcularPercentual() {
        if (metaMl == null || metaMl <= 0 || consumidoMl == null) {
            return 0.0;
        }

        return (consumidoMl * 100.0) / metaMl;
    }

    public int calcularRestanteMl() {
        if (metaMl == null) {
            return 0;
        }

        int consumoAtual = consumidoMl == null ? 0 : consumidoMl;
        return Math.max(metaMl - consumoAtual, 0);
    }

    public boolean isMetaBatida() {
        return calcularPercentual() >= 100.0;
    }

    public EStatusHidratacao getStatus() {
        double percentual = calcularPercentual();

        if (percentual >= 100.0) {
            return EStatusHidratacao.META_BATIDA;
        }

        if (percentual >= 50.0) {
            return EStatusHidratacao.QUASE_LA;
        }

        return EStatusHidratacao.ABAIXO_DA_META;
    }

    @PrePersist
    public void prePersist() {
        if (data == null) {
            data = LocalDate.now();
        }

        if (consumidoMl == null) {
            consumidoMl = 0;
        }
    }
}