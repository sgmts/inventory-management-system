package br.com.sgm.inventory_management_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Formato de e-mail inválido.")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @JsonProperty("senha")
    private String password;
}