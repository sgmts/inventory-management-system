package br.com.sgm.inventory_management_system.dto;

import br.com.sgm.inventory_management_system.model.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

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
    @JsonProperty("cpf")
    private String cpf;

    @JsonProperty("telefone")
    private String telefone;

    @NotBlank(message = "A Data de Nascimento é obrigatória.")
    @JsonProperty("data-nascimento")
    private String dataNascimento;

    @JsonProperty("endereco")
    private EnderecoUserDto endereco;

    private String dataCadastro;

    @JsonProperty("role")
    private Role role = Role.USER; // Padrão: Usuário comum ;
}