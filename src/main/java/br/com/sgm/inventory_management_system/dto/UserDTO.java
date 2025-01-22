package br.com.sgm.inventory_management_system.dto;

import br.com.sgm.inventory_management_system.model.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {

    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("role")
    private Role role = Role.USER; // Padrão: Usuário comum ;
}