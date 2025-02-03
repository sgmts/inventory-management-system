package br.com.sgm.inventory_management_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress {

    @NotBlank(message = "O CEP é obrigatório.")
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "O CEP deve estar no formato 12345-678.")
    @JsonProperty("cep")
    private String cep;

    @JsonProperty("rua")
    private String street;

    @JsonProperty("numero")
    private String number;

    @JsonProperty("complemento")
    private String addressComplement;

    @JsonProperty("bairro")
    private String neighborhood; // Bairro

    @JsonProperty("cidade")
    private String city;

    @JsonProperty("uf")
    private String uf;

    @JsonProperty("regiao")
    private String region;

    public void removeCepFormatting(String cep) {
        log.info("Iniciando a remoção da formatação do CEP: {}", cep);

        if (cep == null || cep.isBlank()) {
            log.warn("O campo CEP está vazio ou nulo. Não é possível remover a formatação.");
            throw new IllegalArgumentException("O CEP não pode ser nulo ou vazio");
        }
        this.cep = cep.replaceAll("[^0-9]", "");
    }
}
