package br.com.sgm.inventory_management_system.dto.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestResponseDto {

    private Long id;

    @NotBlank(message = "O nome da categoria é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome do produto deve ter entre 3 e 100 caracteres.")
    @JsonProperty("nome")
    private String name;

    @Size(max = 500, message = "A categoria pode ter no máximo 500 caracteres.")
    @JsonProperty("descricao")
    private String description;
}
