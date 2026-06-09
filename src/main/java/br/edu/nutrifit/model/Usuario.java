package br.edu.nutrifit.model;

import br.edu.nutrifit.model.enums.EObjetivoUsuario;
import br.edu.nutrifit.model.enums.EPerfilUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
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
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 80, message = "O nome deve ter no máximo 80 caracteres")
    @Column(length = 80, nullable = false)
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Informe um e-mail válido")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres")
    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, max = 100, message = "A senha deve ter entre 6 e 100 caracteres")
    @Column(length = 100, nullable = false)
    private String senha;

    @NotNull(message = "O peso é obrigatório")
    @DecimalMin(value = "20.0", message = "O peso deve ser maior ou igual a 20 kg")
    @DecimalMax(value = "400.0", message = "O peso deve ser menor ou igual a 400 kg")
    @Column(nullable = false)
    private Double peso;

    @NotNull(message = "A altura é obrigatória")
    @DecimalMin(value = "0.50", message = "A altura deve ser maior ou igual a 0,50 m")
    @DecimalMax(value = "2.50", message = "A altura deve ser menor ou igual a 2,50 m")
    @Column(nullable = false)
    private Double altura;

    @NotNull(message = "O objetivo é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private EObjetivoUsuario objetivo;

    @Column(nullable = true, updatable = true)
    private LocalDateTime dataCadastro;

    @Column(nullable = false)
    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private EPerfilUsuario perfil;




    @PrePersist
    public void prePersist() {
        if (ativo == null) {
            ativo = true;
        }

        if (dataCadastro == null) {
            dataCadastro = LocalDateTime.now();
        }

        if (perfil == null) {
            perfil = EPerfilUsuario.USUARIO;
        }
    }


}