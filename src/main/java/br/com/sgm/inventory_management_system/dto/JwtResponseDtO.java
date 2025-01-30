package br.com.sgm.inventory_management_system.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtResponseDtO {

    private String token;
    private String type = "Bearer";

    public JwtResponseDtO(String token) {
        this.token = token;
    }
}