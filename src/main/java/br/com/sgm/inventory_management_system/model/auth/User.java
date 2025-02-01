package br.com.sgm.inventory_management_system.model.auth;

import br.com.sgm.inventory_management_system.model.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres.")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Column(unique = true)
    private String email;

    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
    @Column(nullable = false)
    private String password;

    @Size(min = 11, max = 11, message = "CPF Invalido. Formatos aceitos: 000.000.000-00 ou 00000000000")
    @Column(nullable = false)
    private String cpf;

    private String telefone;

    private LocalDate dataNascimento;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_user_id", referencedColumnName = "id")
    private EnderecoUser endereco;

    @Column(nullable = false)
    private LocalDateTime dataCadastro;

    @Enumerated(EnumType.STRING)
    private Role role;
}