package br.com.sgm.inventory_management_system.dto.user;

import br.com.sgm.inventory_management_system.model.auth.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Slf4j
@Setter
@Getter
public class UserDTO {

    private String id;

    @NotBlank(message = "O nome é obrigatório.")
    @JsonProperty("nome")
    private String name;

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "Formato de e-mail inválido.")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @JsonProperty("senha")
    private String password;

    @NotBlank(message = "O cpf é obrigatório.")
    @CPF(message = "CPF Invalido. Formatos aceitos: 000.000.000-00 ou 00000000000")
    @JsonProperty("cpf")
    private String cpf;

    @Pattern(regexp = "^[1-9]{2}9[0-9]{8}$", message = "O telefone deve estar no formato DDD + número (ex: 31912345678)")
    @JsonProperty("telefone")
    private String cellPhone;

    @NotNull(message = "A data de nascimento é obrigatória.")
    @Past(message = "A data de nascimento deve estar no passado.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    // Define o formato aceito para entrada/saída JSON
    @JsonProperty("data-nascimento")
    private LocalDate birthDate;

    @Valid
    @JsonProperty("endereco")
    private UserAddressDto address;

    private String registrationDate;

    @JsonProperty("role")
    private Role role = Role.USER; // Padrão: Usuário comum ;

    public void removeCpfFormatting(String cpf) {
        log.info("Iniciando formatacao dos caracteres do CPF");

        if (cpf == null || cpf.isBlank()) {
            log.warn("O campo CPF está vazio ou nulo. Não é possível realizar a formatacao.");
            throw new IllegalArgumentException("O CPF não pode ser nulo ou vazio");
        }
        this.cpf = cpf.replaceAll("[^0-9]", "");
    }
}