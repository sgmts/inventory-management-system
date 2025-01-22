package br.com.sgm.inventory_management_system.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtResponseDTO {

    private String token;
    private String type = "Bearer";

    public JwtResponseDTO(String token) {
        this.token = token;
    }
}